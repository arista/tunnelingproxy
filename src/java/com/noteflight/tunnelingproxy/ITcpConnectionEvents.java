package com.noteflight.tunnelingproxy;

import java.io.*;
import java.net.*;

/**
 * The TcpConnection uses this to report events
 **/
public interface ITcpConnectionEvents
{
  /**
   * Called to indicate that new data has been received by the connection.
   * @param buf the data
   * @param offset the offset into the buf where the data starts
   * @param length the length of the data in the buf
   **/
  public void dataReceived(byte[] buf, int offset, int length);

  /**
   * Called when an error has occurred while trying to read from the
   * connection.
   * @param exc the IOException that occurred
   **/
  public void readError(IOException exc);

  /**
   * Called to indicate that the connection has been closed.
   **/
  public void closed();
}
