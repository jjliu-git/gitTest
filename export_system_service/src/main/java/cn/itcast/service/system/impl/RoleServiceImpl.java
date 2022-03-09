package cn.itcast.service.system.impl;

import cn.itcast.dao.system.RoleDao;
import cn.itcast.domain.system.Role;
import cn.itcast.service.system.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    //注入dao对象
    @Autowired
    private RoleDao roleDao;

    @Override
    public PageInfo<Role> findByPage(String companyId, int pageNum, int pageSize) {
        //1.设置分页参数
        PageHelper.startPage(pageNum,pageSize);

        //2.查询全部数据
        List<Role> list = roleDao.findAll(companyId);

        //3.封装PageInfo
        PageInfo<Role> pageInfo = new PageInfo<>(list);

        //4.返回
        return pageInfo;
    }

    /**
     * 添加
     * @param role
     */
    @Override
    public void save(Role role) {
        //1.生成ID
        role.setId(UUID.randomUUID().toString());
        //2.保存数据
        roleDao.save(role);
    }

    /**
     * 更新
     * @param role
     */
    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public void updateRoleModule(String roleid, String moduleIds) {
        //1.先删除当前角色分配过的所有权限
        roleDao.deleteRoleModuleByRoleId(roleid);

        //2.给当前角色逐一添加当前勾选的权限
        if(!StringUtils.isEmpty(moduleIds)){
            String[] moduleIdArray = moduleIds.split(",");
            for(String moduleId:moduleIdArray){
                roleDao.saveRoleModule(roleid,moduleId);
            }
        }
    }

    @Override
    public List<Role> findAll(String loginCompanyId) {
        return roleDao.findAll(loginCompanyId);
    }

    @Override
    public List<String> findUserRoleByUserId(String id) {
        return roleDao.findUserRoleByUserId(id);
    }

    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    @Override
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @Override
    public boolean delete(String id) {
        if (roleDao.findModuleByRoleId(id) > 0) {
            return false;
            //这里需要补充判断是否存在角色关系数据，如果有的话，需要提示用户
        }
        roleDao.delete(id);
        return true;
    }
}
