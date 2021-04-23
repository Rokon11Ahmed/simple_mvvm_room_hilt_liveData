package com.android.simpleblog.common.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.simpleblog.common.db.model.BlogList;

import java.util.List;

@Dao
public interface BlogListDao {
    @Insert
    void insert(BlogList blogList);

    @Query("DELETE From blog_list WHERE id = :movieId")
    void delete(int movieId);

    @Query("DELETE FROM blog_list")
    void clearBlogList();

    @Query("SELECT * FROM blog_list")
    LiveData<List<BlogList>> getBlogList();

    @Query("SELECT * FROM blog_list WHERE id = :blogId ")
    BlogList getBlogByID(int blogId);

    @Query("UPDATE blog_list SET title = :title, description = :description, categories = :categories WHERE id =:id")
    void update(String title, String description,String categories, int id);
}
