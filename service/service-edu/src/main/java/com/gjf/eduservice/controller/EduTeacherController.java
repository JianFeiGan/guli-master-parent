package com.gjf.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gjf.commonutils.R;
import com.gjf.eduservice.entity.EduTeacher;
import com.gjf.eduservice.entity.vo.TeacherQuery;
import com.gjf.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 讲师 前端控制器
 *
 * @author GJFjava
 * @since 2020-11-17
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin  //解决跨域问题
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("delete/{id}")
    public R delTeacher(@PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /*分页查询讲师*/
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageteacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        // 创建page对象
        Page<EduTeacher> page = new Page<>(current, limit);
        //调用方法实现分页
        //调用方法的时候，底层封装，把分页所有数据封装到page对象中
        eduTeacherService.page(page, null);
        //总记录数
        long total = page.getTotal();
        //总记录
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "多条件组合查询讲师")
    @PostMapping("pageteachercondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        /*创建page对象*/
        Page<EduTeacher> pageTheacher = new Page<>(current, limit);
        /*构建条件*/
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        /*多条件组合查询*/
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        /*if判断条件值*/
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        wrapper.orderByDesc("gmt_create");
        eduTeacherService.page(pageTheacher, wrapper);
        //总记录数
        long total = pageTheacher.getTotal();
        //总记录
        List<EduTeacher> records = pageTheacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "添加讲师")
    /*添加讲师接口的方法*/
    @PostMapping("addteacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        return save ? R.ok() : R.error();
    }

    @ApiOperation(value = "根据ID进行查询讲师")
    /*根据ID进行查询讲师*/
    @GetMapping("getteacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    @ApiOperation(value = "讲师修改")
    /*讲师修改*/
    @PostMapping("updatetheacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.updateById(eduTeacher);
        return b ? R.ok() : R.error();
    }

}

