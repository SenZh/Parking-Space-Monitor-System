package com.psms;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * 
 * 
 * 
 * @author ZhangSen
 *
 */


public class ParkingPanel extends JPanel {
/**
	 * 
	 */
//private static Image online_image=null;
//private static Image offline_image=null;
private static final long serialVersionUID = -3110410379475736710L;
private boolean isOnline=true;
private int devId;
//private boolean isConnected=false;
private InnerCanvas canvas=new InnerCanvas();
private JLabel label=new JLabel();
private JPanel panel=new JPanel();
public ParkingPanel()
{	
	super();
//	online_image=this.getToolkit().getImage("image/online.png");
//	offline_image=this.getToolkit().getImage("image/offline.png");
	setLayout(new BorderLayout(0, 0));
	add(canvas,BorderLayout.CENTER);
	add(panel,BorderLayout.NORTH);
	panel.setLayout(new FlowLayout());
	panel.add(label);
	label.setText("设备未连接");
	}
	/*public void paint(Graphics g) {
		String dev="DevID:0X"+Integer.toHexString(devId);
		Image image=null;
		if(isConnected)
		{
		if(isOnline)
			image=online_image;
		else
			image=offline_image;
		}
		else
		{
			image=this.getToolkit().getImage("image/disconnected.png");
			dev="设备未连接";
		}
		Font ft=new Font(Font.SANS_SERIF ,Font.LAYOUT_LEFT_TO_RIGHT ,12);
		 g.drawImage(image, getWidth()/2-64,getHeight()/2-48,this);
		 g.setColor(Color.BLACK);
		 g.setFont(ft);
		 g.drawString(dev,getWidth()/2-dev.length()*7, getHeight()/2-40);
		
	}*/

	
/*	public void update(Graphics arg0) {
		paint(arg0);
	}*/
	public void refreshCanvas()
	{
		canvas.repaint();
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		canvas.setIsOnline(isOnline);
	}
	public int getDevId() {
		return devId;
	}
	public void setDevId(int devId) {
		
		this.devId=devId;
		StringBuffer str=new StringBuffer();
		str.append("设备ID : 0x");
		for(int i=0;i<4-Integer.toHexString(devId).length();i++)
			str.append("0");
		str.append(Integer.toHexString(devId));
		label.setText(str.toString());
	}
	public boolean isConnected() {
		return canvas.isConnected();
	}
	public void setConnected(boolean isConnected) {
		if(isConnected)
			label.setText("设备已连接");
		else
			label.setText("设备未连接");
		canvas.setConnected(isConnected);
	}

}
	

class InnerCanvas extends Canvas
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1265247698457399580L;
	private static Image online_image=null;
	private static Image offline_image=null;
	private Boolean isOnline=false;
	private boolean isConnected=false;
	private  Image bufferImage;
	public InnerCanvas()
	{
		super();
		setVisible(true);
		online_image=this.getToolkit().getImage("image/online.png");
		offline_image=this.getToolkit().getImage("image/offline.png");
	}

	@Override
	public void paint(Graphics g) {
		Image image=null;
		if(isConnected)
		{
		if(isOnline)
			image=	online_image;
		else
			image=	offline_image;	
		}
		else
		{
			image=this.getToolkit().getImage("image/disconnected.png");
		}
		g.drawImage(image, getWidth()/2-64,getHeight()/2-48,this);
		
	}

	@Override
	public void update(Graphics g) {
//		super.update(g);
		bufferImage=createImage(getWidth(), getHeight());
		Graphics gBuffer=bufferImage.getGraphics();
		if(gBuffer!=null)
			paint(gBuffer);
		else
			paint(g);
		gBuffer.dispose();
		g.drawImage(bufferImage, 0, 0,null);
	}
	
	public Boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Boolean isOnline) {
		if(this.isOnline!=isOnline)
		{
			this.isOnline = isOnline;
			repaint();
		}
	}



	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		if(this.isConnected!=isConnected)
		{
		this.isConnected = isConnected;
		repaint();
		}
	}
}