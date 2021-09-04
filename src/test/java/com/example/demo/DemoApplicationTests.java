package com.example.demo;

import com.hw.demo.enums.Datasource;
import com.hw.demo.mapper.UserMapper;
import com.hw.demo.model.UserInfo;
import com.hw.demo.utils.DateUtils;
import com.hw.demo.utils.RedisConfig;
import com.hw.demo.utils.RedisLock;
import com.hw.demo.utils.SnowflakeIdGenerator;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplicationTests.class)
@SpringBootApplication(scanBasePackages = "com.hw.demo.*")
@MapperScan("com.hw.demo.mapper")
//@ContextConfiguration(locations = {"classpath:spring.xml"})
class DemoApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplicationTests.class);

//    @Resource
//    private UserMapper userMapper;

//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;

//    private static final int ROW = 1000000;

    @Autowired
    private RedisLock redisLock;

    @Test
    void testRedisLock() {
        String lockId = null;
        try {
            lockId = redisLock.lock("lockName", 10000, 5000);
            if (lockId != null) {
                LOGGER.info("locked,id:{}", lockId);
            } else {
                LOGGER.info("un locked");
            }
        } finally {
            if (null != lockId) {
                redisLock.unlock("lockName", lockId);
            }
        }
    }
}
