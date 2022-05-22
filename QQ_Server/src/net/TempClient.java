package net;

import model.QQUser;
import utils.MyTools;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TempClient {

  public static void main(String[] args) {


    try {
      Socket s = new Socket("127.0.0.1",8001);

      ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
      Map<MyTools.Flag, QQUser> user = new HashMap<>();
      QQUser user1 = new QQUser("1037782228","123456");
      user.put(MyTools.Flag.Login,user1);

      oos.writeObject(user);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
