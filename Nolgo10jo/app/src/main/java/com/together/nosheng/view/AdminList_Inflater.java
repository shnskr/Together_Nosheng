package com.together.nosheng.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.together.nosheng.R;

public class AdminList_Inflater extends LinearLayout {


    private Context context;
    //ID
    private ImageView imageView;
    private TextView user_Name;
    private TextView user_Email;

    public AdminList_Inflater(Context context) {
        super(context);
        init(context);
    }
    //생성자
    public AdminList_Inflater(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        //인플레이션을 하기 위한 인플레이터
        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.admin_list_item,this,true);
        this.context = context;

//        //catch ID
        imageView = (ImageView)findViewById(R.id.iv_admin);
        user_Name = (TextView)findViewById(R.id.tv_name);
        user_Email = (TextView)findViewById(R.id.tv_email);
    }


    public void setImageView(Context context, Uri uri){
        Glide.with(context)
                .load(uri)
                .into(imageView);
    }


    public void setName(String setNickName){
        user_Name.setText(setNickName);
    }

    public void setemail(String seteMail){
        user_Email.setText(seteMail);
    }

}

