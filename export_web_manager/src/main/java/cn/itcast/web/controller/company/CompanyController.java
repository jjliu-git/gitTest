package cn.itcast.web.controller.company;

import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {
    @Autowired
    private CompanyService companyService;

    @RequestMapping("/list")
    public String findAll(HttpServletRequest request,
                          @RequestParam(defaultValue = "5") Integer pageSize,
                          @RequestParam(defaultValue = "1") Integer pageNum) {
        PageInfo<Company> companyPageInfo = companyService.findByPage(pageSize, pageNum);
        //List<Company> companyList = companyService.findAll();
        request.setAttribute("pageInfo", companyPageInfo);
        return "company/company-list";
    }

    @RequestMapping("/toAdd.do")
    public String toAdd() {
        return "company/company-add";
    }

    @RequestMapping("/edit")
    public String edit(Company company) {
        if (StringUtils.isEmpty(company.getId())) {
            companyService.add(company);
        } else {
            companyService.edit(company);
        }
        return "redirect:/company/list.do";
    }
    /**
     * 进入修改页面
     *  1）URL： http://localhost:8080/company/toUpdate.do
     *  2）参数：id=1
     *  3）返回：/WEB-INF/pages/company/company-update.jsp
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id,HttpServletRequest request){
        //1.查询一个企业对象
        Company company = companyService.findById(id);
        //2.存入request域
        request.setAttribute("company",company);

        return "company/company-update";
    }

    @RequestMapping("/delete")
    public String delete(String id){
        //1.查询一个企业对象
        companyService.delete(id);
        return "redirect:/company/list.do";
    }

}
