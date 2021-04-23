package com.android.simpleblog.feature.addblog;

import androidx.lifecycle.ViewModel;

import com.android.simpleblog.common.models.Blog;
import com.android.simpleblog.common.repository.Repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddBlogViewModel extends ViewModel {
    private Repository repository;

    @Inject
    public AddBlogViewModel(Repository repository) {
        this.repository = repository;
    }

    public void saveNewBlog(Blog blog){
        repository.saveNewBlog(blog);
    }
}
