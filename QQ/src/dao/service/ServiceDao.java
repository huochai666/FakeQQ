package dao.service;

//定义客户端给服务发送数据的状态之类的

import model.*;
import utils.MyTools;

import java.util.ArrayList;
import java.util.List;

public interface ServiceDao {
  public MyTools.Flag doLogin(String username, String password);
  public Object doRegister(String phone, String password);
  public QQUser getPersonData(String qq_id);//根据QQ获取用户资料
  public String queryQQID(String qq_phone); //根据手机号查QQ
  public FriendTable queryFriendList(String qq_id);//根据QQ号查找好友列表
  public List<QQUser> fuzzySearch(String str);//模糊查找（根据QQ号和昵称）
  public void sendMessage(Message message);//发送消息
  public void updateUser(QQUser user);//更新用户资料
  public void addProfile(QQUser user);//加头像
  public void addFriend(String myqq,String addqq);  //双向加好友


  public List<Message> unreadMessage(String qq_id);//根据QQ号查询用户未读消息
  public ArrayList<Message> TwoPersonChat(String qq_id1, String qq_id2);//查询两个人之间的聊天记录

}
