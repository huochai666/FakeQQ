package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FriendTable implements Serializable {
  //个人QQ号
  private String my_account;
  //第一个String是好友QQ号，第二个备注
  private Map<String,String> friendAccount_And_Note = new HashMap<>();

  public FriendTable(String my_account) {
    this.my_account = my_account;
  }

  public String getMy_account() {
    return my_account;
  }

  public void setMy_account(String my_account) {
    this.my_account = my_account;
  }

  public Map<String, String> getFriendAccount_And_Note() {
    return friendAccount_And_Note;
  }

  public void setFriendAccount_And_Note(Map<String, String> friendAccount_And_Note) {
    this.friendAccount_And_Note = friendAccount_And_Note;
  }

}
