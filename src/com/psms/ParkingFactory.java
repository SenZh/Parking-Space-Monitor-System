package com.psms;

import java.util.ArrayList;

import javax.swing.JPanel;
/**
 * 用于管理ParkingPanel
 * 
 * @author ZhangSen
 *
 */
public class ParkingFactory {
private  ArrayList<ParkingPanel> list=null;

public ParkingFactory()
{
	list=new ArrayList<ParkingPanel>();
}
/**
 * 
 *创建ParkingPanel
 * @param num 创建ParkingPanel 的数量
 */
public  void createParkingPanel(int num)
{
	for(int i=0;i<num;i++)
	{
		list.add(new ParkingPanel());
	}
}
/**
 * 
 *把生成的ParkingPanel在JPanel中显示出来（JPanel应该用girdBagLayout）
 * @param j 用于显示的JPanel
 */
public void addToUI(JPanel j )
{
	for(ParkingPanel p:list)
	{
		j.add(p);
	}
}
/**
 * 设置节点的连接状态和车位状态（是否为空）
 * @param devId 设备ID
 * @param status 车位状态
 */
public void setStatus(int devId,boolean status)
{
/**
 *  寻找已连接的节点中是否有devid相同的设备，如果有则
 *  更新车位状态，如果在已连接节点中没有找到，则在未连
 *  接的节点中找一个设置为已连接，并设置车位状态信息。
 * 
 */
	for(ParkingPanel pp:list)
	{
		if(pp.isConnected()) 
		{
			if(pp.getDevId()==devId)
			{
				pp.setOnline(status);
				return;
			}
		}
		else
		{
			pp.setConnected(true);	
			pp.setDevId(devId);
			pp.setOnline(status);
			return;
		}
	}
	System.out.println("ParkingPanel已满");//否则parkingPanel已经满了
}

}
