package dao.implement;

import dao.service.ServiceDao;
import model.FriendTable;
import model.Message;
import model.QQUser;
import utils.DBConnec;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServiceDapImpl implements ServiceDao {

  Connection conn = null;
  PreparedStatement ps = null;

  public ServiceDapImpl(){
    try {
      conn = DBConnec.getConnection();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  //查询用户是否存在（用于登录
  //qq_id也有可能是手机号，故做判断
  @Override
  public Boolean queryUser(String qq_id, String password) {
    String sql = "select * from account where (qq_id=? or qq_phone=?) and qq_password=?";
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1,qq_id);
      ps.setString(2,qq_id);
      ps.setString(3,password);
      System.out.println("用户尝试登录，QQ/手机号："+qq_id+"，密码："+password);
      ResultSet rs = ps.executeQuery();
      return rs.next()?true:false;
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return false;
  }

  @Override
  public int addUser(String qq_phone, String qq_password)  {
    int result = 0;

    //随机生成qq号
    int MAX = 999999999;
    int MIN = 100000000;

    String queryQQ = "select * from account where qq_id=?";
    //判断qq是否存在
    Boolean isExist = true;
    Random rand = new Random();
    int randNumber ;
    String qq_id = null;

    //此循环结束时会得到一个没被使用过的QQ
    while (isExist){
      try {
        //生成随机数
        randNumber =rand.nextInt(MAX - MIN + 1) + MIN; // randNumber 将被赋值为一个 MIN 和 MAX 范围内的随机数
        qq_id = String.valueOf(randNumber);
        //判断是否存在
        ps=conn.prepareStatement(queryQQ);
        ps.setString(1,qq_id);
        ResultSet rs1 = ps.executeQuery();
        isExist=rs1.next()?true:false;
        System.out.println("经查询，随机生成的QQ:"+qq_id+"与数据库中不重复，退出循环");
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }

    try {
      String sql = "insert into account(qq_id,qq_phone,qq_password) value (?,?,?)";
      // 获取preparedStatement
      ps= conn.prepareStatement(sql);
      // 为preparedStatement对象的sql中的占位符设置参数
      ps.setString(1,qq_id);
      ps.setString(2,qq_phone);
      ps.setString(3,qq_password);
      result = ps.executeUpdate();

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    System.out.println(result);
    return result;

  }

  @Override
  //通过手机号查找qq
  public String queryQQID(String phone) {
    String sql = "select qq_id from account where qq_phone=?";
    String qqid = null;
    try {

      ps = conn.prepareStatement(sql);
      ps.setString(1,phone);
      ResultSet rs = ps.executeQuery();
      while(rs.next()){
        qqid = rs.getString(1);
      }

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return qqid;
  }

  @Override
  public FriendTable queryFriendList(String qq_id) {
    String sql = "select * from friendtable where my_account=?";
    FriendTable friendTable = new FriendTable(qq_id);
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1,qq_id);
      ResultSet rs = ps.executeQuery();
      while (rs.next()){
        friendTable.getFriendAccount_And_Note().put(rs.getString("friend_account"),rs.getString("note"));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return friendTable;
  }

  @Override
  //根据qq号找到用户资料
  public QQUser queryQQUser(String qq_id){
    String sql="select * from account where qq_id=?";
    QQUser user = new QQUser(qq_id);
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1,qq_id);
      ResultSet rs = ps.executeQuery();
      while (rs.next()){
        user.setQq_nickname(rs.getString("qq_nickname"));
        user.setQq_profile(rs.getBytes("qq_profile"));
        //可能出错，从数据库接收日期
        user.setQq_birthday(rs.getDate("qq_birthday"));
        user.setQq_signature(rs.getString("qq_signature"));
        user.setQq_sex(rs.getInt("qq_sex"));
        user.setQq_phone(rs.getString("qq_phone"));
        user.setQq_password(rs.getString("qq_password"));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return user;
  }

  @Override
  //模糊查找（根据QQ号和昵称）
  public List<QQUser> fuzzySearch(String str) {
    List<QQUser> list = new ArrayList<>();
    String sql = "select * from account where qq_id like ? or qq_nickname like ?";
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1, "%" + str + "%" );
      ps.setString(2, "%" + str + "%" );
      ResultSet rs = ps.executeQuery();
      while (rs.next()){
        QQUser user = new QQUser();
        user.setQq_id(rs.getString("qq_id"));
        user.setQq_phone(rs.getString("qq_phone"));
        user.setQq_nickname(rs.getString("qq_nickname"));
        user.setQq_sex(rs.getInt("qq_sex"));
        user.setQq_signature(rs.getString("qq_signature"));
        user.setQq_birthday(rs.getDate("qq_birthday"));
        user.setQq_profile(rs.getBytes("qq_profile"));
        list.add(user);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return list;
  }

  @Override
  //根据QQ号查询与自己有关的消息
  public List<Message> unreadMessage(String qq_id) {
    List<Message> list = new ArrayList<>();
    String sql = "select * from message where accepter=? or sender=?";
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1,qq_id);
      ps.setString(2,qq_id);
      ResultSet rs = ps.executeQuery();
      while (rs.next()){
        Message message = new Message();
        message.setSender(rs.getString("sender"));
        message.setAccepter(rs.getString("accepter"));
        message.setMsg(rs.getString("msg"));
        message.setIsRead(rs.getInt("isRead"));
        message.setSendTime(rs.getDate("sendTime"));
        message.setType(rs.getInt("type"));
        list.add(message);
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return list;
  }

  @Override
  //往数据库中插入一条消息
  public void addMessage(Message message) {
    String sql = "insert into message(sender,accepter,msg,sendTime,type,isRead) value (?,?,?,?,?,?)";
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1,message.getSender());
      ps.setString(2,message.getAccepter());
      ps.setString(3,(String) message.getMsg());
      ps.setDate(4,message.getSendTime());
      ps.setInt(5,message.getType());
      ps.setInt(6,message.getIsRead());
      ps.executeUpdate();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

  }

  @Override
  //通过两个qq获取他们的聊天记录
  public ArrayList<Message> TwoPersonChat(String qq_id1, String qq_id2) {
    String sql = "select * from message where (accepter=? and sender=?)or(accepter=? and sender=?)";
    ArrayList<Message> list = new ArrayList<>();
    ResultSet rs = null;
    try {
      ps=conn.prepareStatement(sql);
      ps.setString(1,qq_id1);
      ps.setString(2,qq_id2);
      ps.setString(3,qq_id2);
      ps.setString(4,qq_id1);
      rs = ps.executeQuery();
      while (rs.next()){
        Message message = new Message();
        message.setSender(rs.getString("sender"));
        message.setAccepter(rs.getString("accepter"));
        message.setMsg(rs.getString("msg"));
        message.setIsRead(rs.getInt("isRead"));
        message.setSendTime(rs.getDate("sendTime"));
        message.setType(rs.getInt("type"));
        list.add(message);
      }

    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return list;
  }

  @Override
  //更新用户资料
  public void updateUser(QQUser user) {
    String sql = "update account set qq_nickname=?,qq_sex=?,qq_signature=?,qq_birthday=? where qq_id=?";
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1,user.getQq_nickname());
      ps.setInt(2,user.getQq_sex());
      ps.setString(3,user.getQq_signature());
      ps.setDate(4,user.getQq_birthday());
      ps.setString(5,user.getQq_id());
      ps.executeUpdate();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @Override
  public void addProfile(byte[] bytes, String qq_id) {
    String sql="update account set qq_profile=? where qq_id=?";
    try {
      ps = conn.prepareStatement(sql);
      ps.setBytes(1,bytes);
      ps.setString(2,qq_id);
      ps.executeUpdate();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  @Override
  //双向加好友
  public void addFriend(String myqq, String addqq) {
    String sql="insert into friendtable (my_account,friend_account) values(?,?)";
    try {
      ps = conn.prepareStatement(sql);
      ps.setString(1,myqq);
      ps.setString(2,addqq);
      ps.executeUpdate();

      ps.setString(1,addqq);
      ps.setString(2,myqq);
      ps.executeUpdate();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

  }
}
