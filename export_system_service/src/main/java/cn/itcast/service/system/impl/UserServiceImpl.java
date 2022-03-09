package cn.itcast.service.system.impl;

import cn.itcast.dao.system.UserDao;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public PageInfo<User> listAll(int pageSize, int pageNum, String companyId) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userDao.findAll(companyId);
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        return userPageInfo;
    }

    @Override
    public void save(User user) {
        //1.设置ID
        user.setId(UUID.randomUUID().toString());
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public User findById(String id) {
        User user = userDao.findById(id);
        return user;
    }

    @Override
    public boolean delete(String id) {
        // 判断用户是否绑定了用户
        int count = userDao.findRoleByUserId(id);
        if (count > 0) {
            return false;
        } else {
            userDao.delete(id);
        }
        return true;
    }

    @Override
    public void changeRole(String userid, List<String> roleIds) {
        //1.删除用户分配的角色
        userDao.deleteUserRoleByUserId(userid);

        //2.给用户分配当前勾选的角色
        if(roleIds!=null && roleIds.size()>0){
            for(String roleId:roleIds){
                userDao.saveUserRole(userid,roleId);
            }
        }
    }
}
