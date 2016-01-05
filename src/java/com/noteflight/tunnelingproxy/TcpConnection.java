package com.noteflight.tunnelingproxy;

import java.io.*;
import java.net.*;

/**
 * Represents a TCP connection.  On start(), this will read from the
 * connection and send the appropriate events to the
 * ITcpConnectionEvents.
 **/
public class TcpConnection
  implements Runnable
{
  public interface IServices {
    public ITcpConnectionEvents getTcpConnectionEvents();
  }
  IServices services;

  Socket socket;
  InputStream in;
  OutputStream out;

  public TcpConnection(Socket socket, IServices services)
  {
    this.socket = socket;
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
      try {
        in = socket.getInputStream();
        out = socket.getOutputStream();

        byte[] buf = new byte[4096];
        int numread;
        while((numread = in.read(buf)) >= 0) {
          if(numread > 0) {
            services.getTcpConnectionEvents().dataReceived(buf, 0, numread);
          }
        }
      }
      finally {
        if(socket != null) {
          socket.close();
          services.getTcpConnectionEvents().closed();
        }
      }
    }
    catch(IOException exc) {
      services.getTcpConnectionEvents().readError(exc);
    }
  }

  /**
   * Writes data to the remote end of the connection
   * @param buf the data to write
   * @param offset the offset into buf for the data to write
   * @param length the amount of data to write
   * @return true if the write was successful, false if it failed, probably because the connection was closed
   **/
  public boolean write(byte[] buf, int offset, int length)
  {
    try {
      if(out != null) {
        out.write(buf, offset, length);
        return true;
      }
      else {
        return false;
      }
    }
    catch(IOException exc) {
      return false;
    }
  }
}
