package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class Server implements Runnable{

  Boolean isRunning = null;

  //前面存QQ号，后面存与客户端对应的Socket
  Map<String,Worker> onLineWorker = new HashMap<>();

  Server(){
    isRunning = true;
  }

  @Override
  public void run() {
    ServerSocket ss = null;
    try {
      ss = new ServerSocket(8001);
      System.out.println("服务器启动完毕");

      while (isRunning){
        Thread clientWork = new Thread(new Worker(ss.accept(),onLineWorker));
        clientWork.start();
        System.out.println("有新客户端连接，名字："+clientWork.getName());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
