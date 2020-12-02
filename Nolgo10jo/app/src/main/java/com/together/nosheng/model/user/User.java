package com.together.nosheng.model.user;

import com.google.type.Date;
import com.google.type.DateTime;
<<<<<<< HEAD
=======

import java.util.ArrayList;
>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d

public class User {
    private String eMail;
    private String nickName;
    private Date regDate;
    private String thumbnail;
<<<<<<< HEAD
//
    public User(String eMail, String nickName, Date regDate, String thumbnail) {
=======
    private ArrayList<String> friendList;

//
    public User(String eMail, String nickName, Date regDate, String thumbnail,ArrayList<String> friendList) {
>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d
        this.eMail = eMail;
        this.nickName = nickName;
        this.regDate = regDate;
        this.thumbnail = thumbnail;
<<<<<<< HEAD
    }
=======
        this.friendList = friendList;
    }

    public ArrayList<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }

>>>>>>> dce24541b8bbad489b733864cb33bcdbbaea8b5d
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
