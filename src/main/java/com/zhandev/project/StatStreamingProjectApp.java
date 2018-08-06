package com.zhandev.project;

import com.zhandev.dao.ClassTypeCourseClickCountDao;
import com.zhandev.domain.ClassTypeCourseClickCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class type courses page view web layer.
 */
@RestController
public class StatStreamingProjectApp {

    // For real projects, course name should be got from database by courseId.
    // Use Map here just for simplicity.
    private static Map<String, String> courses = new HashMap<>();
    static{
        courses.put("112", "Spark SQL Tutorial");
        courses.put("128", "Hadoop Basics");
        courses.put("145", "Storm Tutorial");
        courses.put("146", "Spark Streaming Tutorial");
        courses.put("131", "Big Data Interview Skills");
        courses.put("130", "HBase Tutorial");
    }

    @Autowired
    ClassTypeCourseClickCountDao classTypeCourseClickCountDao;

    @RequestMapping(value = "/class-type-courses-click-count", method = RequestMethod.POST)
    @ResponseBody
    public List<ClassTypeCourseClickCount> getClassTypeCourseClickCountList() throws IOException {

        List<ClassTypeCourseClickCount> list = classTypeCourseClickCountDao.getClickCount("20180805"); // hard code here for simplicity, should be modified later
        for (ClassTypeCourseClickCount classTypeCourseClickCount : list) {
            classTypeCourseClickCount.setName(courses.get(classTypeCourseClickCount.getName().substring(9)));  // change day_courseId to course name
        }

        return list;
    }

    @RequestMapping(value = "/courses-page-view", method = RequestMethod.GET)
    public ModelAndView coursesPageView() {
        return new ModelAndView("courses-page-view");
    }

}
