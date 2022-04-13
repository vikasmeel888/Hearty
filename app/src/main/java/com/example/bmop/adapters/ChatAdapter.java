package com.example.bmop.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bmop.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.example.bmop.models.Message;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
;

public class ChatAdapter extends FirestoreRecyclerAdapter<Message, ChatAdapter.ChatViewHolder> {
    private static final String TAG = "ChatAdapter";

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private User user;
    private Context context;
    private OnChatItemLongClickListener onChatItemClickListener;

    public ChatAdapter(Context context, @NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
        this.context = context;
        user = AppPreferences.getInstance().getUser();
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull Message model) {
        Log.d(TAG, "onBindViewHolder: " + model);
        holder.setIsRecyclable(false);
        if (model.getUserId().equals(user.getId())) {

            holder.list_right.setText(model.getMessage());
            holder.list_right.setVisibility(View.VISIBLE);
        } else {
            holder.list_left.setText(model.getMessage());
            holder.list_left.setVisibility(View.VISIBLE);

        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_list, parent, false);
        return new ChatViewHolder(view);
    }

    class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private TextView list_right;
        private TextView list_left;
        private ImageView img_right;
        private ImageView img_left;
        private TextView timestamp_left;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            list_right = itemView.findViewById(R.id.snd_message);
            list_left = itemView.findViewById(R.id.recv_message);
            img_left = itemView.findViewById(R.id.destImage);
            img_right = itemView.findViewById(R.id.userImage);

        }

        @Override
        public boolean onLongClick(View view) {
            Log.d(TAG, "onLongClick: ");
            int pos = getAbsoluteAdapterPosition();
            Message message = getItem(pos);
            Log.d(TAG, "onLongClick: " + message);
            if (onChatItemClickListener != null) {
                onChatItemClickListener.onClickChatItem(message, pos);
            }
            return false;
        }
    }

    public interface OnChatItemLongClickListener {
        void onClickChatItem(Message message, int position);
    }

//    public void setOnChatItemLongClickListener(OnChatItemLongClickListener onChatItemClickListener) {
//        this.onChatItemClickListener = onChatItemClickListener;
//    }

}
