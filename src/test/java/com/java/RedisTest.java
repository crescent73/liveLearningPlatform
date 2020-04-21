package com.java;

import com.java.dao.StatDao;
import com.java.model.entity.LiveUser;
import com.java.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class RedisTest {
    @Autowired
    private StatDao statDao;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testStatDao(){
        LiveUser liveUser = new LiveUser();
        liveUser.setIp("localhost");
        liveUser.setRandomName("666");
        statDao.pushOnlineUser(liveUser);
        System.out.println(statDao.getAllUserOnline());
        statDao.popOnlineUser(liveUser);
        System.out.println(statDao.getAllUserOnline());

    }

    @Test
    public void testRedisSet(){
        LiveUser liveUser = new LiveUser();
        liveUser.setIp("localhost");
        liveUser.setRandomName("666");
        long result1 = redisUtil.sSet("user1", liveUser);
        liveUser.setRandomName("fdsf");
        long result2 = redisUtil.sSet("user1", liveUser);
        liveUser.setRandomName("fdfsdfsf");
        long result3 = redisUtil.sSet("user1", liveUser);
        boolean result = redisUtil.set("user", liveUser);
        Set<Object> userList = redisUtil.sGet("user1");
        System.out.println(userList);
//        System.out.println(result1);
//        statDao.popOnlineUser(liveUser);

    }

    @Test
    public void testRedisList(){
        LiveUser liveUser = new LiveUser();
        liveUser.setIp("localhost");
        liveUser.setRandomName("666");
        redisUtil.lSet("guest",liveUser);
        liveUser.setRandomName("fdfsdfsf");
        redisUtil.lSet("guest",liveUser);
        liveUser.setRandomName("777");
        redisUtil.lSet("guest",liveUser);
        liveUser.setRandomName("888");
        redisUtil.lSet("guest",liveUser);
        liveUser.setRandomName("777");
        redisUtil.lSet("guest",liveUser);
        System.out.println(redisUtil.lGetListSize("guest"));
        redisUtil.lPop("guest");
        System.out.println(redisUtil.lGetListSize("guest"));
    }
}
