package com.example.mkulima.activities.credentials.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.mkulima.Model.User;
import com.example.mkulima.R;
import com.example.mkulima.activities.landing.Landing;
import com.example.mkulima.databinding.FragmentSignUpBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Objects;


public class SignUpFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "SignUpFragment";
    private FragmentSignUpBinding binding;
    private String name, email, phone, natIdNo, password, role, imageUrl;
    private Uri imageUri;

    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.circleImageView.setOnClickListener(v -> {
            if (canReadStorage()) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 200);
            } else {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
        });
        binding.signUpButton.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.signUpButton.setActivated(false);
            name = Objects.requireNonNull(binding.nameEt.getText()).toString().trim();
            email = Objects.requireNonNull(binding.emailEt.getText()).toString().trim();
            phone = Objects.requireNonNull(binding.phoneEt.getText()).toString().trim();
            natIdNo = Objects.requireNonNull(binding.idEt.getText()).toString().trim();
            password = Objects.requireNonNull(binding.passwordEt.getText()).toString().trim();
            int id = binding.roleRadioGroup.getCheckedRadioButtonId();
            if (id == R.id.buyer_radio) {
                role = "Buyer";
            } else {
                role = "Seller";
            }
            if (name.isEmpty()) {
                binding.nameEtLayout.setError("Cannot be empty");
            } else {
                if (email.isEmpty()) {
                    binding.emailEtLayout.setError("Cannot be empty");
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        binding.emailEtLayout.setError("Invalid email address");
                    } else {
                        if (phone.isEmpty()) {
                            binding.phoneEtLayout.setError("Cannot be empty");
                        } else {
                            if (!Patterns.PHONE.matcher(phone).matches()) {
                                binding.phoneEtLayout.setError("Invalid phone number");
                            } else {
                                if (natIdNo.isEmpty()) {
                                    binding.idEtLayout.setError("Cannot be empty");
                                } else {
                                    if (password.isEmpty()) {
                                        binding.passwordEtLayout.setError("Cannot be empty");
                                    } else {
                                        if (role.isEmpty()) {
                                            Snackbar.make(binding.getRoot(), "Please choose a role", Snackbar.LENGTH_LONG).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show();
                                        } else {
                                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                                                    .addOnSuccessListener(authResult -> {
                                                        if (authResult.getUser().getUid() != null) {
                                                            String downloadUrl = uploadImage(imageUri);
                                                            User user = new User(
                                                                    authResult.getUser().getUid(),
                                                                    name,
                                                                    email,
                                                                    phone,
                                                                    natIdNo,
                                                                    password,
                                                                    role,
                                                                    downloadUrl
                                                            );
                                                            saveUserToDb(user);
                                                        }
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        if (e instanceof FirebaseAuthUserCollisionException) {
                                                            Snackbar.make(binding.getRoot(), "User already exist consider login", Snackbar.LENGTH_LONG).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show();
                                                        }
                                                    });
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            binding.progressBar.setVisibility(View.GONE);
            binding.signUpButton.setActivated(true);
            startActivity(new Intent(requireContext(), Landing.class));
            requireActivity().finish();
        });
    }

    private boolean canReadStorage() {
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && Arrays.equals(permissions, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}) && grantResults[0] > 1) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 200);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                binding.circleImageView.setImageBitmap(bm);
            } catch (Exception e) {
                Log.e(TAG, "onActivityResult: image ->" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private String uploadImage(Uri imageUri) {
        return "";
    }

    private void saveUserToDb(User user) {
        FirebaseDatabase.getInstance().getReference("users/" + user.getUid())
                .setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isComplete() && task.isSuccessful()) {
                        Snackbar.make(binding.getRoot(), "User saved successfully", Snackbar.LENGTH_LONG).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Snackbar.make(binding.getRoot(), "Something went wrong try again", Snackbar.LENGTH_LONG).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show();
                });
    }
}