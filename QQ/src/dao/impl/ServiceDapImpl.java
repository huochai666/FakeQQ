package dao.impl;

import dao.service.ServiceDao;
import model.*;
import utils.MyTools;
import utils.ServerConnec;
import view.ChatJFrame;

import javax.swing.plaf.metal.OceanTheme;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class ServiceDapImpl implements ServiceDao {

  public Socket socket = null;
  //从服务器接收来的数据
  private Map<MyTools.Flag,Object> dataService = new HashMap<>();
  public Map<String, ChatJFrame> chat = new HashMap<>();

  public Map<String, ChatJFrame> getChat() {
    return chat;
  }

  public void setChat(Map<String, ChatJFrame> chat) {
    this.chat = chat;
  }

  public ServiceDapImpl(){
    socket = ServerConnec.getSocket();
    System.out.println("新建了一次socket");
    ObjectInputStream ois = null;
    Thread th31 = new Thread(new Runnable() {
      @Override
      public  void run() {
        while(true){
          ObjectInputStream ois = null;
          try {
            ois = new ObjectInputStream(socket.getInputStream());
            Map<MyTools.Flag,Object> temp = (HashMap)ois.readObject();
            //通过keySet把map里的key全丢到set数组里面
            Set<MyTools.Flag> result = temp.keySet();
            for(MyTools.Flag flag:result){
              if(flag== MyTools.Flag.ReceiveMessages){
                Message messages = (Message) temp.get(flag);
                System.out.println("收到来自服务器的："+messages);
                ChatJFrame cj = chat.get(messages.getSender());
                cj.setVisible(true);
                cj.acceptMessage(messages);
                dataService.put(flag,temp.get(flag));
              }else {
                dataService.put(flag,temp.get(flag));
              }
            }
          } catch (IOException e) {
            System.out.println("服务器连接丢失，客户端自动关闭");
            System.exit(0);
          } catch (ClassNotFoundException e) {
            e.printStackTrace();
          }
        }
      }

    });
    th31.start();
  }

  @Override
  public MyTools.Flag doLogin(String username, String password) {
    //flag是请求的状态，可以让服务器识别当前动作，定义一个user放到map里是为了传值给服务器
    QQUser user = new QQUser(username,password);
    Map<MyTools.Flag,QQUser> data = new HashMap<>();
    data.put(MyTools.Flag.Login,user);
    ObjectOutputStream oos = null;

    MyTools.Flag result = null;
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(data);

    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Thread.sleep(20);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    result = (MyTools.Flag) dataService.get(MyTools.Flag.Login_Result);
    return result;
  }

  @Override
  public Object doRegister(String phone, String password) {

    QQUser user = new QQUser(phone,password);
    Map<MyTools.Flag,QQUser> data = new HashMap<>();
    data.put(MyTools.Flag.Register,user);

    ObjectOutputStream oos = null;

    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(data);

    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Thread.sleep(20);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    MyTools.Flag flag = (MyTools.Flag) dataService.get(MyTools.Flag.Register_Result);
    System.out.println(flag);
    switch (flag){
      case Register_Success:
        String num = queryQQID(phone);
        System.out.println("num"+num+",phone"+phone);
        return num;
      case Register_Failed_Phone_exists:
        System.out.println("手机号已存在！");
        return flag;
      case Register_Failed:
        System.out.println("注册失败");
        return flag;
    }

    return null;
  }

  @Override
  //获取用户资料
  public QQUser getPersonData(String qq_id) {
      Map<MyTools.Flag,String> data = new HashMap<>();
      data.put(MyTools.Flag.GetNewPersonData,qq_id);
      ObjectOutputStream oos = null;
      QQUser user = null;
      try {
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(data);
        oos.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }

    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }


      user = (QQUser)dataService.get(MyTools.Flag.GetNewPersonData);

      return user;
    }

  @Override
  //根据手机号获取qq
  public String queryQQID(String qq_phone) {
    Map<MyTools.Flag,QQUser> data = new HashMap<>();
    QQUser user = new QQUser();
    user.setQq_phone(qq_phone);
    data.put(MyTools.Flag.QueryQQID,user);
    ObjectOutputStream oos = null;
    String qq_id = null;

    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(data);

    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("要查询的手机号为："+qq_phone);
    qq_id =  (String)dataService.get(MyTools.Flag.QueryQQID);
    System.out.println("查询完毕，qq号为："+qq_id);
    return qq_id;
  }

  @Override
  //根据QQ号查询好友
  public FriendTable queryFriendList(String qq_id) {
    Map<MyTools.Flag,String> data = new HashMap<>();
    ObjectOutputStream oos = null;
    FriendTable friendTable = null;

    data.put(MyTools.Flag.QueryFriendList,qq_id);
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(data);

    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }


    friendTable = (FriendTable) dataService.get(MyTools.Flag.QueryFriendList);
    return friendTable;
  }

  @Override
  //模糊查找（根据QQ号和昵称）
  public List<QQUser> fuzzySearch(String str) {
    Map<MyTools.Flag,String> data = new HashMap<>();
    List<QQUser> list = null;
    data.put(MyTools.Flag.QueryUserFuzzySearch,str);
    ObjectOutputStream oos = null;
    try {
      oos =new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(data);

      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      list = (List<QQUser>)dataService.get(MyTools.Flag.QueryUserFuzzySearch);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return list;
  }

  @Override
  //发送消息
  public void sendMessage(Message message) {
    Map<MyTools.Flag,Message> data = new HashMap<>();
    data.put(MyTools.Flag.SendMessage,message);

    ObjectOutputStream oos = null;
    try {
      System.out.println("确认一遍："+data);
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(data);
      System.out.println("发送完成："+ data);
      oos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateUser(QQUser user) {
    Map<MyTools.Flag,QQUser> data = new HashMap();
    data.put(MyTools.Flag.UpdateUserData,user);

    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(data);
      oos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addProfile(QQUser user) {
    Map<MyTools.Flag,QQUser> data = new HashMap<>();
    data.put(MyTools.Flag.UpdateUserProfile,user);
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(data);
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addFriend(String myqq, String addqq) {
    ObjectOutputStream oos = null;
    Map<MyTools.Flag,String[]> data = new HashMap<>();
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      String[] strings = {myqq,addqq};
      data.put(MyTools.Flag.AddFriend,strings);
      oos.writeObject(data);
      oos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  //查询未读消息
  public List<Message> unreadMessage(String qq_id) {
    ObjectOutputStream oos = null;
    List<Message> list = null;
    Map<MyTools.Flag,String> data = new HashMap<>();
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      data.put(MyTools.Flag.CheckUnreadMessage,qq_id);
      oos.writeObject(data);
      oos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    list = (List<Message>) dataService.get(MyTools.Flag.CheckUnreadMessage);

    return list;
  }

  @Override
  public ArrayList<Message> TwoPersonChat(String qq_id1, String qq_id2) {
    ObjectOutputStream oos = null;
    ArrayList<Message> list = null;
    String[] str = {qq_id1,qq_id2};
    Map<MyTools.Flag,String[]> data = new HashMap<>();
    try {
      oos = new ObjectOutputStream(socket.getOutputStream());
      data.put(MyTools.Flag.QueryTwoPersonChat,str);
      oos.writeObject(str);
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    list = (ArrayList<Message>) dataService.get(MyTools.Flag.QueryTwoPersonChat);
    return list;
  }

}
