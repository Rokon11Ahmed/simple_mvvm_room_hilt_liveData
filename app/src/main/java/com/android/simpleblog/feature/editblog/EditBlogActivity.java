package com.android.simpleblog.feature.editblog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.simpleblog.common.models.Author;
import com.android.simpleblog.common.models.Blog;
import com.android.simpleblog.databinding.ActivityEditBlogBinding;
import com.android.simpleblog.feature.addblog.AddBlogActivity;
import com.android.simpleblog.feature.main.MainActivity;
import com.android.simpleblog.feature.main.MainViewModel;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditBlogActivity extends AppCompatActivity {
    ActivityEditBlogBinding binding;
    String id;
    EditBlogViewModel viewModel;
    Blog newBlog;
    String title, descreption;
    List<KeyPairBoolData> categorieList = new ArrayList<>();
    ArrayList<String> selectedCategoriesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBlogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Edit Blog");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewModel = new ViewModelProvider(this).get(EditBlogViewModel.class);

        getIntentData();
        initOnClickListener();
    }

    private void initOnClickListener() {
        binding.multipleItemSelectionSpinner.setItems(categorieList, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> selectedItems) {
                selectedCategoriesList.clear();
                for (int i = 0; i < selectedItems.size(); i++) {
                    if (selectedItems.get(i).isSelected()) {
                        selectedCategoriesList.add(selectedItems.get(i).getName());
                    }else {
                        selectedCategoriesList.remove(selectedItems.get(i).getName());
                    }
                }
            }
        });

        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = binding.titleEditText.getText().toString();
                descreption = binding.blogDescreptionEditText.getText().toString();
                if (!title.isEmpty()){
                    if (!descreption.isEmpty()){
                        if (selectedCategoriesList.size()>0){

                            viewModel.updateBlog(title, descreption, selectedCategoriesList, Integer.parseInt(id));
                            startActivity(new Intent(EditBlogActivity.this, MainActivity.class));
                        }else {
                            Toast.makeText(EditBlogActivity.this, "Enter categories", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(EditBlogActivity.this, "Enter blog description", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EditBlogActivity.this, "Enter blog title", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initObservers() {

        viewModel.getBlogByID(Integer.parseInt(id)).observe(this, new Observer<Blog>() {
            @Override
            public void onChanged(Blog blog) {
                newBlog = new Blog();
                newBlog = blog;
                loadData();
            }
        });
    }

    private void loadData() {
        binding.titleEditText.setText(newBlog.getTitle());
        binding.blogDescreptionEditText.setText(newBlog.getDescription());
        categorieList.add(new KeyPairBoolData("Business", false));
        categorieList.add(new KeyPairBoolData("Lifestyle", false));
        categorieList.add(new KeyPairBoolData("Entertainment", false));
        categorieList.add(new KeyPairBoolData("Productivity", false));
        for (int i =0; i<categorieList.size(); i++){
            if (newBlog.getCategories().contains(categorieList.get(i))){
                categorieList.set(i, new KeyPairBoolData(categorieList.get(i).getName(), true));
            }
        }
    }

    private void getIntentData() {
        id = getIntent().getStringExtra("id");
        initObservers();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}