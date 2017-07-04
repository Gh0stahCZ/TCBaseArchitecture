package com.tomaschlapek.tcbasearchitecture.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Class holds information about certain user.
 */
public class RUser extends RealmObject {

  /* Attributes ***********************************************************************************/

  @PrimaryKey
  private String token;

  private String nickName;
  private int credits;
  private String avatar;


  /* Public methods *******************************************************************************/

  /**
   * Default constructor.
   */
  public RUser() {
  }

  /*public RUser(UserInfoResponse userInfoResponse) {
      this.nickName = userInfoResponse.getUserNickName();
      this.credits = userInfoResponse.getUserCredits();
      this.token = userInfoResponse.getToken();
      this.avatar = userInfoResponse.getAvatar();
  }*/

  /* Getters / Setters ****************************************************************************/

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public int getCredits() {
    return credits;
  }

  public void setCredits(int lastName) {
    this.credits = lastName;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }
}
