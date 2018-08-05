package com.zhandev.domain;

import org.springframework.stereotype.Component;

/**
 * Class type course click count bean.
 */
@Component
public class ClassTypeCourseClickCount {

    private String name;  // day_courseId in HBase table
    private long value;  // click_count in HBase table

    public ClassTypeCourseClickCount() {
    }

    public ClassTypeCourseClickCount(String name, long value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
