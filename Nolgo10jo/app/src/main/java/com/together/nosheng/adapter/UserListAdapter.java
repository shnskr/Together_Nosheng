package com.together.nosheng.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.together.nosheng.model.user.User;
import com.together.nosheng.view.AdminList_Inflater;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {

    ArrayList<User> items = new ArrayList<>();
    LayoutInflater inflater;

    //데이터 추가 메소드
    public void addItem(User user){items.add(user);}

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ListView_Inflater 인스턴스 생성 - ListView_Inflater instance generate
        AdminList_Inflater view = new AdminList_Inflater(parent.getContext());

        User user = items.get(position);
        view.setName(user.getNickName());
        view.setemail(user.geteMail());
//        view.setImageView(user.getThumbnail());

        return view;
    }
}
