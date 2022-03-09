package cn.itcast.service.system.impl;

import cn.itcast.dao.system.ModuleDao;
import cn.itcast.domain.system.Module;
import cn.itcast.service.system.ModuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleDao moduleDao;
    @Override
    public PageInfo<Module> findByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Module> moduleList = moduleDao.findAll();
        PageInfo<Module> modulePageInfo = new PageInfo<>(moduleList);
        return modulePageInfo;
    }

    /**
     * 查询指定企业的所有模块
     * @return
     */
    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    /**
     * 添加
     * @param module
     */
    @Override
    public void save(Module module) {
        //1.生成ID
        module.setId(UUID.randomUUID().toString());
        //2.保存数据
        moduleDao.save(module);
    }

    /**
     * 更新
     * @param module
     */
    @Override
    public void update(Module module) {
        moduleDao.update(module);
    }

    /**
     * 根据id查询模块
     * @param id
     * @return
     */
    @Override
    public Module findById(String id) {
        return moduleDao.findById(id);
    }

    /**
     * 删除模块
     * @param id
     * @return
     */
    @Override
    public boolean delete(String id) {
        if (moduleDao.findModuleByRoleId(id) > 0) {
            return false;
            //这里需要补充判断是否存在角色关系数据，如果有的话，需要提示用户
        }
        moduleDao.delete(id);
        return true;
    }

    @Override
    public List<Module> findRoleModuleByRoleId(String id) {
        List<Module> moduleList = moduleDao.findRoleModuleByRoleId(id);
        return moduleList;
    }
}
