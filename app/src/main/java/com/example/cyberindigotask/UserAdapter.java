package com.example.cyberindigotask;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewUser>{

    Context context;
    List<UserModel> userModels;

    public UserAdapter(Context context, List<UserModel> userModels) {
        this.context = context;
        this.userModels = userModels;
    }

    @NonNull
    @Override
    public ViewUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater=LayoutInflater.from(context);
        view=inflater.inflate(R.layout.user_list,parent,false);

        return new UserAdapter.ViewUser(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewUser holder, int position) {
        holder.userId.setText(userModels.get(position).getId());
        holder.userName.setText(userModels.get(position).getFirstName()+" "+userModels.get(position).getLastName());
        holder.userEmail.setText(userModels.get(position).getEmail());
        String  ur=userModels.get(position).getImg();



            Glide.with(context)
                    .load(ur)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            disPlayDesignNo(holder, designNo);
                            holder.userImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher_background));

                            return true;
                        }


                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .thumbnail(0.5f)
                    .into(holder.userImage);



    }


    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public void filterList(List<UserModel> filterdNames) {
        userModels=filterdNames;
        notifyDataSetChanged();
    }
    public class ViewUser extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName,userEmail,userId;
        public ViewUser(@NonNull View itemView) {
            super(itemView);
            userImage=itemView.findViewById(R.id.imageView);
            userName=itemView.findViewById(R.id.name);
            userEmail=itemView.findViewById(R.id.email);
            userId=itemView.findViewById(R.id.id);
        }
    }
}
