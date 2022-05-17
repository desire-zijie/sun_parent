package com.zijie.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijie.eduservice.entity.EduSubject;
import com.zijie.eduservice.entity.SubjectData;
import com.zijie.eduservice.entity.subject.OneSubject;
import com.zijie.eduservice.entity.subject.TwoSubject;
import com.zijie.eduservice.listener.SubjectListener;
import com.zijie.eduservice.mapper.EduSubjectMapper;
import com.zijie.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijie.servicebase.exception.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-16
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //通过文件添加课程
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            //获取文件输入流
            InputStream is = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(is, SubjectData.class,new SubjectListener(eduSubjectService)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(20002,"添加课程分类失败");
        }
    }


    //课程分类列表（树形）
    @Override
    public List<OneSubject> getSubjectList() {
        //1 查询所有一级分类  parentid = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //2 查询所有二级分类  parentid != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        //创建list集合，用于存储最终封装数据
        List<OneSubject> oneSubjects = new ArrayList<OneSubject>();
        //3 封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值，
        for (EduSubject eduSubject : oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
            //oneSubject.setId(eduSubject.getId());
            //oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject, oneSubject);
            List<TwoSubject> oneSubjectChildren = oneSubject.getChildren();
            //在一级分类循环遍历查询所有的二级分类
            for (EduSubject subject : twoSubjectList) {
                //判断二级分类parentid和一级分类id是否一样
                if (subject.getParentId().equals(oneSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    //把Subject值复制到TwoSubject里面
                    BeanUtils.copyProperties(subject,twoSubject);
                    oneSubjectChildren.add(twoSubject);
                }
            }

            oneSubjects.add(oneSubject);
        }

        return oneSubjects;
    }
}
