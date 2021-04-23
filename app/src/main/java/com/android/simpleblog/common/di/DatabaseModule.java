package com.android.simpleblog.common.di;

import android.app.Application;

import androidx.room.Room;

import com.android.simpleblog.common.db.BlogDatabase;
import com.android.simpleblog.common.db.BlogListDao;
import com.android.simpleblog.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    @Provides
    @Singleton
    BlogDatabase provideMovieDatabase(Application application){
        return Room.databaseBuilder(application, BlogDatabase.class, Constants.DataBaseName)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    BlogListDao provideBlogListDao(BlogDatabase blogDatabase){
        return  blogDatabase.blogListDao();
    }
}
