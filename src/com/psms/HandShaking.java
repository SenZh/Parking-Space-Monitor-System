package com.psms;

import javax.swing.ImageIcon;
/*   握手程序，用于验证软件是否与下位机保持连接
 * 
 * 
 * 
 */
class HandShaking implements Runnable  
{

	UartProcess uart;
	main_gui win;
	private boolean isonline=true;
	private boolean run=false;
	public HandShaking(UartProcess uart,main_gui bf)
	{
		this.uart=uart;
		win=bf;
	}
	public void run() {
		
		while(run){
			byte[] workingMode={77,1};
			uart.Write(workingMode);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			if(isonline)  //判断是否在线
			{
				win.Label_isonline.setIcon(new ImageIcon(main_gui.class.getResource("/image/online.png")));
			}
			else
			{
				win.Label_isonline.setIcon(new ImageIcon(main_gui.class.getResource("/image/offline.png")));
			
			}
		byte[] cmd={90,100};
		uart.Write(cmd); //向协调器发送在线指令
		isonline=false;
		try {
			Thread.sleep(5000);  //线程休眠5s
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
	}
	public void setComStatus()
	{		
		isonline=true;	//设置下位机在线状态
		
	}

	public void setStop()   //停止线程
	{
		run=false;
		win.Label_isonline.setIcon(new ImageIcon("D:\\\u5FEB\u76D8\\Java project\\Parking Space Monitor System\\src\\image\\offline.png"));
	}

	public void setStart() //设置开始线程前的状态信息
	{
		run=true;
		isonline=true;
	}
	public boolean isRun()  //返回线程是否在运行
	{
		return run;
	}
	
}
