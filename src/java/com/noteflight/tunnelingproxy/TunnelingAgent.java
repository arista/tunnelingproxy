package com.noteflight.tunnelingproxy;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.*;
import java.net.*;

/**
 * The process that runs behind the firewall with a connection to a
 * TunnelingProxy, that relays requests from the TunnelingProxy to an
 * origin server behind the firewall.
 **/
public class TunnelingAgent
  implements IAgentServices
{
  // Parameters
  public static class TunnelingAgentParams
  {
    @Parameter(names = {"-proxyHost"}, required = true,
               description = "The hostname where the TunnelingProxy is running")
    public String proxyHost;
    @Parameter(names = {"-proxyPort"}, required = true,
               description = "The port number where the TunnelingProxy is accepting agent connections")
    public int proxyPort;
    @Parameter(names = {"-originHost"}, required = true,
               description = "The hostname of the origin server for whom requests are being proxied")
    public String originHost;
    @Parameter(names = {"-originPort"}, required = true,
               description = "The port of the origin server for whom requests are being proxied")
    public int originPort;
    @Parameter(names = {"-proxiedHostname"}, required = true,
               description = "The name of the virtual host for whom requests should be proxied")
    public String proxiedHostname;
  }
  TunnelingAgentParams params;

  ProxyConnection proxyConnection;
  
  public TunnelingAgent(TunnelingAgentParams params)
  {
    this.params = params;
  }

  public void start()
    throws Exception
  {
    proxyConnection = new ProxyConnection(params.proxyHost, params.proxyPort, this);
    proxyConnection.start();
    Packet connectPacket = new AgentConnectPacket(params.proxiedHostname);
    proxyConnection.sendPacket(connectPacket);
  }

  public static void main(String[] args)
    throws Exception
  {
    TunnelingAgentParams params = new TunnelingAgentParams();
    new JCommander(params, args);
    TunnelingAgent agent = new TunnelingAgent(params);
    agent.start();
  }

  //--------------------------------------------------
  // IAgentServices methods
  
  /**
   * Called when the proxy sends a packet to the Agent
   * @param packet the packet that was sent
   **/
  public void proxySentPacket(Packet packet)
  {
    System.out.println("FIXME - proxySentPacket not implemented");
  }

  /**
   * Called when the proxy connection has been closed
   **/
  public void proxyConnectionClosed()
  {
    System.out.println("FIXME - proxyConnectionClosed not implemented");
  }

  /**
   * Called when there is an error reading from the proxy connection
   * @param exc the exception
   **/
  public void proxyReadError(IOException exc)
  {
    System.out.println("FIXME - proxyReadError not implemented");
  }
}
