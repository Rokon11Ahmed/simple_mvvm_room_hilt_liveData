package com.android.simpleblog.common.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.simpleblog.common.db.model.BlogList;

@Database(entities = {BlogList.class}, version = 1,exportSchema = false)
public abstract class BlogDatabase extends RoomDatabase {

    public abstract BlogListDao blogListDao();
}
