package com.together.nosheng.adapter;

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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {

    ArrayList<User> items = new ArrayList<>();
//    LayoutInflater inflater;

    //데이터 추가 메소드
    public void addItem(User user) {
        items.add(user);
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
        Log.i("ddddd", "11111");
        storageRef.child("/user/UID/ic_ivtest1.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시
                Log.i("testing", "goooooooooooooood");
                if (uri != null) {
                    //view.setImageView(uri);
                    view.setImageView(parent.getContext(), uri);
                }
            }
        });
        Log.i("ddddd", "22222");
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