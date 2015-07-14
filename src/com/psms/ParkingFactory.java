package com.psms;

import java.util.ArrayList;

import javax.swing.JPanel;
/**
 * ���ڹ���ParkingPanel
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
 *����ParkingPanel
 * @param num ����ParkingPanel ������
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
 *�����ɵ�ParkingPanel��JPanel����ʾ������JPanelӦ����girdBagLayout��
 * @param j ������ʾ��JPanel
 */
public void addToUI(JPanel j )
{
	for(ParkingPanel p:list)
	{
		j.add(p);
	}
}
/**
 * ���ýڵ������״̬�ͳ�λ״̬���Ƿ�Ϊ�գ�
 * @param devId �豸ID
 * @param status ��λ״̬
 */
public void setStatus(int devId,boolean status)
{
/**
 *  Ѱ�������ӵĽڵ����Ƿ���devid��ͬ���豸���������
 *  ���³�λ״̬������������ӽڵ���û���ҵ�������δ��
 *  �ӵĽڵ�����һ������Ϊ�����ӣ������ó�λ״̬��Ϣ��
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
	System.out.println("ParkingPanel����");//����parkingPanel�Ѿ�����
}

}
