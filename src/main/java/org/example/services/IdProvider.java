package org.example.services;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class IdProvider implements InitializingBean, DisposableBean, BeanPostProcessor {

    Logger logger = Logger.getLogger(IdProvider.class);


    @Override
    public void afterPropertiesSet() {
        logger.info("provider afterPropertiesSet invoked");
    }

    @Override
    public void destroy() {
        logger.info("DisposableBean destroy invoked");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessorBeforeInttialization invoked by bean" + beanName);
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessorAfterInttialization invoked by bean" + beanName);
        return null;
    }

    @PostConstruct
    public void postConstructIdProvider(){
        logger.info("PostConstruct annotated method called");
    }

    @PreDestroy
    public void preDestroyIdProvider(){
        logger.info("PreDestroy annotated method called");
    }

}
