package com.android.simpleblog.feature.addblog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.simpleblog.common.models.Author;
import com.android.simpleblog.common.models.Blog;
import com.android.simpleblog.databinding.ActivityAddBlogBinding;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddBlogActivity extends AppCompatActivity {
    ActivityAddBlogBinding binding;
    List<KeyPairBoolData> categorieList = new ArrayList<>();
    ArrayList<String> selectedCategoriesList = new ArrayList<>();
    String title, descreption;
    AddBlogViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBlogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AddBlogViewModel.class);
        getSupportActionBar().setTitle("Add New");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initOnClickListener();
    }

    private void initOnClickListener() {
        categorieList.add(new KeyPairBoolData("Business", false));
        categorieList.add(new KeyPairBoolData("Lifestyle", false));
        categorieList.add(new KeyPairBoolData("Entertainment", false));
        categorieList.add(new KeyPairBoolData("Productivity", false));
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
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = binding.titleEditText.getText().toString();
                descreption = binding.blogDescreptionEditText.getText().toString();
                if (!title.isEmpty()){
                    if (!descreption.isEmpty()){
                        if (selectedCategoriesList.size()>0){
                            Author author = new Author();
                            author.setId(1);
                            author.setName("John Doe");
                            author.setAvatar("https://i.pravatar.cc/250");
                            author.setProfession("Content Writer");
                            Blog blog = new Blog();
                            blog.setId(0);
                            blog.setTitle(title);
                            blog.setDescription(descreption);
                            blog.setCoverPhoto("https://i.picsum.photos/id/608/200/300.jpg?hmac=b-eDmVyhr3rI_4k3z2J_PNwOxEwSKa5EDC9uFH4jERU");
                            blog.setAuthor(author);
                            blog.setCategories(selectedCategoriesList);
                            viewModel.saveNewBlog(blog);
                            onBackPressed();
                        }else {
                            Toast.makeText(AddBlogActivity.this, "Enter categories", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AddBlogActivity.this, "Enter blog description", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AddBlogActivity.this, "Enter blog title", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}