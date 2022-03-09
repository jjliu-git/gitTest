package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Dept;
import cn.itcast.service.system.DeptService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {
    @Autowired
    private DeptService deptService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       @RequestParam(defaultValue = "1") Integer pageNum){
        String companyId = getLoginCompanyId();
        PageInfo<Dept> byPage = deptService.findByPage(pageSize, pageNum, companyId);
        request.setAttribute("pageInfo",byPage);
        return "system/dept/dept-list";
    }
    /**
     * 进入添加页面
     *  1）URL：http://localhost:8080/system/dept/toAdd.do
     *  2）参数：无
     *  3）返回：/WEB-INF/pages/system/dept/dept-add.jsp
     */
    @RequestMapping("/toAdd")
    public String toAdd(HttpServletRequest request){
        String companyId = getLoginCompanyId();

        //查询当前企业的所有部门，为了展示下拉列表
        List<Dept> list = deptService.findAll(companyId);
        //存入request
        request.setAttribute("deptList",list);

        return "system/dept/dept-add";
    }

    /**
     * 保存数据（添加/修改）
     *   1）URL：http://localhost:8080/system/dept/edit.do
     *   2）参数：部门表单数据
     *   3）返回：重定向回到列表
     */
    @RequestMapping("/edit")
    public String edit(Dept dept){
        //获取登录企业信息
        String companyId = getLoginCompanyId();
        String companyName = getLoginCompanyName();

        dept.setCompanyId(companyId);
        dept.setCompanyName(companyName);

        //判断是否有ID
        if(StringUtils.isEmpty(dept.getId())){
            //添加
            deptService.save(dept);
        }else{
            //修改
            deptService.update(dept);
        }

        return "redirect:/system/dept/list.do";

    }

    /**
     * 进入修改页面
     *  1）URL：http://localhost:8080/system/dept/toUpdate.do
     *  2)参数：id=1f35c554-3777-419b-b607-f88473068edb
     *  3)返回：/WEB-INF/pages/system/dept/dept-update.jsp
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id,HttpServletRequest request){
        String companyId = getLoginCompanyId();

        //1.根据id查询部门
        Dept dept = deptService.findById(id);
        //2.查询当前企业的所有部门
        List<Dept> list = deptService.findAll(companyId);
        //3.存入request域
        request.setAttribute("dept",dept);
        request.setAttribute("deptList",list);

        return "system/dept/dept-update";
    }

    @RequestMapping("delete")
    @ResponseBody // 将java对象转为json 对象
    public Map<String,Object> delete(String id) {
        HashMap<String, Object> map = new HashMap<>();
        boolean bool = deptService.delete(id);
        if (bool) {
           map.put("flag",bool);
        } else {
            map.put("flag",bool);
            map.put("errorMsg","部门存在关联数据，无法删除");
        }
        return map;
    }
}
