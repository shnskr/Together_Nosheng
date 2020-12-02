package com.together.nosheng.model.user;

import com.google.type.Date;
import com.google.type.DateTime;

public class User {
    private String eMail;
    private String nickName;
    private Date regDate;
    private String thumbnail;
//
    public User(String eMail, String nickName, Date regDate, String thumbnail) {
        this.eMail = eMail;
        this.nickName = nickName;
        this.regDate = regDate;
        this.thumbnail = thumbnail;
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
