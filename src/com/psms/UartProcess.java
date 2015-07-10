package com.psms;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;
import javax.swing.DefaultComboBoxModel;

class UartProcess
{
	main_gui window;
	CommPortIdentifier portId;
	SerialPort uartPort;
	InputStream input;
	BufferedInputStream bufferedInputStream;
	OutputStream output;
	int rate,databits,stopbits,parity;
	DataAutoProcess datap;
	public UartProcess(main_gui _window,DataAutoProcess datap)
	{
		window=_window;
		this.datap=datap;
	}
	public void listPort()
	{
		
		DefaultComboBoxModel<String> mymodel=new DefaultComboBoxModel<String>(); 
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> en = CommPortIdentifier.getPortIdentifiers();
	    while (en.hasMoreElements()) {
	        portId = en.nextElement();
	        if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	            mymodel.addElement(portId.getName());
	        }
	    }
	    if(mymodel.getSize()>0)
	    window.getPortBox().setModel(mymodel);
	}
	public void setParameter()
	{
		rate=Integer.parseInt(window.getRate());
		databits=Integer.parseInt(window.getDataBits());
		switch (window.getStopBits()) {
		case  "1":
			stopbits=SerialPort.STOPBITS_1;
			break;
		case "1.5":
			stopbits=SerialPort.STOPBITS_1_5;
			break;
		case "2":
			stopbits=SerialPort.STOPBITS_2;
			break;
		default:
			break;
		}
		parity=window.getParity();
		
	}
	public boolean openPort()
	{
		try {
			portId=CommPortIdentifier.getPortIdentifier(window.getPort());
		} catch (NoSuchPortException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		try {
			uartPort=(SerialPort) portId.open("parking monitor system", 2000);
		} catch (PortInUseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		
		try {
			setParameter();
			uartPort.setSerialPortParams(rate, databits, stopbits, parity);
		} catch (UnsupportedCommOperationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		try {
			input=uartPort.getInputStream();
			bufferedInputStream=new BufferedInputStream(input);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		
		try {
			output=uartPort.getOutputStream();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		uartPort.notifyOnDataAvailable(true);
		try {
			uartPort.addEventListener(new SerialPortEventListener() {
				
				@Override
				public void serialEvent(SerialPortEvent arg0) {
					// TODO 自动生成的方法存根
					switch (arg0.getEventType()) {
					   case SerialPortEvent.BI:
				        case SerialPortEvent.OE:
				        case SerialPortEvent.FE:
				        case SerialPortEvent.PE:
				        case SerialPortEvent.CD:
				        case SerialPortEvent.CTS:
				        case SerialPortEvent.DSR:
				        case SerialPortEvent.RI:
				        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
				            break;
				        case SerialPortEvent.DATA_AVAILABLE:
				        byte[] buffer=new byte[1024];
				        int numbytes=0;
						try {
							if(input.available()>0)
							{
								numbytes=bufferedInputStream.read(buffer);
							}
							window.textArea_out.append(new String(buffer,0,numbytes));
							if(datap!=null)
							datap.Process(buffer, numbytes);
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
			
						}
						break;
					}
				}
			});
		} catch (TooManyListenersException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		return true;

	}
	public void closePort()
	{
		uartPort.removeEventListener();
		uartPort.close();
		uartPort=null;
		portId=null;
	
	}
	public void Write()
	{
		
		try {
			output.write(window.textArea_in.getText().getBytes());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void Write(byte[] buff)
	{
		try {
			output.write(buff);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
}