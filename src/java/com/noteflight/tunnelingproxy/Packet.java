package com.noteflight.tunnelingproxy;

import java.io.*;
import java.util.*;

/**
 * Superclass for packets flowing between the agent and the proxy
 **/
public abstract class Packet
{
  public static enum Type
  {
    AgentConnectPacket(1),
    ;
    private int typeNum;
    private Type(int typeNum)
    {
      this.typeNum = typeNum;
    }
    public int getTypeNum() { return typeNum; }
    private static Map<Integer,Type> typesByTypeNum;
    public static Map<Integer,Type> getTypesByTypeNum()
    {
      if(typesByTypeNum == null) {
        typesByTypeNum = new HashMap<>();
        for(Type t : values()) {
          typesByTypeNum.put(t.getTypeNum(), t);
        }
      }
      return typesByTypeNum;
    }
  }

  public abstract Type getPacketType();
  public abstract void write(DataOutput dout) throws IOException;
  public abstract void read(DataInput din) throws IOException;

  public static Packet readPacket(DataInput din) throws IOException
  {
    int typeNum = din.readInt();
    Type type = Type.getTypesByTypeNum().get(typeNum);
    Packet p = null;
    if(type != null) {
      switch(type) {
      case AgentConnectPacket:
        p = new AgentConnectPacket();
        break;
      }
    }
    if(p == null) {
      throw new RuntimeException("Unknown incoming packet type " + typeNum);
    }
    p.read(din);
    return p;
  }

  public static void writePacket(Packet packet, DataOutput dout) throws IOException
  {
    dout.writeInt(packet.getPacketType().getTypeNum());
    packet.write(dout);
  }
}
