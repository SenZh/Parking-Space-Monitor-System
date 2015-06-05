package com.psms;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.TooManyListenersException;

import javafx.scene.image.Image;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/*  ͣ��λ����ϵͳ������
 *                                  
 */

public class psms {
	
	main_gui window;
	UartProcess uart;
	DataAutoProcess dataProcess;
	NetManager<Node> coor;
	HandShaking handShaking;
	boolean isopen=false;
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		psms mainthread =new psms(); //����ʵ������
		mainthread.initializeGui();  //��ʼ������
		mainthread.initializeNetMgr();  //��ʼ��zigbee���������
		mainthread.initializeUart();  //��ʼ������
		mainthread.initializeAction(); //ע�ᰴ����Ӧ
		
	}
	
	public void initializeGui()   //��ʼ������
	{	
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");  //���ý���UI
		} catch (ClassNotFoundException e) {		
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {	
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		window=new main_gui();  //���ɽ���ʵ������
		window.setVisiable(true);//���ý���ɼ�
		window.tabbedPane.addChangeListener(new ChangeListener() {        //ע��tabbedpane��Ӧ����		
			@Override
			public void stateChanged(ChangeEvent e) {
				switch (window.tabbedPane.getSelectedIndex()) {
				case 1:                         //���ѡ����ǡ�������Ϣ����壬����ʾ��Ӧ��������Ϣ
					Iterator<Node> iterator=coor.iterator();
					window.textArea_nodeInfo.setText(null);
					while(iterator.hasNext())
					{
						Node node=iterator.next();					
						window.textArea_nodeInfo.append(node+"\n");
					}
					break;
				
				default:
					break;
				}
			}
		});
		
		
	}
	public void initializeAction()
	{
		window.Button_open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				if(!isopen)
				{
					if(uart.openPort())
					{
						isopen=true;
						window.Button_open.setText("�رմ���");
						byte[] cmd={90,100};
						uart.Write(cmd);
					}
				}
				else {
					handShaking.setStop();
					uart.closePort();
					window.Button_open.setText("�򿪴���");
					isopen=false;
				}
			}
		});
		window.Button_send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {			
				uart.Write();
			}
		});
		window.Button_clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				window.textArea_out.setText(null);
			}
		});
		
	}
	public void initializeUart()
	{		
		dataProcess=new DataAutoProcess(window,coor);
		uart=new UartProcess(window,dataProcess);
		uart.listPort();
		handShaking=new HandShaking(uart, window);
		dataProcess.setHandShaking(handShaking);
	}
	public void initializeNetMgr()
	{
		coor=new NetManager<Node>(0);

	}

}






class NumberFormat
{
	static public String toHex(int i)
	{
		String numString=Integer.toHexString(i).toUpperCase();
		String headString;
		int length=numString.length();
		switch (length) {
		case 1:
			headString="0x000"+numString;
			break;
		case 2:
		headString="0x00"+numString;
		break;
		case 3:
			headString="0x0"+numString;
			break;
		case 4:
			headString="0x"+numString;
			break;
		default:
			headString=numString;
			break;
		
		}
		return headString;
	}
}
