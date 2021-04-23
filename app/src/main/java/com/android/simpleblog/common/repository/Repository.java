package com.android.simpleblog.common.repository;

import android.util.Log;
import android.widget.LinearLayout;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.simpleblog.common.db.BlogListDao;
import com.android.simpleblog.common.db.model.BlogList;
import com.android.simpleblog.common.models.Blog;
import com.android.simpleblog.common.models.ResponseModel;
import com.android.simpleblog.common.network.BlogApiService;
import com.android.simpleblog.feature.app.App;
import com.android.simpleblog.utils.Extras;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    BlogApiService apiService;
    BlogListDao blogListDao;
    private static final String TAG = "Repository";

    @Inject
    public Repository(BlogApiService apiService, BlogListDao blogListDao) {
        this.apiService = apiService;
        this.blogListDao= blogListDao;
    }

    public void getBlogListFromNetwork(){
        Call<ResponseModel> call = apiService.getBlogFromNetwork();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()){
                    ResponseModel responseModel = response.body();
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            blogListDao.clearBlogList();
                            for (int i =0; i<responseModel.getBlogs().size(); i++){
                                Blog blog = responseModel.getBlogs().get(i);
                                String categories = Extras.arrayListTOString(blog.getCategories());
                                String author = blog.getAuthor().toString();
                                BlogList blogList = new BlogList(blog.getTitle(), blog.getDescription(), blog.getCoverPhoto(), categories, author);
                                blogListDao.insert(blogList);
                            }
                        }
                    };
                    thread.start();

                }else {
                    Log.d(TAG, "onResponse: response is not successfull");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }

    public LiveData<List<BlogList>> getAllBlogList() {
        return blogListDao.getBlogList();
    }

    public void saveNewBlog(Blog blog){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                String categories = Extras.arrayListTOString(blog.getCategories());
                String author = blog.getAuthor().toString();
                BlogList blogList = new BlogList(blog.getTitle(), blog.getDescription(), blog.getCoverPhoto(), categories, author);
                blogListDao.insert(blogList);
            }
        };
        thread.start();
    }

    public BlogList getBlogByID(int id){
        return blogListDao.getBlogByID(id);
    }

    public void updateBlogByID(String title, String descreiption, String category, int id){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                blogListDao.update(title, descreiption, category, id);
            }
        });
        thread.start();
    }


}
