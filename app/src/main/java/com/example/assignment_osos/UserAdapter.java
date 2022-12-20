package com.example.assignment_osos;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ArrayList<Users> list;
    Context context;

    public UserAdapter(ArrayList<Users> userList, Context context) {
        this.list = userList;
        this.context = context;
    }

    public void updateList(ArrayList<Users> l) {
        for (Users user : l) {
            list.add(user);
        }
        this.notifyDataSetChanged();
    }

    public Users getUser(int i) {
        return list.get(i);
    }

    public void removeUser(int i) {
        list.remove(i);
        this.notifyItemRemoved(i);
    }

    public void addUser(Users user, int i) {
        list.add(i, user);
        this.notifyItemInserted(i);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = list.get(position);
        holder.name.setText(users.getName());
        holder.username.setText(users.getUsername());
        holder.email.setText(users.getEmail());
        Address curAddress = users.getAddress();
        holder.street.setText(curAddress.getStreet());
        holder.city.setText(curAddress.getCity());
        holder.zipcode.setText(curAddress.getZipcode());
        if (users.getColor() != -1) holder.userCard.setCardBackgroundColor(Color.DKGRAY);
        else holder.userCard.setCardBackgroundColor(Color.GRAY);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, username, email, street, city, zipcode;
        CardView userCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            username = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.email);
            street = itemView.findViewById(R.id.street_text);
            city = itemView.findViewById(R.id.city_text);
            zipcode = itemView.findViewById(R.id.zipcode_text);
            userCard = itemView.findViewById(R.id.user_card);
        }
    }
}
