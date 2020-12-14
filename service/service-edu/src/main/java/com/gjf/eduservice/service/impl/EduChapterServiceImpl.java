package com.gjf.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gjf.eduservice.entity.EduChapter;
import com.gjf.eduservice.entity.EduVideo;
import com.gjf.eduservice.entity.chapter.ChapterVo;
import com.gjf.eduservice.entity.chapter.VideoVo;
import com.gjf.eduservice.mapper.EduChapterMapper;
import com.gjf.eduservice.service.EduChapterService;
import com.gjf.eduservice.service.EduVideoService;
import com.gjf.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;//注入小节service

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapper);

        //根据课程id查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);
        //创建list集合，用于最终封装数据
        List<ChapterVo> findList = new ArrayList<>();



        //遍历查询章节list集合进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把chapterVo放到最终集合findList里面去
            findList.add(chapterVo);

            //封装章节的小节
            List<VideoVo> videoVoList = new ArrayList<>();
            //遍历查询小节list集合，进行封装
            for (int j = 0; j < eduVideoList.size(); j++) {
                EduVideo eduVideo = eduVideoList.get(j);
                //判断：小节里面chapterid和章节里面的id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            //把封装之后的小节videoVoList放到章节对象里
            chapterVo.setChildren(videoVoList);
        }
        return findList;
    }

    ////删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterid章节id 查询小节表，如果查询数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //判断
        if(count >0) {//查询出小节，不进行删除
            throw new GuliException(20001,"不能删除");
        } else { //不能查询数据，进行删除
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            //成功  1>0   0>0
            return result>0;
        }
    }

    //2 根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
