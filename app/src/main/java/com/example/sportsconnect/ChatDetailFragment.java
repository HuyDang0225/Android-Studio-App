package com.example.sportsconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.List;

public class ChatDetailFragment extends Fragment {

    private static final String ARG_GAME_ID = "game_id";
    private String gameId;
    private TextView messagesView;

    public static ChatDetailFragment newInstance(String gameId) {
        ChatDetailFragment fragment = new ChatDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GAME_ID, gameId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gameId = requireArguments().getString(ARG_GAME_ID);
        Game game = JoinedGamesStore.getGame(gameId);
        if (game == null) {
            getParentFragmentManager().popBackStack();
            return;
        }

        TextView title = view.findViewById(R.id.tvChatDetailTitle);
        TextView details = view.findViewById(R.id.tvChatDetailDetails);
        messagesView = view.findViewById(R.id.tvChatMessages);
        EditText messageInput = view.findViewById(R.id.etChatMessage);
        Button sendButton = view.findViewById(R.id.btnSendMessage);

        title.setText(game.getTitle());
        details.setText(game.getSport() + " • " + game.getLocationTime());
        renderMessages();

        sendButton.setOnClickListener(v -> {
            JoinedGamesStore.addMessage(gameId, messageInput.getText().toString());
            messageInput.setText("");
            renderMessages();
        });
    }

    private void renderMessages() {
        List<String> messages = JoinedGamesStore.getMessages(gameId);
        StringBuilder text = new StringBuilder();
        for (String message : messages) {
            if (text.length() > 0) {
                text.append("\n\n");
            }
            text.append(message);
        }
        messagesView.setText(text.toString());
    }
}
