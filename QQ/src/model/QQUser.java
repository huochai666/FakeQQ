package model;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.sql.Date;

public class QQUser implements Serializable {
  private String qq_id;             //QQ号
  private String qq_password;       //QQ密码
  private String qq_phone;          //手机号
  private String qq_nickname;       //昵称
  private int qq_sex;               //性别
  private String qq_signature;      //个性签名
  private Date qq_birthday;         //生日
  private byte[] qq_profile;        //头像

  public String getQq_id() {
    return qq_id;
  }

  public void setQq_id(String qq_id) {
    this.qq_id = qq_id;
  }

  public String getQq_password() {
    return qq_password;
  }

  public void setQq_password(String qq_password) {
    this.qq_password = qq_password;
  }

  public String getQq_phone() {
    return qq_phone;
  }

  public void setQq_phone(String qq_phone) {
    this.qq_phone = qq_phone;
  }

  public String getQq_nickname() {
    return qq_nickname;
  }

  public void setQq_nickname(String qq_nickname) {
    this.qq_nickname = qq_nickname;
  }

  public int getQq_sex() {
    return qq_sex;
  }

  public void setQq_sex(int qq_sex) {
    this.qq_sex = qq_sex;
  }

  public String getQq_signature() {
    return qq_signature;
  }

  public void setQq_signature(String qq_signature) {
    this.qq_signature = qq_signature;
  }

  public Date getQq_birthday() {
    return qq_birthday;
  }

  public void setQq_birthday(Date qq_birthday) {
    this.qq_birthday = qq_birthday;
  }

  public byte[] getQq_profile() {
    return qq_profile;
  }

  public void setQq_profile(byte[] qq_profile) {
    this.qq_profile = qq_profile;
  }

  public QQUser() {
  }

  public QQUser(String qq_id){
    this.qq_id = qq_id;
  }

  public QQUser(String qq_phone, String qq_password) {
    this.qq_phone = qq_phone;
    this.qq_password = qq_password;
  }

  public QQUser(String qq_id, String qq_password, String qq_phone) {
    this.qq_id = qq_id;
    this.qq_password = qq_password;
    this.qq_phone = qq_phone;
  }

  @Override
  public String toString() {
    return "QQUser{" +
        "qq_id='" + qq_id + '\'' +
        ", qq_password='" + qq_password + '\'' +
        ", qq_phone='" + qq_phone + '\'' +
        ", qq_nickname='" + qq_nickname + '\'' +
        ", qq_sex=" + qq_sex +
        ", qq_signature='" + qq_signature + '\'' +
        ", qq_birthday=" + qq_birthday +
        ", qq_profile='" + qq_profile + '\'' +
        '}';
  }
}
