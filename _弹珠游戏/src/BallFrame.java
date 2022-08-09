import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class BallFrame extends JPanel implements ActionListener{
  // 实例化一个数组对象
  public Ball[] ball;
  // 实例化一个随机数对象
  public Random r = new Random();
  public JFrame jf;
  public JLabel time_label;
  public JLabel label;
  public JTextField t1;
  public JTextField t2;
  public JButton jb;
  public int mouseX;
  public int mouseY;
  public int flag = 0;
  public boolean isEnd = false;
  public int flag1 = 0;
  public static void main(String[] args) {
    // 实例化一个面板对象
    BallFrame bf = new BallFrame();
    // 调用initUI方法
    bf.initUI();
    //重来
//    while(true) {
//      if(Ball.isNotFinish == true && bf.isEnd == false && bf.flag1 == 1 ) {
//        bf.flag1 = 0;
//        Ball.isNotFinish = true;
//        bf.repaint();
//        bf.jb.setVisible(true);
//        bf.t1.setVisible(true);
//        bf.t2.setVisible(true);
//        bf.time_label.setVisible(false);
//        bf.label.setVisible(false);
//        bf.initUI();
//      }
//    }
  }
  public BallFrame() {
//    Cursor cur=new Cursor(Cursor.CROSSHAIR_CURSOR);//这一句就是设置了一个十字形的鼠标样式
//    this.setCursor(cur);
//    this.setCursor(cur);
  }
  public void actionPerformed(ActionEvent e) {

  }
  // 界面函数
  public void initUI() {
    jf = new JFrame("来玩弹珠吧!");// 实例化面板对象
    //jf.setLayout(null);//设置窗体布局模式
    jf.setSize(new Dimension(600, 600));// 设置面板大小
    jf.setResizable(false);// 设置不可调节大小
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭按钮
    jf.setLocationRelativeTo(null);// 设置窗体居中
    this.setBackground(Color.white);// 设置面板背景为白色
    jf.setVisible(true);// 设置窗体可见
    jf.add(this, BorderLayout.CENTER);// 将面板添加到窗体上
    //设置计时功能

    time_label = new JLabel("0 s");
    label = new JLabel("时  间  ：");
    // 设置文本标签前景颜色
    label.setForeground(Color.red);
    time_label.setForeground(Color.red);
    label.setBounds(500,1,50,50);
    time_label.setBounds(550,1,50,50);
    label.setVisible(false);
    time_label.setVisible(false);
    this.add(label);
    this.add(time_label);


    //增加开始页面
    //JPanel start_page = new JPanel();
    //start_page.setLayout(null);
    //增加开始按钮
    jb = new JButton("开始");
    //增加提示框
    t1 = new JTextField("小球的个数：",7);
    //增加输入框
    t2 = new JTextField(3);
    //控制第一个提示框为不可编辑
    t1.setEditable(false);
    //将开始按钮、提示框、输入框添加进面板中
    this.add(jb);
    this.add(t1);
    this.add(t2);
    //设置面板的位置和大小
    //start_page.setBounds(0,0,600,600);
    //设置按钮的位置和大小
    jb.setBounds(220,200,125 ,100);
    //设置提示框的位置和大小
    t1.setBounds(220,305,80,50);
    //设置输入框的位置和大小
    t2.setBounds(300,305,45,50);
    //增加计时功能
    //this.add(start_page);


    //将开始页面添加进窗口中
//    jf.add(start_page);
    //添加开始按钮的监听器


    this.addMouseMotionListener(new MouseMotionListener() {
      @Override
      public void mouseDragged(MouseEvent e) {

      }

      @Override
      public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
      }
    });
    start_Listener sl = new start_Listener(this);
    jb.addActionListener(sl);
  }

  public void createBall(BallFrame bf) {
    for (int i = 0; i < ball.length; i++) {
      // 实例化每个小球对象
      ball[i] = new Ball(new Color(r.nextInt(255), r.nextInt(255),
              r.nextInt(255)), r.nextInt(550), r.nextInt(550), 20,
              r.nextInt(4) + 1, r.nextInt(4) + 1,bf,i,time_label,mouseX,mouseY);
    }
    for (int i = 0; i < ball.length; i++) {
      // 将每个小球线程运行起来
      ball[i].start();
    }
  }

  // 重写paint方法
  public void paint(Graphics g) {
    // 调用父类的paint方法
    super.paint(g);
    if(flag == 1) {
      for (int i = 0; i < ball.length; i++) {
        // 从ball中获取颜色并设置
        g.setColor(ball[i].getcolor());
        // 画出小球
        g.fillOval(ball[i].getX(), ball[i].getY(), ball[i].getRadiu(),
                ball[i].getRadiu());
      }
      // 调用碰撞函数
      collision();
    }
  }

  // 碰撞函数
  private void collision() {
    // 距离数组，存储两小球间的距离
    double[][] dis = new double[ball.length][ball.length];
    for (int i = 0; i < ball.length; i++) {
      for (int j = 0; j < ball.length; j++) {
        // 计算两个小球间的距离
        dis[i][j] = Math.sqrt(Math.pow(ball[i].getX() - ball[j].getX(),
                2) + Math.pow(ball[i].getY() - ball[j].getY(), 2));
      }
    }
    for (int i = 0; i < ball.length; i++) {
      for (int j = i + 1; j < ball.length; j++) {
        if (dis[i][j] < (ball[i].getRadiu() + ball[j].getRadiu()) / 2) {
          int t;
          // 交换小球x方向的速度
          t = ball[i].getVx();
          ball[i].setVx(ball[j].getVx());
          ball[j].setVx(t);
          // 交换小球y方向的速度
          t = ball[i].getVy();
          ball[i].setVy(ball[j].getVy());
          ball[j].setVy(t);
          // 确定碰撞后第二个小球的位置，更加拟合碰撞的现象
          int x2 = ball[j].getX() - ball[i].getX(), y2 = ball[j]
                  .getY() - ball[i].getY();
          ball[j].setX(ball[i].getX() + x2);
          ball[j].setY(ball[j].getY() + y2);
        } else {
        }
      }
    }
  }
}
class start_Listener implements ActionListener {
  private BallFrame bf;
  start_Listener(BallFrame bf) {
    this.bf = bf;
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    try{

      //错误处理，大于25个球即不允许程序运行
      if(Integer.parseInt(bf.t2.getText()) <=0 || Integer.parseInt(bf.t2.getText()) >30) {
        JOptionPane.showMessageDialog(bf,"错误！");
      }else {

        bf.flag = 1;
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image image = new ImageIcon(getClass().getResource("rectangle.png")).getImage();
        Cursor cursor = tk.createCustomCursor(image, new Point(10, 10), "norm");
        bf.setCursor(cursor);

        //start_page.setVisible(false);
        bf.t1.setVisible(false);
        bf.t2.setVisible(false);
        bf.jb.setVisible(false);
        bf.label.setVisible(true);
        bf.time_label.setVisible(true);
        bf.ball = new Ball[Integer.parseInt(bf.t2.getText())];
        bf.createBall(bf);
      }
    }catch (NumberFormatException nfe) {
      JOptionPane.showMessageDialog(bf.jf,"错误！");
    }
  }
}


class Ball extends Thread {
  // 初始化一些对象名
  private Color color;
  private int x, y, radiu, vx, vy;
  private BallFrame bf;
  private int id;
  public static boolean isNotFinish = true;
  private JLabel time_label;
  private long start,end;
  private long last_time;
  private int mouseX,mouseY;
  public int index;
  private static int flag2  = 1;
  /**
   * 构造函数
   *
   //   * @param color小球颜色
   //   * @param x小球横坐标
   //   * @param y小球纵坐标
   //   * @param radiu小球直径
   //   * @param vx小球横向速度
   //   * @param vy小球纵向速度
   //   * @param bf面板
   //   * @param id标志
   */
  public Ball(Color color, int x, int y, int radiu, int vx, int vy,
              BallFrame bf, int id,JLabel time_label,int mouseX,int mouseY) {
    this.color = color;
    this.x = x;
    this.y = y;
    this.radiu = radiu;
    this.vx = vx;
    this.vy = vy;
    this.bf = bf;
    //this.jf = jf;
    this.id = id;
    this.time_label = time_label;
    this.mouseX = mouseX;
    this.mouseY = mouseY;
  }

  // 重写run方法
  public void run() {
    //super.run();// 调用父类run方法
    // 执行无限循环

    start = System.currentTimeMillis();

    while (isNotFinish) {
      synchronized (bf) {
        bf.repaint();
        // System.out.println("第"+id+"个球的x:"+x +"   y:"+y);
        x += vx;// 改变x的速度
        y += vy;// 改变y的速度
        // 如果x越界
        if (x <= 0 || x + radiu >= bf.getWidth()) {
          vx = -vx;// x速度反向
          if (x < 0)
            x = 0;
          else if (x > bf.getWidth() - radiu)
            x = bf.getWidth() - radiu;
          else {
          }
        }
        // 如果y越界
        else if (y <= 0 || y + radiu >= bf.getHeight()) {
          vy = -vy;// y速度反向
          if (y < 0)
            y = 0;
          else if (y > bf.getHeight() - radiu)
            y = bf.getHeight() - radiu;
          else {
          }
        } else {
        }
      }
      try {
        Thread.sleep(10);// 设置睡眠时间为10ms
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      end = System.currentTimeMillis();
      last_time = (end - start)/1000;
      time_label.setText(last_time + " s");
      synchronized (bf) {
        if(flag2 == 1) {
          if(Math.sqrt(Math.pow(bf.mouseX - this.x,2) + Math.pow(bf.mouseY - this.y,2)) <= 17
                  || bf.mouseX < 5 || bf.mouseX > bf.getWidth() - 15 || bf.mouseY < 5 || bf.mouseY > bf.getHeight() -15) {
            flag2 = 0;
            isNotFinish = false;
            JOptionPane.showMessageDialog(bf,"恭喜你坚持了" + last_time +" s");
          }
        }
      }

    }

    //结束
//    if(index == 1) {
//      bf.isEnd = true;
//      bf.flag1 = 0;
//      //重来
//    }else if(index == 0) {
//      bf.flag1 = 1;
//      bf.isEnd = false;
//      isNotFinish = true;
//      bf.time_label.setText("0 s");
//      //取消
//    }else if(index == 2) {
//    }
  }

  public Color getcolor() {
    return color;
  }

  public void setcolor(Color color) {
    this.color = color;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getRadiu() {
    return radiu;
  }

  public void setRadiu(int radiu) {
    this.radiu = radiu;
  }

  public int getVx() {
    return vx;
  }

  public void setVx(int vx) {
    this.vx = vx;
  }

  public int getVy() {
    return vy;
  }

  public void setVy(int vy) {
    this.vy = vy;
  }
}




