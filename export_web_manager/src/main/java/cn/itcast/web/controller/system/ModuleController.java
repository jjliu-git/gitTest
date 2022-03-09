package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Module;
import cn.itcast.service.system.ModuleService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {
    //注入Service
    @Autowired
    private ModuleService moduleService;


    /**
     * 方法的API：
     *    方法URL： http://localhost:8080/system/module/list.do
     *    方法参数： pageNum=1&pageSize=5
     *    方法返回值： /WEB-INF/pages/system/module/module-list.jsp
     *
     *    @RequestParam(defaultValue = "1")： 给参数设置默认值，当前参数为NULL使用默认值
     */
    @RequestMapping("/list")
    public String list(
            @RequestParam(defaultValue = "1")Integer pageNum,
            @RequestParam(defaultValue = "5")Integer pageSize){
        //调用业务方法
        PageInfo pageInfo = moduleService.findByPage(pageNum,pageSize);
        //把数据存入request域
        request.setAttribute("pageInfo",pageInfo);
        //返回jsp页面
        return "system/module/module-list";
    }


    /**
     * 跳转新增页面
     *   1）URL：http://localhost:8080/system/module/toAdd.do
     *   2）无
     *   3）返回：/WEB-INF/pages/system/module/module-add.jsp
     */
    @RequestMapping("/toAdd")
    public String toAdd(){

        //查询所有模块
        List<Module> moduleList = moduleService.findAll();
        request.setAttribute("menus",moduleList);

        return "system/module/module-add";
    }


    /**
     * 保存数据（添加/修改）
     *  1）URL： http://localhost:8080/system/module/edit.do
     *  2）参数： 模块的表单所有数据
     *  3）返回： 重定向回到列表
     */
    @RequestMapping("/edit")
    public String edit(Module module){

        //判断是否存在id值
        if(StringUtils.isEmpty(module.getId())){
            //不存在ID值，为添加
            moduleService.save(module);
        }else{
            //存在ID值，为修改
            moduleService.update(module);
        }

        return "redirect:/system/module/list.do";
    }

    /**
     * 进入修改页面
     *  1）URL： http://localhost:8080/system/module/toUpdate.do
     *  2）参数：id=1
     *  3）返回：/WEB-INF/pages/system/module/module-update.jsp
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //1.查询一个模块对象
        Module module = moduleService.findById(id);
        //2.存入request域
        request.setAttribute("module",module);

        //查询所有模块
        List<Module> moduleList = moduleService.findAll();
        request.setAttribute("menus",moduleList);

        return "system/module/module-update";
    }

    /**
     * 删除
     *  1）URL：http://localhost:8080/module/delete.do
     *  2）参数：id=895f8314-8490-4c1f-b98e-c999da93eb5e
     *  3）返回： 重定向回到列表
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(String id){
        HashMap<String, Object> map = new HashMap<>();
        boolean flag = moduleService.delete(id);
        if (!flag) {
            map.put("errorMsg","模块存在关联数据，无法删除");
        }
        map.put("flag",flag);
        return map;
    }
}
