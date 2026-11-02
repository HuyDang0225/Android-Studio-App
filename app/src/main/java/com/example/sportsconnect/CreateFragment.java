package com.example.sportsconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hook up all inputs matching the updated fragment_create.xml IDs
        EditText etGameTitle = view.findViewById(R.id.etGameTitle);
        EditText etSportType = view.findViewById(R.id.etSportType);
        EditText etPeopleCount = view.findViewById(R.id.edittext_people_count);
        EditText etLocation = view.findViewById(R.id.edittext_location);
        EditText etDate = view.findViewById(R.id.edittext_date);
        EditText etTime = view.findViewById(R.id.edittext_time);
        Button btnCreateEvent = view.findViewById(R.id.btn_create_event);

        btnCreateEvent.setOnClickListener(v -> {
            String title = etGameTitle.getText().toString().trim();
            String sport = etSportType.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String people = etPeopleCount.getText().toString().trim();

            if (!title.isEmpty() && !sport.isEmpty() && !location.isEmpty() && !people.isEmpty()) {
                Toast.makeText(getContext(), "Event Created Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}