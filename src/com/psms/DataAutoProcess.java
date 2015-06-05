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
	final int NUMOFFRAME=13;//ÿ������֡����
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
		for(int i=0;i<inlength;i++)  //�ж��Ƿ񱨰���֡��ʼͷ
		{
			if(indata[i]==START)
			{
				cursor=i;
				isstart=true;
				break;
			}
		}
		if(isstart)           //��ʼ�����������
		{
			
			for(int i=cursor;i<inlength;i++)   //�����������м���֡ͷ
			{
				if(indata[i]==START)
					start++;
			}
			for(int i=cursor;i<inlength;i++)  //�������ݵ�data[]�����ж�֡ͷ֡β�����Ƿ���ͬ
			{
				data[length+i-cursor]=indata[i];
				if(indata[i]==END)
					end++;
				if(end==start)    //֡ͷ֡β������ͬ������ѭ��������֮������ݣ��������ݳ���
				{
					length+=i-cursor+1;
					break;
				}
				if(length>100)  //�����ݳ��ȴ���1000��ʱ�򣬶������е����ݣ����¿�ʼ
				{
					start=0;
					end=0;
					length=0;
					isstart=false;
					return;
				}
				
			}
			if(start>end)  //֡ͷ֡β������ͬ���������ݳ���,�ȴ���һ������
				length+=inlength-cursor;
			else if(start<end)
				;
			else if (start==end)  //������ͬ����ʼ�����ݽ��н���   ���ݸ�ʽ����0λΪ֡ͷ����1λΪ֡���ȣ���2λΪУ��λ�����һλΪ֡β������Ϊ����λ
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
						//log.WriteLog("����֡ͷ��������֡����"+framelen+" ��"+i+"֡ "+" ��"+frame+"֡ "+"���ݣ�"+new String(buff));  //��¼������־
					}
					else if(buff[framelen-1]!=END)
						;
					//	log.WriteLog("����֡β��������֡����"+framelen+" ��"+i+"֡ "+" ��"+frame+"֡ "+"���ݣ�"+new String(buff));  //��¼������־
				
				
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
		if(indata[1]==15)   		//	�ڵ���Ϣ
										   //��0λΪ֡ͷ����1λΪ֡���ȣ���2ΪУ��λ����3λΪ��־λ����4-13λΪ����λ����14λΪ֡β
		{
			byte flag=indata[3];
			boolean incorrect=false;
			byte parity=0;
			String str=new String(indata, 4, 5);
			for(int i=4;i<14;i++)  //�ж�10λ�����Ƿ�ȫ��Ϊ0-9���ַ�
			{
				if(indata[i]<48||indata[i]>57)
				{
					incorrect=true;
			//		log.WriteLog("�����ַ���ȫΪ���֣�"+new String(indata,4,5));
				}
				parity+=indata[i]-48;
			}
			if(parity!=indata[2])
			{
				incorrect=true;
//				log.WriteLog("����У�����У��֡ :"+indata[2]+"  ����У��ֵ��"+parity+" ���ݣ�"+new String(indata,4,5));
			//	System.out.println("parity is wrong�� parity:"+indata[2]+"  local:" +parity+"  data:"+str);
				
			}
			
			if(!incorrect)   //���������Ч
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
		else if(indata[1]==5&&indata[2]==0)  //ָ��
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
