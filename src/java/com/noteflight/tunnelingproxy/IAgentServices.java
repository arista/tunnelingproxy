package com.noteflight.tunnelingproxy;

import java.io.*;
import java.net.*;

/**
 * The services made available to objects in the TunnelingAgent
 **/
public interface IAgentServices
{
  /**
   * Called when the proxy sends a packet to the Agent
   * @param packet the packet that was sent
   **/
  public void proxySentPacket(Packet packet);

  /**
   * Called when the proxy connection has been closed
   **/
  public void proxyConnectionClosed();

  /**
   * Called when there is an error reading from the proxy connection
   * @param exc the exception
   **/
  public void proxyReadError(IOException exc);
}
