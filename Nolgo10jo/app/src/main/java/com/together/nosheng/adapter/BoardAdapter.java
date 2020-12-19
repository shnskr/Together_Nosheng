package com.together.nosheng.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.together.nosheng.R;
import com.together.nosheng.model.board.Board;
import com.together.nosheng.view.BoardActivity;
import com.together.nosheng.view.ItemViewActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {

    private List<Board> datas;
    private RecyclerViewClickListener listener;
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public BoardAdapter(List<Board> datas, RecyclerViewClickListener listener) {
        this.datas = datas;
        this.listener = listener;
    }

    public BoardAdapter(List<Board> datas, RecyclerViewClickListener listener, Context context) {
        this.datas = datas;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item_one, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {//여여여여여여여어ㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓ기가 애매하다
        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        Date date = new Date();//date타입 쓰려고 심플포맷 사용

        Board data = datas.get(position);//'data' 객체를 만들어서 'datas'안에있는 것들에 포지션(인덱스)를 넣고
        Log.i("힝구", data.toString());
//#################Hid 가 문제인가???##################################
        //holder는 아이템 1개 씩인거에요
        holder.Hid.setText(data.getDocId());
//        holder.Hdate.setText(data.getDate()); //심플포맷 달형이 어제알려준거쓰자
        holder.Hdate.setText(format.format(date)); //심플포맷 달형이 어제알려준거쓰자
        holder.Hcontents.setText(data.getContents());
        holder.Htitle.setText(data.getTitle());
        holder.documentId = data.getDocumentId();//db삭제시 불러올 문서 아이디

//        holder.date.setText((CharSequence) data.getDate());
//        holder.identify.setText(data.getDocumentId());################################

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");

                Intent intent = new Intent(context, ItemViewActivity.class);
                intent.putExtra("title", data.getTitle());
                intent.putExtra("contents", data.getContents());
                intent.putExtra("date", format.format(data.getDate()));
                intent.putExtra("docId", data.getDocId());
                intent.putExtra("documentId", data.getDocumentId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }



    public class BoardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                listener.onClick(itemView, getAdapterPosition());
            }
        };

        private String documentId;//db삭제시 불러올 문서 아이디
        private TextView Htitle;
        private TextView Hcontents;
        private TextView Hdate;
        private TextView Hid;

        //생성자
        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);

//            Htitle = itemView.findViewById(R.id.it_title);############################################이거일수도잇고
//            Hcontents = itemView.findViewById(R.id.it_contents);
//            Hdate = itemView.findViewById(R.id.it_time);
//            Hid = itemView.findViewById(R.id.it_id);
            Htitle = itemView.findViewById(R.id.item_board_title);//board_item_one################################이거일수도 있어
            Hcontents = itemView.findViewById(R.id.item_board_contents);
            Hid = itemView.findViewById(R.id.item_board_id);
            Hdate = itemView.findViewById(R.id.item_board_date);
            itemView.setOnClickListener(this);
/////////////////////////////////////////////////////////////////////////////////////////////////////////remove 버튼
            itemView.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show();


                }
            });
            /////////////////////          /////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
        void show() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("삭제하시겄어유?");//팝업창의 제목

            String tv_text = "ㄹㅇ삭제?!";//팝업창 내용
            builder.setMessage(tv_text);

            builder.setPositiveButton("이이",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            db.collection("AdminBoard").document(documentId)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("성공!","성공!!!!!!!!!!!!!!!!");
                                            Log.i("지우기성공",documentId);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("실패!","실패!!!!!!!!!!!!!!!!!!!!!!!!");
                                        }
                                    });
                        }
                    });

            builder.setNegativeButton("노노",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            builder.show();
        }

        //이건 원본에 없길래 잠시 지웟당;

//        public void onBind(Board board){
//            Htitle.setText(board.getTitle());
//            Hcontents.setText(board.getContents());
////            Hdate.setText((CharSequence) board.getDate());
//            Hid.setText(board.getDocId());
//        }


        @Override
        public void onClick(View v) {
            listener.onClick(itemView, getAdapterPosition());
        }
    }

}
