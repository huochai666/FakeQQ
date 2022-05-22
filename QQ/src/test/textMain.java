package test;

import dao.factory.ServiceFactory;
import dao.impl.ServiceDapImpl;
import model.Message;
import model.QQUser;
import utils.MyTools;
import view.Login;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class textMain {

  public static void main(String[] args) throws InterruptedException {
    Login text = new Login();
//    Date date = new Date(System.currentTimeMillis());
//
//    java.util.Date nowDate = new java.util.Date(new java.sql.Date(System.currentTimeMillis()).getTime());
//    java.util.Date sqlDate = new java.util.Date(new java.sql.Date(System.currentTimeMillis()-10000).getTime());
//
//    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    String newDate = df.format(nowDate);
//    String sqlDate1 = df.format(sqlDate);
//    System.out.println("newDate:"+newDate+",sqlDate1"+sqlDate1);
//    try {
//      nowDate = df.parse(newDate);
//      sqlDate =  df.parse(sqlDate1);
//    } catch (ParseException e) {
//      e.printStackTrace();
//    }
//    Long time =  nowDate.getTime();
//    Long time2 =  sqlDate.getTime();
//    int Minutes = (int)(time - time2) / (1000 * 60 );
//    System.out.println("差了有分钟："+Minutes);

  }

}
