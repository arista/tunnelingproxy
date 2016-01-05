package com.noteflight.tunnelingproxy;

import java.io.*;
import java.net.*;

/**
 * The server that accepts requests from TunnelingAgents
 **/
public class AgentServer
  implements Runnable
{
  int port;
  IProxyServices services;
  ServerSocket serverSocket;
  
  public AgentServer(int port, IProxyServices services)
  {
    this.port = port;
    this.services = services;
  }

  public void start()
    throws IOException
  {
    serverSocket = new ServerSocket(port);
    new Thread(this).start();
  }

  public void run()
  {
    while(true) {
      try {
        Socket s = serverSocket.accept();
        services.agentServerReceivedConnection(s);
      }
      catch(IOException exc) {
        services.agentServerConnectionError(exc);
      }
    }
  }
}
