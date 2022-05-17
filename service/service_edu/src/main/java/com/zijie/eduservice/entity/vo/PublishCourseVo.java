package com.zijie.eduservice.entity.vo;

import lombok.Data;

/**
 * @author DZJ
 * @create 2021-10-19 19:43
 * @Description
 */
@Data
public class PublishCourseVo {
    private String title;
    private String cover;//封面
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示

}
