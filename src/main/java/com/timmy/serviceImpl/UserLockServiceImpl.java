package com.timmy.serviceImpl;

import org.springframework.stereotype.Service;

import com.timmy.entity.UserLock;
import com.timmy.service.UserLockService;
import com.timmy.websocket.WebSocketPool;

@Service
public class UserLockServiceImpl implements UserLockService {

	@Override
	public void setUserLock(UserLock userLock,
			String starTime, String endTime) {
		StringBuilder sb=new StringBuilder();
		sb.append("{\"cmd\":\"setuserlock\",\"count\":1,\"record\":[{");
		  
		  int weekZone2=2;
		  sb.append("\"enrollid\":" +userLock.getEnrollId()+ ",");
          sb.append("\"weekzone\":" +userLock.getWeekZone()+ ",");
         
          sb.append("\"group\":" +userLock.getGroup()+ ",");
          sb.append("\"starttime\":\""+starTime+"\",");
          sb.append("\"endtime\":\""+endTime+"\"}]}");
		String message=sb.toString();
	//	System.out.println(sb);
		WebSocketPool.sendMessageToAll(message);
		
		
	}
	
}
