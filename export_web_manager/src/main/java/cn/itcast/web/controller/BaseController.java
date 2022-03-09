package cn.itcast.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {
    // 用于抽取Controller类的公共代码
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected HttpSession session;

    /**
     * 获取登录企业id方法
     *
     * @return 企业id
     */
    public String getLoginCompanyId() {
        return "1";
    }

    /**
     * 获取登录企业名称方法
     */
    protected String getLoginCompanyName(){
        return "传智播客教育股份有限公司";
    }
}
