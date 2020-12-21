package com.together.nosheng.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.together.nosheng.R;
import com.together.nosheng.databinding.LayoutNoteListItemBinding;
import com.together.nosheng.model.project.Post;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.ProjectViewModel;

public class PostView extends LinearLayout {

    private ProjectViewModel projectViewModel;

    private TextView noteTitle, noteWriter, noteRegDate, noteNotice;
    private ImageButton delete;

    private String TAG = "PostView";


    public PostView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_note_list_item,this, true);

        noteTitle = findViewById(R.id.note_title);
        noteWriter = findViewById(R.id.txt_nickname);
        noteRegDate = findViewById(R.id.txt_write_date);
        noteNotice = findViewById(R.id.txt_notice);
        delete = findViewById(R.id.btn_delete);
    }

    public void setPostTitle(String pt){
        noteTitle.setText(pt);
    }

    public void setTxt_nickname(String tn){
        noteWriter.setText(tn);
    }

    public void setPostRegDate(String rd){
        noteRegDate.setText(rd);
    }

    public void setNotice(boolean n) {
        Log.i(TAG, "isnotice null??");
        if(n){
            noteNotice.setVisibility(VISIBLE);
        } else {
            noteNotice.setVisibility(GONE);
        }
    }

    public void deleteNote(Context context, List<Post> posts, String projectId, int position, FragmentActivity fragment) {
        projectViewModel = new ViewModelProvider(fragment).get(ProjectViewModel.class);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("메모 삭제!")
                        .setMessage("삭제하신 메모는 복구 할 수 없습니다.\n삭제하시겠습니까?")
                        .setIcon(R.drawable.ic_baseline_announcement_24);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        posts.remove(position);
                        Log.i(TAG, posts.toString());
                        Log.i(TAG, projectId);
                        projectViewModel.addPost(projectId,posts);
                        Toast.makeText(context, "메모가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        Log.i("메모가 삭제되었습니다.", "0");
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("메모 삭제 요청이 취소되었습니다.", "1");
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

}
