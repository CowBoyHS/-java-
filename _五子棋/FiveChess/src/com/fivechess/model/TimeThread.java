package com.fivechess.model;
import java.awt.Color;
import javax.swing.JLabel;

/**
 * 计时线程
 *
 */
public class TimeThread extends Thread{
	JLabel label; //计时标签
	public TimeThread(JLabel label)
	{
		this.label=label;
	}
	public void run()
	{
		//获取开始时间（毫秒），即当前时间
		long startTime = System.currentTimeMillis();
		while(true)
		{
			long currentTime = System.currentTimeMillis();
			long time = currentTime-startTime;
			//30秒倒计时
			label.setText(String.valueOf(30-time/1000));
			//如果30秒结束，督促
			if(label.getText().equals("0"))
			{
				this.interrupt();
				label.setText("快下！！！");
				label.setForeground(Color.red);
			}
			//中断线程退出
			if(this.isInterrupted())
			{
				break;
			}
		}
	}
}
