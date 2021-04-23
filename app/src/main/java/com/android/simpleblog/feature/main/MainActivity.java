package com.android.simpleblog.feature.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.simpleblog.R;
import com.android.simpleblog.common.db.model.BlogList;
import com.android.simpleblog.common.models.Blog;
import com.android.simpleblog.databinding.ActivityMainBinding;
import com.android.simpleblog.feature.addblog.AddBlogActivity;
import com.android.simpleblog.utils.Extras;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    MainViewModel viewModel;
    ActivityMainBinding binding;
    ArrayList<Blog> blogArrayList = new ArrayList<>();

    BlogRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.callNetworkForBlog();

        initView();
        initObservers();
        initOnClickListener();
    }

    private void initOnClickListener() {
        binding.addBlogFloatingActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddBlogActivity.class));
            }
        });
    }

    private void initView() {
        adapter = new BlogRecyclerAdapter(blogArrayList);
        binding.blogRecyclerView.setAdapter(adapter);
        binding.blogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initObservers() {
        viewModel.getAllBlogList().observe(this, blogLists -> {
            blogArrayList.clear();
            for (int i = 0; i<blogLists.size(); i++){
                BlogList blog = blogLists.get(i);
                Blog singleBlog = new Blog();
                singleBlog.setId(blog.getId());
                singleBlog.setTitle(blog.getTitle());
                singleBlog.setCoverPhoto(blog.getCoverPhoto());
                singleBlog.setDescription(blog.getDescription());
                singleBlog.setCategories(Extras.stringToArrayList(blog.getCategories()));
                singleBlog.setAuthor(Extras.stringTOAuthor(blog.getAuthor()));

                blogArrayList.add(singleBlog);
            }
            adapter.notifyDataSetChanged();
        });
    }
}