package com.udemy.course.ormtool.web;

import com.udemy.course.ormtool.annotations.Bean;
import com.udemy.course.ormtool.context.ApplicationRunner;
import com.udemy.course.ormtool.context.Environment;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.glassfish.jersey.servlet.ServletContainer;

@Bean
public class RestServer { //implements ApplicationRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String BASE_PATH = "/v1/tool/*";
    private final Environment environment;

    private Server server;

    public RestServer(Environment environment) {
        this.environment = environment;
    }

    private int getConfiguredPort() {
        return Integer.parseInt( environment.getProperty("application.server.port"));
    }

    public void startServer() throws Exception {

        int httpPort = getConfiguredPort();
        server = new Server(httpPort);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder servletContainerHolder = context.addServlet(ServletContainer.class, BASE_PATH);
        servletContainerHolder.setInitOrder(0);
        servletContainerHolder.setInitParameter("javax.ws.rs.Application", RestApplication.class.getName());

        server.start();
        server.join();
    }

    public void stopServer() throws Exception {
        server.stop();
        server.destroy();
    }

//    @Override
    public void run(String[] args) {
        try {
            startServer();
        } catch (Exception e) {
            log.error("Could Not Start WebServer: {}", e.getMessage());
        }
    }
}
