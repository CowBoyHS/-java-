import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;



import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.sql.*;
import java.time.LocalDate;

public class login extends JFrame{
  private static int count=0;
  private static JButton bt1;//登陆按钮
  private static JButton bt2;//忘记密码按钮
  private static JLabel jl_1;//登录的版面
  private static JFrame jf_1;//登陆的框架
  private static JTextField jtext1;//用户名
  private static JPasswordField jtext2;//密码
  private static JLabel jl_admin;
  private static JLabel jl_password;
  private static JLabel reminder;
  private static JRadioButton Jrb_stu;//学生
  private static JRadioButton Jrb_admin;//管理员
  public login() throws MalformedURLException {//初始化登陆界面
    Font font =new Font("黑体", Font.PLAIN, 16);//设置字体
    jf_1=new JFrame("登陆界面");
//        jf_1.setSize(450, 400);
    //给登陆界面添加背景图片

    jl_1=new JLabel();

    jl_admin=new JLabel("ID");
    jl_admin.setBounds(20, 86, 60, 50);
    jl_admin.setFont(font);

    jl_password=new JLabel("密码");
    jl_password.setBounds(20, 133, 60, 50);
    jl_password.setFont(font);

    bt1=new JButton("登陆");         //更改成loginButton
    bt1.setBounds(90, 250, 100, 25);
    bt1.setFont(font);

    bt2=new JButton("退出");
    bt2.setBounds(250, 250, 100, 25);
    bt2.setFont(font);

    //加入文本框
    jtext1=new JTextField();
    jtext1.setBounds(150, 100, 250, 20);
    jtext1.setFont(font);

    jtext2=new JPasswordField();//密码输入框
    jtext2.setBounds(150, 150, 250, 20);
    jtext2.setFont(font);

    reminder =new JLabel();//错误信息提示
    reminder.setBounds(150,180,250, 30);

    //加入选择框
    Jrb_admin=new JRadioButton("管理员");
    Jrb_admin.setBounds(150,35,75,20);

    Jrb_stu=new JRadioButton("学生",true);
    Jrb_stu.setBounds(250,35,70,20);

    ButtonGroup group=new ButtonGroup();
    group.add(Jrb_stu);
    group.add(Jrb_admin);



    //
    jl_1.add(jtext1);
    jl_1.add(jtext2);
    jl_1.add(reminder);
    jl_1.add(jl_admin);
    jl_1.add(jl_password);
    jl_1.add(bt1);
    jl_1.add(bt2);

    jf_1.add(jl_1);

    jl_1.add(Jrb_admin);
    jl_1.add(Jrb_stu);

    jf_1.setVisible(true);
    jf_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jf_1.setLocation(300,400);
    jf_1.setBounds(500,250,450,400);
    ActionListener bt1_ls =new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                String admin = jtext1.getText();
                String password = String.valueOf(jtext2.getPassword());
                String pswd = String.valueOf(password); // 将char数组转化为string类型
                reminder.setText(" ");
                try {
                  if (Jrb_stu.isSelected() && loginCheck_stu(admin, password)) {
                    //
                    System.out.println("学生登录！");

                    new stuPage(admin).init();//密码正确跳转到学生用户界面

                  } else {
                    reminder.setText("输入有误");
                  }
                } catch (SQLException e) {
                  throw new RuntimeException(e);
                }
                try {
                  if (Jrb_admin.isSelected()
                          && loginCheck_admin(admin, password)) {

                    System.out.println("管理员登录！");

                  } else {
                    reminder.setText("输入有误");
                  }
                } catch (SQLException e) {
                  throw new RuntimeException(e);
                }
              }
            };
    bt1.addActionListener(bt1_ls);
    //退出事件的处理
    ActionListener bt2_ls=new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        System.exit(0);//终止当前程序
      }
    };
    bt2.addActionListener(bt2_ls);
  }
  //-------------------入口
  public static void main(String[] args) throws MalformedURLException {
    //初始化登陆界面
    login lg = new login();//登陆界面————>功能界面
    //登陆点击事件
  }

  public boolean loginCheck_stu(String ReaderId,String pswd) throws SQLException {  //检查学生用户密码是否正确
    String url="jdbc:mysql://localhost:3306/booksmanager?useSSL = false & &serverTimezone = GMT";
    String username="root";
    String password="Wjjmt919112";
    Connection con= DriverManager.getConnection(url,username,password);
    String sql="select * from reader where readerID=? and password=? ";
    PreparedStatement p=con.prepareStatement(sql);
    p.setString(1,ReaderId);
    p.setString(2,pswd);

    ResultSet r=p.executeQuery();
    if(r.next()){
      return true;
    }
    return false;
  }

  public boolean loginCheck_admin(String adminId,String pswd) throws SQLException {//检查管理员密码是否正确
    String url="jdbc:mysql://localhost:3306/booksmanager?useSSL = false & &serverTimezone = GMT";
    String username="root";
    String password="Wjjmt919112";
    Connection con= DriverManager.getConnection(url,username,password);
    String sql="select * from admin where adminId=? and pwsd=? ";
    PreparedStatement p=con.prepareStatement(sql);
    p.setString(1,adminId);
    p.setString(2,pswd);

    ResultSet r=p.executeQuery();
    if(r.next()){
      return true;
    }
    return false;
  }
}



class stuPage extends JFrame {
  private  JButton SearchBook;//查看书籍模式
  private  JButton BorrowBook;//借书模式
  private  JButton ReturnBook;//还书模式
  //查看书籍模块
  private  JLabel ISBN;// 书号
  private JTextField ISBNT;

  private  JLabel Title; //书名
  private JTextField TitleT;

  private  JTextField AuthorT;//作者
  private  JLabel Author;

  private JTextField PublisherT; //出版社
  private  JLabel Publisher;

  private JTextField EditionNumberT;  //版本
  private  JLabel EditionNumber;

  private JTextField PublicationDateT;   //出版日期
  private  JLabel PublicationDate;

  private JTextField TypeT ;//类型
  private  JLabel Type;

  private TableModel tableModelBook;//模式
  private JTable jtabelBook;//图书表格

  private JButton btn_submit;//提交
  //
  private JLabel orderBy;
  private JTextField orderByT;
  JPanel jp1;//功能1


  //借书模块
  private JLabel bookName;//书名
  private JTextField bookNameT;//书名输入框

  private JLabel bookId;//bookId
  private JTextField bookIdT;//bookID输入框

  private JButton btn_borrow;//借阅

  private JTextArea messameT;//显示区域
  JPanel jp2;//功能2


  //还书模块
  private JPanel jp3;//功能3
  private JPanel Jp_return;//归还界面
  private JTextArea Jtremind;//提示

  private JLabel Iddd;//学号
  private JTextField IdT;//学号

  private JLabel ISBNlbt;//
  private JTextField ISBNTT;
  private JButton btn_return;//归还

  //容器
  JPanel box1;//功能按键的容器
  JPanel box2;//功能实现区域容器

  //默认路径
  private String url="jdbc:mysql://localhost:3306/booksmanager?useSSL = false & &serverTimezone = GMT";
  private String username="root";
  private String password="Wjjmt919112";
  private String sql="select * from books";
  //
  String Id;//读者Id;
  public stuPage(String Id){
    this.Id=Id;
  }

  public void init() throws SQLException {
    SearchBook=new JButton("查找书籍");
    BorrowBook=new JButton("借书");
    ReturnBook=new JButton("还书");

    //功能选择区域
    box1=new JPanel();//功能按键的容器
    box1.setPreferredSize(new Dimension(900,60));
    box1.setBorder(new TitledBorder("功能区"));
    box1.add(SearchBook);
    box1.add(BorrowBook);
    box1.add(ReturnBook);

    //功能实现区
    JPanel box2=new JPanel();

    //功能实现1
    jp1=new JPanel();
    jp1.setPreferredSize(new Dimension(898,540));
    jp1.setBorder(new TitledBorder("查书"));
    jp1.setVisible(false);
    box2.add(jp1);
    realize();
    //功能2
    jp2=new JPanel();
    jp2.setPreferredSize(new Dimension(898,540));
    jp2.setBorder(new TitledBorder("借书"));
    jp2.setVisible(false);
    box2.add(jp2);
    realize2();

    //功能3
    jp3=new JPanel();
    jp3.setPreferredSize(new Dimension(898,540));
    jp3.setBorder(new TitledBorder("还书"));
    jp3.setVisible(false);
    box2.add(jp3);
    realize3();

    //
    add(box1, BorderLayout.NORTH);
    add(box2);
    setBounds(100,100,900,600);
    setVisible(true);
    setDefaultCloseOperation(1);
  }
  //----------------------------------------测试
  public static void main(String[] args) throws SQLException {
    //
    new stuPage("222000212").init();
  }
  //----------------------------------------测试
  public void realize() throws SQLException {
    jp1.setLayout(new BorderLayout());//borderLayout布局
    //容器1

    JPanel jpp1=new JPanel();
    jpp1.setPreferredSize(new Dimension(900,80));
    jpp1.setBorder(new TitledBorder("1"));

    //容器2
    JPanel jpp2=new JPanel();
    jpp2.setPreferredSize(new Dimension(900,400));
    jpp2.setBorder(new TitledBorder("2"));

    //添加容器1 2
    jp1.add(jpp1,BorderLayout.NORTH);
    jp1.add(jpp2,BorderLayout.CENTER);

    //
    ISBN=new JLabel(" 书号:   ");
    ISBNT=new JTextField("",14);
    jpp1.add(ISBN);
    jpp1.add(ISBNT);
    //
    Author= new JLabel("   作者:    ");
    AuthorT=new JTextField("",14);
    jpp1.add(Author);
    jpp1.add(AuthorT);

    //
    Title=new JLabel("  书名:   ");
    TitleT =new JTextField("",14);
    jpp1.add(Title);
    jpp1.add(TitleT);
    //
    Publisher =new JLabel("  出版社:  ");
    PublisherT=new JTextField("",20);
    jpp1.add(Publisher);
    jpp1.add(PublisherT);
    //
    EditionNumber=new JLabel("  版本: ");
    EditionNumberT=new JTextField("",14);
    jpp1.add(EditionNumber);
    jpp1.add(EditionNumberT);
    //
    PublicationDate=new JLabel("  出版日期:   ");
    PublicationDateT=new JTextField("",13);
    jpp1.add(PublicationDate);
    jpp1.add(PublicationDateT);
    //
    Type=new JLabel("  类型:  ");
    TypeT=new JTextField("",13);
    jpp1.add(Type);
    jpp1.add(TypeT);
    //
    orderBy=new JLabel("  排序By:     ");
    orderByT=new JTextField("",12);
    jpp1.add(orderBy);
    jpp1.add(orderByT);
    //
    btn_submit=new JButton("查询");
    btn_submit.setPreferredSize(new Dimension(70,20));
    jpp1.add(btn_submit);

    //添加Table
    tableModelBook=new TableModel(url,username,password,sql);
    jtabelBook=new JTable(tableModelBook);
    jtabelBook.setPreferredSize(new Dimension(900,400));
    JScrollPane jsp=new JScrollPane(jtabelBook);
    jsp.setPreferredSize(new Dimension(900,400));
    jpp2.add(jsp);

    // 查询事件
    btn_submit.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                int flage = 0;
                String sql = "select * from books ";
                if (!ISBNT.getText().isEmpty()) {
                  sql += "where ISBN=" + "'" + ISBNT.getText() + "'";
                  flage = 1;
                }

                if (!TitleT.getText().isEmpty() && flage == 0) {
                  sql += "where title=" + "'" + TitleT.getText() + "'";
                  flage = 1;

                } else if (!TitleT.getText().isEmpty() && flage == 1) {
                  sql += " and" + " title=" + "'" + TitleT.getText() + "'";
                }

                if (!AuthorT.getText().isEmpty() && flage == 0) {
                  sql += "where author=" + "'" + AuthorT.getText() + "'";
                  flage = 1;

                } else if (!AuthorT.getText().isEmpty() && flage == 1) {
                  sql += " and" + " author=" + "'" + AuthorT.getText() + "'";
                }

                if (!PublisherT.getText().isEmpty() && flage == 0) {
                  sql += "where publisher=" + "'" + PublisherT.getText() + "'";
                  flage = 1;

                } else if (!PublisherT.getText().isEmpty() && flage == 1) {
                  sql += " and" + " publisher=" + "'" + PublisherT.getText() + "'";
                }

                if (!EditionNumberT.getText().isEmpty() && flage == 0) {
                  sql += "where editionNumber=" + "'" + EditionNumberT.getText() + "'";
                  flage = 1;

                } else if (!EditionNumberT.getText().isEmpty() && flage == 1) {
                  sql +=
                          " and"
                                  + " editionNumber="
                                  + "'"
                                  + EditionNumberT.getText()
                                  + "'";
                }

                if (!PublicationDateT.getText().isEmpty() && flage == 0) {
                  sql += "where publicationDate=" + "'" + PublicationDateT.getText() + "'";
                  flage = 1;

                } else if (!PublicationDateT.getText().isEmpty() && flage == 1) {
                  sql +=
                          " and"
                                  + " publicationDate="
                                  + "'"
                                  + PublicationDateT.getText()
                                  + "'";
                }
                if (!TypeT.getText().isEmpty() && flage == 0) {
                  sql += "where type=" + "'" + TypeT.getText() + "'";
                  flage = 1;

                } else if (!TypeT.getText().isEmpty() && flage == 1) {
                  sql += "where and" + " type=" + "'" + TypeT.getText() + "'";
                }

                if (!orderByT.getText().isEmpty()) {
                  sql +=" order by "+ orderByT.getText();
                }
                System.out.println(sql);//
                try {
                  tableModelBook.setQuery(sql);
                } catch (SQLException ex) {
                  throw new RuntimeException(ex);
                }
              }
            });

    SearchBook.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                jp2.setVisible(false);
                jp3.setVisible(false);
                jp1.setVisible(true);
              }

            });
  }



  public void realize2(){//实现功能区域2
    jp2.setLayout(new BorderLayout());//borderLayout布局
    //容器1

    JPanel jpp21=new JPanel();
    jpp21.setPreferredSize(new Dimension(900,100));
    jpp21.setBorder(new TitledBorder("选择书籍"));

    //容器2
    JPanel jpp22=new JPanel();
    jpp22.setBorder(new TitledBorder("结果反馈"));

    jp2.add(jpp21,BorderLayout.NORTH);
    jp2.add(jpp22);

    bookName =new JLabel("书名：");
    bookNameT =new JTextField("",15);

    bookId=new JLabel("书号：");//bo
    bookIdT=new JTextField(15);

    btn_borrow=new JButton("借阅");
    btn_borrow.setPreferredSize(new Dimension(70,20));

    JLabel jLabel=new JLabel("(至少输入书名或者书号)");//提示标签

    //借阅功能区的添加
    jpp21.add(bookName);
    jpp21.add(bookNameT);
    jpp21.add(bookId);
    jpp21.add(bookIdT);
    jpp21.add(btn_borrow);
    jpp21.add(jLabel);

    //结束操作提示区域
    messameT=new JTextArea("",20,80);
    jpp22.add(messameT);

    btn_borrow.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                String bookname = bookNameT.getText();
                String bookId = bookIdT.getText();
                messameT.setText("");
                try {
                  Check(bookname, bookId, messameT);
                } catch (SQLException ex) {
                  throw new RuntimeException(ex);
                }
              }
            });

    BorrowBook.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        jp1.setVisible(false);
        jp3.setVisible(false);
        jp2.setVisible(true);
      }
    });
  }
  public void realize3(){////实现功能区域3
    Font font =new Font("黑体", Font.PLAIN, 16);//设置字体
    jp3.setLayout(new BorderLayout());
    Jp_return=new JPanel();
    Jp_return.setPreferredSize(new Dimension(900,80));
    Jp_return.setBorder(new TitledBorder("选择归还的书籍"));

    Jtremind=new JTextArea();
    Jtremind.setPreferredSize(new Dimension(700,300));
    JScrollPane jSp3=new JScrollPane(Jtremind);
    JPanel jSp33=new JPanel();
    jSp33.setBorder(new TitledBorder("结果反馈"));
    jSp33.add(jSp3);

    Iddd=new JLabel("学号:");
    Iddd.setBounds(200,200,20,20);
    Iddd.setFont(font);
    IdT=new JTextField("",20);

    ISBNlbt=new JLabel("ISBN:");
    ISBNT=new JTextField("",20);

    btn_return=new JButton("归还");
    btn_return.setPreferredSize(new Dimension(60,20));

    Jp_return.add(Iddd);
    Jp_return.add(IdT);
    Jp_return.add(ISBNlbt);
    Jp_return.add(ISBNT);
    Jp_return.add(btn_return);

    jp3.add(Jp_return,BorderLayout.NORTH);
    jp3.add(jSp33);



    btn_return.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String ISBN=ISBNT.getText();
        String ID=IdT.getText();
        try {
          checkre( ISBN,ID,Jtremind);
        } catch (SQLException ex) {
          throw new RuntimeException(ex);
        }
      }
    });
    ReturnBook.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        jp1.setVisible(false);
        jp2.setVisible(false);
        jp3.setVisible(true);
      }
    });
  }

  //查询是否符合借书条件
  public void Check(String bookname, String bookId, JTextArea jTextArea) throws SQLException {
    LocalDate date = LocalDate.now();
    Connection con= DriverManager.getConnection(url,username,password);
    String sql="select number ,ISBN from books where ";
    String sql3 =
            "update  books set number=number-1   where ";
    int flage1=0;//标志

    int flage=0;//标志
    if(!bookId.isEmpty()){
      sql+=" ISBN="+"'"+bookId+"'";
      sql3+=" ISBN="+"'"+bookId+"'";
      flage=1;
    }
    if(!bookname.isEmpty()&&flage==0){
      sql+=" title="+"'"+bookname+"'";
      sql3+=" title="+"'"+bookname+"'";
    }else if(!bookname.isEmpty()&&flage==1){
      sql+=" and title="+"'"+bookname+"'";
      sql3+=" and title="+"'"+bookname+"'";
    }

    System.out.println(sql3);

    System.out.println(sql);
    String sql1="select limits from reader where readerID="+"'"+Id+"'";

    Connection con1=DriverManager.getConnection(url,username,password);
    Statement stm=con1.createStatement();
    ResultSet r=stm.executeQuery(sql);
    String ISBN;
    if(r.next()){
      if(r.getInt(1)>=1){
        flage1=1;
        ISBN=r.getString(2);
      }else{
        messameT.setText("此书被借完");
        return;
      }
    }else{
      messameT.setText("查无此书");
      return;
    }

    ResultSet r2=stm.executeQuery(sql1);

    if(r2.next()){
      if(r2.getInt(1)>=1&&flage1==1){
        jTextArea.setText("借阅成功!"+ISBN);
        // 添加记录
        stm.executeUpdate("insert into record value ("+"'"+ISBN+"'"+","+"'"+Id+"'"+","+"'"+"1999-12-9"+"'"+","+"'"+"1234-02-13"+"'"+")");
        stm.executeUpdate(
                "update  reader set limits=limits-1  where readerID="
                        + "'"
                        + Id
                        + "'");
        stm.executeUpdate(sql3);
      } else if (flage1==1&&r2.getInt(1)<=0) {
        jTextArea.setText("已到达您的最大借书额度!");
      }
    }
  }
  public void checkre(String ISBNTT,String Iddd,JTextArea Jtremind ) throws SQLException {
    String sql= "select * from record where "+"ISBN="+"'"+ISBNTT+"'" +"and"+ " readerID="+"'"+Iddd+"'";//查询记录
    String sql1="update books set number=number+1 where ISBN='"+ISBNTT+"'";//返回书本数
    String sql2 = "update reader set  limits=limits+1 where readerID='"+Iddd+"'";//返回可借阅书本数

    System.out.println(sql);
    Connection con= DriverManager.getConnection(url,username,password);
    Statement stm=con.createStatement();
    ResultSet r=stm.executeQuery(sql);
    if(r.next()){
      stm.executeUpdate("delete from record where  ISBN="+"'"+ISBNTT+"'"+ "and readerID="+"'"+Iddd+"'");
      stm.executeUpdate(sql1);
      stm.executeUpdate(sql2);
      Jtremind.setText("成功归还");
    }else{
      Jtremind.setText("对不起！您没有该条借书记录");
    }
  }
}



class TableModel extends AbstractTableModel {//数据模型，用于展示从数据库中查出来的数据的一种模型，结合Jtable使用
  private Connection connection;
  private Statement statement;
  private ResultSet resultSet;
  private ResultSetMetaData metaData;
  private int ColumnNumber;//列数
  private int RowNumber;//行数



  public TableModel(String url,String uname,String pswd,String sql) throws SQLException {
    this.connection=DriverManager.getConnection(url,uname,pswd);
    this.statement=this.connection.createStatement();
    setQuery(sql);
  }


  public int getRowCount() {
    try {
      resultSet.last();
    } catch (SQLException e) {
      System.out.println("error");
    }
    try {
      return resultSet.getRow();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int getColumnCount() {
    try {
      return metaData.getColumnCount();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    try {
      resultSet.absolute( rowIndex + 1 );
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    try {
      return resultSet.getObject( columnIndex + 1 );
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getColumnName(int column) {
    try {
      return metaData.getColumnName(column + 1);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return true;
  }
  public void setQuery(String sql) throws SQLException {
    resultSet=statement.executeQuery(sql);
    metaData=resultSet.getMetaData();
    fireTableStructureChanged();
  }
  //------------------------------------------测试
  public static void main(String[] args) throws SQLException {
    //
    String url = "jdbc:mysql://localhost:3306/booksmanager?useSSL = false & &serverTimezone = GMT";
    String username = "root";
    String password = "Wjjmt919112";
    String sql = "select * from admin";
    JFrame jf = new JFrame();
    TableModel tableModel = new TableModel(url, username, password, sql);
    JTable jTable = new JTable(tableModel);
    JScrollPane jsp = new JScrollPane(jTable);
    jf.add(jsp, BorderLayout.SOUTH);

    JTextArea jTextArea = new JTextArea();
    JButton jButton_submit = new JButton("提交");
    JPanel jPanel = new JPanel();
//          jPanel.setVisible(false);

    jPanel.add(jTextArea);
    jPanel.add(jButton_submit);

    jf.add(jPanel);

    jButton_submit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String sql = jTextArea.getText();
        try {
          tableModel.setQuery(sql);
        } catch (SQLException ex) {
          throw new RuntimeException(ex);
        }
      }
    });

    jf.setBounds(200, 300, 200, 300);
    jf.setVisible(true);
    jf.pack();
    jf.setDefaultCloseOperation(1);
  }
}
//--------------------------------------------------