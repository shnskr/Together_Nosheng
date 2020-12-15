package com.together.nosheng.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.Timestamp;
import com.together.nosheng.R;
import com.together.nosheng.databinding.ActivityFragmentBoardBinding;
import com.together.nosheng.databinding.FragmentNewPostBinding;
import com.together.nosheng.model.project.Post;
import com.together.nosheng.model.project.Project;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.viewmodel.ProjectViewModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class NewPostFragment extends Fragment implements
        View.OnTouchListener, GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, View.OnClickListener, TextWatcher {

    private FragmentNewPostBinding postBinding;

    private Project project;
    private ProjectViewModel projectViewModel;
    private Post newPost = new Post();
    private List<Post> posts;

    private String TAG = "PostActivity";

    //vars
    private String projectId;
    private boolean isNewNote;
    private int noteMode;
    private GestureDetector mGestureDetector;
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener;

    private static final int EDIT_MODE_DISABLED = 0;
    private static final int EDIT_MODE_ENABLED = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        postBinding = FragmentNewPostBinding.inflate(inflater, container, false);
        View view = postBinding.getRoot();

        projectId = getActivity().getIntent().getStringExtra("projectId");
        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        projectViewModel.getCurrentProject().observe(getViewLifecycleOwner(), new Observer<Map<String, Project>>() {
            @Override
            public void onChanged(Map<String, Project> stringProjectMap) {
                posts = stringProjectMap.get(projectId).getPosts();
            }
        });

        setListeners();

        if (getIncomingIntent()) {
            enableEditMode();
        } else {
            disableContentInteraction();
        }

        return view;
    }

    public void saveNewNote() {
        posts.add(newPost);
        projectViewModel.addPost(projectId,posts);
    }


    //setting
    private void setListeners(){
        simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener();
        mGestureDetector = new GestureDetector(getActivity(),simpleOnGestureListener);
        postBinding.noteText.setOnTouchListener(this);
        postBinding.toolbarCheck.setOnClickListener(this);
        postBinding.noteTextTitle.setOnClickListener(this);
        postBinding.toolbarBackArrow.setOnClickListener(this);
        postBinding.noteText.addTextChangedListener(this);
        postBinding.switchNotice.setOnClickListener(this);
    }


    //mode
    private boolean getIncomingIntent(){
        if(getActivity().getIntent().hasExtra("selected_note")){
            newPost = getActivity().getIntent().getParcelableExtra("selected_note");

            noteMode = EDIT_MODE_ENABLED;
            isNewNote = false;
            return false;
        }
        noteMode = EDIT_MODE_ENABLED;
        isNewNote = true;
        return true;
    }

    private void disableContentInteraction(){
        postBinding.noteText.setKeyListener(null);
        postBinding.noteText.setFocusable(false);
        postBinding.noteText.setFocusableInTouchMode(false);
        postBinding.noteText.setCursorVisible(false);
        postBinding.noteText.clearFocus();
    }

    private void enableContentInteraction(){
        postBinding.noteText.setKeyListener(new EditText(getContext()).getKeyListener());
        postBinding.noteText.setFocusable(true);
        postBinding.noteText.setFocusableInTouchMode(true);
        postBinding.noteText.setCursorVisible(true);
        postBinding.noteText.requestFocus();
    }

    private void enableEditMode(){
        postBinding.backArrowContainer.setVisibility(View.GONE);
        postBinding.checkContainer.setVisibility(View.VISIBLE);

        postBinding.noteTextTitle.setVisibility(View.GONE);
        postBinding.noteEditTitle.setVisibility(View.VISIBLE);

        noteMode = EDIT_MODE_ENABLED;

        enableContentInteraction();
    }

    private void disableEditMode(){
        Log.d(TAG, "disableEditMode: called.");
        postBinding.backArrowContainer.setVisibility(View.VISIBLE);
        postBinding.checkContainer.setVisibility(View.GONE);

        postBinding.noteTextTitle.setVisibility(View.VISIBLE);
        postBinding.noteEditTitle.setVisibility(View.GONE);

        noteMode = EDIT_MODE_DISABLED;

        disableContentInteraction();

        // Check if they typed anything into the note. Don't want to save an empty note.
        String temp = postBinding.noteText.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if(temp.length() > 0){

            setNoteProperties();

            Log.d(TAG, "disableEditMode: initial: " + newPost.toString());
            Log.d(TAG, "disableEditMode: final: " + newPost.toString());

        }
    }

    private void setNoteProperties(){
        String title = postBinding.noteEditTitle.getText().toString();
        Log.i(TAG, "title 길이 : "+title.length() +" : " + postBinding.noteEditTitle.getText());
        if(title.length() == 0) {
            newPost.setTitle("NoteTitle");
            Log.i(TAG, "널포인트 잡은데로 들어오나 안들어오나?");
        } else {
            postBinding.noteTextTitle.setText(title);
            newPost.setTitle(title);
            Log.i(TAG, "널포인트 잡은데로 들어오나 안들어오나?????????????");
        }
        newPost.setContent(postBinding.noteText.getText().toString());
        newPost.setRegDate(new Date());
        newPost.setNickName(GlobalApplication.firebaseUser.getUid());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.toolbar_back_arrow :
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.board_container, new BoardFragmentActivity()).commit();
                break;
            case R.id.toolbar_check :
                setNoteProperties();
                saveNewNote();
                disableEditMode();
                break;
            case R.id.note_text_title :
                enableEditMode();
                postBinding.noteEditTitle.requestFocus();
                postBinding.noteEditTitle.setSelection(postBinding.noteEditTitle.length());
                break;
            case R.id.switch_notice :
                if(postBinding.switchNotice.isChecked()){
                    newPost.setNotice(true);
                    Log.i(TAG, "set notice");

                }else {
                    newPost.setNotice(false);
                    Log.i(TAG, "cancel notice");
                }
                break;
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
