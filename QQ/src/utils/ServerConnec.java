package utils;

import model.QQUser;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerConnec {
  static Socket socket = null;
  public static Socket getSocket(){
    try {
      socket = new Socket(InetAddress.getByName("localhost"),8001);
      System.out.println("socket连接上了");


    } catch (IOException e) {
      e.printStackTrace();
    }
    return socket;
  }

  public static void release(){
    Map<MyTools.Flag,Boolean> map = new HashMap<>();
    map.put(MyTools.Flag.Release,true);
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(ServerConnec.socket.getOutputStream());
      oos.writeObject(map);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}

