package com.timmy.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.timmy.entity.AccessDay;
import com.timmy.entity.AccessWeek;
import com.timmy.entity.EnrollInfo;
import com.timmy.entity.LockGroup;
import com.timmy.entity.Msg;
import com.timmy.entity.Person;
import com.timmy.entity.Records;
import com.timmy.entity.SetUserReturnInfo;
import com.timmy.entity.UserInfo;
import com.timmy.entity.UserLock;
import com.timmy.mapper.EnrollInfoMapper;
import com.timmy.service.AccessDayService;
import com.timmy.service.AccessWeekService;
import com.timmy.service.EnrollInfoService;
import com.timmy.service.LockGroupService;
import com.timmy.service.PersonService;
import com.timmy.service.RecordsService;
import com.timmy.service.UserLockService;
import com.timmy.websocket.WSServer;
import com.timmy.websocket.WebSocketPool;




@Controller

public class AllController {
	
	/*@Autowired
	EnrollInfoService enrollInfoService;
	*/
	@Autowired
	AccessDayService accessaDayService;
	
    @Autowired
    AccessWeekService accessWeekService;
    
    @Autowired
    LockGroupService lockGroupService;
    
    @Autowired
    UserLockService userLockService;
    
    @Autowired
    EnrollInfoService enrollInfoService;
    
    @Autowired
    PersonService personService;
    
    @Autowired
    RecordsService recordService;
	
	@RequestMapping("/hello1")
	public String hello() {
		return "hello";
	}

	@ResponseBody
    @RequestMapping(value="/sendWs",method = RequestMethod.GET)
    public Msg sendWs() {
		String  message="{\"cmd\":\"getuserlist\",\"stn\":true}";
        WebSocketPool.sendMessageToAll(message);
        List<Person>person=personService.selectAll();
        for (int i = 0; i < person.size(); i++) {
			int enrollId2=person.get(i).getId();
			personService.getSignature(enrollId2);
		}
        return  Msg.success();
    }
	
	@ResponseBody
    @RequestMapping("sendGetUserInfo")
    public String sendGetUserInfo(@RequestParam("enrollId")int enrollId) {
		//String  message="{\"cmd\":\"getuserlist\",\"stn\":true}";
		
		String message="{\"cmd\":\"getuserinfo\",\"enrollid\":"+enrollId+",\"backupnum\":0}";
		System.out.println("message"+message);
        WebSocketPool.sendMessageToAll(message);
        
       
        
        return "hello";
    }
	
	@ResponseBody
	@RequestMapping(value="/setPersonToDevice",method = RequestMethod.GET)
	public Msg sendSetUserInfo(){
	    	List<UserInfo>userInfos=enrollInfoService.usersToSendDevice();
		    
	    	System.out.println(userInfos.size());
		    for (int i = 0; i < userInfos.size(); i++) {
				int enrollId=userInfos.get(i).getEnrollId();
				String name=userInfos.get(i).getName();
				int backupnum=userInfos.get(i).getBackupnum();
				int admin=userInfos.get(i).getAdmin();
				String record=userInfos.get(i).getRecord();
				personService.setUserToDevice(enrollId, name, backupnum, admin, record);
			}
		return Msg.success();
		
	}
	
	@ResponseBody
	@RequestMapping(value="/setOneUser",method = RequestMethod.GET)
	public Msg setOneUserTo(@RequestParam("enrollId")int enrollId) {
		Person person=new Person();
		person=personService.selectByPrimaryKey(enrollId);
		EnrollInfo enrollInfo=new EnrollInfo();
		enrollInfo=enrollInfoService.selectByBackupnum(enrollId, 0);
		if(enrollInfo!=null){
			personService.setUserToDevice(enrollId, person.getName(), enrollInfo.getBackupnum(), person.getRollId(), enrollInfo.getSignatures());
			return Msg.success();
		}else{
			return Msg.fail();
		}
		//personService.setUserToDevice(enrollId, person.getName(), enrollInfo.getBackupnum(), person.getRollId(), enrollInfo.getSignatures());
	//	return Msg.success();
	}
	
	
	@ResponseBody
	@RequestMapping(value="/deletePersonFromDEvice",method = RequestMethod.GET)
	public Msg deleteDeviceUserInfo(@RequestParam("enrollId")int enrollId,@RequestParam("backupnum")String backupnum){	
		String message="{\"cmd\":\"deleteuser\",\"enrollid\":"+enrollId+",\"backupnum\":"+backupnum+"}";	
		WebSocketPool.sendMessageToAll(message);
		personService.deleteByPrimaryKey(enrollId);
		return Msg.success();
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/initSystem",method = RequestMethod.GET)
	public String initSystem() {
		
		String  message="{\"cmd\":\"initsys\"}";
		System.out.println(message);
        WebSocketPool.sendMessageToAll(message);
		return "hello";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/getAllLog",method = RequestMethod.GET)
	public Msg getAllLog() {	
		String  message="{\"cmd\":\"getalllog\",\"stn\":true}";
		System.out.println(message);
        WebSocketPool.sendMessageToAll(message);
		return Msg.success();
		
		
	}
	
	@ResponseBody
	@RequestMapping("addAccessDay")
	public String addAccessDay(@ModelAttribute AccessDay accessDay ) {
		
		return "hello";
	}
	
	@ResponseBody
	@RequestMapping(value="/setAccessDay",method = RequestMethod.POST)
	public Msg setAccessDay(@ModelAttribute AccessDay accessDay) {
		if(accessaDayService.selectByPrimaryKey(accessDay.getId())!=null){
			return Msg.fail();
		}
		accessaDayService.insert(accessDay);
		
		accessaDayService.setAccessDay();
		return Msg.success();
	}
	
	@ResponseBody
	@RequestMapping(value="/setAccessWeek",method = RequestMethod.POST)
	public Msg setAccessWeek(@ModelAttribute AccessWeek accessWeek) {	
	//	accessWeek.set
		if(accessWeekService.selectByPrimaryKey(accessWeek.getId())!=null){
			return Msg.fail();
		}
		accessWeekService.insert(accessWeek);
		accessWeekService.setAccessWeek();	
		return Msg.success();
		
	}
	
	@ResponseBody
	@RequestMapping(value="/setAccessWeek2",method = RequestMethod.GET)
	public String setAccessWeek2(@ModelAttribute AccessWeek accessWeek) {	
	//	accessWeek.set
		return "hello";
		
	}
	@ResponseBody
	@RequestMapping(value="/setLocckGroup",method = RequestMethod.POST)
	public Msg setLockGroup(@ModelAttribute LockGroup lockGroup) {		
		lockGroupService.setLockGroup(lockGroup);	
		return Msg.success();
	}
	
	
	@ResponseBody
	@RequestMapping(value="/setUserLock",method=RequestMethod.POST)
	public Msg setUserLock(@ModelAttribute UserLock userLock) {
		
		userLockService.setUserLock(userLock, "2019-06-06 00:00:00", "2099-03-25 00:00:00");
		return Msg.success();
	}
	
	@RequestMapping(value="/emps")
	@ResponseBody
	public Msg getAllPersonFromDB(@RequestParam(value="pn",defaultValue="1") Integer pn) {
		// 引入 PageHelper 分页插件
				/**
				 * 在查询之前只需要调用，传入要显示的页码，以及每页显示的数量 startPage 后紧跟的查询就是分页查询
				 */
		PageHelper.startPage(pn, 8);
		List<Person>emps=personService.selectAll();
		
		PageInfo page= new PageInfo(emps,5);
		
		return Msg.success().add("pageInfo", page);
		
	}
	
	@RequestMapping(value="/recordPage",method = RequestMethod.GET)
	@ResponseBody
	public String getLogPage() {
		
		return "logRecords";
	}
	
	@RequestMapping(value="/records")
	@ResponseBody
	public Msg getAllLogFromDB(@RequestParam(value="pn",defaultValue="1") Integer pn){
		PageHelper.startPage(pn, 8);
		
		List<Records>records=recordService.selectAllRecords();
		
		PageInfo page=new PageInfo(records, 5);

		return Msg.success().add("pageInfo", page);
		
	}
	
	@RequestMapping(value="/accessDays",method = RequestMethod.GET)
	@ResponseBody
	public Msg getAccessDayFromDB() {
		List<AccessDay>accessDays=accessaDayService.selectAll();
		return Msg.success().add("accessdays", accessDays);
		
	}
	
	
	public Msg uploadUserToDevice(@RequestParam("enrollId")int enrollId) {
		
		Person person=personService.selectByPrimaryKey(enrollId);
		
		return Msg.success();
	}
	
}
