package com.erika.askme.Configuration;

import com.erika.askme.Interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-12 22:39
 **/
@Component
public class AskMeConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    PassportInterceptor passport;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passport);
        super.addInterceptors(registry);
    }
}
