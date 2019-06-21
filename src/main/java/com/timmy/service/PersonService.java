package com.timmy.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.timmy.entity.Person;

public interface PersonService {

	int deleteByPrimaryKey(int id);

    int insert(Person record);

    int insertSelective(Person record);


    Person selectByPrimaryKey(int id);

   
    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);
    
    List<Person> selectAll();
    public void setUserToDevice(int enrollId,String name,int backupnum,int admin,String records);
    
    public void getSignature(int enrollId);
}
