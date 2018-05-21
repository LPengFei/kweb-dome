package com.cnksi.utils;

import com.jfinal.plugin.activerecord.Record;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Beetl模板引擎工具类
 *
 * Created by xyl on 2017/8/8, 008.
 */
public class BeetlUtil {


    /**
     *  解析并渲染字符串
     *
     * @param templateStr 模板字符串
     * @param paraMap 模板参数
     * @return
     * @throws IOException
     */
    public static String renderString(String templateStr, Map<String, Object> paraMap) throws IOException {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate(templateStr);
        t.binding(paraMap);
        return t.render();
    }

    public static void main(String[] args) throws IOException {
        Map<String, Object> map = new HashMap<>();
//        map.put("name", "ksi");
        Record record = new Record();
        record.set("name", "xyl");
        map.put("record", record);
        map.put("name", "test");
        System.out.println(renderString("hell, ${record.name} ${name}", map));
    }
}
