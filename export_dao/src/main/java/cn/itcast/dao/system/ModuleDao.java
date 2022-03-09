package cn.itcast.dao.system;

import cn.itcast.domain.system.Module;

import java.util.List;

public interface ModuleDao {
    //根据id查询
    Module findById(String moduleId);

    //根据id删除
    void delete(String moduleId);

    //添加
    void save(Module module);

    //更新
    void update(Module module);

    //查询全部
    List<Module> findAll();

    int findModuleByRoleId(String id);

    /**
     * 查询角色关联的模块集合
     * @param id
     * @return
     */
    List<Module> findRoleModuleByRoleId(String id);
}
