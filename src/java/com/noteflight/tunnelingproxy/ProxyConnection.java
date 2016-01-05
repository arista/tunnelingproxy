package com.noteflight.tunnelingproxy;

import java.io.*;
import java.net.*;

/**
 * Represents the long-lived connection from an TunnelingAgent to the
 * TunnelingProxy.  This is that representatin on the agent side.
 **/
public class ProxyConnection
  implements Runnable
{
  String proxyHostname;
  int proxyPort;
  IAgentServices services;

  Socket socket;
  InputStream in;
  OutputStream out;
  DataInputStream din;
  DataOutputStream dout;

  public ProxyConnection(String proxyHostname, int proxyPort, IAgentServices services)
  {
    this.proxyHostname = proxyHostname;
    this.proxyPort = proxyPort;
    this.services = services;
  }

  public void start()
    throws IOException
  {
    socket = new Socket(proxyHostname, proxyPort);
    in = socket.getInputStream();
    out = socket.getOutputStream();
    din = new DataInputStream(in);
    dout = new DataOutputStream(out);
    new Thread(this).start();
  }

  public void run()
  {
    try {
      try {
        try {
          while(true) {
            Packet packet = Packet.readPacket(din);
            services.proxySentPacket(packet);
          }
        }
        catch(EOFException exc) {
          services.proxyConnectionClosed();
        }
      }
      finally {
        if(socket != null) {
          socket.close();
        }
      }
    }
    catch(IOException exc) {
      services.proxyReadError(exc);
    }
  }

  public void sendPacket(Packet packet)
    throws IOException
  {
    Packet.writePacket(packet, dout);
  }
}
