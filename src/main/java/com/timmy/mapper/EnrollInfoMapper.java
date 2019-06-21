package com.timmy.mapper;

import com.timmy.entity.EnrollInfo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnrollInfoMapper {
  
    int deleteByPrimaryKey(Integer id);

    int insert(@Param("enrollId")int enrollid,@Param("backupnum")int backupnum,@Param("signatures")String signature);

    int insertSelective(EnrollInfo record);

   

    EnrollInfo selectByPrimaryKey(Integer id);

   
    int updateByPrimaryKeySelective(EnrollInfo record);

    int updateByPrimaryKey(EnrollInfo record);
    
    EnrollInfo selectByBackupnum(@Param("enrollId")int enrollId,@Param("backupnum")int backupnum);
    
    List<EnrollInfo> selectAll();
}