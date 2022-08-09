package book_bms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.sql.*;

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
        jl_1.add(reminder);//
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
        ActionListener bt1_ls =
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        // TODO Auto-generated method stub
                        String admin = jtext1.getText();
                        String password = String.valueOf(jtext2.getPassword());
                        String pswd = String.valueOf(password); // 将char数组转化为string类型
                        reminder.setText(" ");
//                        try {
//                            Thread.sleep(200);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
                        try {
                            if (Jrb_stu.isSelected() && loginCheck_stu(admin, password)) {
                                //
                                System.out.println("是学生啊！");
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
                                ////密码正确跳转到用户管理未写员界面
                                //。。。。暂时
                                System.out.println("是管理员啊！");

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
        login hl = new login();//登陆界面————>功能界面
        //登陆点击事件
    }

    public boolean loginCheck_stu(String U_name,String pswd) throws SQLException {  //检查学生用户密码是否正确
        String url="jdbc:mysql://localhost:3306/kk?useSSL = false & &serverTimezone = GMT";
        String username="root";
        String password="159357HH";
        Connection con= DriverManager.getConnection(url,username,password);
        String sql="select * from reader where readerID=? and password=? ";
        PreparedStatement p=con.prepareStatement(sql);
        p.setString(1,U_name);
        p.setString(2,pswd);

        ResultSet r=p.executeQuery();
        if(r.next()){
            return true;
        }
        return false;
    }

      public boolean loginCheck_admin(String U_name,String pswd) throws SQLException {//检查管理员密码是否正确
            String url="jdbc:mysql://localhost:3306/kk?useSSL = false & &serverTimezone = GMT";
            String username="root";
            String password="159357HH";
            Connection con= DriverManager.getConnection(url,username,password);
            String sql="select * from admin where adminId=? and pwsd=? ";
            PreparedStatement p=con.prepareStatement(sql);
            p.setString(1,U_name);
            p.setString(2,pswd);

            ResultSet r=p.executeQuery();
            if(r.next()){
                  return true;
            }
            return false;
      }

}
