package com.noteflight.tunnelingproxy;

import java.io.*;
import java.net.*;

/**
 * A generic TcpServer that makes calls to ITcpServerEvents when
 * server connections arrive.
 **/
public class TcpServer
  implements Runnable
{
  /** The Services required by the TcpServer */
  public interface IServices {
    public ITcpServerEvents getTcpServerEvents();
  }
  IServices services;

  int port;
  ServerSocket serverSocket;

  //--------------------------------------------------
  public TcpServer(int port, IServices services)
  {
    this.port = port;
    this.services = services;
  }

  //--------------------------------------------------
  public void start()
  {
    new Thread(this).start();
  }

  public void run()
  {
    try {
      serverSocket = new ServerSocket(port);
      while(true) {
        Socket s = serverSocket.accept();
        services.getTcpServerEvents().connectionAccepted(s);
      }
    }
    catch(IOException exc) {
      services.getTcpServerEvents().serverSocketError(exc);
    }
  }
}
