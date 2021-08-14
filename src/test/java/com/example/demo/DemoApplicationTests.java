package com.example.demo;

import com.hw.demo.mapper.UserMapper;
import com.hw.demo.model.UserInfo;
import com.hw.demo.services.biz.UserService;
import com.hw.demo.utils.DateUtils;
import com.hw.demo.utils.SnowflakeIdGenerator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplicationTests.class)
@SpringBootApplication(scanBasePackages = "com.hw.demo.*")
@MapperScan("com.hw.demo.mapper")
//@ContextConfiguration(locations = {"classpath:spring.xml"})
class DemoApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplicationTests.class);

    @Resource
    private UserMapper userMapper;

    @Resource(name = "userService")
    private UserService userService;

    private static final int ROW = 17;

    @Test
    public void shardRouteForDsAndTable() {
        long start = System.currentTimeMillis();

        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(0, 0);//雪花ID生成器

        for (int i = 1; i < ROW; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUuid(String.valueOf(snowflakeIdGenerator.nextId())/*UUIDGeneratorUtils.getUUID32()*/);
            userInfo.setUserId("Sa" + i);
            userInfo.setUserName("极客时间GKSJ" + i);
            userInfo.setCreateBy("JK001");
            userInfo.setUpdateBy("JK001");
            userInfo.setLogicId("2");
            userInfo.setLogicFlag("0");
            userInfo.setCreateDate(DateUtils.getTimeStamp(DateUtils.format19));
            userInfo.setUpdateDate(DateUtils.getTimeStamp(DateUtils.format19));
            userMapper.insert(userInfo);
        }

        LOGGER.info("use time:{} and the row is:{}", (System.currentTimeMillis() - start), ROW);
    }

    @Test
    public void testTransXa(){
//        userService.insertSuccess();
        userService.insertFailed();
    }
}
