package com.hw.demo.services.biz.impl;

import com.hw.demo.mapper.UserMapper;
import com.hw.demo.model.UserInfo;
import com.hw.demo.services.biz.UserService;
import com.hw.demo.utils.DateUtils;
import com.hw.demo.utils.UUIDGeneratorUtils;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;

@Service("userService")
public class UserServiceImpl implements UserService {

    private boolean isOk = true;

    @Resource
    private UserMapper userMapper;

    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    @ShardingTransactionType(TransactionType.XA)
    public void insertSuccess() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUuid(UUIDGeneratorUtils.getUUID32());
        userInfo.setUserId("Sa");
        userInfo.setUserName("极客时间GKSJ");
        userInfo.setCreateBy("JK001");
        userInfo.setUpdateBy("JK001");
        userInfo.setLogicId("AL001");//逻辑节点不一致使其跨库
        userInfo.setLogicFlag("0");
        userInfo.setCreateDate(DateUtils.getTimeStamp(DateUtils.format19));
        userInfo.setUpdateDate(DateUtils.getTimeStamp(DateUtils.format19));
        userMapper.insert(userInfo);


        UserInfo nUserInfo = new UserInfo();
        BeanUtils.copyProperties(userInfo, nUserInfo, UserInfo.class);
        nUserInfo.setUuid(UUIDGeneratorUtils.getUUID32());
        nUserInfo.setLogicId("MP001");//逻辑节点不一致使其跨库
        userMapper.insert(nUserInfo);
    }

    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    @ShardingTransactionType(TransactionType.XA)
    public void insertFailed() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUuid(UUIDGeneratorUtils.getUUID32());
        userInfo.setUserId("Sa");
        userInfo.setUserName("JKSJ666");
        userInfo.setCreateBy("JK001");
        userInfo.setUpdateBy("JK001");
        userInfo.setLogicId("AC001");//逻辑节点不一致使其跨库
        userInfo.setLogicFlag("0");
        userInfo.setCreateDate(DateUtils.getTimeStamp(DateUtils.format19));
        userInfo.setUpdateDate(DateUtils.getTimeStamp(DateUtils.format19));
        userMapper.insert(userInfo);


        UserInfo nUserInfo = new UserInfo();
        BeanUtils.copyProperties(userInfo, nUserInfo, UserInfo.class);
        nUserInfo.setUuid(UUIDGeneratorUtils.getUUID32());
        userInfo.setUserId("fvbca");
        nUserInfo.setLogicId("MB001");//逻辑节点不一致使其跨库
        userMapper.insert(nUserInfo);
        if (isOk) {
            throw new RuntimeException();
        }
    }
}
