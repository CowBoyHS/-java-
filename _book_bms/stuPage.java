package book_bms;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class stuPage extends JFrame {
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
      private String url="jdbc:mysql://localhost:3306/kk?useSSL = false & &serverTimezone = GMT";
      private String username="root";
      private String password="159357HH";
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
            Shixian1();
            //功能2
            jp2=new JPanel();
            jp2.setPreferredSize(new Dimension(898,540));
            jp2.setBorder(new TitledBorder("借书"));
            jp2.setVisible(false);
            box2.add(jp2);
            Shixian2();

            //功能3
            jp3=new JPanel();
            jp3.setPreferredSize(new Dimension(898,540));
            jp3.setBorder(new TitledBorder("还书"));
            jp3.setVisible(false);
            box2.add(jp3);
            Shixian3();

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
      public void Shixian1() throws SQLException {
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



      public void Shixian2(){//实现功能区域2
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
      public void Shixian3(){////实现功能区域3
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
