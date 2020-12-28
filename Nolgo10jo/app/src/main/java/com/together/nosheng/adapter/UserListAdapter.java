package com.together.nosheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.together.nosheng.model.user.User;
import com.together.nosheng.util.GlobalApplication;
import com.together.nosheng.view.AdminList_Inflater;
import com.together.nosheng.view.AdminUserActivity;
import com.together.nosheng.view.AuserProjectListActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {

    ArrayList<String> ids = new ArrayList<>();
    ArrayList<User> items = new ArrayList<>();
//    LayoutInflater inflater;
    Context context;


    public UserListAdapter(Context context) {
        this.context = context;
    }

    //데이터 추가 메소드
    public void addItem(User user, String docId) {
        items.add(user);
        ids.add(docId);
    }

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

        Log.i("tesssting", user.getNickName());
        view.setName(user.getNickName());
        view.setemail(user.geteMail());
//        view.setImageView(user.getThumbnail());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child("/user/UID/iv_test.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시
                if (uri != null) {
                    //view.setImageView(uri);
                    view.setImageView(parent.getContext(), uri);
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {//회원리스트 아이템 클릭해서 프로젝트 리스트로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , AuserProjectListActivity.class);
                intent.putExtra("docId", ids.get(position));
                context.startActivity(intent);
            }
        });
        return view;


    }




    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("dd", "Error getting bitmap", e);
        }
        return bm;
    }
}