package com.example.todolist.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

        holder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Title");
                // Set up the input
                final EditText input = new EditText(view.getContext());
                input.setText(content.getData());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper helper = new DBHelper(view.getContext());
                        content.setData(input.getText().toString());
                        helper.updateData(content);
                        listContent.set(position, content);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
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
        CardView cvItem;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
            cvItem = itemView.findViewById(R.id.cv_item);
        }
    }
}
