package com.timmy.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timmy.entity.Person;
import com.timmy.mapper.PersonMapper;
import com.timmy.service.PersonService;
import com.timmy.websocket.WebSocketPool;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired 
	PersonMapper personMapper;

	
	@Override
	public int updateByPrimaryKeySelective(Person record) {
		// TODO Auto-generated method stub
		return personMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Person record) {
		// TODO Auto-generated method stub
		return personMapper.updateByPrimaryKey(record);
	}



	


	@Override
	public int insertSelective(Person person) {
		// TODO Auto-generated method stub
		return personMapper.insertSelective(person);
	}



	@Override
	public int insert(Person person) {
		// TODO Auto-generated method stub
		return personMapper.insert(person);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return personMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Person selectByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return personMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Person> selectAll() {
		// TODO Auto-generated method stub
		return personMapper.selectAll();
	}

      public void setUserToDevice(int enrollId,String name,int backupnum,int admin,String records) {
		
    	   try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		String message="{\"cmd\":\"setuserinfo\",\"enrollid\":"+enrollId+ ",\"name\":\"" + name +"\",\"backupnum\":" + backupnum
					+ ",\"admin\":" + admin + ",\"record\":\"" + records + "\"}"; 
    		WebSocketPool.sendMessageToAll(message);
		
	  }
      
      public void getSignature(int enrollId) {
    	 List<Person>persons=personMapper.selectAll();
    	 try {
 			Thread.sleep(400);
 		} catch (InterruptedException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	 String message="{\"cmd\":\"getuserinfo\",\"enrollid\":"+enrollId+",\"backupnum\":0}";
 	//	System.out.println("message"+message);
         WebSocketPool.sendMessageToAll(message);
	}
}
