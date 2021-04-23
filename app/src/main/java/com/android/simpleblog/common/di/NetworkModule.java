package com.android.simpleblog.common.di;

import com.android.simpleblog.common.network.BlogApiService;
import com.android.simpleblog.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    @Provides
    @Singleton
    public static BlogApiService provideMovieApiService(){
        return  new Retrofit.Builder()
                .baseUrl(Constants.BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BlogApiService.class);
    }
}
