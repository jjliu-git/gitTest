package cn.itcast.dao.system;

import cn.itcast.domain.system.Dept;

import java.util.List;

public interface DeptDao {

    /**
     * 查询所有部门（针对某企业来查询）
     */
    public List<Dept> findAll(String companyId);

    /**
     * 根据ID查询部门（目的：是为了封装Dept对象中的parent对象）
     */
    public Dept findById(String id);

    void save(Dept dept);

    void update(Dept dept);

    long findDeptByParentId(String id);

    void delete(String id);
}
