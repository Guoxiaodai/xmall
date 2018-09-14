package com.guoxiaodai.service;

import com.guoxiaodai.pojo.TbMember;
import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.pojo.dto.MemberDto;

public interface MemberService {
    DataTablesResult findMemberList(int draw, int start, int length, String search, String minDate, String maxDate, String orderColumn, String orderDir);

    DataTablesResult getMemberCount();
    TbMember userNameIsExist(String username);

    TbMember emailIsExist(String email);

    TbMember phoneIsExist(String phone);
    TbMember addMember(MemberDto memberDto);
    TbMember getMemberById(Long id);
    TbMember editUserNameIsExist(Long id,String username);

    TbMember editEmailIsExist(Long id, String email);

    TbMember editPhoneIsExist(Long id, String phone);
    TbMember updateMemberById(Long id,MemberDto memberDto);
    int changePass(Long id,MemberDto memberDto);
    int removeMember(Long id);
    int stopMember(Long id);
    int startMember(Long id);



    DataTablesResult findRemoveMemberList(int draw, int start, int length, String search, String minDate, String maxDate, String orderColumn, String orderDir);
    DataTablesResult getRemoveMemberCount();
    int delMember(Long id);


}
