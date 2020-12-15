package com.together.nosheng.model.user;

import com.google.firebase.Timestamp;
import com.together.nosheng.model.project.Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class User {
    private String eMail;
    private String nickName;
    private Date regDate;
    private String thumbnail;
    private List<String> friendList;
    private List<String> projectList;

    public User(String eMail, String nickName, Date regDate, String thumbnail, ArrayList<String> friendList) {
        this.eMail = eMail;
        this.nickName = nickName;
        this.regDate = regDate;
        this.thumbnail = thumbnail;
        this.friendList = friendList;
    }

    public User (String nickName){
        this.nickName = nickName;
    }
    public User() {

    }

    public List<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<String> friendList) {
        this.friendList = friendList;
    }

    public List<String> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<String> projectList) {
        this.projectList = projectList;
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

    public void setRegDate(Timestamp regDate) {
        this.regDate = regDate.toDate();
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
