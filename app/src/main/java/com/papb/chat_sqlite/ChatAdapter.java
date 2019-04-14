package com.papb.chat_sqlite;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.papb.chat_sqlite.dbhelper.ChatHelper;
import com.papb.chat_sqlite.dbhelper.UserHelper;
import com.papb.chat_sqlite.model.ChatModel;
import com.papb.chat_sqlite.model.UserModel;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.CustomViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private ChatHelper mahasiswaHelper;


    private ArrayList<ChatModel> chat;

    private UserHelper userHelper;
    private ArrayList<UserModel> userModels;


    public ChatAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mahasiswaHelper = new ChatHelper(context);
        userHelper = new UserHelper(context);

        userHelper.open();
        userModels = userHelper.getAllData();
        userHelper.close();


    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                               int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.row_view, viewGroup, false);

        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        String username = "anonym" ;
        for (UserModel singleUser : userModels) {
            if(singleUser.getId() == chat.get(position).getUserId()){
                username = singleUser.getUsername();
            }
        }
        final String message = chat.get(position).getMessage();
        final String createdAt = chat.get(position).getCreatedAt();
        holder.txtUsername.setText(username);
        holder.txtMessage.setText(message);
        holder.txtCreatedAt.setText(createdAt);

        holder.cv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent regIntent = new Intent(context ,  EditChatActivity.class);
                regIntent.putExtra("EDIT_ID" , chat.get(position).getId());
                regIntent.putExtra("EDIT_USER_ID" , chat.get(position).getUserId());
                regIntent.putExtra("EDIT_MESSAGE" , chat.get(position).getMessage());
                regIntent.putExtra("EDIT_CREATED_AT" , chat.get(position).getCreatedAt());

                context.startActivity(regIntent);
            }
        });
    }

    private void deleteitem(int id) {

        mahasiswaHelper.open();
        mahasiswaHelper.delete(id);
        mahasiswaHelper.close();

        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return chat.size();
    }

    public void addItem(ArrayList<ChatModel> mData) {
        this.chat = mData;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView txtUsername ,  txtMessage, txtCreatedAt;
        private CardView cv;

        public CustomViewHolder(View itemView) {
            super(itemView);

            txtUsername = (TextView) itemView.findViewById(R.id.main_chat_username);
            txtMessage = (TextView) itemView.findViewById(R.id.main_chat_message);
            txtCreatedAt = (TextView) itemView.findViewById(R.id.main_chat_created_at);
            cv = (CardView) itemView.findViewById(R.id.cv);


        }

    }


}