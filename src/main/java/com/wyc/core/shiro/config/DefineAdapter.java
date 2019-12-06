package com.wyc.core.shiro.config;

import com.wyc.core.shiro.interceptor.ModelInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by wangyc on 2019/11/19.
 */
@Configuration
public class DefineAdapter  implements WebMvcConfigurer  {
    @Autowired
    public ModelInterceptor modelInterceptor;

    @Value("${filesDir.staticFileDir}")
    private String staticFileDir;
    @Value("${filesDir.uploadFileDir}")
    private String uploadFileDir;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注解拦截
        //registry.addInterceptor(roleInterceptor).addPathPatterns("/**");

        registry.addInterceptor(modelInterceptor).addPathPatterns("/**/*.html").excludePathPatterns("/index.html","/main.html","/404.html","/403.html","/LotteryMgr/LotteryUser.html");

    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("main");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticFileDir).addResourceLocations("file:"+uploadFileDir);
    }


}