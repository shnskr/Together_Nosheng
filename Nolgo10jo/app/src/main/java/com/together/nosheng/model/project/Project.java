package com.together.nosheng.model.project;

import com.together.nosheng.model.plan.Plan;
import com.together.nosheng.model.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    private List<User> members;
    private List<Plan> plans;

    public Project() {
        budgets = new HashMap<>();
        checkLists = new HashMap<>();
        tags = new HashMap<>();
        posts = new ArrayList<>();
        plans = new ArrayList<>();
        members = new ArrayList<>();
        budgets.put("식비", new Budget());
        budgets.put("숙박비", new Budget());
        budgets.put("교통비", new Budget());
        budgets.put("비상금", new Budget());
        budgets.put("기타", new Budget());
    }

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

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    @Override
    public String toString() {
        return "Project{" +
                "title='" + title + '\'' +
                ", regDate=" + regDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", budgets=" + budgets +
                ", tags=" + tags +
                ", posts=" + posts +
                ", checkLists=" + checkLists +
                ", members=" + members +
                '}';
    }
}