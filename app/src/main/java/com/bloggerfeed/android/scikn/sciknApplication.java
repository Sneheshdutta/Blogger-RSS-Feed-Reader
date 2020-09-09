package com.bloggerfeed.android.scikn;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import com.bloggerfeed.android.scikn.api.DataSource;

/**
 * Created by namlu on 18-Jun-16.
 *
 * Custom singleton class used to ...
 */
public class sciknApplication extends Application {

    //Singleton instance of sciknApplication
    private static sciknApplication sharedInstance;
    private DataSource dataSource;

    public static sciknApplication getSharedInstance(){
        return sharedInstance;
    }

    //What does this method return?
    //Called by ItemAdapter.getItemCount()
    public static DataSource getSharedDataSource(){
        return sciknApplication.getSharedInstance().getDataSource();
    }

    //What does this method return?
    //Called by sciknApplication.getSharedDataSource()
    public DataSource getDataSource(){
        return dataSource;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedInstance = this;
        dataSource = new DataSource();

        // DisplayImageOptions class is composed of settings which pertain to each image loading request
        // We create a default instance of these options to be used each time Blocly requests an image.
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        // Returns singleton class instance
        ImageLoader.getInstance().init(configuration);
    }
}
