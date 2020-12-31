package com.together.nosheng.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.fragment.app.FragmentActivity;

import com.together.nosheng.model.project.Project;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.view.MemberView;
import com.together.nosheng.viewmodel.ProjectViewModel;
import com.together.nosheng.viewmodel.UserViewModel;

import java.util.List;
import java.util.Map;

public class MemberAdapter extends BaseAdapter {

    private Context context;
    private String projectId;
    private List<String> members;
    private Map<String,List<String>> userTags;
    private List<String> userNicknames;
    private UserViewModel userViewModel;
    private ProjectViewModel projectViewModel;

    private String TAG = "MemberAdapter";


    public MemberAdapter(Context context, UserViewModel userViewModel,ProjectViewModel projectViewModel,
                         String projectId, List<String> members,Map<String,List<String>> userTags, List<String> userNicknames) {
        this.context = context;
        this.projectId = projectId;
        this.members = members;
        this.userTags = userTags;
        this.userNicknames = userNicknames;
        this.userViewModel = userViewModel;
        this.projectViewModel = projectViewModel;
    }

    @Override
    public int getCount() {
        return userNicknames.size();
    }

    @Override
    public Object getItem(int position) {
        return members.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MemberView memberView = new MemberView(context);

        memberView.setNickName(userNicknames.get(position));

        memberView.deleteMember(members, userTags, projectId, position, userViewModel, projectViewModel);

        String uid = GlobalApplication.firebaseUser.getUid();
        for(String member : members){
            if(member.equals(uid)){
                memberView.setStar();
            }
        }

        if(members.size() == 1) {
            memberView.setDeleteBtn();
        }


        return memberView;
    }
}
