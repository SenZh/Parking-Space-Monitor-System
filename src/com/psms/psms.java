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
/*  停车位管理系统主程序
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
		// TODO 自动生成的方法存根
		psms mainthread =new psms(); //生成实例对象
		mainthread.initializeGui();  //初始化界面
		mainthread.initializeNetMgr();  //初始化zigbee网络管理器
		mainthread.initializeUart();  //初始化串口
		mainthread.initializeAction(); //注册按键响应
		
	}
	
	public void initializeGui()   //初始化界面
	{	
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");  //设置界面UI
		} catch (ClassNotFoundException e) {		
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {	
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		window=new main_gui();  //生成界面实例对象
		window.setVisiable(true);//设置界面可见
		window.tabbedPane.addChangeListener(new ChangeListener() {        //注册tabbedpane响应程序		
			@Override
			public void stateChanged(ChangeEvent e) {
				switch (window.tabbedPane.getSelectedIndex()) {
				case 1:                         //如果选择的是“网络信息”面板，则显示响应的网络信息
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
				// TODO 自动生成的方法存根
				if(!isopen)
				{
					if(uart.openPort())
					{
						isopen=true;
						window.Button_open.setText("关闭串口");
						byte[] cmd={90,100};
						uart.Write(cmd);
					}
				}
				else {
					handShaking.setStop();
					uart.closePort();
					window.Button_open.setText("打开串口");
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
