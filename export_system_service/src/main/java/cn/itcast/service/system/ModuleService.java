package cn.itcast.service.system;

import cn.itcast.domain.system.Module;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ModuleService {
    /**
     * 分页查询模块
     */
    public PageInfo<Module> findByPage(int pageNum, int pageSize);

    /**
     * 查询指定企业的所有模块
     * @return
     */
    List<Module> findAll();

    /**
     * 添加
     * @param module
     */
    void save(Module module);

    /**
     * 更新
     * @param module
     */
    void update(Module module);

    /**
     * 根据id查询模块
     * @param id
     * @return
     */
    Module findById(String id);

    /**
     * 删除模块
     * @param id
     * @return
     */
    boolean delete(String id);

    List<Module> findRoleModuleByRoleId(String id);
}
