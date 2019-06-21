package com.timmy.fingerDeviceDemo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.timmy.entity.AccessDay;
import com.timmy.entity.AccessWeek;
import com.timmy.entity.Person;
import com.timmy.entity.Records;
import com.timmy.entity.UserInfo;
import com.timmy.mapper.AccessDayMapper;
import com.timmy.mapper.AccessWeekMapper;
import com.timmy.mapper.DeviceMapper;
import com.timmy.mapper.EnrollInfoMapper;
import com.timmy.mapper.PersonMapper;
import com.timmy.mapper.RecordsMapper;
import com.timmy.service.AccessDayService;
import com.timmy.service.AccessWeekService;
import com.timmy.service.DeviceService;
import com.timmy.service.EnrollInfoService;
import com.timmy.service.LockGroupService;
import com.timmy.service.PersonService;
import com.timmy.service.RecordsService;
import com.timmy.service.UserLockService;



@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类 

@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"}) 
public class DemoTest {
	
	@Autowired
	DeviceMapper deviceMapper;
	
	@Autowired
	RecordsService recordsService;
	
	@Autowired
	DeviceService deviceService;
	
	@Autowired
	PersonMapper personMaper;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	EnrollInfoMapper enrollInfoMapper;
	
	@Autowired
	EnrollInfoService enrollInfoService;
	
	@Autowired
	AccessDayMapper accessDayMapper;
	
	@Autowired
	AccessDayService accessDayService;
	
	@Autowired
	AccessWeekMapper accessWeekMapper;
	
	@Autowired
	AccessWeekService accessWeekService;
	
	@Autowired
	LockGroupService lockGroupService;
	
	@Autowired
	UserLockService userLockService;
	 @Test
	    public void testDemo() {
	    
	/*	System.out.println(deviceService.selectDeviceBySerialNum("ZX0006827605"));
		// System.out.println(deviceService.insert("122333565", 1));
		System.out.println(deviceService.updateStatusByPrimaryKey(1, 1));
		
		Records records=new Records();
		records.setDeviceSerialNum("1111111");
		records.setEnrollId(1);
		records.setEvent(0);
		records.setIntout(1);
		records.setMode(2);
		records.setRecordsTime("2019-05-28 16:20:20");
		
		recordsService.insert(records);*/
		 /*Person person=new Person();
		 person.setId("00000003333");
		 person.setName("chingzhou");
		 person.setRollId(0);
		 
		 System.out.println(personMaper.insert(person));*/
		 
		/* int enrollId=112;
		 int backupnum=0;
		 String signatures="02sjjwhhwowohebebbebbew82772hwavavsuu928";
		 System.out.println(enrollInfoMapper.insert(enrollId, backupnum, signatures));*/
		 
		// System.out.println(personMaper.selectByPrimaryKey(66));
		 //System.out.println(enrollInfoService.selectByBackupnum(33, 0));
		// System.out.println();
		// System.out.println(personMaper.selectAll());
		// System.out.println(enrollInfoService.selectAll());
	 	// System.out.println(enrollInfoMapper.selectAll());
		 
		 
		 //System.out.println(enrollInfoService.usersToSendDevice());
			/*List<UserInfo>userInfos=enrollInfoService.usersToSendDevice();
			System.out.println(userInfos);*/
		// System.out.println();
		/* SimpleDateFormat sdfDateFormat=new SimpleDateFormat("HH:mm");
		 Date date=new Date();
		 String time1=sdfDateFormat.format(date);
		 AccessDay accessDay=new AccessDay();
		 accessDay.setId(1);
         accessDay.setName("门禁时间段1");	
         accessDay.setSerial("ZXMK16302277");
         
         accessDay.setStartTime1("00:00");
         accessDay.setEndTime1(time1);
         accessDay.setStartTime2("00:00");
         accessDay.setEndTime2("00:00");
         accessDay.setStartTime3("00:00");
         accessDay.setEndTime3("00:00");
         accessDay.setStartTime4("00:00");
         accessDay.setEndTime4("00:00");
         accessDay.setStartTime5("00:00");
         accessDay.setEndTime5("00:00");
         //accessDayMapper.insert(accessDay);
         accessDayService.insert(accessDay);*/
		 
	//	 accessDayService.setAccessDay();
		 
		// System.out.println(accessWeekService.selectAllAccessWeek());
		/*
		 AccessWeek accessWeek=new AccessWeek();
		 
		   accessWeek.setId(2);
		   
		   accessWeek.setSerial("ZXMK16302277");
		   accessWeek.setName("天时间段二");
		   accessWeek.setMonday(1);
		   accessWeek.setTuesday(1);
		   accessWeek.setWednesday(1);
		   accessWeek.setThursday(1);
		   accessWeek.setFriday(1);
		   accessWeek.setSaturday(1);
		   accessWeek.setSunday(1);
		   accessWeekService.insert(accessWeek);*/
		//   accessWeek.setMonday();
		 
		// accessWeekService.setAccessWeek();
		// lockGroupService.setLockGroup(123, 2, 4,5, 332);
		// userLockService.setUserLock(1, 1, 1, "2019-06-06", "2029-06-06");
		// System.out.println(personService.selectAll());
		// System.out.println(personService.deleteByPrimaryKey(1));
		// System.out.println(recordsService.selectAllRecords());
		// System.out.println(accessDayService.selectAll());
		// System.out.println(accessWeekService.selectByPrimaryKey(1));
		 //System.out.println(accessDayService.selectByPrimaryKey(1));
		 
		 
	    }
	

}
