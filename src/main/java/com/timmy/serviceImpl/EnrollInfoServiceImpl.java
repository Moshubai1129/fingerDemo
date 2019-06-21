package com.timmy.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timmy.entity.EnrollInfo;
import com.timmy.entity.Person;
import com.timmy.entity.UserInfo;
import com.timmy.mapper.EnrollInfoMapper;
import com.timmy.mapper.PersonMapper;
import com.timmy.service.EnrollInfoService;

@Service
public class EnrollInfoServiceImpl implements EnrollInfoService{
	
	@Autowired
	EnrollInfoMapper enrollInfoMapper;
	
	@Autowired
	PersonMapper personMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return enrollInfoMapper.deleteByPrimaryKey(id);
	}

	

	@Override
	public int insertSelective(EnrollInfo record) {
		// TODO Auto-generated method stub
		return enrollInfoMapper.insertSelective(record);
	}

	@Override
	public EnrollInfo selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return enrollInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(EnrollInfo record) {
		// TODO Auto-generated method stub
		return enrollInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(EnrollInfo record) {
		// TODO Auto-generated method stub
		return enrollInfoMapper.updateByPrimaryKey(record);
	}



	@Override
	public int insert(int enrollid, int backupnum, String signature) {
		// TODO Auto-generated method stub
		return enrollInfoMapper.insert(enrollid, backupnum, signature);
	}



	@Override
	public EnrollInfo selectByBackupnum(int enrollId, int backupnum) {
		// TODO Auto-generated method stub
		return enrollInfoMapper.selectByBackupnum(enrollId, backupnum);
	}



	@Override
	public List<UserInfo> usersToSendDevice() {
		List<Person>persons=personMapper.selectAll();
		List<EnrollInfo>enrollInfos=enrollInfoMapper.selectAll();
		List<UserInfo>userInfos=new ArrayList<UserInfo>();
		for (int i = 0; i < persons.size(); i++) {
			//Person person=new Person();
			for (int j = 0; j < enrollInfos.size(); j++) {
				if(persons.get(i).getId()==enrollInfos.get(j).getEnrollId()){
					UserInfo userInfo=new UserInfo();
					userInfo.setAdmin(persons.get(i).getRollId());
					userInfo.setBackupnum(enrollInfos.get(j).getBackupnum());
					userInfo.setEnrollId(persons.get(i).getId());
					userInfo.setName(persons.get(i).getName());
					userInfo.setRecord(enrollInfos.get(j).getSignatures());
					
					userInfos.add(userInfo);
				}
				
			}
		}

		return userInfos;
	}



	@Override
	public List<EnrollInfo> selectAll() {
		// TODO Auto-generated method stub
		return enrollInfoMapper.selectAll();
	}

	
	
}
