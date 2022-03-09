package cn.itcast.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换器类
 *  源类型
 *  目标类型
 */
public class StringToDateConverter implements Converter<String,Date>{
    @Override
    public Date convert(String source) {

        //判空
        if(StringUtils.isEmpty(source)){
            return null;
        }

        //转换
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}

