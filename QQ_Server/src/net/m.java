package net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class m {

  //启动服务器线程
  public static void main(String[] args) {
    Server s = new Server();
    Thread ts = new Thread(s);
    ts.start();



//    ArrayList list = new ArrayList();
//    list.add("aaaa");
//    list.add("aaaa");
//    list.add("bbbb");
//    list.add("cccc");
//    list  = new ArrayList<String>(new HashSet<String>(list));//ArrayList去重复
//    System.out.println(list);
  }

}
