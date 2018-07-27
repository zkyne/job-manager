package com.zkyne.jobmanager.config;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @ClassName: FreeMarkerConfig
 * @Description: freemarker配置类
 * @Author: zkyne
 * @Date: 2018/7/18 10:01
 */
@SpringBootConfiguration
public class FreeMarkerConfig {
//    @Resource
//    private PageTag pageTag;
    @Resource
    private Configuration configuration;
    @Resource
    private FreeMarkerViewResolver resolver;
    @Resource
    private InternalResourceViewResolver springResolver;

    @PostConstruct
    public void setSharedVariable(){
        configuration.setDateFormat("yyyy/MM/dd");

        configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");

        //setSharedVariable配置的是自己的freemarker的自定义标签，在这里把标签注入到共享变量中去就可以在模板中直接调用了
//        configuration.setSharedVariable("pageTag", pageTag);
        try {
            configuration.setSetting("template_update_delay", "1");
            configuration.setSetting("default_encoding", "UTF-8");
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        springResolver.setPrefix("/templates");//解析前缀后XXX路径下的jsp文件
        springResolver.setSuffix(".ftl");
        springResolver.setOrder(1);

        resolver.setSuffix(".ftl");//解析后缀为html的
        resolver.setCache(false);//是否缓存模板
        resolver.setRequestContextAttribute("request");//为模板调用时，调用request对象的变量名
        resolver.setOrder(0);
    }

}
