package com.together.nosheng.model.project;

import java.util.HashMap;
import java.util.Map;

public class CheckList {
    private Map<String, Boolean> check = new HashMap<>();

    public Map<String, Boolean> getCheck() {
        return check;
    }

    public void setCheck(Map<String, Boolean> check) {
        this.check = check;
    }
}
