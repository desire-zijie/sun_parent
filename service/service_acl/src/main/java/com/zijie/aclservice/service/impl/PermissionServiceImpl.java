package com.zijie.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijie.aclservice.entity.Permission;
import com.zijie.aclservice.entity.RolePermission;
import com.zijie.aclservice.entity.User;
import com.zijie.aclservice.helper.MemuHelper;
import com.zijie.aclservice.helper.PermissionHelper;
import com.zijie.aclservice.mapper.PermissionMapper;
import com.zijie.aclservice.service.PermissionService;
import com.zijie.aclservice.service.RolePermissionService;
import com.zijie.aclservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;
    
    @Autowired
    private UserService userService;
    
    //获取全部菜单
    @Override
    public List<Permission> queryAllMenu() {
        //1 查询菜单表所有数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);
        //2 把查询所有菜单list集合按照要求进行封装
        List<Permission> finalList = getFinalList(permissionList);
        return finalList;
    }

    //获取菜单
    private List<Permission> getFinalList(List<Permission> permissionList) {
        //创建list集合，用于数据最终封装
        List<Permission> permissions = new ArrayList<>();
        //把所有菜单list集合遍历，得到顶层菜单 pid=0菜单，设置level是1
        for (Permission permissionNode : permissionList) {
            //获取最底层菜单（一级菜单）
            if ("0".equals(permissionNode.getPid())) {
                permissionNode.setLevel(1);
                //根据顶层菜单，向里面进行查询子菜单，封装到permissions里
                permissions.add(selectChildren(permissionNode,permissionList));
            }
        }
        return permissions;
    }

    //获取二级菜单，三级菜单。。。递归调用
    private Permission selectChildren(Permission permissionNode, List<Permission> permissionList) {
        //1 因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
        permissionNode.setChildren(new ArrayList<Permission>());
        //2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        for (Permission it : permissionList) {
            if (it.getPid().equals(permissionNode.getId())) {
                it.setLevel(permissionNode.getLevel() + 1);
                //把查询出来的子菜单放到父菜单里面
                permissionNode.getChildren().add(selectChildren(it, permissionList));
            }
        }
        return permissionNode;
    }

    //根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));
        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }
       // List<Permission> permissionList = bulid(allPermissionList);
        return null;
    }

    //给角色分配权限
    @Override
    public void saveRolePermissionRealtionShip(String roleId, String[] permissionIds) {
        //roleId角色id
        //permissionId菜单id 数组形式
        //1 创建list集合，用于封装添加数据
        ArrayList<RolePermission> rolePermissions = new ArrayList<>();
        //遍历所有菜单数组
        for (String permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            //封装到list集合
            rolePermissions.add(rolePermission);
        }
        //向角色菜单关系表批量添加
        rolePermissionService.saveBatch(rolePermissions);
    }

    //递归删除菜单
    @Override
    public void removeChildById(String id) {
        //1 创建list集合，用于封装所有删除菜单id值
        ArrayList<String > list = new ArrayList<>();
        list.add(id);
        //2 向idList集合设置删除菜单id
        this.selectChildrenMenu(id,list);
        baseMapper.deleteBatchIds(list);
    }

    //2 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
    private void selectChildrenMenu(String id, ArrayList<String> list) {
        //查询菜单里面子菜单id
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",id);
        wrapper.select("id");
        List<Permission> permissions = baseMapper.selectList(wrapper);
        for (Permission permission : permissions) {
            //将子菜单id封装到list集合中
            list.add(permission.getId());
            //递归查询
            this.selectChildrenMenu(permission.getId(),list);
        }
    }

    //根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        List<JSONObject> result = MemuHelper.bulid(permissionList);
        return result;
    }

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     *	递归获取子节点
     * @param id
     * @param idList
     */
    private void selectChildListById(String id, List<String> idList) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

}
