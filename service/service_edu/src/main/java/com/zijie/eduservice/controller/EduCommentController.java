package com.zijie.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zijie.commonutils.JWTUtils;
import com.zijie.commonutils.R;
import com.zijie.commonutils.UcenterMemberVo;
import com.zijie.eduservice.client.UcenterClient;
import com.zijie.eduservice.entity.EduComment;
import com.zijie.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/eduservice/edu-comment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService eduCommentService;

    @Autowired
    private UcenterClient ucenterClient;

    //分页查询课程评论
    @GetMapping("/getPageComment/{page}/{limit}")
    public R getPageComment(@PathVariable long page,@PathVariable long limit,@RequestParam("courseId") String courseId) {
        Page<EduComment> commentPage = new Page<>(page, limit);
        Map<String, Object> map = eduCommentService.getPageComment(commentPage,courseId);
        return R.ok().data(map);
    }

    //添加评论
    @PostMapping("/addComment")
    public R addComment(@RequestBody EduComment eduComment, HttpServletRequest request) {
        //获取用户id
        String id = JWTUtils.getMemberIdByJwtToken(request);
        //远程调用接口
        UcenterMemberVo member = ucenterClient.getUserById(id);
        eduComment.setAvatar(member.getAvatar());
        eduComment.setNickname(member.getNickname());
        eduComment.setMemberId(member.getId());
        eduCommentService.save(eduComment);
        return R.ok();
    }
}

