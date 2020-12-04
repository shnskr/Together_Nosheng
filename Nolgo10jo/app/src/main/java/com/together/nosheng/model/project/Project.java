package com.together.nosheng.model.project;

import com.together.nosheng.model.user.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Project {
//    private String projectId;
    private String title;
    private Date regDate;
    private Date startDate;
    private Date endDate;
    private Map<String, Budget> budgets; // Key : 식비같은 큰 항목 이름, Value : Budget class(total 예산과 세부 항목이 필드)
    private Map<String, String> tags; // Key : 유저 id, Value : Tag name
    private List<Post> posts; // 게시글 목록
    private Map<String, CheckList> checkLists; // Key : 유저 ID,  Value : CheckList class(체크리스트 항목)
//    private Map<String, PinMember> pinMembers;
//    private Map<String, PinRecommend> pinRecommends;
    private List<User> members;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Map<String, Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(Map<String, Budget> budgets) {
        this.budgets = budgets;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Map<String, CheckList> getCheckLists() {
        return checkLists;
    }

    public void setCheckLists(Map<String, CheckList> checkLists) {
        this.checkLists = checkLists;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

//    public String getProjectId() {
//        return projectId;
//    }
//
//    public void setProjectId(String projectId) {
//        this.projectId = projectId;
//    }
}
