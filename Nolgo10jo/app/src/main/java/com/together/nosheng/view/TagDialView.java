package com.together.nosheng.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.together.nosheng.R;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.util.List;

public class TagDialView extends LinearLayout {

    private ProjectViewModel projectViewModel;

    private CheckBox checkBox;
    private TextView tagName;
    private ImageButton deleteTag;

    private boolean check;

    private String TAG = "Tag Dial View";

    public TagDialView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_tag_list_item,this,true);

        checkBox = findViewById(R.id.checkbox);
        tagName = findViewById(R.id.txt_tagName);
        deleteTag = findViewById(R.id.delete_tag);
    }

    public boolean setCheckBox() {
        checkBox.toggle();

        if (checkBox.isChecked()) {
            check = true;
        } else {
            check = false;
        }

        return check;
    }

    public boolean setCheckBoxs() {
            checkBox.setChecked(true);
        return check;
    }

    public void setTagName(String tn) {
        tagName.setText(tn);
    }

    public void setDeleteTag(List<String> tags, int position, String projectId, FragmentActivity fragmentActivity){

        projectViewModel = new ViewModelProvider(fragmentActivity).get(ProjectViewModel.class);

        deleteTag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //tag list에서 삭제
                tags.remove(position);
                Log.i(TAG, tags+"/"+position);
                projectViewModel.addTag(projectId,tags);

                //user가 보유한 tag list 에서 삭제


            }
        });
    }

}
