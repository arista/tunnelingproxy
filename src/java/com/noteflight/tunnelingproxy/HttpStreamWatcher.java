package com.noteflight.tunnelingproxy;

import java.io.*;
import java.net.*;

/**
 * "Watches" the incoming bytes for an HttpStream and fires the
 * appropriate events based on what it sees.
 **/
public class HttpStreamWatcher
{
  public interface IServices {
    public IHttpStreamWatcherEvents getHttpStreamWatcherEvents();
  }
  IServices services;

  public HttpStreamWatcher(IServices services)
  {
    this.services = services;
  }

  //--------------------------------------------------
  IHttpStreamWatcherEvents getEvents()
  {
    return services.getHttpStreamWatcherEvents();
  }

  //--------------------------------------------------
  /**
   * Processes the incoming bytes from the given buffer
   * @param buf the bytes to process
   * @param offset the offset into buf to start processing
   * @param length the number of bytes to process
   **/
  public void process(byte[] buf, int offset, int length)
  {
    for(int i = 0; i < length; i++) {
      process(buf[offset + i]);
    }
  }

  /**
   * Processes one byte
   * @param val the byte to process
   **/
  public void process(byte val)
  {
    // FIXME - implement this
  }
}
