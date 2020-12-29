package com.together.nosheng.view;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;
import java.util.List;

import com.together.nosheng.R;
import com.together.nosheng.model.project.Post;
import com.together.nosheng.viewmodel.ProjectViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

public class MemberView extends LinearLayout {

    private UserViewModel userViewModel;
    private ProjectViewModel projectViewModel;

    private ImageView thumbnail;
    private TextView nickName;
    private ImageButton delete;

    public MemberView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_member_list_item, this, true);

        thumbnail = findViewById(R.id.img_thumbnail);
        nickName = findViewById(R.id.txt_nickname);
        delete = findViewById(R.id.btn_delete);
    }

    public void setNickName(String wd){
        nickName.setText(wd);
    }

    private void setThumbnail(String tn){

    }

    private void deleteMember(Context context, List<String> members, String projectId, int position, FragmentActivity fragmentActivity){
        userViewModel = new ViewModelProvider(fragmentActivity).get(UserViewModel.class);
        projectViewModel = new ViewModelProvider(fragmentActivity).get(ProjectViewModel.class);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
