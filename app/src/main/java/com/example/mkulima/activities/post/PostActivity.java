package com.example.mkulima.activities.post;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.mkulima.Model.Product;
import com.example.mkulima.R;
import com.example.mkulima.databinding.ActivityPostBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {
    private ActivityPostBinding binding;
    private Uri imageUri;
    String ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imageViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canReadStorage()){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,300);
                }else {
                    ActivityCompat.requestPermissions(PostActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},400);
                }
            }
        });
        FirebaseStorage.getInstance().getReference("storage").putFile(imageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        ui = task.getResult().getUploadSessionUri().toString();
                    }
                });
        binding.post.setOnClickListener(v -> {
            Product p = new Product(binding.title.getText().toString(),binding.description.getText().toString(),binding.price.getText().toString(),ui,new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(System.currentTimeMillis()),Integer.parseInt(binding.stock.getText().toString()));
            FirebaseDatabase.getInstance().getReference().setValue(p).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                }
            });
        });
    }
    private boolean canReadStorage(){
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            imageUri = data.getData();
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                binding.imageViewPic.setImageBitmap(bm);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}