package com.android.simpleblog.feature.main;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.android.simpleblog.common.db.model.BlogList;
import com.android.simpleblog.common.models.Blog;
import com.android.simpleblog.common.repository.Repository;
import com.android.simpleblog.feature.app.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private Repository repository;
    MutableLiveData<ArrayList<Blog>> blogList = new MutableLiveData<>();

    @Inject
    public MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public void callNetworkForBlog(){
        repository.getBlogListFromNetwork();
    }

    public LiveData<List<BlogList>> getAllBlogList() {
        return repository.getAllBlogList();
    }

}
