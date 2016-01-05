package com.noteflight.tunnelingproxy;

import java.io.*;
import java.util.*;

/**
 * The first packet sent by the Agent to the Proxy, directing the
 * Proxy to send requests with the given hostname to the Agent.
 **/
public class AgentConnectPacket
  extends Packet
{
  public String hostname;

  public AgentConnectPacket() {}
  public AgentConnectPacket(String hostname)
  {
    this.hostname = hostname;
  }
  public Type getPacketType()
  {
    return Type.AgentConnectPacket;
  }
  public void write(DataOutput dout) throws IOException
  {
    dout.writeUTF(hostname);
  }
  public void read(DataInput din) throws IOException
  {
    hostname = din.readUTF();
  }
  public String toString()
  {
    return "AgentConnectPacket(hostname: \"" + hostname + "\")";
  }
}
