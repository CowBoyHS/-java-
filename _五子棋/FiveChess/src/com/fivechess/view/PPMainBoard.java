package com.fivechess.view;

import com.fivechess.model.*;
import com.fivechess.net.*;

import javax.management.MBeanAttributeInfo;
import javax.swing.*;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * 人人对战页面
 * 接收信息线程
 * @author admin
 */
public class PPMainBoard extends MainBoard {
    private PPChessBoard ppcb;
    private JButton startButton;
    private JButton send; //聊天发送按钮
    private JLabel timecount;//计时器标签
    //双方状态

	  private JLabel jLabel1;
	  private JLabel jLabel2;
    private JTextArea talkArea;
    private JTextField textField_ip; //输入IP框
    private JTextField talkField; //聊天文本框
    private String ip;
    private DatagramSocket socket;
    private String gameState;
    private String enemyGameState;//敌人状态
    private Logger logger = Logger.getLogger("游戏");
    public JButton getstart(){return startButton;}
    public String getIp() {return ip;}
    public JTextField getTf() {return textField_ip;}
    public DatagramSocket getSocket() {return socket;}
    public JLabel getLabel1(){return jLabel1;}
    public JLabel getLabel2(){return jLabel2;}

    public static void main(String[] args) {
        new PPMainBoard();
    }
    public PPMainBoard()
    {
        init ();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    /**
     * 初始化页面
     */
    public void init()
    {
        gameState = "NOT_START";
        enemyGameState = "NOT_START";
        ppcb = new PPChessBoard(this);
        ppcb.setClickable(PPMainBoard.CAN_NOT_CLICK_INFO);
        ppcb.setBounds(210, 40, 570, 585);
        ppcb.setVisible(true);
        ppcb.setInfoBoard(talkArea);
        textField_ip = new JTextField("请输入IP地址");
        textField_ip.setBounds(780, 75, 200, 30);
        textField_ip.addMouseListener(this);
        startButton = new JButton("准备游戏");//设置名称，下同
        startButton.setBounds(780,180, 200, 50);//设置起始位置，宽度和高度，下同
        startButton.setBackground(new Color(50,205,50));//设置颜色，下同
        startButton.setFont(new Font("宋体", Font.BOLD, 20));//设置字体，下同
        startButton.addActionListener(this);

        send=new JButton("发送");
        send.setBounds(840, 550, 60, 30);
        send.setBackground(new Color(50,205,50));
        send.addActionListener(this);

        talkField = new JTextField("聊天");
        talkField.setBounds(780, 510, 200, 30);
        talkField.addMouseListener(this);

        timecount=new JLabel("    计时器:");
        timecount.setBounds(320,1,200,50);
        timecount.setFont(new Font("宋体", Font.BOLD, 30));

 	      jLabel1 = new JLabel();
		    jLabel1.setBounds(130,75,200,50);
 	      jLabel2 = new JLabel();
		    jLabel2.setBounds(130,410,200,50);

        timecount = new JLabel("    计时器:");
        timecount.setBounds(320,1,200,50);
        timecount.setFont(new Font("宋体", Font.BOLD, 30));

        talkArea = new JTextArea();  //对弈信息
        talkArea.setEnabled(false);
        talkArea.setBackground(Color.WHITE);
        //滑动条
        JScrollPane p = new JScrollPane(talkArea);
        p.setBounds(780, 295, 200, 200);
        add(textField_ip);
        add(ppcb);
        add(startButton);
        add(timecount);
        add(p);
        add(send);
        add(talkField);
        //加载线程
        StartThread();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton)
        {
            if(!textField_ip.getText().isEmpty()
                    &&!textField_ip.getText().equals("不能连接到此IP")
                        &&!textField_ip.getText().equals("请输入IP地址")
                            &&!textField_ip.getText().equals("不能为空")) {
                ip = textField_ip.getText();
                startButton.setEnabled(false);
                startButton.setText("等待对方准备");
                textField_ip.setEditable(false);
                //发送准备好信息
                NetTool.sendUDPBroadCast(ip,"ready, ");
                gameState = "ready";
                if(enemyGameState == "ready")
                {
                    gameState = "FIGHTING";
                    ppcb.setClickable(CAN_CLICK_INFO);
                    startButton.setText("正在游戏");
                    timer = new TimeThread(label_timeCount);
                    timer.start();
                }
            }
            else 
            {
                textField_ip.setText("不能为空");
            }
        }

        // 聊天发送按钮
        else if(e.getSource() == send)
        {
        	if(!talkField.getText().isEmpty()&&!talkField.getText().equals("不能为空"))
        	{
        		//获得输入的内容
        		String msg = talkField.getText();
        		talkArea.append("我："+msg+"\n");
        		talkField.setText("");
        		ip = textField_ip.getText();
        		NetTool.sendUDPBroadCast(ip,"enemy"+","+msg);
        	}
        	else {
        		talkField.setText("不能为空");
          }
        }else {

        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	if(e.getSource() == textField_ip)
    	{
          textField_ip.setText("");
    	}
    	else if(e.getSource() == talkField)
    	{
    		talkField.setText("");
    	}
    }

    public void StartThread()
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    byte buf[] = new byte[1024];
                    socket = new DatagramSocket(10000);
                    DatagramPacket dp = new DatagramPacket(buf, buf.length);
                    while(true)
                    {
                        socket.receive(dp);
                        //0.接收到的发送端的主机名
                        InetAddress ia = dp.getAddress();
                        logger.info("对手IP："+ia.getHostName());
                        //1.接收到的内容
                        String data = new String(dp.getData(),0,dp.getLength());
                        if(data.isEmpty())
                        {
                            ppcb.setClickable(MainBoard.CAN_NOT_CLICK_INFO);
                        }
                        else {
                            String[] msg = data.split(",");
                            System.out.println(msg[0]+" "+msg[1]);
                            //接收到对面准备信息并且自己点击了准备
                            if(msg[0].equals("ready"))
                            {
                                enemyGameState = "ready";
                                System.out.println("对方已准备");
                                if(gameState.equals("ready"))
                                {
                                    gameState = "FIGHTING";
                                    ppcb.setClickable(MainBoard.CAN_CLICK_INFO);
                                    startButton.setText("正在游戏");

                                    logger.info("等待对方消息");
                                    timer = new TimeThread(label_timeCount);
                                    timer.start();
                                }
                            }
                            else if(msg[0].equals("POS")){
                                System.out.println("发送坐标");
                                //重新启动计时线程
                                timer = new TimeThread(label_timeCount);
                                timer.start();
                            }
                            else if(msg[0].equals("enemy"))
                            {
                                talkArea.append("对手："+msg[1]+"\n");
                                logger.info("对手发送的消息"+msg[1]);
                            }
                        }
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
