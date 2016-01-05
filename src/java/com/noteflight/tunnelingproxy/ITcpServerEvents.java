package com.noteflight.tunnelingproxy;

import java.io.*;
import java.net.*;

/**
 * The TcpServer uses this to report events
 **/
public interface ITcpServerEvents
{
  /**
   * Called when an error occurs trying to create or accept server
   * connections
   * @param exc the IOException representing the error
   **/
  public void serverSocketError(IOException exc);
  
  /**
   * Called when a new connection has been accepted by the server
   * @param socket the Socket holding the connection
   **/
  public void connectionAccepted(Socket socket);
}
