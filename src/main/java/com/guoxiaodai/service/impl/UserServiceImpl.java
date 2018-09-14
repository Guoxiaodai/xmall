package com.guoxiaodai.service.impl;

import com.guoxiaodai.common.exception.XmallException;
import com.guoxiaodai.mapper.TbPermissionMapper;
import com.guoxiaodai.mapper.TbRoleMapper;
import com.guoxiaodai.mapper.TbRolePermMapper;
import com.guoxiaodai.mapper.TbUserMapper;
import com.guoxiaodai.pojo.*;
import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.pojo.dto.RoleDto;
import com.guoxiaodai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * User管理Service
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private TbRoleMapper tbRoleMapper;
    @Autowired
    private TbRolePermMapper tbRolePermMapper;
    @Autowired
    private TbPermissionMapper tbPermissionMapper;


    @Override
    public String getPasswordByUserName(String username) {
        TbUserExample example =new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        if (tbUserMapper.selectByExample(example)==null){
            throw new XmallException("获得密码失败");
        }
        return tbUserMapper.selectByExample(example).get(0).getPassword();
    }
    //获得用户角色数据
    @Override
    public Set<String> getRolesByUserName(String username) {
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        Set<String> roles =new HashSet<>();

        try {
            List<TbUser> tbUser = tbUserMapper.selectByExample(example);
            for (TbUser user : tbUser) {
                String role = user.getDescription();
                roles.add(role);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }
    //获得用户权限
    @Override
    public Set<String> getPermissionsByUserName(String username) {
        Set<String> permission=new HashSet<>();
        try {
            TbUserExample example=new TbUserExample();
            TbUserExample.Criteria criteria = example.createCriteria();
             criteria.andUsernameEqualTo(username);

            List<TbUser> tbUser =tbUserMapper.selectByExample(example);
            for (TbUser user : tbUser) {
                Integer roleId = user.getRoleId();
                //从tbroleperm表中找到所有roleid为roleId的permissionId；
                permission = getPermissionIdByRoleId(roleId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return permission;
    }

    @Override
    public Set<String> getPermissionIdByRoleId(Integer roleId) {
        TbRolePermExample example =new TbRolePermExample();
        TbRolePermExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        List<TbRolePerm> tbRolePerms = tbRolePermMapper.selectByExample(example);
        Set<String> permissions =new HashSet<>();
        for (TbRolePerm tbRolePerm : tbRolePerms) {
            Integer permissionId = tbRolePerm.getPermissionId();
            //通过permissionId在tbpermission表中查询对应的permission
            TbPermission tbPermission = getPermissionById(permissionId);
                String permission = tbPermission.getPermission();
                permissions.add(permission);

        }
        return permissions;
    }

    @Override
    public TbPermission getPermissionById(Integer permissionId) {
        TbPermission tbPermission = tbPermissionMapper.selectByPrimaryKey(permissionId);
        return tbPermission;
    }

    @Override
    public TbUser getUserByUsername(String username) {
        List<TbUser> list;
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria=example.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andStateEqualTo(1);
        try {
            list=tbUserMapper.selectByExample(example);
        }catch (Exception e){
            throw new XmallException("通过ID获取用户信息失败");
        }
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int countRole() {
        TbRoleExample example=new TbRoleExample();
        int result=tbRoleMapper.countByExample(example);
        return result;
    }

    @Override
    public DataTablesResult getRoleList() {
        DataTablesResult result=new DataTablesResult();
        List<RoleDto> list=new ArrayList<>();
        TbRoleExample example=new TbRoleExample();
        List<TbRole> list1=tbRoleMapper.selectByExample(example);
        if(list1==null){
            throw new XmallException("获取角色列表失败");
        }
        for(TbRole tbRole:list1){
            RoleDto roleDto=new RoleDto();
            roleDto.setId(tbRole.getId());
            roleDto.setName(tbRole.getName());
            roleDto.setDescription(tbRole.getDescription());

            List<String> permissions=tbUserMapper.getPermsByRoleId(tbRole.getId());
            String names="";
            if(permissions.size()>1){
                names+=permissions.get(0);
                for(int i=1;i<permissions.size();i++){
                    names+="|"+permissions.get(i);
                }
            }else if(permissions.size()==1){
                names+=permissions.get(0);
            }
            roleDto.setPermissions(names);

            list.add(roleDto);
        }
        result.setData(list);
        return result;
    }

    @Override
    public int getPermissionCount() {
        TbPermissionExample example=new TbPermissionExample();
        int result = tbPermissionMapper.countByExample(example);
        return result;
    }

    @Override
    public DataTablesResult getPermissionList() {
        DataTablesResult result=new DataTablesResult();
        List<RoleDto> list=new ArrayList<>();
        TbPermissionExample example=new TbPermissionExample();
        List<TbPermission> list1=tbPermissionMapper.selectByExample(example);
        if(list1==null){
            throw new XmallException("获取角色权限失败");
        }
        for(TbPermission tbPermission:list1){
            RoleDto roleDto=new RoleDto();
            roleDto.setId(tbPermission.getId());
            roleDto.setName(tbPermission.getName());

            roleDto.setPermissions(tbPermission.getPermission());

            list.add(roleDto);
        }
        result.setData(list);
        return result;
    }

    @Override
    public int getUserCount() {
        TbUserExample example=new TbUserExample();
        int result = tbUserMapper.countByExample(example);
        return result;
    }

    @Override
    public DataTablesResult getUserList() {
        DataTablesResult result=new DataTablesResult();

        TbUserExample example=new TbUserExample();
        List<TbUser> list1=tbUserMapper.selectByExample(example);
        if(list1==null){
            throw new XmallException("获取管理员列表失败");
        }
        for (TbUser tbUser : list1) {
            String names="";
            Iterator it=getRolesByUserName(tbUser.getUsername()).iterator();
            while (it.hasNext()){
                names+=it.next()+" ";
            }
            tbUser.setPassword("");
            tbUser.setRoleNames(names);
        }
        result.setData(list1);
        return result;
    }

}
