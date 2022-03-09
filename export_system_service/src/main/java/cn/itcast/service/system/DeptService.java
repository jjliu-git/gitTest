package cn.itcast.service.system;

import cn.itcast.domain.system.Dept;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DeptService {

    PageInfo<Dept> findByPage(int pageSize, int pageNum, String param);

    List<Dept> findAll(String companyId);

    void save(Dept dept);

    void update(Dept dept);

    Dept findById(String id);

    boolean delete(String id);
}
