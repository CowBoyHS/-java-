package com.fivechess.view;

import com.fivechess.judge.*;
import com.fivechess.model.*;
import com.fivechess.net.*;
import javax.swing.*;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.*;

/**
 * 人人对战棋盘
 * @author admin
 */
public class PPChessBoard extends ChessBoard {
    private int role; //角色
    private JTextArea talkArea; //交流信息
    private PPMainBoard mb;
    private int step[][] = new int[30*30][2];//定义储存步数数组
    private int stepCount = 0;//初始化数组
    //加载黑白棋子，用于判定玩家所执棋
    private ImageIcon imageIcon1 = new ImageIcon(blackChess);
    private ImageIcon imageIcon2 = new ImageIcon(whiteChess);
    private Logger logger = Logger.getLogger("棋盘");
    /**
     * 构造函数，初始化棋盘的图片，初始化数组
     * @param mb 人人对战页面
     */
    public PPChessBoard(PPMainBoard mb) {
        super();
        this.mb = mb;
        //设置先开始游戏的玩家执白
        setRole(Chess.WHITE);
    }
    /**
     * 设置聊天窗口
     * @param area 聊天窗口
     */
    public void setInfoBoard(JTextArea area)
    {
        talkArea=area;
    }
    /**
     * 保存黑白棋子的坐标于二维数组中
     * @param posX
     * @param posY
     */
    private void saveStep(int posX,int posY)
    {
        stepCount++;
        step[stepCount][0] = posX;
        step[stepCount][1] = posY;
    }
    /**
     * 设置角色
     * @param role 角色
     */
    public void setRole(int role)
    {
        this.role=role;
    }
    /**
     * 获得角色
     * @return 角色
     */
    public int getRole()
    {
        return role;
    }
    /**
     * 从父类继承的方法，自动调用，绘画图形
     * @param g 该参数是绘制图形的句柄
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    /**
     * 获得结果
     * @return 结果
     */
    public int getResult()
    {
        return result;
    }
    /**
     * 胜利事件
     * @param winner 胜方
     */
    public void WinEvent(int winner)
    {
        //白棋赢
        if(winner == Chess.WHITE) {
        	//中断线程
            try {
                mb.getTimer().interrupt();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            mb.getstart().setText("开始游戏");
            mb.getstart().setEnabled(true);
            result = Chess.WHITE;
            JOptionPane.showMessageDialog(mb,"恭喜！白棋获胜");
            logger.info("白棋获胜！初始化页面");
            setClickable(PPMainBoard.CAN_NOT_CLICK_INFO);
            //初始化页面
            initArray();
            mb.getLabel().setText(null);

        }
        //黑棋赢
        else if(winner == Chess.BLACK){
        	//中断线程
            try {
                mb.getTimer().interrupt();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            mb.getstart().setText("开始游戏");
            mb.getstart().setEnabled(true);
            result = Chess.BLACK;
            setClickable(MainBoard.CAN_NOT_CLICK_INFO);
            JOptionPane.showMessageDialog(mb,"恭喜！黑棋获胜");
            logger.info("黑棋获胜！初始化页面");
            //初始化页面
            initArray();
            mb.getLabel().setText(null);
        }
    }
    /**
     * 按下鼠标时，记录鼠标的位置，并改写数组的数值，重新绘制图形
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(clickable == PPMainBoard.CAN_CLICK_INFO) {
            chessX = e.getX();
            chessY = e.getY();
            //限定点击区域为棋盘区域
            if (chessX < 524 && chessX > 50 && chessY < 523 && chessY > 50) {
                float x = (chessX - 49) / 25;
                float y = (chessY - 50) / 25;
                int x1 = (int) x;
                int y1 = (int) y;
                //如果这个地方没有棋子
                if (chess[x1][y1] == Chess.BLANK) {
                    
                    if(role== Chess.WHITE) {
                        logger.info("白棋落子:" + x1 + "," + y1);
                    }
                    else if(role==Chess.BLANK){
                        logger.info("黑棋落子:" + x1 + "," + y1);
                    }
                    //计时线程中断
                    try {
                        mb.getTimer().interrupt();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    chess[x1][y1] = role;
                    saveStep(x1, y1);
                    //发送棋坐标
                    NetTool.sendUDPBroadCast(mb.getIp(),"POS"+","+ x1 + "," + y1 + "," + role);
                    //判断输赢
                    int winner=Judge.whowin(x1,y1,chess,role);
                    WinEvent(winner);
                    setClickable(MainBoard.CAN_NOT_CLICK_INFO);
                }
            }
        }
    }
    
    /**
     *鼠标点击事件
     *@param e
     **/
     @Override
     public void mouseMoved(MouseEvent e) {
     	if(clickable == MainBoard.CAN_CLICK_INFO) {
     		 mousex = e.getX();
     		 mousey = e.getY();
     		 repaint();
     	}
     }
}


/**
 * 棋盘页面父类
 * 初始化棋盘页面
 */
 class ChessBoard extends JPanel implements MouseMotionListener,MouseListener{
    //棋子的横坐标
    public int chessX;
    //棋子的纵坐标
    public int chessY;
    //鼠标坐标
    public int mousex;
    public int mousey;
    public JLabel timecount;
    //棋盘上隐形的坐标，每一个小区域代表一个数组元素
    public int chess[][]=new int[COLS][RAWS];
    public int clickable;
    public static final int GAME_OVER = 0; //结束游戏
    public static final int COLS = 19;
    public static final int RAWS = 19;//棋盘的行数和列数
    public int result = 1; //结果，为0则结束游戏
    public Image whiteChess;
    //黑白棋子
    public Image chessBoardImage;
    public Image blackChess;
    //鼠标经过显示图片
    public Image position;


    public ChessBoard() {
        //加载棋盘、黑棋、白棋、位置图片
        chessBoardImage = Toolkit.getDefaultToolkit().getImage("images/chessBoard.jpg");
        whiteChess = Toolkit.getDefaultToolkit().getImage("images/white.png");
        blackChess = Toolkit.getDefaultToolkit().getImage("images/black.png");
        position = Toolkit.getDefaultToolkit().getImage("images/position.gif");
        initArray();
        addMouseListener(this);
        addMouseMotionListener(this);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(chessBoardImage, 35, 35,null);
        //页面可点击，则显示预下棋框
        if(clickable==MainBoard.CAN_CLICK_INFO)
            this.showPosition(g);
        //对应位置显示黑棋、白棋
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if (chess[i][j] == Chess.BLACK) {
                    g.drawImage(blackChess, 49 + i * 25 , 50 + j * 25 , null);
                } else if (chess[i][j] == Chess.WHITE) {
                    g.drawImage(whiteChess, 49 + i * 25 , 50 + j * 25 , null);
                }
            }
        }
    }
    public void setClickable(int clickable)
    {
        this.clickable = clickable;
    }

    public void initArray()
    {
        for(int i = 0; i < 19; i++)
        {
            for(int j = 0; j < 19; j++)
            {
                chess[i][j]= Chess.BLANK;
            }
        }
    }

    /**
     * 坐标图片跟随鼠标显示
     * @param g
     */
    private void showPosition(Graphics g)
    {
        int FrameWidth = getWidth(); //面板宽度
        int FrameHeight = getHeight(); //面板高度
        int x=(FrameWidth-475)/2;
        int y=(FrameHeight-489)/2;

        int px = mousex-(mousex-x)%25;//25为棋盘格子大小
        int py = mousey-(mousey-y)%25;
        if(px<x)px = x;
        if(py<y)py = y;
        if(px>(x + (475-25)))
            px=x + (475-25);
        if(py > (y + (475-25)))
            py = y + (475-25);
        g.drawImage(position,px,py,25,25,null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    /**
     * 按下鼠标时，记录鼠标的位置，并改写数组的数值，重新绘制图形
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}

/**
 * 人机对战和人人对战父类
 * 初始化二者相同页面、计时器
 * @author admin
 */
 class MainBoard extends JFrame implements MouseListener, ActionListener {
    public static final int CAN_CLICK_INFO = 1; //棋盘可以点击
    public static final int CAN_NOT_CLICK_INFO = 0; //棋盘不可以点击
    public JLabel label_timeCount;//计时
    public JLabel timecount;
    public TimeThread timer;
    public int result = 1;
    public TimeThread getTimer() {return timer;}
    public JLabel getLabel() {return label_timeCount;}
    public MainBoard()
    {
        setLayout(null); //取消原来布局
        setBounds(100,30,1000,680);
        init1();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("来玩五子棋吧");
    }

    /**
     * 绘图
     * @param g
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        repaint();
    }

    /**
     * 初始化计时标签
     */
    public void init1()
    {
        label_timeCount=new JLabel();
        label_timeCount.setFont(new Font("宋体",Font.BOLD,30));
        label_timeCount.setBounds(502, 1, 500, 50);
        add(label_timeCount);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}


