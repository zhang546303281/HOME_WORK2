package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hw.demo.enums.Datasource;
import com.hw.demo.mapper.UserMapper;
import com.hw.demo.model.UserInfo;
import com.hw.demo.utils.DateUtils;
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

    @Resource
    private UserMapper userMapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private static final int ROW = 1000000;

    @Test
    void ormFrameTest() {
        try {

            HintManager hintManager = HintManager.getInstance();

            hintManager.setDatabaseShardingValue(Datasource.WRITE_DATASOURCE.getValue());//指定写库执行
//            singleForeachAdd();//单次提交循环执行
            batchAdd();//开启事务会话按批次一组一组提交

            hintManager.setDatabaseShardingValue(Datasource.READ_DATASOURCE.getValue());//切换到读库执行
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("USER_NAME", "1");
            List<UserInfo> result = userMapper.selectList(queryWrapper);
            LOGGER.info("data result:{}", result);
            Assert.assertNotNull(result);

            hintManager.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void singleForeachAdd() {
        long start = System.currentTimeMillis();

        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(0, 0);//雪花ID生成器

        for (int i = 1; i < ROW; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUuid(String.valueOf(snowflakeIdGenerator.nextId())/*UUIDGeneratorUtils.getUUID32()*/);
            userInfo.setUserId("GKSJ" + i);
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

    public void batchAdd(){
        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);

        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(0, 0);//雪花ID生成器

        try {
            long start = System.currentTimeMillis();
            UserMapper userMapper = session.getMapper(UserMapper.class);
            for (int i = 0; i < ROW; i++) {

                UserInfo userInfo = new UserInfo();
                userInfo.setUuid(String.valueOf(snowflakeIdGenerator.nextId())/*UUIDGeneratorUtils.getUUID32()*/);
                userInfo.setUserId("GKSJ" + i);
                userInfo.setUserName("极客时间GKSJ" + i);
                userInfo.setCreateBy("JK001");
                userInfo.setUpdateBy("JK001");
                userInfo.setLogicId("2");
                userInfo.setLogicFlag("0");
                userInfo.setCreateDate(DateUtils.getTimeStamp(DateUtils.format19));
                userInfo.setUpdateDate(DateUtils.getTimeStamp(DateUtils.format19));
                userMapper.insert(userInfo);

                if (i % 500 == 0 || i == ROW - 1) {
                    session.commit();
                    session.clearCache();
                }
            }
            LOGGER.info("batch use time:{} and the row is:{}", (System.currentTimeMillis() - start), ROW);
        } catch (Exception e) {
            session.rollback();
        } finally {
            session.close();
        }
    }
}
