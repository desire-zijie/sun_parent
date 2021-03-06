package com.zijie.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijie.commonutils.R;
import com.zijie.eduservice.client.VodClient;
import com.zijie.eduservice.entity.EduVideo;
import com.zijie.eduservice.mapper.EduVideoMapper;
import com.zijie.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijie.servicebase.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    //根据课程id删除小节
    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        //根据课程id删除视频
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);
        ArrayList<String> list = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
                list.add(eduVideo.getVideoSourceId());
            }
        }
        if (list.size() > 0) {
            vodClient.removeVideos(list);
        }
        baseMapper.delete(wrapper);
    }

    //根据视频id删除视频
    @Override
    public boolean deleteVideo(String videoId) {
        EduVideo eduVideo = this.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            //根据id，原程调用实现视频删除
            R r = vodClient.removeAliYunVideo(videoSourceId);
            if (!r.getSuccess()) {
                throw new MyException(20001, "删除视频出错了");
            }
        }
        return this.removeById(videoId);
    }

}
