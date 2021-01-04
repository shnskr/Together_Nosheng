package com.together.nosheng.model.project;

import java.util.ArrayList;
import java.util.List;

public class CheckList {
    private List<String> item = new ArrayList<>();
    private List<Boolean> check = new ArrayList<>();

    public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }

    public List<Boolean> getCheck() {
        return check;
    }

    public void setCheck(List<Boolean> check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "CheckList{" +
                "item=" + item +
                ", check=" + check +
                '}';
    }
}
