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
        List<Game> joinedGames = JoinedGamesStore.getJoinedGames();

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new ChatGroupAdapter(joinedGames, game ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, ChatDetailFragment.newInstance(game.getId()))
                        .addToBackStack(null)
                        .commit()));

        emptyState.setVisibility(joinedGames.isEmpty() ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(joinedGames.isEmpty() ? View.GONE : View.VISIBLE);
    }
}
