package com.example.sportsconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatGroupAdapter extends RecyclerView.Adapter<ChatGroupAdapter.ChatGroupViewHolder> {

    public interface OnChatClickListener {
        void onChatClick(Game game);
    }

    private final List<Game> games;
    private final OnChatClickListener listener;

    public ChatGroupAdapter(List<Game> games, OnChatClickListener listener) {
        this.games = games;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_group, parent, false);
        return new ChatGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatGroupViewHolder holder, int position) {
        Game game = games.get(position);
        holder.title.setText(game.getTitle());
        holder.details.setText(game.getSport() + " • " + game.getLocationTime());
        holder.openButton.setOnClickListener(v -> listener.onChatClick(game));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    static class ChatGroupViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView details;
        Button openButton;

        ChatGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvChatGroupTitle);
            details = itemView.findViewById(R.id.tvChatGroupDetails);
            openButton = itemView.findViewById(R.id.btnOpenChat);
        }
    }
}
