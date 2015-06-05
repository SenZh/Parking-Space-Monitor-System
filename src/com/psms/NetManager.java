package com.psms;

import java.util.Iterator;

class NetManager<E> extends Node implements Iterable<E>
{
	int cooraddress;
	public NetManager(int address) {
		super(address);
		setDeviceType(COOR);
		cooraddress=address;
		this.setAddress(address);
	}
	public boolean addNoe(int address,int parent)
	{
		Node parentNode;
		Node childNode=this.searchByAddress(address);
		if(parent==cooraddress)
		parentNode=this;
		else {
			parentNode=this.searchByAddress(parent);
		}
		
		if(childNode==null)
		{
			if(parentNode!=null)
			{
				parentNode.addChild(address);
				childNode=parentNode.searchByAddress(address);
				childNode.setParent(parentNode);
				return true;
			}
			else {
				System.out.println("not found parentNode when adding node!");
				return false;
			}
		}
		else
		{
			System.out.println("node is exist when adding a new node!");
			return false;
		}
	}
	public boolean cmdSetDeviceId(int address,int deviceid)
	{	
		if(address==cooraddress){
		setDeviceId(deviceid);
		return true;
	}
		else{
		Node node=searchByAddress(address);
		if(node!=null)
		{
			node.setDeviceId(deviceid);
			return true;
		}
		else
		{
			System.out.println("not found node when setting deviceId!");
			return false;
		}
		}
	}
	public boolean cmdSetDeviceType(int address,int devicetype)
	{
		Node node=searchByAddress(address);
		if(node!=null)
		{
			node.setDeviceType(devicetype);
			return true;
		}
		else
		{
			System.out.println("not found node when setting deviceType!");
			return false;
		}
	}
	
	public boolean cmdSetParking(int address,int parking) {
		
		Node node=searchByAddress(address);
		if(node!=null)
		{
			if(parking==0)
			{
				node.setParkingStatus(false);
				return true;
			}
			else if(parking==1)
			{
				node.setParkingStatus(true);
				return true;
			}
			else {
				System.out.println("cmd is wrong when setting ParkingStatus!");
				return false;
			}
		}
		else
		{
			System.out.println("not found node when setting ParkingStatus!");
			return false;
		}
	}
	public boolean cmdSetBattery(int address,int battery)
	{
		Node node=searchByAddress(address);
		if(node!=null)
		{
			node.setBattery(battery);;
			return true;
		}
		else
		{
			System.out.println("not found node when setting Battery!");
			return false;
		}
	}
	@Override
	public Iterator<E> iterator() {
		// TODO 自动生成的方法存根
		return new Itr<E>(this);
	}
	private class Itr<E> implements Iterator<E>
	{
		Node node;
		int index=0;
		int num,returned=0;		
		public Itr(NetManager<E> nm)
		{
			node=(Node)nm;
			num=node.getChildrenNumber()+1;
		}
		@Override
		public boolean hasNext() {
			// TODO 自动生成的方法存根
			return returned<num;
		}
		@Override
		public E next() {
			// TODO 自动生成的方法存根
			return (E)find();
		}
		@SuppressWarnings("unused")
		private Node find()
		{			
				int childlength=node.childNodes.size();
				if(childlength>0)
				{
					for(;index<childlength;index++)
					{
						Node childreNode=node.childNodes.get(index);
						if(childreNode.childNodes==null)
						{	
							index=node.childNodes.indexOf(childreNode)+1;
							returned++;
							return childreNode;
						}	
						else if(childreNode.childNodes.size()==0)
						{	
							index=node.childNodes.indexOf(childreNode)+1;
							returned++;
							return childreNode;
						}	
						else 
						{
							index=0;
							node=childreNode;
							return find();
						}
					}
					if(index==childlength)
					{
						Node child=node;
						node=node.getParent();
						if(node!=null)
						index=node.childNodes.indexOf(child)+1;
						returned++;
						return child;
					}
					
					
				}
				else{
					returned++;
					return node;
				}
				return null;
						
								
		}
		
	}
	
	
}
