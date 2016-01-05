package com.noteflight.tunnelingproxy;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * "Watches" the incoming bytes for an HttpStream and fires the
 * appropriate events based on what it sees.
 **/
public class ProxyServlet
  extends HttpServlet
{
  IProxyServices services;
  
  public ProxyServlet(IProxyServices services)
  {
    this.services = services;
  }

  protected void service(HttpServletRequest request,
                         HttpServletResponse response)
    throws ServletException,
           IOException
  {
    services.proxyRequestReceived(request, response);
  }
}
