package model;

import java.io.File;
import java.io.Serializable;
import java.sql.Date;

public class Message implements Serializable,Comparable {
  private String sender;          //发送方
  private String accepter;        //接收方
  private Object msg;             //正文
  private Date sendTime;          //发送时间
  private int type;          //类型（0消息，1文件，2图片）
  private int isRead;        //消息是否已读
  private File file;//文件

  @Override
  public int compareTo(Object o) {
    return getSendTime().compareTo(((Message)o).getSendTime());
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getAccepter() {
    return accepter;
  }

  public void setAccepter(String accepter) {
    this.accepter = accepter;
  }

  public Object getMsg() {
    return msg;
  }

  public void setMsg(Object msg) {
    this.msg = msg;
  }

  public Date getSendTime() {
    return sendTime;
  }

  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getIsRead() {
    return isRead;
  }

  public void setIsRead(int isRead) {
    this.isRead = isRead;
  }

  @Override
  public String toString() {
    return "Message{" +
        "sender='" + sender + '\'' +
        ", accepter='" + accepter + '\'' +
        ", msg='" + msg + '\'' +
        ", sendTime=" + sendTime +
        ", type=" + type +
        ", isRead=" + isRead +
        '}';
  }
}
