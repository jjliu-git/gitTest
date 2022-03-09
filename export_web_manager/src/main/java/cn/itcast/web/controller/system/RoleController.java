package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.Role;
import cn.itcast.service.system.DeptService;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.RoleService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色控制器
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private ModuleService moduleService;


    /**
     * 分页查询
     *   1）URL：http://localhost:8080/system/role/list.do
     *   2）参数：pageNum=1&pageSize=5
     *   3)返回： /WEB-INF/pages/system/role/role-list.jsp
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize){
        //当前登录企业的ID
        String companyId = getLoginCompanyId();

        //1.查询角色数据
        PageInfo pageInfo = roleService.findByPage(companyId,pageNum,pageSize);

        //2.存入request域
        request.setAttribute("pageInfo",pageInfo);

        //3.返回页面
        return "system/role/role-list";
    }

    /**
     * 进入添加页面
     *  1）URL：http://localhost:8080/system/role/toAdd.do
     *  2）参数：无
     *  3）返回：/WEB-INF/pages/system/role/role-add.jsp
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "system/role/role-add";
    }

    /**
     * 保存数据（添加/修改）
     *   1）URL：http://localhost:8080/system/role/edit.do
     *   2）参数：角色表单数据
     *   3）返回：重定向回到列表
     */
    @RequestMapping("/edit")
    public String edit(Role role){
        //获取登录企业信息
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();

        role.setCompanyId(companyId);
        role.setCompanyName(companyName);

        //判断是否有ID
        if(StringUtils.isEmpty(role.getId())){
            //添加
            roleService.save(role);
        }else{
            //修改
            roleService.update(role);
        }

        return "redirect:/system/role/list.do";
    }

    /**
     * 进入修改页面
     *  1）URL：http://localhost:8080/system/role/toUpdate.do
     *  2)参数：id=1f35c554-3777-419b-b607-f88473068edb
     *  3)返回：/WEB-INF/pages/system/role/role-update.jsp
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        String companyId = getLoginCompanyId();

        //1.根据id查询角色
        Role role = roleService.findById(id);
        //2.存入request域
        request.setAttribute("role",role);

        return "system/role/role-update";
    }

    /**
     * 删除角色
     *  1）URL：http://localhost:8080/system/role/delete.do
     *  2）参数：id=1
     *  3）返回：{"flag":true/false,"errorMsg":"xxxxx"}
     */
    @RequestMapping("/delete")
    @ResponseBody //把Java对象转换为Json字符串
    public Map<String,Object> delete(String id){
        HashMap<String, Object> map = new HashMap<>();
        boolean flag = roleService.delete(id);
        if (!flag) {
            map.put("errorMsg","角色存在关联数据，无法删除");
        }
        map.put("flag",flag);
        return map;
    }

    @RequestMapping("/roleModule")
    public String roleModule(String roleid){
        //1.查询当前角色
        Role role = roleService.findById(roleid);
        request.setAttribute("role",role);
        return "system/role/role-module";
    }

    @RequestMapping("getZtreeNodes")
    @ResponseBody
    public List<Map<String,Object>> getZtreeNodes(@RequestParam(name = "roleid") String id) {
        List<Map<String, Object>> list = new ArrayList<>();
        // 获取所有的模块
        List<Module> moduleListAll = moduleService.findAll();
        // 获取角色关联的所有模块
        List<Module> moduleList = moduleService.findRoleModuleByRoleId(id);
        // 一个模块一个map
        for (Module m : moduleListAll) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",m.getId());
            map.put("pId",m.getParentId());
            map.put("name",m.getName());
            map.put("open",true);
            for (Module m2: moduleList) {
                if (m.equals(m2)) {
                    map.put("checked", true);
                }
            }
          list.add(map);
        }
        return list;
    }

    @RequestMapping("/updateRoleModule")
    public String updateRoleModule(String roleid,String moduleIds){

        //调用service方法
        roleService.updateRoleModule(roleid,moduleIds);

        return "redirect:/system/role/list.do";
    }

}
