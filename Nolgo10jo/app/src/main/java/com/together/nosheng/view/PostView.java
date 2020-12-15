package com.together.nosheng.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.together.nosheng.R;
import com.together.nosheng.databinding.LayoutNoteListItemBinding;
import com.together.nosheng.model.project.Post;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;

public class PostView extends LinearLayout {

    private LayoutNoteListItemBinding listItemBinding;
    private TextView postTitle, postNickName, postRegDate, postNotice;
    private Switch switchNotice;

    public PostView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        super(inflate(inflater,container,savedInstanceState));
        listItemBinding = LayoutNoteListItemBinding.inflate(inflater,container,false);
    }

    private static Context inflate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    public PostView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_note_list_item,this, true);

        postTitle = findViewById(R.id.note_title);
        postNickName = findViewById(R.id.txt_nickname);
        postRegDate = findViewById(R.id.txt_write_date);
        postNotice = findViewById(R.id.txt_notice);
        switchNotice = findViewById(R.id.switch_notice);
    }

    public void setPostTitle(String pt){
        postTitle.setText(pt);
    }

    public void setTxt_nickname(String tn){
        postNickName.setText(tn);
    }

    public void setPostRegDate(String rd){
        postRegDate.setText(rd);
    }

    public void setNotice(boolean n) {
        if(n){
            postNotice.setVisibility(VISIBLE);
        } else {
            postNotice.setVisibility(GONE);
        }
    }

}
