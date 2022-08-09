import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;

public class Calculator2 extends JFrame implements ActionListener{
  private String[] keys = {"1/x","⬅","(",")","/","sqr","7","8","9","*","x^2","4","5","6","-","x^3","1","2","3","+","c","π","0",".","="};
  private JButton buttons[] = new JButton[keys.length];
  private JTextField TextArea = new JTextField("0.0");
  private String s = "";//用来记录显示屏上显示的内容
  int sqr_flag = 0,x2_flag = 0,x3_flag = 0,cal_flag = 0,flag = 0,equal_flag = 0,x_div_flag = 0;
  int count = 0;
  public final static double PI = 3.1415926;

  public Calculator2() {
    super("计算器");
    this.setLayout(null);
    //设置菜单栏
    JMenuBar mBar = new JMenuBar();
    setJMenuBar(mBar);
    JMenu m = new JMenu("编辑（E）");
    JMenuItem[] mI = {new JMenuItem("复制（C）"),new JMenuItem("粘贴（V）")};
    mBar.add(m);
    m.add(mI[0]);
    m.add(mI[1]);
    m.setMnemonic('E');
    mI[0].setMnemonic('C');
    mI[0].setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
    mI[1].setMnemonic('V');
    mI[1].setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
    //复制功能的实现
    mI[0].addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Clipboard clipboard =  Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(TextArea.getText());
        clipboard.setContents(selection, null);
      }
    });
    //粘贴功能的实现
    mI[1].addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println( e.getSource());
        String s = TextArea.getText();
        TextArea.setText(s + s);
      }
    });

    //设置显示框
    TextArea.setBounds(15, 10, 400, 140);
    TextArea.setHorizontalAlignment(JTextField.RIGHT);
    TextArea.setEditable(false);
    TextArea.setFont(new Font("Dialog",1,20));
    this.add(TextArea);

    //设置按钮
    int x = 15,y = 160;
    for (int i = 0; i < keys.length; i++){
      buttons[i] = new JButton();
      buttons[i].setFont(new Font("Dialog",1,20));
      buttons[i].setText(keys[i]);
      buttons[i].setBounds(x, y, 75, 45);
      if(x<320){
        x+=80;
      }
      else{
        x = 15;
        y += 50;
      }
      this.add(buttons[i]);
    }
    for (int i = 0; i < keys.length; i++){
      buttons[i].addActionListener(this);
    }
  }
  //设置计算器上按钮的监听器
  public void actionPerformed(ActionEvent e){
    String label = e.getActionCommand();
    if (label == "c"|| label == "=")
    {
      x_div_flag = 0;
      x3_flag = 0;
      x2_flag = 0;
      sqr_flag = 0;
      flag = 0;
      if(label == "=")
      {
        equal_flag = 1;
        String s[] = calculate(this.s);
        String result = Result(s);
        this.s = result;
        TextArea.setText(this.s);
      }
      else
      {
        equal_flag = 0;
        this.s = "";
        TextArea.setText("0.0");
      }
    }
    else if (label == "sqr")
    {
      if(this.s == "" || Double.valueOf(calculate2 (this.s)) < 0 ) {
        TextArea.setText("ERROR");
      }else {
        sqr_flag = 1;
        String n = calculate2 (this.s);
        TextArea.setText(n);
        this.s = n;
      }
    }
    else if(label == "x^2")
    {
      if(this.s == ""){
        TextArea.setText("ERROR");
      }else {
        x2_flag = 1;
        String n = calculate3(this.s);
        TextArea.setText(n);
        this.s = n;
      }
    }else if(label=="x^3") {
      if(this.s == "") {
        TextArea.setText("ERROR");
      }else {
        x3_flag = 1;
        String n = calculate4(this.s);
        TextArea.setText(n);
        this.s = n;
      }
    }else if(label == "1/x") {
      if(this.s == "" ) {
        TextArea.setText("ERROR");
      }else {
        x_div_flag = 1;
        String n = calculate5 (this.s);
        TextArea.setText(n);
        this.s = n;
      }
    }
    //实现π功能，将其在文本框中输出π
    else if(label=="π"){
      this.s = this.s + "π";
      TextArea.setText(this.s);
    }else if(label == "⬅") {
      if(s.length() <= 1) {
        this.s = "";
        TextArea.setText("0.0");
      }else if(this.s == "ERROR"){
        this.s = "";
        TextArea.setText("0.0");
      }else {
        this.s = this.s.substring(0,s.length()-1);
        TextArea.setText(this.s);
      }
    }else if(label == "+" || label == "-"|| label == "*"||label == "/") {
      if(this.s == "" && ( label == "*"||label == "/" )) {
        TextArea.setText("ERROR");
      }else {
        if(sqr_flag == 1 || equal_flag == 1 || x2_flag == 1 || x3_flag == 1 || x_div_flag == 1) {
          flag = 1;
        }
        this.s = this.s + label;
        TextArea.setText(this.s);
      }

    }else if(label == "." ) {
      if(equal_flag == 1 && flag == 0) {
        this.s = label;
        TextArea.setText(label);
        equal_flag = 0;
      }else if(sqr_flag == 1 && flag == 0) {
        this.s = label;
        TextArea.setText(label);
        sqr_flag = 0;
      }else if(x2_flag == 1 && flag == 0) {
        this.s = label;
        TextArea.setText(label);
        x2_flag = 0;
      }else if(x3_flag == 1 && flag == 0){
        this.s = label;
        TextArea.setText(label);
        x3_flag = 0;
      }else if(x_div_flag == 1 && flag == 0){
        this.s = label;
        TextArea.setText(label);
        x_div_flag = 0;
      }
      else {
        this.s = this.s + label;
        TextArea.setText(this.s);
      }
    }else{
      if(equal_flag == 1 && flag == 0) {
        this.s = label;
        TextArea.setText(label);
        equal_flag = 0;
      }else if(sqr_flag == 1 && flag == 0) {
        this.s = label;
        TextArea.setText(label);
        sqr_flag = 0;
      }else if(x2_flag == 1 && flag == 0){
        this.s = label;
        TextArea.setText(label);
        x2_flag = 0;
      }else if(x3_flag == 1 && flag == 0) {
        this.s = label;
        TextArea.setText(label);
        x3_flag = 0;
      }else if(x_div_flag == 1 && flag == 0) {
        this.s = label;
        TextArea.setText(label);
        x_div_flag = 0;
      }else{
        this.s = this.s + label;
        TextArea.setText(this.s);
      }
    }
  }
  //运算法则，将中缀表达式转换为后缀表达式
  private String[] calculate(String str) {
    try{
      //先对字符串进行操作，让合法的字符串加上一些运算符
      for(int i = 0; i < str.length(); i++) {
        //对大数的处理
        if(str.charAt(i) == 'E') {
          String s1 = "";
          for(int j = i + 1;"0123456789".indexOf(str.charAt(j)) >=0; j++) {
            s1 = s1 + str.charAt(j);
          }
          int ten_count = Integer.parseInt(s1);
          double num = 1;
          int k ;
          for( k = 0; k < ten_count; k++) {
            num *=10;
          }
          String s3 = String.valueOf(num);
          BigDecimal final_num = new BigDecimal(s3);
          String s2 = "";
          BigDecimal a = new BigDecimal(str.substring(0,i));
          a = a.multiply(final_num);
          //System.out.println(a);
          s2= a.stripTrailingZeros().toPlainString();
          String[] after = str.split("E");
          after[0] = s2;
          //System.out.println(after[0]);
          str = after[0] +after[1];
          //System.out.println(str);
        }
        //如果有PI的话，判断使用的是否合法，若是2Π则自动加上*号，如果是Π2则报错
        if(str.charAt(i) == 'π') {
          if(i + 1 !=str.length()) {
            if("+-*/".indexOf(str.charAt(i+1)) < 0 ) {
              String[] ss = new String[1];
              ss[0] = "ERROR";
              return ss;
            }
          }
          if(i-1 > -1) {
            if("0123456789".indexOf(str.charAt(i-1)) >= 0) {
              StringBuffer strbuffer = new StringBuffer(str);
              strbuffer.insert(i,"*");
              str = new String(strbuffer);
              //System.out.println(str);
            }
          }
        }
      }
      String s= "" ;
      char a[]=new char[100];
      String res[]=new String[100];
      int top=-1,j=0;
      for (int i=0;i<str.length();i++)
      {
        //为数字
        if ("0123456789.".indexOf(str.charAt(i))>=0)
        {
          s="";
          for (;i<str.length() && "0123456789.".indexOf(str.charAt(i))>=0;i++)
          {
            s=s+str.charAt(i);
          }
          i--;
          res[j] = s;
          j++;
        }else if("π".indexOf(str.charAt(i)) >= 0) {
          res[j] = PI +"";
          j++;
        }
        else if ("(".indexOf(str.charAt(i))>=0)
        {
          top++;
          a[top]=str.charAt(i);
        }
        else if (")".indexOf(str.charAt(i))>=0)
        {
          for (;;)
          {
            if (a[top]!='('){
              res[j]=a[top]+"";
              j++;
              top--;
            }
            else{
              top--;
              break;
            }
          }
        }
        else if ("*%/".indexOf(str.charAt(i))>=0){
          if (top==-1)
          {
            top++;
            a[top]=str.charAt(i);
          }
          else
          {
            if ("*%/".indexOf(a[top])>=0)
            {
              res[j]=a[top]+"";
              j++;
              a[top]=str.charAt(i);
            }
            else if ("(".indexOf(a[top])>=0)
            {
              top++;
              a[top]=str.charAt(i);
            }
            else if ("+-".indexOf(a[top])>=0)
            {
              top++;
              a[top]=str.charAt(i);
            }
          }
        }
        else if ("+-".indexOf(str.charAt(i))>=0)
        {
          if (top==-1)
          {
            top++;
            a[top]=str.charAt(i);
          }
          else
          {
            if ("%*/".indexOf(a[top])>=0)
            {
              res[j]=a[top]+"";
              j++;
              a[top]=str.charAt(i);
            }
            else if ("(".indexOf(a[top])>=0)
            {
              top++;
              a[top]=str.charAt(i);
            }
            else if ("+-".indexOf(a[top])>=0)
            {
              res[j]=a[top]+"";
              j++;
              a[top]=str.charAt(i);
            }
          }
        }
      }
      for (;top!=-1;)
      {
        res[j]=a[top]+"";
        j++;
        top--;
      }
      return res;
    }catch(Exception e) {
      String[] ss = new String[1];
      ss[0] = "ERROR";
      return ss;
    }
  }
  //进行开方运算
  public String calculate2(String str){
    int index = 0;
    try{
      double a = Double.parseDouble(str);
    }catch (NumberFormatException e) {
      index = 1;
      TextArea.setText("ERROR");
    }
    if(index != 1) {
      String result = "";
      double a = Double.parseDouble(str),b=0;
      if(a < 0) {
        result= String.valueOf(a);
        return result;
      }else {
        b = Math.sqrt(a);
        result=String.valueOf(b);
      }
      return result;
    }else {
      return "-1";
    }
  }
  //进行平方运算
  public String calculate3(String str){
    int index = 0;
    try{
      double a = Double.parseDouble(str);
    }catch (NumberFormatException e) {
      index = 1;
    }
    if(index != 1) {
      String result = "";
      double a = Double.parseDouble(str),b=0;
      b = Math.pow(a, 2);
      result= String.valueOf(b);
      return result;
    }else {
      return "ERROR";
    }
  }
  //进行三次方运算
  public String calculate4(String str){
    int index = 0;
    try{
      double a = Double.parseDouble(str);
    }catch (NumberFormatException e) {
      index = 1;
    }
    if(index != 1) {
      String result = "";
      double a = Double.parseDouble(str),b=0;
      b = Math.pow(a, 3);
      result= String.valueOf(b);
      return result;
    }else {
      return "ERROR";
    }
  }
  //进行负一次方运算方运算
  public String calculate5(String str){
    int index = 0;
    try{
      double a = Double.parseDouble(str);
      //可能会有问题
      if(a == 0) {
        index = 1;
      }
    }catch (NumberFormatException e) {
      index = 1;
    }
    if(index != 1 ) {
      String result = "";
      double a = Double.parseDouble(str),b=0;
      b = 1/a;
      result= String.valueOf(b);
      return result;
    }else {
      return "ERROR";
    }
  }
  //计算后缀表达式
  public String Result(String str[])
  {
    try{
      String Result[]=new String[100];
      Double num_stack[] = new Double[100];
      String sign_stack[] = new String[100];
      int num_stack_top = -1;
      int sign_stack_top = -1;
      int Top=-1;
      int Result_Length = 0;
      for (int i=0;str[i]!=null || sign_stack_top > -1;i++){

        //System.out.println(str[i]);
        if(sign_stack_top > -1 && Result_Length == 1 && cal_flag == 1) {
          if(str[i]!=null) {
            i--;
          }
          cal_flag = 0;
          double x,y,n;
          //压入结果数组
          if(sign_stack[sign_stack_top] == "*") {
            x = Double.parseDouble(Result[Top]);
            Top--;
            Result_Length--;
            y = num_stack[num_stack_top];
            //System.out.println(y);
            n = y * x;
            Top++;
            Result_Length++;
            Result[Top] = String.valueOf(n);
            //System.out.println(n);
            sign_stack_top--;
            num_stack_top--;
          }else if(sign_stack[sign_stack_top] == "/") {
            x = Double.parseDouble(Result[Top]);
            if(x == 0) {
              return "ERROR";
            }else {
              Top--;
              Result_Length--;
              y = num_stack[num_stack_top];
              n = y / x;
              Top++;
              Result_Length++;
              Result[Top] = String.valueOf(n);
              sign_stack_top--;
              num_stack_top--;
            }
          }
        }else {
          if(str[i] == "ERROR") {
              return "ERROR";
          }
          //System.out.println("else中");
          //为数字
          if ( "+-*/".indexOf(str[i]) < 0 ) {
            //System.out.println("222");
            Top++;
            Result[Top] = str[i];
            Result_Length++;
          }
          if ("+-*/".indexOf(str[i])>=0)
          {
            double x,y,n;
            if(Result_Length == 1 && "-".indexOf(str[i]) >= 0) {
              //暂存数字栈中存在数字时将cal_flag设为1
              if(num_stack_top > -1 ) {
                //System.out.println("cal_flag = 1 ");
                cal_flag = 1;
              }
              x = Double.parseDouble(Result[Top]);
              y = 0;
              n = y-x;
              //System.out.println(n);
              Result[Top]=String.valueOf(n);
            }else if(Result_Length == 1 && "+".indexOf(str[i]) >= 0){
              if(num_stack_top > -1 ) {
                cal_flag = 1;
              }
              x = Double.parseDouble(Result[Top]);
              y = 0;
              n = y+x;
              Result[Top]=String.valueOf(n);
            }else if(Result_Length == 1 && "*".indexOf(str[i]) >= 0){

              x = Double.parseDouble(Result[Top]);//2
              Top--;
              Result_Length--;
              num_stack_top++;
              num_stack[num_stack_top] = x;
              sign_stack_top++;
              sign_stack[sign_stack_top] = "*";
            }else if(Result_Length == 1 && "/".indexOf(str[i]) >= 0) {
              x = Double.parseDouble(Result[Top]);
              Top--;
              Result_Length--;
              num_stack_top++;
              num_stack[num_stack_top] = x;
              sign_stack_top++;
              sign_stack[sign_stack_top] = "/";
            }
            else {
              x=Double.parseDouble(Result[Top]);
              Top--;
              Result_Length--;
              y=Double.parseDouble(Result[Top]);
              Top--;
              Result_Length--;
              if ("-".indexOf(str[i])>=0)
              {
                n=y-x;
                Top++;
                Result[Top]=String.valueOf(n);
                Result_Length++;
              }
              if ("+".indexOf(str[i])>=0)
              {
                n=y+x;
                Top++;
                Result[Top]=String.valueOf(n);
                Result_Length++;
              }
              if ("*".indexOf(str[i])>=0)
              {
                n=y*x;
                Top++;
                Result[Top]=String.valueOf(n);
                Result_Length++;
              }
              if ("/".indexOf(str[i])>=0)
              {
                if (x==0)
                {
                  String s="ERROR";
                  return s;
                }
                else
                {
                  n=y/x;
                  Top++;
                  Result[Top]=String.valueOf(n);
                  Result_Length++;
                }
              }
            }
          }
        }
      }
      return Result[Top];
    }catch (Exception e) {
      return "ERROR";
    }
  }
  public static void main(String[] args) {
    Calculator2 app = new Calculator2();
    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    app.setSize(450,500);
    app.setResizable(false);
    app.setVisible(true);
  }
}
