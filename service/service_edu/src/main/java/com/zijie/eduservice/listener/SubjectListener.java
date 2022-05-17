package com.zijie.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijie.eduservice.entity.EduSubject;
import com.zijie.eduservice.entity.SubjectData;
import com.zijie.eduservice.service.EduSubjectService;
import com.zijie.servicebase.exception.MyException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author DZJ
 * @create 2021-10-16 15:18
 * @Description
 */
@AllArgsConstructor
@NoArgsConstructor
public class SubjectListener extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
    //不能实现数据库操作
    private EduSubjectService eduSubjectService;

    //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null) {
            throw new MyException(20001,"文件数据为空");
        }
        String oneSubjectName = subjectData.getOneSubjectName();
        if (this.existOneSubject(oneSubjectName) == null) {//没有相同一级分类，进行添加
            EduSubject oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(oneSubjectName);//设置一级分类名称
            eduSubjectService.save(oneSubject);
        }
        //获取一级分类id值
        String pid = existOneSubject(oneSubjectName).getId();
        String twoSubjectName = subjectData.getTwoSubjectName();
        //判断二级分类是否重复
        if (this.existTwoSubject(oneSubjectName,pid) == null) {
            EduSubject twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(twoSubjectName);//设置二级分类名称
            eduSubjectService.save(twoSubject);
        }
    }

    //判断一级分类不能重复添加,查找是否存在
    private EduSubject existOneSubject(String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        //where title = "" and parent_id = "0"
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }

    //判断二级分类不能重复添加,查找是否存在
    private EduSubject existTwoSubject(String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject eduSubject = eduSubjectService.getOne(wrapper);
        return eduSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
