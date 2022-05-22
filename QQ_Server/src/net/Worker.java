package net;

import dao.factory.ServiceFactory;
import dao.implement.ServiceDapImpl;
import jdk.internal.util.xml.impl.Input;
import model.FriendTable;
import model.Message;
import model.QQUser;
import utils.MyTools;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Worker implements Runnable{

  Socket socket;
  Map<String,Worker> onLineWorker;

  Worker(Socket socket,Map<String,Worker> onLineWorker){
    this.socket = socket;
    this.onLineWorker = onLineWorker;
  }

  @Override
  public void run() {
    String qq_id ;
    String qq_password;
    String qq_phone;

    ObjectInputStream oi = null;
    ObjectOutputStream oos = null;

    //判断线程是否继续存活
    boolean life = true;
    //判断是否已经登陆（可将值设为false，退出登录）
    boolean logged = false;

    while(life){
      try {
        ServiceDapImpl serviceDap = ServiceFactory.newInstance();
        System.out.println(socket.getPort());
        oi = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
        Map<MyTools.Flag, QQUser> data = (HashMap)oi.readObject();

        Map<MyTools.Flag,Object> outData = new HashMap<>();//发给客户端的数据包

        //通过keySet把map里的key全丢到set数组里面
        Set<MyTools.Flag> result = data.keySet();
        for(MyTools.Flag flag:result){
          System.out.println("尝试"+flag);
          switch (flag){
            case QueryQQID: //通过手机号查找QQ
              String resultQQID1 = serviceDap.queryQQID(data.get(flag).getQq_phone());
              System.out.println("要查询的手机号为："+data.get(flag).getQq_phone()+"，经查询QQ号码为"+resultQQID1);
              outData.put(MyTools.Flag.QueryQQID,resultQQID1);
              oos.writeObject(outData);
              break;
            case Login:
              //验证用户名密码是否正确
              if(serviceDap.queryUser(data.get(flag).getQq_phone(),data.get(flag).getQq_password())){
                qq_phone = data.get(flag).getQq_phone();
                qq_id = serviceDap.queryQQID(qq_phone)==null?qq_phone:serviceDap.queryQQID(qq_phone);
//                qq_password = data.get(flag).getQq_password();
                System.out.println("验证成功！QQ号"+qq_id+"密码正确"+serviceDap.queryQQID(qq_phone));
                if(serviceDap.queryQQID(qq_phone)==null){
                  outData.put(MyTools.Flag.Login_Result,MyTools.Flag.Login_Success_inNumber);
                }else {
                  outData.put(MyTools.Flag.Login_Result,MyTools.Flag.Login_Success_inPhone);
                }
//                oos.writeObject(true);
                oos.writeObject(outData);
                logged = true;
                onLineWorker.put(qq_id,this);
                while (logged) {
                  System.out.println("现在登录的老哥有："+onLineWorker);
                  //登录完成之后处理登录完之后的数据
                  oi = new ObjectInputStream(socket.getInputStream());
                  oos = new ObjectOutputStream(socket.getOutputStream());
                  Map<MyTools.Flag,Object> outDataLogged = new HashMap<>();//发给客户端的数据包(登录过的)
                  Map<MyTools.Flag, Object> dataLogged = (HashMap)oi.readObject();
                  //通过keySet把map里的key全丢到set数组里面
                  Set<MyTools.Flag> resultLogged = dataLogged.keySet();
                  for(MyTools.Flag flagLogged:resultLogged) {
                    System.out.println("尝试" + flagLogged);
                    switch (flagLogged) {
                      //根据QQ号获取用户资料
                      case GetNewPersonData:
                        QQUser user = serviceDap.queryQQUser((String) dataLogged.get(flagLogged));
                        outDataLogged.put(MyTools.Flag.GetNewPersonData,user);
                        break;

                      case QueryQQID: //通过手机号查找QQ
                        String resultQQID = serviceDap.queryQQID(((QQUser)dataLogged.get(flagLogged)).getQq_phone());
                        outDataLogged.put(MyTools.Flag.QueryQQID,resultQQID);
                        break;

                      case QueryFriendList: //通过QQ查找好友列表
                        FriendTable friendTable = serviceDap.queryFriendList((String) dataLogged.get(flagLogged));
                        outDataLogged.put(MyTools.Flag.QueryFriendList,friendTable);
                        break;

                      case QueryUserFuzzySearch: //通过QQ号或昵称模糊查找用户
                        List<QQUser> list = serviceDap.fuzzySearch((String) dataLogged.get(flagLogged));
                        outDataLogged.put(MyTools.Flag.QueryUserFuzzySearch,list);
                        break;
                      case SendMessage://发送一条消息
                        Message message = (Message) dataLogged.get(flagLogged);//从客户端接收到消息
                        //文件类型为消息时，转发到对应的socket
                        if(message.getType()==0 || message.getType()==1 || message.getType()==2){
                          if(onLineWorker.get(message.getAccepter())!=null){//在线的话，提醒他更新消息
                            onLineWorker.get(message.getAccepter()).receiveMessages(message);//转发到接收人对标的socket中
                          }
                        }
                        break;

                      case CheckUnreadMessage://根据QQ查询未读消息
                        String checkId = (String) dataLogged.get(flagLogged);//需要查询的用户ID
                        List<Message> listUnread = serviceDap.unreadMessage(checkId);
                        outDataLogged.put(MyTools.Flag.CheckUnreadMessage,listUnread);
                        break;

                      case QueryTwoPersonChat://两个qq之间的聊天记录
                        String[] str1 = (String[]) dataLogged.get(flagLogged);
                        List<Message> listTwoPerson = serviceDap.TwoPersonChat(str1[0],str1[1]);
                        outDataLogged.put(MyTools.Flag.QueryTwoPersonChat,listTwoPerson);
                        break;

                      case UpdateUserData://更新用户资料
                        QQUser user2 = (QQUser) dataLogged.get(flagLogged);
                        serviceDap.updateUser(user2);
                        break;

                      case UpdateUserProfile://更新用户头像
                        QQUser user3 =(QQUser) dataLogged.get(flagLogged);
                        serviceDap.addProfile(user3.getQq_profile(),user3.getQq_id());
                        break;

                      case Release:
                        System.out.println("注销一个");
                        onLineWorker.remove(qq_id);
                        life=false;
                        break;
                      case AddFriend:
                        String[] strings = (String[]) dataLogged.get(flagLogged);
                        serviceDap.addFriend(strings[0],strings[1]);
                        break;
                    }
                  }
                  oos.writeObject(outDataLogged);
                }
              }else {
                System.out.println("密码错误！");
                outData.put(MyTools.Flag.Login_Result,MyTools.Flag.Login_Failed);
              }
              oos.writeObject(outData);
              break;
            case Register:
              String qqid = serviceDap.queryQQID(data.get(flag).getQq_phone());
              if(qqid == null){
                if(serviceDap.addUser(data.get(flag).getQq_phone(),data.get(flag).getQq_password())==1){
                  qqid = serviceDap.queryQQID(data.get(flag).getQq_phone());
                  System.out.println("注册成功！QQ号为："+qqid);
                  outData.put(MyTools.Flag.Register_Result,MyTools.Flag.Register_Success);//注册成功返回QQ号
                }else{
                  outData.put(MyTools.Flag.Register_Result,MyTools.Flag.Register_Failed);//注册失败，QQ号已存在
                }
              }else {
                outData.put(MyTools.Flag.Register_Result,MyTools.Flag.Register_Failed_Phone_exists);//注册失败，手机号已存在
              }
              oos.writeObject(outData);
              break;
            case Release:
              System.out.println("注销一个");
              onLineWorker.remove(data.get(flag).getQq_id());
              life=false;
              break;
          }
        }
      } catch (IOException e) {
        break;
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }

    }

  }

  void receiveMessages(Message message){
    ObjectOutputStream oos = null;
    try {
      Map<MyTools.Flag,Message> data = new HashMap<>();
      data.put(MyTools.Flag.ReceiveMessages,message);
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(data);
      System.out.println(message);
      oos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
