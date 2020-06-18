package com.java;

import com.java.dao.CollectionDao;
import com.java.model.dto.CollectionDetail;
import com.java.model.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class XmlTest {
    @Autowired
    private CollectionDao collectionDao;

    @Test
    public void collectionTest(){
        Course course = new Course();
        course.setCourseId(1);
        List<CollectionDetail> userCollection = collectionDao.findUserCollection(1, course);
    }
}
