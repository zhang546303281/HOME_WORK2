package com.hw.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hw.demo.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {


}
