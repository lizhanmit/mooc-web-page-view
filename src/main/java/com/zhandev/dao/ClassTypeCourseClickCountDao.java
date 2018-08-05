package com.zhandev.dao;

import com.zhandev.domain.ClassTypeCourseClickCount;
import com.zhandev.utils.HBaseUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class type course click count data access layer.
 */
@Component
public class ClassTypeCourseClickCountDao {

    private String tableName = "mooc_course_clickcount";

    /**
     * Get list of ClassTypeCourseClickCount objectives by date.
     * @param date
     * @return  List of lassTypeCourseClickCount objectives.
     * @throws IOException
     */
    public List<ClassTypeCourseClickCount> getClickCount(String date) throws IOException {
        List<ClassTypeCourseClickCount> list = new ArrayList<>();

        Map<String, Long> map = HBaseUtils.getInstance().getClickCount(tableName, date);

        for (Map.Entry<String, Long> entry : map.entrySet()) {
            ClassTypeCourseClickCount classTypeCourseClickCount = new ClassTypeCourseClickCount(entry.getKey(), entry.getValue());
            list.add(classTypeCourseClickCount);
        }

        return list;
    }

}
