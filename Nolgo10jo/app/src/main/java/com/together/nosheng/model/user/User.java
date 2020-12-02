package com.together.nosheng.model.user;

import com.google.type.Date;

import java.util.ArrayList;

public class User {
    private String eMail;
    private String nickName;
    private Date regDate;
    private String thumbnail;
    private ArrayList<String> friendList;

    public User(String eMail, String nickName, Date regDate, String thumbnail,ArrayList<String> friendList) {
        this.eMail = eMail;
        this.nickName = nickName;
        this.regDate = regDate;
        this.thumbnail = thumbnail;
        this.friendList = friendList;
    }

    public ArrayList<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }

    public User (String nickName){
        this.nickName = nickName;
    }
    public User() {

    }


    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
