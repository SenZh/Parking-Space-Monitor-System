package com.psms;

import java.util.ArrayList;
import java.util.Iterator;

class Node
{
	static final int ROUTER=0x0012;
	static final int ENDDEVICE=0x00AB;
	static final int COOR=0x0056;
	static final boolean DECREASE=false;
	static final boolean INCREASE=true;
	private int address=0;
	private int deviceid=0;
	private int devicetype=0;
	private boolean isparking=false;
	private int battery=0;
	private int level=0;
	private Node parentNode=null;
	ArrayList<Node> childNodes;
	private int numofchildren=0;
	public Node(int address)
	{
		this.address=address;
	}
	public Node(int address,int deviceid)
	{
		this.address=address;
		this.deviceid=deviceid;
	}
	void addChild(int address)
	{
		childNodes.add(new Node(address));
		updateChildrenNumber(INCREASE);
	}
	void setAddress(int buf)
	{
		if(address==0)
		address=buf;
		else {
			System.out.println("can't change the address value!");
		}
	}
	void setDeviceId(int buf)
	{
		deviceid=buf;
		
	}
	void setDeviceType(int buf)
	{
		if(devicetype==0)
		{
		devicetype=buf;
		if(devicetype==ROUTER||devicetype==COOR)
			childNodes=new ArrayList<Node>(5);
		else
			childNodes=null;
		}
		else
			System.out.println("can't change the DeviceType value!");
	}
	void setBattery(int buf)
	{
		battery=buf;
	}
	void setParkingStatus(boolean buf)
	{
		isparking=buf;
	}
	void setLevel()
	{
		if(parentNode!=null)
			level=parentNode.getLevel()+1;
		else {
			System.out.println("not found parentNode when setting Level!");
		}
	}
	void setParent(Node parent)
	{
		this.parentNode=parent;
		setLevel();
	}
	void updateChildrenNumber(boolean i)
	{
		if(i)
		numofchildren++;
		else
		numofchildren--;
		if(devicetype!=COOR)
		parentNode.updateChildrenNumber(i);
	}
	int getChildrenNumber()
	{
		return numofchildren;
	}
	int getLevel()
	{
		return level;
	}
	int getAddress()
	{
		return address;
	}
	int getDeviceId()
	{
		return deviceid;
	}
	int getDevicetype()
	{
		return devicetype;
	}
	boolean getParkingStatus()
	{
		return isparking;
	}
	int getBattery()
	{
		return battery;
	}
	Node getParent()
	{
		return parentNode;
	}
	public String toString()
	{
		String devtpe;
		String chldnmbString=null;
		String prtaddString=null;
		switch (devicetype) {
		case COOR:
			devtpe="Coord   ";
			chldnmbString="  Children Nubmer:"+getChildrenNumber();
			prtaddString="";
			break;
		case ENDDEVICE:
			devtpe="EndDev";
			chldnmbString="";
			prtaddString="  ParentAddress:"+NumberFormat.toHex(parentNode.getAddress());
			break;
		case ROUTER:
			devtpe="Router  ";
			chldnmbString="  Children Nubmer:"+getChildrenNumber();
			prtaddString="  ParentAddress:"+NumberFormat.toHex(parentNode.getAddress());
			break;
		default:
			devtpe="Unknown";
			break;
		}
		return "Address:"+NumberFormat.toHex(address)+"  DeviceID:"+NumberFormat.toHex(deviceid)+"  DeviceType:"+devtpe+"  Level:" +level +prtaddString+chldnmbString;
	}
	Node searchByAddress(int address)
	{
		return searchByAddress(this, address);
	}
	Node searchByAddress(Node nodep,int address)
	{
		Iterator<Node> iterator=nodep.childNodes.iterator();
		Node node=null;
		while(iterator.hasNext())
		{
			Node buffNode=iterator.next();
			if(buffNode.address==address)
			{
				
				node=buffNode;
				break;
			}
		}
		if(node!=null)
			return node;
		else {
			
			iterator=nodep.childNodes.iterator();
			while(iterator.hasNext())
			{
				Node buffNode=iterator.next();
				if(buffNode.devicetype==ROUTER||buffNode.devicetype==COOR)
				{
					return buffNode.searchByAddress(address);
				}
			}
			return node;
		}
	}
	
}
