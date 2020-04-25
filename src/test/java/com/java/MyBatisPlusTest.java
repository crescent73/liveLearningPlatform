package com.java;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.dao.SignDao;
import com.java.model.entity.Sign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class MyBatisPlusTest {

    @Autowired
    private SignDao signDao;

    @Test
    public void test() {

        QueryWrapper<Sign> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(false,"user_id", null);
        List<Sign> signs = signDao.selectList(queryWrapper);
        System.out.println(signs);
//        List<Sign> signList = signDao.selectAll();
//        System.out.println(signList);
    }
}
