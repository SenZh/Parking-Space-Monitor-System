package com.psms;

import javax.swing.ImageIcon;
/*   ���ֳ���������֤����Ƿ�����λ����������
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
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			if(isonline)  //�ж��Ƿ�����
			{
				win.Label_isonline.setIcon(new ImageIcon(main_gui.class.getResource("/image/online.png")));
			}
			else
			{
				win.Label_isonline.setIcon(new ImageIcon(main_gui.class.getResource("/image/offline.png")));
			
			}
		byte[] cmd={90,100};
		uart.Write(cmd); //��Э������������ָ��
		isonline=false;
		try {
			Thread.sleep(5000);  //�߳�����5s
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
	}
	public void setComStatus()
	{		
		isonline=true;	//������λ������״̬
		
	}

	public void setStop()   //ֹͣ�߳�
	{
		run=false;
		win.Label_isonline.setIcon(new ImageIcon("D:\\\u5FEB\u76D8\\Java project\\Parking Space Monitor System\\src\\image\\offline.png"));
	}

	public void setStart() //���ÿ�ʼ�߳�ǰ��״̬��Ϣ
	{
		run=true;
		isonline=true;
	}
	public boolean isRun()  //�����߳��Ƿ�������
	{
		return run;
	}
	
}
