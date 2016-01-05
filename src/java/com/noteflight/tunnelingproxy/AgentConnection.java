package com.noteflight.tunnelingproxy;

import java.io.*;
import java.net.*;

/**
 * Represents the long-lived connection from an TunnelingAgent to the
 * TunnelingProxy.  This is that representatin on the proxy side.
 **/
public class AgentConnection
  implements Runnable
{
  IProxyServices services;
  Socket socket;
  InputStream in;
  OutputStream out;
  DataInputStream din;
  DataOutputStream dout;

  public String hostname;

  public AgentConnection(Socket socket, IProxyServices services)
  {
    this.services = services;
    this.socket = socket;
  }

  public void start()
  {
    new Thread(this).start();
  }

  public void run()
  {
    try {
      try {
        in = socket.getInputStream();
        out = socket.getOutputStream();
        din = new DataInputStream(in);
        dout = new DataOutputStream(out);

        try {
          while(true) {
            Packet packet = Packet.readPacket(din);
            services.agentSentPacket(this, packet);
          }
        }
        catch(EOFException exc) {
          services.agentConnectionClosed(this);
        }
      }
      finally {
        if(socket != null) {
          socket.close();
        }
      }
    }
    catch(IOException exc) {
      services.agentReadError(this, exc);
    }
  }
}
