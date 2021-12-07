package com.example.mkulima.activities.credentials.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mkulima.activities.landing.Landing;
import com.example.mkulima.databinding.FragmentLoginBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentLoginBinding binding;
    private String email, password;

    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.loginBtn.setOnClickListener(v -> {
            email = Objects.requireNonNull(binding.emailEtLogin.getText()).toString().trim();
            password = Objects.requireNonNull(binding.passwordEtLogin.getText()).toString().trim();
            if (email.isEmpty()) {
                binding.emailEtLayoutLogin.setError("Cannot be empty");
            } else {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.emailEtLayoutLogin.setError("Invalid email address");
                } else {
                    if (password.isEmpty()) {
                        binding.passwordEtLogin.setError("Cannot be empty");
                    } else {
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful() && task.isComplete()) {
                                        startActivity(new Intent(requireContext(), Landing.class));
                                        requireActivity().finish();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Snackbar.make(binding.getRoot(), "Something went wrong try again", Snackbar.LENGTH_LONG).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show();
                                });
                    }
                }
            }
        });
    }
}