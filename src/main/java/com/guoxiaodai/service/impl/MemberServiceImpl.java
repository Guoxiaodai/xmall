package com.guoxiaodai.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guoxiaodai.common.exception.XmallException;
import com.guoxiaodai.mapper.TbMemberMapper;
import com.guoxiaodai.pojo.TbMember;
import com.guoxiaodai.pojo.TbMemberExample;
import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.pojo.dto.MemberDto;
import com.guoxiaodai.service.MemberService;
import com.guoxiaodai.utils.DtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * 会员管理Service
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private TbMemberMapper tbMemberMapper;

    @Override
    public DataTablesResult findMemberList(int draw, int start, int length, String search, String minDate, String maxDate, String orderCol, String orderDir) {
        DataTablesResult result=new DataTablesResult();

        try{
            //分页
            PageHelper.startPage(start/length+1,length);
            List<TbMember> list = tbMemberMapper.selectByMemberInfo("%"+search+"%",minDate,maxDate,orderCol,orderDir);
            PageInfo<TbMember> pageInfo=new PageInfo<>(list);

            for(TbMember tbMember:list){
                tbMember.setPassword("");
            }

            result.setRecordsFiltered((int)pageInfo.getTotal());
            result.setRecordsTotal(getMemberCount().getRecordsTotal());

            result.setDraw(draw);
            result.setData(list);
        }catch (Exception e){
            throw new XmallException("加载用户列表失败");
        }

        return result;
    }

    @Override
    public DataTablesResult getMemberCount() {
        DataTablesResult result=new DataTablesResult();
        TbMemberExample example=new TbMemberExample();
        TbMemberExample.Criteria criteria=example.createCriteria();
        criteria.andStateNotEqualTo(2);
        try{

            result.setRecordsTotal((int) tbMemberMapper.countByExample(example));
        }catch (Exception e){
            throw new XmallException("统计会员数失败");
        }

        return result;
    }

    @Override
    public TbMember userNameIsExist(String username) {
        List<TbMember> list;
        TbMemberExample example=new TbMemberExample();
        TbMemberExample.Criteria criteria=example.createCriteria();
        criteria.andUsernameEqualTo(username);
        try {
            list=tbMemberMapper.selectByExample(example);
        }catch (Exception e){
            throw new XmallException("ID获取会员信息失败");
        }
        if(!list.isEmpty()){
            list.get(0).setPassword("");
            return list.get(0);
        }
        return null;
    }

    @Override
    public TbMember emailIsExist(String email) {
        List<TbMember> list;
        TbMemberExample example=new TbMemberExample();
        TbMemberExample.Criteria criteria=example.createCriteria();
        criteria.andEmailEqualTo(email);
        try {
            list=tbMemberMapper.selectByExample(example);
        }catch (Exception e){
            throw new XmallException("ID获取会员信息失败");
        }
        if(!list.isEmpty()){
            list.get(0).setPassword("");
            return list.get(0);
        }
        return null;
    }

    @Override
    public TbMember phoneIsExist(String phone) {
        List<TbMember> list;
        TbMemberExample example=new TbMemberExample();
        TbMemberExample.Criteria criteria=example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        try {
            list=tbMemberMapper.selectByExample(example);
        }catch (Exception e){
            throw new XmallException("ID获取会员信息失败");
        }
        if(!list.isEmpty()){
            list.get(0).setPassword("");
            return list.get(0);
        }
        return null;
    }

    @Override
    public TbMember addMember(MemberDto memberDto) {
        TbMember tbMember= DtoUtil.MemberDto2Member(memberDto);

        if(userNameIsExist(tbMember.getUsername())!=null){
            throw new XmallException("用户名已被注册");
        }
        if(phoneIsExist(tbMember.getPhone())!=null){
            throw new XmallException("手机号已被注册");
        }
        if(emailIsExist(tbMember.getEmail())!=null){
            throw new XmallException("邮箱已被注册");
        }

        tbMember.setState(1);
        tbMember.setCreated(new Date());
        tbMember.setUpdated(new Date());
        String md5Pass = DigestUtils.md5DigestAsHex(tbMember.getPassword().getBytes());
        tbMember.setPassword(md5Pass);

        if(tbMemberMapper.insert(tbMember)!=1){
            throw new XmallException("添加用户失败");
        }
        return phoneIsExist(tbMember.getPhone());
    }

    @Override
    public TbMember getMemberById(Long id) {
        TbMember tbMember;
        try {
            tbMember=tbMemberMapper.selectByPrimaryKey(id);
        }catch (Exception e){
            throw new XmallException("ID获取会员信息失败");
        }
        tbMember.setPassword("");
        return tbMember;
    }

    @Override
    public TbMember editUserNameIsExist(Long id, String username) {
        TbMember tbMember=getMemberById(id);
        TbMember newTbMember=null;
        if(tbMember.getUsername()==null||!tbMember.getUsername().equals(username)){
            newTbMember=userNameIsExist(username);
        }
        return newTbMember;
    }

    @Override
    public TbMember editEmailIsExist(Long id, String email) {
        TbMember tbMember=getMemberById(id);
        TbMember newTbMember=null;
        if(tbMember.getEmail()==null||!tbMember.getEmail().equals(email)){
            newTbMember=emailIsExist(email);
        }
        return newTbMember;
    }
    @Override
    public TbMember editPhoneIsExist(Long id, String phone) {
        TbMember tbMember=getMemberById(id);
        TbMember newTbMember=null;
        if(tbMember.getPhone()==null||!tbMember.getPhone().equals(phone)){
            newTbMember=phoneIsExist(phone);
        }
        return newTbMember;
    }

    @Override
    public TbMember updateMemberById(Long id, MemberDto memberDto) {
        TbMember tbMember= DtoUtil.MemberDto2Member(memberDto);

        if(editUserNameIsExist(id,tbMember.getUsername())!=null){
            throw new XmallException("用户名已被注册");
        }
        if(editPhoneIsExist(id,tbMember.getPhone())!=null){
            throw new XmallException("手机号已被注册");
        }
        if(editEmailIsExist(id,tbMember.getEmail())!=null){
            throw new XmallException("邮箱已被注册");
        }

        tbMember.setUpdated(new Date());
        TbMemberExample example =new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);

        if(tbMemberMapper.updateByExampleSelective(tbMember,example)!=1){
            throw new XmallException("添加用户失败");
        }
        return editPhoneIsExist(id,tbMember.getPhone());
    }

    @Override
    public int changePass(Long id, MemberDto memberDto) {
        TbMember tbMember= DtoUtil.MemberDto2Member(memberDto);
        tbMember.setUpdated(new Date());
        String md5Pass = DigestUtils.md5DigestAsHex(tbMember.getPassword().getBytes());
        tbMember.setPassword(md5Pass);
        TbMemberExample example =new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        if(tbMemberMapper.updateByExampleSelective(tbMember,example)!=1){
            throw new XmallException("修改密码失败");
        }
        return 1;
    }

    @Override
    public int removeMember(Long id) {
        TbMember tbMember =new TbMember();
        tbMember.setState(2);
        tbMember.setUpdated(new Date());
        TbMemberExample example =new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        if (tbMemberMapper.updateByExampleSelective(tbMember,example)!=1){
            throw new XmallException("删除用户失败");
        }
        return 1;
    }

    @Override
    public int stopMember(Long id) {
        TbMember tbMember =new TbMember();
        tbMember.setState(0);
        tbMember.setUpdated(new Date());
        TbMemberExample example =new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        if (tbMemberMapper.updateByExampleSelective(tbMember,example)!=1){
            throw new XmallException("停用用户失败");
        }
        return 1;
    }
    @Override
    public int startMember(Long id) {
        TbMember tbMember =new TbMember();
        tbMember.setState(1);
        tbMember.setUpdated(new Date());
        TbMemberExample example =new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        if (tbMemberMapper.updateByExampleSelective(tbMember,example)!=1){
            throw new XmallException("启用用户失败");
        }
        return 1;
    }







    @Override
    public DataTablesResult findRemoveMemberList(int draw, int start, int length, String search, String minDate, String maxDate, String orderCol, String orderDir) {
        DataTablesResult result=new DataTablesResult();
        try{
            //分页
            PageHelper.startPage(start/length+1,length);
            List<TbMember> list = tbMemberMapper.selectByRemoveMemberInfo("%"+search+"%",minDate,maxDate,orderCol,orderDir);
            PageInfo<TbMember> pageInfo=new PageInfo<>(list);

            for(TbMember tbMember:list){
                tbMember.setPassword("");
            }

            result.setRecordsFiltered((int)pageInfo.getTotal());
            result.setRecordsTotal(getMemberCount().getRecordsTotal());

            result.setDraw(draw);
            result.setData(list);
        }catch (Exception e) {
            throw new XmallException("加载用户列表失败");
        }

        return result;
    }
    @Override
    public DataTablesResult getRemoveMemberCount() {
        DataTablesResult result=new DataTablesResult();
        TbMemberExample example=new TbMemberExample();
        TbMemberExample.Criteria criteria=example.createCriteria();
        criteria.andStateEqualTo(2);
        try{

            result.setRecordsTotal((int) tbMemberMapper.countByExample(example));
        }catch (Exception e){
            throw new XmallException("统计会员数失败");
        }

        return result;
    }
    @Override
    public int delMember(Long id) {
        if(tbMemberMapper.deleteByPrimaryKey(id)!=1){
            throw new XmallException("彻底删除用户失败");
        }
        return 1;
    }
}
