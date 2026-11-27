package com.example.sportsconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerChatGroups);
        TextView emptyState = view.findViewById(R.id.tvNoChats);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new ChatGroupAdapter(new ArrayList<>(), game ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, ChatDetailFragment.newInstance(game.getId()))
                        .addToBackStack(null)
                        .commit()));

        emptyState.setText("Loading your group chats...");
        emptyState.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        JoinedGamesStore.loadJoinedGames(requireContext(), new JoinedGamesStore.GamesCallback() {
            @Override
            public void onLoaded(List<Game> games) {
                if (!isAdded()) {
                    return;
                }
                recyclerView.setAdapter(new ChatGroupAdapter(games, game ->
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment,
                                        ChatDetailFragment.newInstance(game.getId()))
                                .addToBackStack(null)
                                .commit()));
                emptyState.setVisibility(games.isEmpty() ? View.VISIBLE : View.GONE);
                emptyState.setText("Join a game to create its group chat.");
                recyclerView.setVisibility(games.isEmpty() ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onError(Exception exception) {
                if (!isAdded()) {
                    return;
                }
                emptyState.setText("Unable to load saved chats.");
                emptyState.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
    }
}
