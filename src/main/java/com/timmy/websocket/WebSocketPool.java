package com.timmy.websocket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocket.READYSTATE;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.timmy.entity.SetUserReturnInfo;
import com.timmy.entity.UserInfo;
import com.timmy.service.EnrollInfoService;
public class WebSocketPool {

	@Autowired
	EnrollInfoService enrollInfoService;
	
	private static final Map<WebSocket, String> wsUserMap = new HashMap<WebSocket, String>();
    
	//public static  SetUserReturnInfo setUserReturnInfo=new SetUserReturnInfo() ; 
	public static  final List<SetUserReturnInfo>listSetUserReturn=new ArrayList<SetUserReturnInfo>();
	 
	
	
	
    /**
     * 通过websocket连接获取其对应的用户
     * 
     * @param conn
     * @return
     */
    public static String getDeviceByWs(WebSocket conn) {
        return wsUserMap.get(conn);
    }

    /**
     * 根据userName获取WebSocket,这是一个list,此处取第一个
     * 因为有可能多个websocket对应一个userName（但一般是只有一个，因为在close方法中，我们将失效的websocket连接去除了）
     * 
     * @param user
     */
    public static WebSocket getWsByDevice(String deviceSn) {
        Set<WebSocket> keySet = wsUserMap.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String cuser = wsUserMap.get(conn);
                if (cuser.equals(deviceSn)) {
                    return conn;
                }
            }
        }
        return null;
    }

    /**
     * 向连接池中添加连接
     * 
     * @param inbound
     */
    public static void addDevice(String deviceSn, WebSocket conn) {
        wsUserMap.put(conn, deviceSn); // 添加连接
    }

    /**
     * 获取所有连接池中的用户，因为set是不允许重复的，所以可以得到无重复的user数组
     * 
     * @return
     */
    
    
    public static Collection<String> getOnlineDevice() {
        List<String> setDevices = new ArrayList<String>();
        Collection<String> setDevice = wsUserMap.values();
        for (String d : setDevice) {
        	setDevices.add(d);
        }
        return setDevices;
    }

    /**
     * 移除连接池中的连接
     * 
     * @param inbound
     */
    public static boolean removeDevices(WebSocket conn) {
        if (wsUserMap.containsKey(conn)) {
            wsUserMap.remove(conn); // 移除连接
            return true;
        } else {
            return false;
        }
    }

    /**
     * 向特定的用户发送数据
     * 
     * @param user
     * @param message
     */
    public static void sendMessageToDevices(WebSocket conn, String message) {
        if (null != conn && null != wsUserMap.get(conn)) {
            conn.send(message);
        }
    }

    /**
     * 向所有的用户发送消息
     * 
     * @param message
     */
    public static  void sendMessageToAll(String message) {
    //	System.out.println("发送数据了");
        Set<WebSocket> keySet = wsUserMap.keySet();
     
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String device = wsUserMap.get(conn);
                if (device != null) {
                    conn.send(message);
                  
                }
            }
        }
        
        
    }
 
    /**
     * 初始化系统
     * @param conn
     * @param message
     */
   
    
    
    
    public static void initSystem(WebSocket webSocket,String message){
    	
    }

    public static void setUserinfoReturn(WebSocket webSocket,SetUserReturnInfo setUserReturnInfo){
    	//jsonNode=message;
    	listSetUserReturn.add(setUserReturnInfo);
    	System.out.println("sssssss"+listSetUserReturn);
    	
    }
    
    public static void getUserList(WebSocket webSocket){
     String  message="{\"cmd\":\"getuserlist\",\"stn\":true}";
     //arg0.send("{\"cmd\":\"getuserlist\",\"stn\":false}");
       webSocket.send(message);
   /*  if (null != conn && null != wsUserMap.get(conn)) {
         conn.send(message);
     }*/
    }
    
  
    
	public  boolean sendSetUserInfo(){
		List<UserInfo>userInfos=enrollInfoService.usersToSendDevice();
		//List<UserInfo>userInfos=enrollInfoService.usersToSendDevice();
		 System.out.println("sssss"+userInfos.size());
		//System.out.println(userInfos);
		   int i=0;
		//while(i<userInfos.size()){
			UserInfo userInfo=userInfos.get(0);
			int enrollId=userInfo.getEnrollId();
			String name=userInfo.getName();
			int backupnum=userInfo.getBackupnum();
			int admin=userInfo.getAdmin();
			String records=userInfo.getRecord();
			String message="{\"cmd\":\"setuserinfo\",\"enrollid\":"+enrollId+ ",\"name\":\"" + name +"\",\"backupnum\":" + backupnum
					+ ",\"admin\":" + admin + ",\"record\":\"" + records + "\"}"; 
			 WebSocketPool.sendMessageToAll(message);
		//	SetUserReturnInfo setUserReturnInfo=WebSocketPool.getUserResult();
			 System.out.println("发送状态"+listSetUserReturn);
		/*	if(setUserReturnInfo.getResult()){
			 i++;*/
		//	}
		
			//i++;
		//}
		// System.out.println(WebSocketPool.getUserResult());
		return WSServer.setUserResult;
		
	}
	
	
}
