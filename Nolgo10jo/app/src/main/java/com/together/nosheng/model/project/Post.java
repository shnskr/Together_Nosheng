package com.together.nosheng.model.project;

import java.util.Date;

public class Post {
    private String title;
    private String content;
    private Date regDate;
    private boolean notice;
    private String nickName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public boolean isNotice() {
        return notice;
    }

    public void setNotice(boolean notice) {
        this.notice = notice;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", regDate=" + regDate +
                ", notice=" + notice +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
