package com.noteflight.tunnelingproxy;

import java.io.*;
import java.net.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * The Jetty-based server that receives incoming HTTP connections.
 * Each request is delegated to a ProxyServlet.
 **/
public class ProxyServer
{
  IProxyServices services;
  int port;
  
  public ProxyServer(int port, IProxyServices services)
  {
    this.port = port;
    this.services = services;
  }

  public void start()
    throws Exception
  {
    Server server = new Server(port);
    ProxyServlet servlet = new ProxyServlet(services);

    // Create the ServletHandler that will handle api requests
    ServletHandler servletHandler = new ServletHandler();
    servletHandler.addServletWithMapping(new ServletHolder(servlet), "/*");

    // Set the server to first search for static assets, then the
    // servlet
    HandlerList handlers = new HandlerList();
    handlers.setHandlers(new Handler[] {
        servletHandler,
        new DefaultHandler()
      });
    server.setHandler(handlers);
    server.start();
  }
}
