package com.example.sportsconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<Game> gameList;
    private OnJoinClickListener listener;

    public interface OnJoinClickListener {
        void onJoinClick(Game game);
    }

    public GameAdapter(List<Game> gameList, OnJoinClickListener listener) {
        this.gameList = gameList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_card, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.tvSportCategory.setText(game.getSport().toUpperCase());
        holder.tvPlayerCount.setText(game.getCurrentPlayers() + "/" + game.getMaxPlayers() + " Spots Filled");
        holder.tvGameTitle.setText(game.getTitle());
        holder.tvLocationTime.setText(game.getLocationTime());

        holder.btnJoinGame.setOnClickListener(v -> listener.onJoinClick(game));
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView tvSportCategory, tvPlayerCount, tvGameTitle, tvLocationTime;
        Button btnJoinGame;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSportCategory = itemView.findViewById(R.id.tvSportCategory);
            tvPlayerCount = itemView.findViewById(R.id.tvPlayerCount);
            tvGameTitle = itemView.findViewById(R.id.tvGameTitle);
            tvLocationTime = itemView.findViewById(R.id.tvLocationTime);
            btnJoinGame = itemView.findViewById(R.id.btnJoinGame);
        }
    }
}