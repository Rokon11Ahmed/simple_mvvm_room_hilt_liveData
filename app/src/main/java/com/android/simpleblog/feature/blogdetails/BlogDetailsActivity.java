package com.android.simpleblog.feature.blogdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.simpleblog.common.models.Blog;
import com.android.simpleblog.databinding.ActivityBlogDetailsBinding;
import com.android.simpleblog.feature.editblog.EditBlogActivity;
import com.android.simpleblog.utils.Extras;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BlogDetailsActivity extends AppCompatActivity {
    ActivityBlogDetailsBinding binding;
    String id, title, descreption, coverPhoto, autorName, authorAvater, authorProfession;
    ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlogDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getIntentData();
        initOnClickListener();
    }

    private void initOnClickListener() {
        binding.editBlogFloatingActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BlogDetailsActivity.this, EditBlogActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void getIntentData() {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        descreption = getIntent().getStringExtra("descreption");
        coverPhoto = getIntent().getStringExtra("cover_photo");
        autorName = getIntent().getStringExtra("author_name");
        authorAvater = getIntent().getStringExtra("author_avater");
        authorProfession = getIntent().getStringExtra("author_profession");
        categories = getIntent().getStringArrayListExtra("categories");

        loadData();
    }

    private void loadData() {
        binding.blogTitleTextView.setText(title);
        binding.blogDescreptionTextView.setText(descreption);
        binding.authorNameProfession.setText(authorProfession);
        binding.authorNameTextView.setText(autorName);
        binding.categoriesTextView.setText(Extras.arrayListTOString(categories));
        Glide.with(binding.blogHeaderImageView).load(coverPhoto).into(binding.blogHeaderImageView);
        Glide.with(binding.authorImageView).load(authorAvater).into(binding.authorImageView);
    }
}