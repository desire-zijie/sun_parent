package com.zijie.educenter.controller;

import com.google.gson.Gson;
import com.zijie.commonutils.JWTUtils;
import com.zijie.educenter.entity.UcenterMember;
import com.zijie.educenter.service.UcenterMemberService;
import com.zijie.educenter.utils.ConstantPropertiesUtil;
import com.zijie.educenter.utils.HttpClientUtils;
import com.zijie.servicebase.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author DZJ
 * @create 2021-10-30 17:18
 * @Description
 */
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxLoginController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @RequestMapping("/callback")
    public String callback(String code,String state) {
        try {
            //1 获取code值，临时票据，类似于验证码
            //2 拿着code请求 微信固定的地址，得到两个值 accsess_token 和 openid
            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code);
            //请求这个拼接好的地址，得到返回两个值 accsess_token 和 openid
            //使用httpclient发送请求，得到返回结果
            String userInfo = HttpClientUtils.get(accessTokenUrl);
            //从accessTokenInfo字符串获取出来两个值 accsess_token 和 openid
            //把accessTokenInfo字符串转换map集合，根据map里面key获取对应值
            Gson gson = new Gson();
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String openid = (String) userInfoMap.get("openid");
            String accessToken = (String) userInfoMap.get("access_token");
            //把扫描人信息添加数据库里面
            //判断数据表里面是否存在相同微信信息，根据openid判断
            UcenterMember member = ucenterMemberService.getByOpenid(openid);
            if (member == null) {
                //3 拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                String resultUserInfo = HttpClientUtils.get(userInfoUrl);
                //解析json
                HashMap resultUserInfoMap = gson.fromJson(resultUserInfo, HashMap.class);
                String nickname = (String) resultUserInfoMap.get("nickname");//昵称
                String headimgurl = (String) resultUserInfoMap.get("headimgurl");//头像
                //向数据库中插入一条记录
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                member.setNickname(nickname);
                ucenterMemberService.save(member);
            }
            //使用jwt根据member对象生成token字符串
            String id = member.getId();
            String token = JWTUtils.getJwtToken(id, member.getNickname());
            //最后：返回首页面，通过路径传递token字符串
            return "redirect:http://localhost:3000?token=" + token;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001,"登录失败");
        }
    }


    @RequestMapping("/login")
    public String login() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址 url编码
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        String url = null;
        try {
            url = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String qrcodeUrl = String.format(baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                url,
                "atguigu");
        return "redirect:" + qrcodeUrl;
    }

}
