package cn.itcast.service.system.impl;

import cn.itcast.dao.system.DeptDao;
import cn.itcast.domain.system.Dept;
import cn.itcast.service.system.DeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;


    @Override
    public PageInfo<Dept> findByPage(int pageSize, int pageNum, String param) {
        PageHelper.startPage(pageNum,pageSize);
        List<Dept> all = deptDao.findAll(param);
        PageInfo<Dept> deptPageInfo = new PageInfo<>(all);
        return deptPageInfo;
    }

    @Override
    public List<Dept> findAll(String companyId) {
        return deptDao.findAll(companyId);
    }

    @Override
    public void save(Dept dept) {
        dept.setId(UUID.randomUUID().toString());
        deptDao.save(dept);
    }

    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }

    @Override
    public Dept findById(String id) {
        return deptDao.findById(id);
    }

    @Override
    public boolean delete(String id) {
        //1）如果删除的部门，有子部门，不能直接，提示用户“该部门存在其他关联，不能删除”
        //1.1 查询该部门是否存在子部门
        long count = deptDao.findDeptByParentId(id);
        if(count>0){
            //有子部门
            return false;
        }
        //2）如果删除的部门，没有子部门，直接删除，提示用户“删除成功”
        deptDao.delete(id);
        return true;
    }

}
