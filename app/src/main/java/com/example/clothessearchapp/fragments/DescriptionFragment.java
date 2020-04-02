package com.example.clothessearchapp.fragments;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clothessearchapp.R;

public class DescriptionFragment extends Fragment {

    private String content;
    private TextView text;

    public DescriptionFragment(String content){
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);

        text = view.findViewById(R.id.text);
        text.setMovementMethod(new ScrollingMovementMethod());
        text.setText(content);

        return view;
    }
}
