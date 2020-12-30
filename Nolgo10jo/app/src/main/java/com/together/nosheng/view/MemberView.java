package com.together.nosheng.view;

import android.content.Context;
import android.media.Image;
import android.util.Log;
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
import java.util.Map;

import com.together.nosheng.R;
import com.together.nosheng.model.project.Post;
import com.together.nosheng.viewmodel.ProjectViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

public class MemberView extends LinearLayout {

    private ImageView thumbnail;
    private TextView nickName;
    private ImageButton delete;
    private ImageView star;

    private String TAG = "MemberView";

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
        star = findViewById(R.id.mark_star);
    }

    public void setNickName(String wd){
        nickName.setText(wd);
    }

    public void setThumbnail(String tn){

    }

    public void deleteMember(List<String> members, Map<String,List<String>> userTags, String projectId, int position,
                             UserViewModel userViewModel, ProjectViewModel projectViewModel){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String member = members.get(position);
                Log.i(TAG,"member : "+member);
                userTags.remove(member);
                projectViewModel.addUserTags(projectId,userTags);

                List<String> memberProject = userViewModel.getMemberProject(member);
                Log.i(TAG,"memberProject : "+memberProject);
                memberProject.remove(projectId);
                Log.i(TAG,"memberProject222 : "+memberProject);
                userViewModel.updateUserProjectList(member, memberProject);

                members.remove(position);
                projectViewModel.addMember(projectId, members);
            }
        });
    }

    public void setStar() {
        star.setVisibility(VISIBLE);
    }

    public void setDeleteBtn() {
        delete.setVisibility(INVISIBLE);
    }
}
