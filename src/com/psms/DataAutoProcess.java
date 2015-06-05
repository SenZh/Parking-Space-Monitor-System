package com.psms;

class DataAutoProcess
{
	main_gui window;
	NetManager<Node> coor;
	UartProcess uart;
	HandShaking handShaking;
	Thread handThread;
	byte[] data=new byte[1024];
	int length=0;
	int start=0,end=0;
	boolean isstart=false;
	boolean handfirst=true;
	final byte START=64;//"@"
	final byte END=35;//"#"
	final int NUMOFFRAME=13;//每个数据帧长度
	final byte DEVICEID=65;
	final byte PARENT=66;
	final byte DEVICETYPE=67;
	final byte BATTERY=68;
	final byte PARKING=69;
	final byte HAND=90;
	public DataAutoProcess(main_gui win,NetManager<Node> netmgr)
	{
		window=win;
		coor=netmgr;
	}
	public void setHandShaking(HandShaking hs)
	{
		handShaking=hs;
	}
	public void Process(byte[] indata,int inlength)
/*	{
		if(indata[0]==START)
			isstart=true;
		if(isstart)
		{
		  for(int i=0;i<inlength;i++)
		  {
			  if(indata[i]==START)
				 start++;
			data[length+i]=indata[i];		
		  }
		  	length+=inlength;
		  if(indata[inlength-1]==END)
			 DataProcess();	  
		
		}
		else {
			
		}
	
	}
*/
	
 {
		int cursor=0;
		if(!isstart)
		for(int i=0;i<inlength;i++)  //判断是否报包含帧开始头
		{
			if(indata[i]==START)
			{
				cursor=i;
				isstart=true;
				break;
			}
		}
		if(isstart)           //开始分析打包数据
		{
			
			for(int i=cursor;i<inlength;i++)   //计算数据中有几个帧头
			{
				if(indata[i]==START)
					start++;
			}
			for(int i=cursor;i<inlength;i++)  //复制数据到data[]，并判断帧头帧尾数量是否相同
			{
				data[length+i-cursor]=indata[i];
				if(indata[i]==END)
					end++;
				if(end==start)    //帧头帧尾数量相同，结束循环，丢弃之后的数据，更新数据长度
				{
					length+=i-cursor+1;
					break;
				}
				if(length>100)  //当数据长度大于1000的时候，丢弃所有的数据，重新开始
				{
					start=0;
					end=0;
					length=0;
					isstart=false;
					return;
				}
				
			}
			if(start>end)  //帧头帧尾数量不同，更新数据长度,等待下一包数据
				length+=inlength-cursor;
			else if(start<end)
				;
			else if (start==end)  //数量相同，开始对数据进行解析   数据格式：第0位为帧头，第1位为帧长度，第2位为校验位，最后一位为帧尾，其余为数据位
			{
				final int frame=start;
				int frame_cursor=0;
				start=0;
				end=0;
				length=0;
				isstart=false;
				for(int i=0;i<frame;i++)
				{
					int framelen=data[frame_cursor+1];	
					byte[] buff=new byte[framelen];
					System.arraycopy(data,frame_cursor, buff, 0, framelen);
					frame_cursor+=framelen;
					if(buff[0]==START&&buff[framelen-1]==END)
					{
					//	System.out.println(new String(buff, 3, framelen-4)+"  frame:"+frame+"  framelen:"+framelen);
						DataProcess(buff);
					}
					else if(buff[0]!=START)
					{
						//log.WriteLog("数据帧头出错：计算帧长度"+framelen+" 第"+i+"帧 "+" 共"+frame+"帧 "+"数据："+new String(buff));  //记录出错日志
					}
					else if(buff[framelen-1]!=END)
						;
					//	log.WriteLog("数据帧尾出错：计算帧长度"+framelen+" 第"+i+"帧 "+" 共"+frame+"帧 "+"数据："+new String(buff));  //记录出错日志
				
				
				}
			}
				
		}
			
	}
	void DataProcess(byte[] indata)
/*	{		
		for(int i=0;i<start;i++)
		{
			
			byte cmd=data[i*NUMOFFRAME+1];
			byte[] data1=new byte[5];
			byte[] data2=new byte[5];
			for(int x=0;x<5;x++)
			{
				data1[x]=data[i*NUMOFFRAME+2+x];
				data2[x]=data[i*NUMOFFRAME+7+x];
			}
			
			int address=Integer.parseInt(new String(data1));
			int data=Integer.parseInt(new String(data2));
			switch (cmd) {
			case DEVICEID:		
				coor.cmdSetDeviceId(address, data);
				break;			
			case DEVICETYPE:
				coor.cmdSetDeviceType(address, data);
				break;
			case PARENT:
				coor.addNoe(address,data);
				break;
			case PARKING:
				coor.cmdSetParking(address, data);
				break;
			case BATTERY:
				coor.cmdSetBattery(address, data);
			case HAND:
				if(handShaking.isRun())
				{
					handShaking.setComStatus();	
				
				}
				else {				
						handShaking.setStart();
						handThread=new Thread(handShaking);
						handThread.start();
				}
				break;
				
			default:
				System.out.println("Unknown cmd!");
				break;
			}
			
			
		}
		clear();
	}
*/
	{
		if(indata[1]==15)   		//	节点信息
										   //第0位为帧头，第1位为帧长度，第2为校验位，第3位为标志位，第4-13位为数据位，第14位为帧尾
		{
			byte flag=indata[3];
			boolean incorrect=false;
			byte parity=0;
			String str=new String(indata, 4, 5);
			for(int i=4;i<14;i++)  //判断10位数据是否全部为0-9的字符
			{
				if(indata[i]<48||indata[i]>57)
				{
					incorrect=true;
			//		log.WriteLog("数据字符不全为数字："+new String(indata,4,5));
				}
				parity+=indata[i]-48;
			}
			if(parity!=indata[2])
			{
				incorrect=true;
//				log.WriteLog("数据校验出错：校验帧 :"+indata[2]+"  本地校验值："+parity+" 数据："+new String(indata,4,5));
			//	System.out.println("parity is wrong！ parity:"+indata[2]+"  local:" +parity+"  data:"+str);
				
			}
			
			if(!incorrect)   //如果数据有效
			{
				byte[] data1=new byte[5];
				byte[] data2=new byte[5];
				for(int i=0;i<5;i++)
				{
					data1[i]=indata[i+4];
					data2[i]=indata[i+9];
				}
				int address=Integer.parseInt(new String(data1));
				int data=Integer.parseInt(new String(data2));
				switch(flag)
				{
				case DEVICEID:		
					coor.cmdSetDeviceId(address, data);
					break;			
				case DEVICETYPE:
					coor.cmdSetDeviceType(address, data);
					break;
				case PARENT:
					coor.addNoe(address,data);
					break;
				case PARKING:
					coor.cmdSetParking(address, data);
					break;
				case BATTERY:
					coor.cmdSetBattery(address, data);
				default:
			//		System.out.println("Unknown cmd!");
					break;
				
				}
			}
		}
		else if(indata[1]==5&&indata[2]==0)  //指令
		{
			switch(indata[3])
			{
			case HAND:
				if(handShaking.isRun())
				{
					handShaking.setComStatus();	
				
				}
				else {				
						handShaking.setStart();
						handThread=new Thread(handShaking);
						handThread.start();
				}
				break;
			default:
				System.out.println("Unknown cmd!");
				break;
			}
		}
		else
			System.out.println("Unknown Data type!");
	}

	
		
}
