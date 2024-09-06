package com.example.todolist.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.helper.DBHelper;
import com.example.todolist.model.Content;

import java.util.ArrayList;

public class ListContentAdapter extends RecyclerView.Adapter<ListContentAdapter.ListViewHolder> {

    private ArrayList<Content> listContent;

    public ListContentAdapter(ArrayList<Content> listContent){this.listContent = listContent;}

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_item, parent,false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Content content = listContent.get(position);
        holder.tvContent.setText(content.getData());

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                DBHelper helper = new DBHelper(view.getContext());
                helper.deleteData(content);
                listContent.clear();
                listContent.addAll(helper.getAllData());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listContent.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{

        TextView tvContent;
        Button btnHapus;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
        }
    }
}
