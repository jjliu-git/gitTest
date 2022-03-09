package cn.itcast.service.system;

import cn.itcast.domain.system.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    PageInfo<User> listAll(int pageSize, int pageNum, String companyId);

    void save(User user);

    void update(User user);

    User findById(String id);

    boolean delete(String id);


    void changeRole(String userid, List<String> roleIds);
}
