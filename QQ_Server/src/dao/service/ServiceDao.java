package dao.service;

import model.FriendTable;
import model.Message;
import model.QQUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ServiceDao  {
  public Boolean queryUser(String qq_id,String password);//根据账号密码判断是否登录成功
  public int addUser(String qq_phone, String qq_password) throws SQLException, ClassNotFoundException;
  public String queryQQID(String phone); //手机号查询qq号
  public FriendTable queryFriendList(String qq_id);//根据QQ号查找好友列表
  public QQUser queryQQUser(String qq_id);//根据QQ号查询用户信息
  public List<QQUser> fuzzySearch(String str);//模糊查找（根据QQ号和昵称）
  public List<Message> unreadMessage(String qq_id);//根据QQ查询于自己有关的消息
  public void addMessage(Message message);//人不在线的时候往数据库里放一条离线数据
  public ArrayList<Message> TwoPersonChat(String qq_id1,String qq_id2);//查询两个人之间的聊天记录
  public void updateUser(QQUser user);//更新用户资料
  public void addProfile(byte[] bytes,String qq_id);//加头像
  public void addFriend(String myqq,String addqq);  //双向加好友
}
