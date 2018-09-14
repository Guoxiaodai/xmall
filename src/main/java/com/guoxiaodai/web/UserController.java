package com.guoxiaodai.web;

import com.guoxiaodai.pojo.TbUser;
import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.pojo.common.Result;
import com.guoxiaodai.service.UserService;
import com.guoxiaodai.shiro.MyRealm;
import com.guoxiaodai.utils.GeetestLib;
import com.guoxiaodai.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/geetestInit{id}",method = RequestMethod.GET)
    @ResponseBody
    public String geetesrInit(HttpServletRequest request){

        GeetestLib gtSdk = new GeetestLib(GeetestLib.id,GeetestLib.key,GeetestLib.newfailback);

        String resStr = "{}";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<>();

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);

        resStr = gtSdk.getResponseStr();

        return resStr;
    }
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> login(String username, String password,
                                String challenge,String validate,String seccode,
                                HttpServletRequest request){

        //极验验证
        GeetestLib gtSdk = new GeetestLib(GeetestLib.id, GeetestLib.key,GeetestLib.newfailback);

        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }

        if (gtResult == 1) {
            MyRealm myRealm=new MyRealm();

            //1、构造SecurityManager环境
            DefaultSecurityManager defaultSecurityManager =new DefaultSecurityManager();
            defaultSecurityManager.setRealm(myRealm);

            //2、主体提交认证请求
            SecurityUtils.setSecurityManager(defaultSecurityManager);
            Subject subject = SecurityUtils.getSubject();
            String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
            UsernamePasswordToken token =new UsernamePasswordToken(username,md5Pass);
            subject.login(token);
            System.out.println("1---------"+subject.isAuthenticated());

            try {
                subject.login(token);
                return new ResultUtil<Object>().setData(null);
            }catch (Exception e){
                return new ResultUtil<Object>().setErrorMsg("用户名或密码错误");
            }
        }
        else {
            // 验证失败
            return new ResultUtil<Object>().setErrorMsg("验证失败");
        }

    }

    @RequestMapping(value = "/user/logout",method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> logout(){

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/userInfo",method = RequestMethod.GET)
    @ResponseBody
    public Result<TbUser> getUserInFo(){
        String username= SecurityUtils.getSubject().getPrincipal().toString();
        TbUser tbUser=userService.getUserByUsername(username);
        tbUser.setPassword(null);
        return new ResultUtil<TbUser>().setData(tbUser);

    }

    @RequestMapping(value = "/user/roleCount",method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> getRoleCount(){

        int result=userService.countRole();
        return new ResultUtil<Object>().setData(result);
    }
    @RequestMapping(value = "/user/roleList",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult getRoleList(){

        DataTablesResult result=userService.getRoleList();
        return result;
    }

    @RequestMapping(value = "/user/permissionCount",method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> getPermissionCount(){
        int result=userService.getPermissionCount();
        return new ResultUtil<Object>().setData(result);

    }

    @RequestMapping(value = "/user/permissionList",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult getPermissionList(){
        DataTablesResult result=userService.getPermissionList();
        return result;
    }
    @RequestMapping(value = "/user/userCount",method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> getUserCount(){
        int result=userService.getUserCount();
        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/user/userList",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult getUserList(){
        DataTablesResult result=userService.getUserList();
        return result;
    }
}
