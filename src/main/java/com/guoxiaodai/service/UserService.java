package com.guoxiaodai.service;

import com.guoxiaodai.pojo.TbPermission;
import com.guoxiaodai.pojo.TbUser;
import com.guoxiaodai.pojo.common.DataTablesResult;

import java.util.Set;

public interface UserService {
    String getPasswordByUserName(String username);

    Set<String> getRolesByUserName(String username);

    Set<String> getPermissionsByUserName(String username);

    Set<String> getPermissionIdByRoleId(Integer roleId);
    TbPermission getPermissionById(Integer permissionId);

    TbUser getUserByUsername(String username);

    int countRole();

    DataTablesResult getRoleList();

    int getPermissionCount();

    DataTablesResult getPermissionList();

    int getUserCount();

    DataTablesResult getUserList();
}
