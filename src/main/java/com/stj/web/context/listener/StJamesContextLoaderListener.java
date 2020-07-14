package com.stj.web.context.listener;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

public class StJamesContextLoaderListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
    }

    
}
