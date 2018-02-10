package com.erika.askme.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-10 20:24
 **/
@Aspect
@Component
public class testaop {
    private static final Logger logger= LoggerFactory.getLogger(testaop.class) ;
    @Before("execution(* com.erika.askme.controller.IndexController.*(..))")
    public void start()
    {
        logger.info("Before the execution");
    }

    @After("execution(* com.erika.askme.controller.IndexController.*(..))")
    public void end()
    {
        logger.info("finished");
    }
}
