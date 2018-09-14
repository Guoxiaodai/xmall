package com.guoxiaodai.web;

import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.pojo.common.Result;
import com.guoxiaodai.pojo.dto.MemberDto;
import com.guoxiaodai.service.MemberService;
import com.guoxiaodai.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 会员管理Controller
 */
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/member/count",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult getMemberCount() {

        return memberService.getMemberCount();
    }

    @RequestMapping(value = "/member/list",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult getMemberList(int draw, int start, int length, String searchKey, @RequestParam("search[value]") String search,
                                          String minDate, String maxDate,
                                          @RequestParam("order[0][column]") int orderCol, @RequestParam("order[0][dir]") String orderDir) {

        //获取客户端需要排序的列
        String[] cols = {"checkbox", "id", "username", "sex", "phone", "email", "address", "created", "updated", "state"};
        String orderColumn = cols[orderCol];
        //默认排序列
        if (orderColumn == null) {
            orderColumn = "created";
        }
        //获取排序方式 默认为desc(asc)
        if (orderDir == null) {
            orderDir = "desc";
        }
        if (!search.isEmpty()) {
            searchKey = search;
        }
        DataTablesResult result = memberService.findMemberList(draw, start, length, searchKey, minDate, maxDate, orderColumn, orderDir);
        return result;

    }

    @RequestMapping(value = "/member/username",method = RequestMethod.GET)
    @ResponseBody
    public  Boolean userNameIsExist(String username){
        if(memberService.userNameIsExist(username)!=null){
            return false;
        }
        return true;

    }

    @RequestMapping(value = "/member/email",method = RequestMethod.GET)
    @ResponseBody
    public Boolean emailIsExist(String email){
        if(memberService.emailIsExist(email)!=null){

            return false;
        }
        return true;

    }

    @RequestMapping(value = "/member/phone",method = RequestMethod.GET)
    @ResponseBody
    public Boolean phoneIsExist(String phone){
        if(memberService.phoneIsExist(phone)!=null){
            return false;
        }
        return true;

    }

    @RequestMapping(value = "/member/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> addMember(@ModelAttribute MemberDto memberDto){

        memberService.addMember(memberDto);
        return new ResultUtil<Object>().setData(null);

    }
    @RequestMapping(value = "/member/edit/{id}/username",method = RequestMethod.GET)
    @ResponseBody
    public Boolean editUserNameIsExist(@PathVariable Long id,String username){
        if (memberService.editUserNameIsExist(id,username)!=null){
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/member/edit/{id}/email",method = RequestMethod.GET)
    @ResponseBody
    public Boolean editEmailIsExist(@PathVariable Long id,String email){

        if (memberService.editEmailIsExist(id,email)!=null){
            return false;
        }
        return true;

    }
    @RequestMapping(value = "/member/edit/{id}/phone",method = RequestMethod.GET)
    @ResponseBody
    public Boolean editPhoneIsExist(@PathVariable Long id,String phone){

        if (memberService.editPhoneIsExist(id,phone)!=null){
            return false;
        }
        return true;

    }
    @RequestMapping(value = "/member/update/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> updateMemberById(@PathVariable Long id,@ModelAttribute MemberDto memberDto){
        memberService.updateMemberById(id,memberDto);
        return new ResultUtil<Object>().setData(null);

    }
    @RequestMapping(value = "/member/changePass/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> changePass(@PathVariable Long id,@ModelAttribute MemberDto memberDto){

        memberService.changePass(id,memberDto);
        return new ResultUtil<Object>().setData(null);
    }
    @RequestMapping(value = "/member/remove/{ids}",method = RequestMethod.PUT)
    @ResponseBody
    public Result<Object> removeMember(@PathVariable Long[] ids){

        for (Long id : ids) {
            memberService.removeMember(id);
        }
        return new ResultUtil<Object>().setData(null);

    }
    @RequestMapping(value = "/member/stop/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Result<Object> stopMember(@PathVariable Long id){
        memberService.stopMember(id);
        return new ResultUtil<Object>().setData(null);

    }
    @RequestMapping(value = "/member/start/{ids}",method = RequestMethod.PUT)
    @ResponseBody
    public Result<Object> startMember(@PathVariable Long[] ids){
        for (Long id : ids) {
            memberService.startMember(id);
        }
        return new ResultUtil<Object>().setData(null);

    }
    //------删除的会员管理-------------------------------------------------------------------------
    @RequestMapping(value = "member/list/remove",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult getRemoveMemberList(int draw, int start, int length, String searchKey, @RequestParam("search[value]") String search,
                                                 String minDate, String maxDate,
                                                 @RequestParam("order[0][column]") int orderCol, @RequestParam("order[0][dir]") String orderDir) {

        //获取客户端需要排序的列
        String[] cols = {"checkbox", "id", "username", "sex", "phone", "email", "address", "created", "updated", "state"};
        String orderColumn = cols[orderCol];
        //默认排序列
        if (orderColumn == null) {
            orderColumn = "created";
        }
        //获取排序方式 默认为desc(asc)
        if (orderDir == null) {
            orderDir = "desc";
        }
        if (!search.isEmpty()) {
            searchKey = search;
        }
        DataTablesResult result = memberService.findRemoveMemberList(draw, start, length, searchKey, minDate, maxDate, orderColumn, orderDir);
        return result;
    }


    @RequestMapping(value = "/member/count/remove",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult getRemoveMemberCount() {

        return memberService.getRemoveMemberCount();
    }

    @RequestMapping(value = "/member/del/{ids}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result<Object> delMember(@PathVariable Long[] ids){

        for (Long id : ids) {
            memberService.delMember(id);
        }
        return new ResultUtil<Object>().setData(null);

    }
}
