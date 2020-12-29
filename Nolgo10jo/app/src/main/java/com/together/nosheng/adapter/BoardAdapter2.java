package com.together.nosheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.together.nosheng.R;
import com.together.nosheng.model.board.Board;
import com.together.nosheng.view.ItemViewActivity2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BoardAdapter2 extends RecyclerView.Adapter<BoardAdapter2.BoardViewHolder2> {

    private List<Board> datas;
    private Context context;
    private RecyclerViewClickListener listener;


    public BoardAdapter2(List<Board> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public BoardAdapter2(List<Board> datas, Context context, RecyclerViewClickListener listener) {
        this.datas = datas;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BoardViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoardViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item_one2,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoardAdapter2.BoardViewHolder2 holder, int position) {

        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        Date date = new Date();

        Board data = datas.get(position);

        holder.Hid.setText(data.getDocId());

        holder.Hdate.setText(format.format(date));
        holder.Hcontents.setText(data.getContents());
        holder.Htitle.setText(data.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");

                Intent intent = new Intent(context, ItemViewActivity2.class);
                intent.putExtra("title", data.getTitle());
                intent.putExtra("contents", data.getContents());
                intent.putExtra("date", format.format(data.getDate()));
                intent.putExtra("docId", data.getDocId());
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


    public class BoardViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener listener = new RecyclerViewClickListener() {

            @Override
            public void onClick(View v, int position) {
                listener.onClick(itemView, getAdapterPosition());
            }
        };

        private TextView Htitle;
        private TextView Hcontents;
        private TextView Hdate;
        private TextView Hid;



        public BoardViewHolder2(@NonNull View itemView) {
            super(itemView);

            Htitle = itemView.findViewById(R.id.item_board_title2);
            Hcontents = itemView.findViewById(R.id.item_board_contents2);
            Hid = itemView.findViewById(R.id.item_board_writer2);
            Hdate = itemView.findViewById(R.id.item_board_date2);
//            itemView.setOnClickListener(this);

        }




        @Override
        public void onClick(View v) {
            listener.onClick(itemView, getAdapterPosition());
        }
    }


}
