package com.timmy.websocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

















import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timmy.entity.Device;
import com.timmy.entity.EnrollInfo;
import com.timmy.entity.Person;
import com.timmy.entity.Records;
import com.timmy.entity.SetUserReturnInfo;
import com.timmy.entity.UserTemp;
import com.timmy.mapper.DeviceMapper;
import com.timmy.service.DeviceService;
import com.timmy.service.EnrollInfoService;
import com.timmy.service.PersonService;
import com.timmy.service.RecordsService;
import com.timmy.service.UserLockService;



public class WSServer extends WebSocketServer{


	@Autowired
	DeviceService deviceService;
	
	@Autowired
	RecordsService recordsService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	EnrollInfoService enrollInfoService;
	
	@Autowired
	UserLockService userLockService;
	
	int j=0;
    int h=0;
    int e=0;
   static int l;
   public static boolean setUserResult;
  public static Logger logger = LoggerFactory.getLogger(WSServer.class);
  
	  
	  public WSServer(InetSocketAddress address) {
	        super(address);
	        logger.info("地址" + address);
	    }

	    public WSServer(int port) throws UnknownHostException {
	        super(new InetSocketAddress(port));
	        logger.info("端口" + port);
	    }
	  
	  
	@Override
	public void onOpen(org.java_websocket.WebSocket conn,
			ClientHandshake handshake) {
		// TODO Auto-generated method stub
	//	deviceService=(DeviceService)ContextLoader.getCurrentWebApplicationContext().getBean(DeviceService.class);
		  System.out.println("有人连接Socket conn:" + conn);
	      //  l++;
		 logger.info("有人连接Socket conn:" + conn.getRemoteSocketAddress());
		 l++;
		
	}

	@Override
	public void onClose(org.java_websocket.WebSocket conn, int code,
			String reason, boolean remote) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(org.java_websocket.WebSocket conn, String message) {
		// TODO Auto-generated method stub
		// logger.info("receiver:" + message);
		//  System.out.println("连接数据"+conn);
		 
		  // WebSocketPool.getUserList(conn);
		//  WebSocketPool.sendMessageToAll("{\"cmd\":\"getuserlist\",\"stn\":true}");
	        ObjectMapper objectMapper = new ObjectMapper();
	        String ret;
	       
	        
			//userLockService.setUserLock(1, 2, 1, "2019-06-06 00:00:00", "2029-06-06 00:00:00");
	            try {
	    	        String msg = message.replaceAll(",]", "]");
	    	        
					JsonNode jsonNode = (JsonNode)objectMapper.readValue(msg, JsonNode.class);
				//	System.out.println("数据"+jsonNode);
					if (jsonNode.has("cmd")) {
						ret=jsonNode.get("cmd").asText();
						if ("reg".equals(ret)) {
							try {
								
							this.getDeviceInfo(jsonNode, conn);
								
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								conn.send("{\"ret\":\"reg\",\"result\":false,\"reason\":1}");
								e.printStackTrace();
							}
						}else if("sendlog".equals(ret)){
							
							try {
								this.getAttandence(jsonNode,conn);
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								conn.send("{\"ret\":\"sendlog\",\"result\":false,\"reason\":1}");
								e.printStackTrace();
							}
						}else if("senduser".endsWith(ret)){
							//System.out.println(jsonNode);
						/*	try {
								this.getEnrollInfo(jsonNode,conn);
							
							} catch (Exception e) {
								// TODO Auto-generated catch block
								conn.send("{\"ret\":\"senduser\",\"result\":false,\"reason\":1}");
								e.printStackTrace();
							}*/
						}
						
					}else if(jsonNode.has("ret")){
						ret=jsonNode.get("ret").asText();
						//boolean result;
						if("getuserlist".equals(ret)){
						//	System.out.println(jsonNode);
							this.getUserList(jsonNode,conn);
						}else if("getuserinfo".equals(ret)){
							this.getUserInfo(jsonNode,conn);
						}else if("setuserinfo".equals(ret)){
							Boolean result=jsonNode.get("result").asBoolean();
							//WebSocketPool.setUserResult(result);
							//setUserResult=result;
							SetUserReturnInfo setUserReturnInfo=new SetUserReturnInfo();
							setUserReturnInfo.setResult(result);
							setUserReturnInfo.setRet("setuserinfo");
							setUserReturnInfo.setSn(jsonNode.get("sn").asText());
							WebSocketPool.setUserinfoReturn(conn, setUserReturnInfo);
							System.out.println("下发数据"+jsonNode);
						}else if("getalllog".equals(ret)){
							System.out.println("获取所有打卡记录"+jsonNode);
							try {
								this.getAllLog(jsonNode,conn);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}else if("deleteuser".equals(ret)){
							System.out.println("删除人员"+jsonNode);
						}else if("initsys".equals(ret)){
							System.out.println("初始化系统"+jsonNode);
						}else if("setdevlock".equals(ret)){
							System.out.println("设置天时间段"+jsonNode);
						}else if("setuserlock".equals(ret)){
							System.out.println("门禁授权"+jsonNode);
						}
						
					}
					
					
				}  catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

	

	
        /* public static void main(String[] args) {
        		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring-mybatis.xml"); 
            	WSServer ws=(WSServer) ac.getBean("webSocket");
        		ws.start();
         }*/
	

	

	
	@Override
	public void onError(org.java_websocket.WebSocket conn, Exception ex) {
		// TODO Auto-generated method stub
		
	}

	public void onStart() {
		
		// TODO Auto-generated method stub
		
	}
	
	
	

	

	 /**
	     * 获取所有连接池中的用户，因为set是不允许重复的，所以可以得到无重复的user数组
	     * 
	     * @return
	     */
	 
	 
	 
	 
	    
	   //获得连接设备信息
	public void getDeviceInfo(JsonNode jsonNode,org.java_websocket.WebSocket args1){
		String sn=jsonNode.get("sn").asText();
		System.out.println("序列号"+sn);
		if(sn!=null){
		//	System.out.println("hhdggdggd");
			WebSocketPool.addDevice(sn, args1);
			Device d1=deviceService.selectDeviceBySerialNum(sn);
			
			if(d1==null){
				int i=	deviceService.insert(sn, 1);
				System.out.println(i);
			}else{
				//deviceService.updateByPrimaryKey()
				deviceService.updateStatusByPrimaryKey(d1.getId(), 1);
			}
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 //long l=System.currentTimeMillis();
	      
		    args1.send("{\"ret\":\"reg\",\"result\":true,\"cloudtime\":\"" + sdf.format(new Date()) + "\"}");	
		}
		
	  	
	}
    
	/* private void deviceJoin(org.java_websocket.WebSocket conn,String deviceSn){
	        WebSocketPool.addDevice(deviceSn, conn);
	    }*/
   //获得打卡记录，包括机器号
	private void getAttandence(JsonNode jsonNode,
			org.java_websocket.WebSocket conn) {
		// TODO Auto-generated method stub
		String sn=jsonNode.get("sn").asText();
		int count=jsonNode.get("count").asInt();
		int logindex=jsonNode.get("logindex").asInt();
		List<Records>recordAll=new ArrayList<Records>();
		System.out.println(jsonNode);
		JsonNode records=jsonNode.get("record");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (count>0) {
			Iterator iterator=records.elements();
			while(iterator.hasNext()){
				JsonNode type=(JsonNode)iterator.next();
				int enrollId=type.get("enrollid").asInt();
				String timeStr=type.get("time").asText();
				int mode=type.get("mode").asInt();
				int inOut=type.get("inout").asInt();
				int event=type.get("event").asInt();
				Records record=new Records();
				record.setDeviceSerialNum(sn);
				record.setEnrollId(enrollId);
				record.setEvent(event);
				record.setIntout(inOut);
				record.setMode(mode);
				record.setRecordsTime(timeStr);
				recordAll.add(record);	
				
				 
               
			}
			conn.send("{\"ret\":\"sendlog\",\"result\":true,\"cloudtime\":\"" + sdf.format(new Date()) + "\"}");
		}
	    
	    System.out.println(recordAll);
		for (int i = 0; i < recordAll.size(); i++) {
			Records recordsTemp=recordAll.get(i);	
			recordsService.insert(recordsTemp);
		}
		
	}

	//获取机器推送注册信息
	private void getEnrollInfo(JsonNode jsonNode,
			org.java_websocket.WebSocket conn) {
		// TODO Auto-generated method stub
		//System.out.println("连接数据类型"+(conn.getData()).getClass());
		//int enrollId=jsonNode.get("enrollid").asInt();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sn=jsonNode.get("sn").asText();
		String signatures1=jsonNode.get("record").asText();
		
			int backupnum=jsonNode.get("backupnum").asInt();
			if(backupnum!=10&&backupnum!=11){
				int enrollId=jsonNode.get("enrollid").asInt();
				String name=jsonNode.get("name").asText();
				int rollId=jsonNode.get("admin").asInt();
				String signatures=jsonNode.get("record").asText();
				Person person=new Person();
				person.setId(enrollId);
				person.setName(name);
				person.setRollId(rollId);
			//	System.out.println("员工号"+enrollId);
				personService.selectByPrimaryKey(enrollId);
				//System.out.println("人员信息"+personService.selectByPrimaryKey(enrollId));
				if (personService.selectByPrimaryKey(enrollId)==null) {
					personService.insert(person);
				}
                if (enrollInfoService.selectByBackupnum(enrollId, backupnum)==null) {
                	enrollInfoService.insert(enrollId, backupnum, signatures);
				}
				
				conn.send("{\"ret\":\"sendlog\",\"result\":true,\"cloudtime\":\"" + sdf.format(new Date()) + "\"}");
			}
		
	}

       //获取用户列表，服务器主动发出请求
     	private void getUserList(JsonNode jsonNode,
			org.java_websocket.WebSocket conn) {
		    List<UserTemp>userTemps=new ArrayList<UserTemp>();
     		boolean result=jsonNode.get("result").asBoolean();
     		
     		int count;
     		JsonNode records=jsonNode.get("record");
     		System.out.println("用户列表"+records);
     		if(result){
     			count=jsonNode.get("count").asInt();
     			//System.out.println("用户数："+count);
     			if (count>0) {
     				 Iterator iterator=records.elements();
     	     		  while (iterator.hasNext()) {
     	     			JsonNode type=(JsonNode)iterator.next();
     					int enrollId=type.get("enrollid").asInt();
     					//int enrollId=Integer.valueOf(enrollId1);
     					int admin=type.get("admin").asInt();
     					int backupnum=type.get("backupnum").asInt();
     					UserTemp userTemp=new UserTemp();
     					userTemp.setEnrollId(enrollId);
     					userTemp.setBackupnum(backupnum);
     					userTemp.setAdmin(admin);
     					userTemps.add(userTemp);
     					
     					
     				}
     	     		  
     	     		  
				}
     		 
     			  //conn.send("{\"cmd\":\"getuserlist\",\"stn\":false}");
     		}
     		//System.out.println(userTemps);
     		for (int i = 0; i < userTemps.size(); i++) {
				UserTemp uTemp=new UserTemp();
				uTemp=userTemps.get(i);
				if(personService.selectByPrimaryKey(uTemp.getEnrollId())==null){
					Person personTemp=new Person();
					personTemp.setId(uTemp.getEnrollId());
					personTemp.setName("");
					personTemp.setRollId(uTemp.getAdmin());
					personService.insert(personTemp);
					
					
				}
			}
     		
	     }


//     	获得用户详细信息
     	private void getUserInfo(JsonNode jsonNode,
    			org.java_websocket.WebSocket conn) {
    		// TODO Auto-generated method stub
     		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     	//	System.out.println("用户具体信息"+jsonNode);
     		Boolean result=jsonNode.get("result").asBoolean();
     		
     		String sn=jsonNode.get("sn").asText();
     		int backupnum=jsonNode.get("backupnum").asInt();
     		System.out.println(jsonNode);
     		if(result){
     			if(backupnum!=10&&backupnum!=11){
     				int enrollId=jsonNode.get("enrollid").asInt();
     				String name=jsonNode.get("name").asText();
     				int admin=jsonNode.get("admin").asInt();
     				String signatures=jsonNode.get("record").asText();
     				Person person=new Person();
     				person.setId(enrollId);
     				person.setName(name);
     				person.setRollId(admin);
     				if (personService.selectByPrimaryKey(enrollId)==null) {
    					personService.insert(person);
    				}
                    if (enrollInfoService.selectByBackupnum(enrollId, backupnum)==null) {
                    	enrollInfoService.insert(enrollId, backupnum, signatures);
    				}
     			}
     			
     		//	conn.send("{\"cmd\":\"getuserlist\",\"stn\":false}");
     		}
    		//String sn=jsonNode.get("sn").asText();
    	}
     	
     	//获取全部打卡记录
     	private void getAllLog(JsonNode jsonNode, WebSocket conn) {
    		// TODO Auto-generated method stub
     		Boolean result=jsonNode.get("result").asBoolean();
     		List<Records>recordAll=new ArrayList<Records>();
    		
     		String sn=jsonNode.get("sn").asText();
    		JsonNode records=jsonNode.get("record");
    	///	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     		int count;
     		if (result) {
				count=jsonNode.get("count").asInt();
				if(count>0){
					Iterator iterator=records.elements();
					while(iterator.hasNext()){
						JsonNode type=(JsonNode)iterator.next();
						int enrollId=type.get("enrollid").asInt();
						String timeStr=type.get("time").asText();
						int mode=type.get("mode").asInt();
						int inOut=type.get("inout").asInt();
						int event=type.get("event").asInt();
						Records record=new Records();
					//	record.setDeviceSerialNum(sn);
						record.setEnrollId(enrollId);
						record.setEvent(event);
						record.setIntout(inOut);
						record.setMode(mode);
						record.setRecordsTime(timeStr);
						record.setDeviceSerialNum(sn);
						recordAll.add(record);	
						 
		               
					}
				
				}
			}
     	//	 System.out.println(recordAll);
     		for (int i = 0; i < recordAll.size(); i++) {
     			Records recordsTemp=recordAll.get(i);	
     			recordsService.insert(recordsTemp);
     		}
    		
    	}
}

