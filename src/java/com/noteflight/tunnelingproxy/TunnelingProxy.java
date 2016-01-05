package com.noteflight.tunnelingproxy;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * This is the main class that runs the TunnelingProxy.  The proxy is
 * meant to run outside of the firewall and accept incoming HTTP
 * connections.  TunnelingAgent processes are started within the
 * firewall, and also connect to the TunnelingProxy, registering
 * themselves with a specific HTTP host.  Incoming HTTP requests are
 * then "routed" to those TunnelingAgents based on host.
 **/
public class TunnelingProxy
  implements IProxyServices
{
  // Parameters
  public static class TunnelingProxyParams
  {
    @Parameter(names = {"-httpPort"}, required = true,
               description = "The port on which to accept incoming http connections")
    public int httpPort;
    @Parameter(names = {"-agentPort"}, required = true,
               description = "The port on which to accept connections from TunnelingAgents")
    public int agentPort;
  }
  TunnelingProxyParams params;

  ProxyServer proxyServer;
  AgentServer agentServer;
  
  Map<String,AgentConnection> agentByHostname = new HashMap<>();

  public TunnelingProxy(TunnelingProxyParams params)
  {
    this.params = params;
  }

  public void start()
    throws Exception
  {
    proxyServer = new ProxyServer(params.httpPort, this);
    proxyServer.start();
    agentServer = new AgentServer(params.agentPort, this);
    agentServer.start();
  }

  public static void main(String[] args)
    throws Exception
  {
    TunnelingProxyParams params = new TunnelingProxyParams();
    new JCommander(params, args);
    TunnelingProxy proxy = new TunnelingProxy(params);
    proxy.start();
  }

  public synchronized void registerAgent(AgentConnection agent, String hostname)
  {
    // FIXME - check for duplicate
    agentByHostname.put(hostname, agent);
    agent.hostname = hostname;
    // FIXME - real logging
    System.out.println("Registered agent with hostname " + hostname);
  }
  
  //--------------------------------------------------
  // IProxyServices methods
  
  /**
   * Called when the AgentServer runs into an error while trying to
   * accept a connection
   * @param exc the exception
   **/
  public void agentServerConnectionError(IOException exc)
  {
    System.out.println("FIXME - agentServerConnectionError not implemented");
  }

  /**
   * Called when the agentServer receives a new connection from an
   * TunnelingAgent
   * @param socket the connection's socket
   **/
  public void agentServerReceivedConnection(Socket socket)
  {
    AgentConnection agentConnection = new AgentConnection(socket, this);
    agentConnection.start();
  }

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
           IOException
  {
    System.out.println("FIXME - agentServerReceivedConnection not implemented");
  }

  /**
   * Called when an AgentConnection sends a Packet
   * @param agent the AgentConnection from which the Packet was received
   * @param packet the Packet that was received
   **/
  public void agentSentPacket(AgentConnection agent, Packet packet)
  {
    switch(packet.getPacketType()) {
    case AgentConnectPacket: {
      AgentConnectPacket pp = (AgentConnectPacket) packet;
      registerAgent(agent, pp.hostname);
      break;
    }
    default: {
      System.out.println("FIXME - unknown packet type " + packet.getPacketType());
    }
    }
  }

  /**
   * Called when an AgentConnection closes its connection
   * @param agent the AgentConnection that was closed
   **/
  public void agentConnectionClosed(AgentConnection agent)
  {
    System.out.println("FIXME - agentConnectionClosed not implemented");
  }

  /**
   * Called when an error occurs reading from an AgentConnection
   * @param agent the AgentConnection that had the error
   * @exc the exception
   **/
  public void agentReadError(AgentConnection agent, IOException exc)
  {
    System.out.println("FIXME - agentReadError not implemented");
  }
}
