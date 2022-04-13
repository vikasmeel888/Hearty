package com.example.bmop.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bmop.R;
import com.example.bmop.activities.ChatActivity;
import com.example.bmop.models.IDModelMsg;
import com.example.bmop.models.Medicine;
import com.example.bmop.models.User;
import com.example.bmop.utils.AppPreferences;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.example.bmop.utils.Constants.CHATS_COLLECTION;


public class MessageAdapter extends FirestoreRecyclerAdapter<IDModelMsg, MessageAdapter.MsgViewHolder> {
    Context context;
    User user;
    FirebaseFirestore firebaseFirestore;
    public  MessageAdapter(Context context, FirestoreRecyclerOptions<IDModelMsg> query){
        super(query);
        this.context = context;
        user = AppPreferences.getInstance().getUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageAdapter.MsgViewHolder holder, int position, @NonNull IDModelMsg model) {
        holder.setIsRecyclable(false);
        String uid="";
        List<String> list = model.getChatters();
        if(list.get(0).equals(user.getId())){
            uid = list.get(1);
        }else{
            uid=list.get(0);
        }

        firebaseFirestore.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete( Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot!=null && documentSnapshot.exists()){
                        User user1= documentSnapshot.toObject(User.class);
                        holder.name.setText(user1.getFirstName()+" "+user1.getLastName());
                        holder.con_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context,ChatActivity.class);
                                intent.putExtra("id",user1.getId());
                                context.startActivity(intent);
                            }
                        });
                    }
                }
            }
        });
    }

    @NonNull
    @Override
    public MessageAdapter.MsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_container,parent,false);
        return new MessageAdapter.MsgViewHolder(view);
    }


    public class MsgViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ConstraintLayout con_layout;
        public MsgViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.username);
            con_layout = itemView.findViewById(R.id.con_layout);
        }
    }
}

