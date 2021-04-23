package com.android.simpleblog.feature.editblog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.simpleblog.common.db.model.BlogList;
import com.android.simpleblog.common.models.Blog;
import com.android.simpleblog.common.repository.Repository;
import com.android.simpleblog.utils.Extras;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EditBlogViewModel extends ViewModel {
    private Repository repository;
    @Inject
    public EditBlogViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Blog> getBlogByID(int id){
        MutableLiveData<Blog> blogMutableLiveData = new MutableLiveData<>();
        BlogList blogList =repository.getBlogByID(id) ;
        Blog blog = new Blog();
        blog.setId(blogList.getId());
        blog.setTitle(blogList.getTitle());
        blog.setDescription(blogList.getDescription());
        blog.setCoverPhoto(blogList.getCoverPhoto());
        blog.setCategories(Extras.stringToArrayList(blogList.getCategories()));
        blog.setAuthor(Extras.stringTOAuthor(blogList.getAuthor()));
        blogMutableLiveData.setValue(blog);
        return blogMutableLiveData;
    }

    public void updateBlog(String title, String description, ArrayList<String> categories, int id){
        repository.updateBlogByID(title, description, Extras.arrayListTOString(categories), id);
    }
}
