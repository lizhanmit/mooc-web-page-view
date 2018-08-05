package com.zhandev.dao;

import com.zhandev.domain.ClassTypeCourseClickCount;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ClassTypeCourseClickCountDaoTest {

    @Test
    public void testGetClickCount() throws IOException {
        ClassTypeCourseClickCountDao classTypeCourseClickCountDao = new ClassTypeCourseClickCountDao();
        List<ClassTypeCourseClickCount> list = classTypeCourseClickCountDao.getClickCount("20180805");
        for (ClassTypeCourseClickCount classTypeCourseClickCount : list) {
            System.out.println(classTypeCourseClickCount.getName() + ": " + classTypeCourseClickCount.getValue());
        }
    }
}
