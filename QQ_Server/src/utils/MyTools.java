package utils;

import java.io.Serializable;

public class MyTools {
  public enum Flag implements Serializable {
    Login,//登录
    Login_Result,//登录返回的结果
    Login_Success_inPhone,//使用手机登录成功
    Login_Success_inNumber,//使用QQ号登录成功
    Login_Failed,//登录失败

    Register,//注册
    Register_Result,//注册返回结果
    Register_Success,//注册成功
    Register_Failed_Phone_exists,//注册失败，手机号已存在
    Register_Failed,//注册失败

    UpdateUserData,//更新用户资料
    UpdateUserProfile,//更新用户头像

    GetNewPersonData,//获取最新个人资料
    QueryQQID,//根据手机号获取QQ
    QueryFriendList,//根据QQ号获取好友列表以及备注

    QueryUserFuzzySearch,//提供QQ号/昵称，模糊查找用户

    SendMessage,//发送消息
    ReceiveMessages,//接受消息

    CheckUnreadMessage,//根据QQ查看未读消息

    AddFriend,  //双向加好友

    QueryChatHistory,//根据QQ号查询与之有关的聊天信息
    QueryTwoPersonChat,//查询两个qq号之间的聊天记录

    Release;//告诉服务器释放Socket
  }

}
