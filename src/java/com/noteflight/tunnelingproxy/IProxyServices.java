package com.noteflight.tunnelingproxy;

import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * The services made available to objects in the TunnelingProxy
 **/
public interface IProxyServices
{
  /**
   * Called when the AgentServer runs into an error while trying to
   * accept a connection
   * @param exc the exception
   **/
  public void agentServerConnectionError(IOException exc);

  /**
   * Called when the agentServer receives a new connection from an
   * TunnelingAgent
   * @param socket the connection's socket
   **/
  public void agentServerReceivedConnection(Socket socket);

  /**
   * Called when the proxy server receives an HTTP request
   * @param request the http request
   * @param response the http response
   * @throws ServletException
   * @throws IOException
   **/
  public void proxyRequestReceived(HttpServletRequest request,
                                   HttpServletResponse response)
    throws ServletException,
           IOException;

  /**
   * Called when an AgentConnection sends a Packet
   * @param agent the AgentConnection from which the Packet was received
   * @param packet the Packet that was received
   **/
  public void agentSentPacket(AgentConnection agent, Packet packet);

  /**
   * Called when an AgentConnection closes its connection
   * @param agent the AgentConnection that was closed
   **/
  public void agentConnectionClosed(AgentConnection agent);

  /**
   * Called when an error occurs reading from an AgentConnection
   * @param agent the AgentConnection that had the error
   * @exc the exception
   **/
  public void agentReadError(AgentConnection agent, IOException exc);
}
