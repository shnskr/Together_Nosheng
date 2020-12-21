package com.together.nosheng.model.project;

import java.util.HashMap;
import java.util.Map;

public class Budget{
    private int total;
    private Map<String, Integer> detail = new HashMap<>();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Map<String, Integer> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, Integer> detail) {
        this.detail = detail;
    }
}
