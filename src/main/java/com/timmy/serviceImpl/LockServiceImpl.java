package com.timmy.serviceImpl;

import org.springframework.stereotype.Service;

import com.timmy.entity.LockGroup;
import com.timmy.service.LockGroupService;
import com.timmy.websocket.WebSocketPool;

@Service
public class LockServiceImpl implements LockGroupService{

	@Override
	public void setLockGroup(LockGroup lockGroup) {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder();
		 sb.append("{\"cmd\":\"setdevlock\",\"lockgroup\":[");
		
		 
         sb.append("{\"group\":" + lockGroup.getGroup1() + "},");
         sb.append("{\"group\":" + lockGroup.getGroup2() + "},");
         sb.append("{\"group\":" + lockGroup.getGroup3() + "},");
         sb.append("{\"group\":" + lockGroup.getGroup4()+ "},");
         sb.append("{\"group\":" + lockGroup.getGroup5()+ "}]}");
         
         String message=sb.toString();
         System.out.println("锁组合信息"+message);
         WebSocketPool.sendMessageToAll(message);
		
	}

}
