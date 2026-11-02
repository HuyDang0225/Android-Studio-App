package com.example.sportsconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate your discover screen layout XML here
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the RecyclerView using the view parameter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerGameFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Game> sampleGames = new ArrayList<>();
        sampleGames.add(new Game("1", "Basketball", "3v3 Full Court Run", "📍 Lake Eola Park • 🕒 Today, 6:00 PM", 4, 6));
        sampleGames.add(new Game("2", "Soccer", "Casual Grass Pickup", "📍 UCF Memory Mall • 🕒 Tomorrow, 5:30 PM", 8, 10));
        sampleGames.add(new Game("3", "Soccer", "Casual Grass Pickup", "📍 UCF RWC Park • 🕒 Tomorrow, 7:00 PM", 5, 11));
        sampleGames.add(new Game("4", "Badminton", "Badminton Pickup ", "📍 UCF Memory Mall • 🕒 Today, 5:30 PM", 2, 4));

        GameAdapter adapter = new GameAdapter(sampleGames, clickedGame -> {
            // Handle Join Game button click action here
        });

        recyclerView.setAdapter(adapter);
    }
}