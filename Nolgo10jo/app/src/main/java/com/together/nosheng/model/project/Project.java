package com.together.nosheng.model.project;

import com.together.nosheng.model.user.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Project {
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

}
