package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Dept;
import cn.itcast.domain.system.Role;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.DeptService;
import cn.itcast.service.system.RoleService;
import cn.itcast.service.system.UserService;
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
@RequestMapping("/system/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "5") int pageSize,
                       @RequestParam(defaultValue = "1") int pageNum){
        String companyId = getLoginCompanyId();
        PageInfo<User> userPageInfo = userService.listAll(pageSize, pageNum, companyId);
        request.setAttribute("pageInfo",userPageInfo);
        // 因为视图解析器中配置的返回前缀是WEB_INF/pages/
        return "system/user/user-list";
    }

    @RequestMapping("toAdd")
    public String toAdd() {
        String companyId = getLoginCompanyId();
        //提前查询当前企业的所有部门，以便展示部门列表
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList",deptList);

        return "system/user/user-add";
    }

    @RequestMapping("edit")
    public String edit(User user) {
        String loginCompanyId = getLoginCompanyId();
        String loginCompanyName = getLoginCompanyName();
        user.setCompanyId(loginCompanyId);
        user.setCompanyName(loginCompanyName);
        if (StringUtils.isEmpty(user.getId())) {
            // ADD
            userService.save(user);
        } else {
            userService.update(user);
        }
        return "redirect:/system/user/list.do";
    }

    /**
     * 进入修改页面
     *    1）访问路径： http://localhost:8080/system/user/toUpdate.do
     *    2) 方法参数: id=1
     *    3) 方法返回值： /WEB-INF/pages/system/user/user-update.jsp
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){

        //1.根据id查询用户
        User user = userService.findById(id);
        request.setAttribute("user",user);

        //2.查询所有用户作为上级用户
        String companyId = getLoginCompanyId();
        //提前查询当前企业的所有部门，以便展示部门列表
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList",deptList);

        return "system/user/user-update";
    }

    /**
     * 删除用户
     *   1）访问路径： http://localhost:8080/system/user/delete.do
     *   2) 方法参数: id=1
     *   3) 方法返回值： {flag:false,message:'错误信息'}
     *
     *   @ResponseBody：把Java对象转换为json字符串，返回给前端。方法返回值上或方法上
     *   @RequestBody： 接收页面的json字符串，转换成Java对象。方法参数上
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(String id){
        HashMap<String, Object> map = new HashMap<>();
        boolean flag = userService.delete(id);
        if (!flag) {
            map.put("errorMsg","用户存在关联数据，无法删除");
        }
        map.put("flag",flag);
        return map;
    }

    @RequestMapping("/roleList")
    public String roleList(String id) {
        //1.查询当前企业的所有角色
        List<Role> roleList = roleService.findAll(getLoginCompanyId());

        //2.查询当前用户分配过的角色
        List<String> userRoleList = roleService.findUserRoleByUserId(id);

        request.setAttribute("roleList", roleList);
        request.setAttribute("userRoleList", userRoleList);

        //3.查询当前用户
        User user = userService.findById(id);
        request.setAttribute("user", user);

        return "system/user/user-role";
    }

    /**
     * 保存用户和角色的关系
     *  1）URL: http://localhost:8080/system/user//changeRole.do
     *  2)参数：userid=1&roleIds=1&roleIds=2&roleIds=3....
     *  3）返回：重定向到列表
     *
     *     接收多个同名的参数的情况：
     *      1）String： 把所有参数值以逗号分隔拼接成一个字符串
     *      2）String[]: 使用数组分别接收每个参数值
     *      3）List<String>: 不能直接使用集合接收，必须加上@RequestParam注解接收
     */
    @RequestMapping("/changeRole")
    public String changeRole(String userid,@RequestParam("roleIds") List<String> roleIds){

        //调用业务
        userService.changeRole(userid,roleIds);

        return "redirect:/system/user/list.do";
    }
}
