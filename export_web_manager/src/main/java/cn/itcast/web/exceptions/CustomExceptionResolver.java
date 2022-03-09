package cn.itcast.web.exceptions;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理类
 *   HandlerExceptionResolver: 只要实现类该接口，控制器只要抛出异常，就会跳转到该接口的resolveException方法
 */
@Component
public class CustomExceptionResolver implements HandlerExceptionResolver{
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //把错误信息转换到error.jsp页面显示

        ModelAndView mv = new ModelAndView();
        //设置错误信息
        mv.addObject("errorMsg","对不起，我错了。"+e.getMessage());
        //设置页面
        mv.setViewName("error");
        return mv;
    }
}

