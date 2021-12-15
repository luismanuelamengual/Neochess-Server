package org.neochess;

import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;

public class Server {
    private final org.eclipse.jetty.server.Server server;
    private final ServerConnector connector;

    public Server() {
        server = new org.eclipse.jetty.server.Server();
        connector = new ServerConnector(server);
        server.addConnector(connector);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        JettyWebSocketServletContainerInitializer.configure(context, (servletContext, wsContainer) -> {
            wsContainer.setMaxTextMessageSize(65535);
            wsContainer.addMapping("/*", ClientHandler.class);
        });
    }

    public void setPort(int port) {
        connector.setPort(port);
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public void join() throws InterruptedException {
        System.out.println("Use Ctrl+C to stop server");
        server.join();
    }
}