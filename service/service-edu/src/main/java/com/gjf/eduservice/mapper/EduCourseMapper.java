package com.gjf.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gjf.eduservice.entity.EduCourse;
import com.gjf.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getPublishCourseInfo(String courseId);
}
