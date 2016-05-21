package mvp.gxj.com.circleview;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by gxj on 2016/5/21.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        configImageLoader();
    }

    private void configImageLoader() {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .threadPoolSize(4)
                .threadPriority(3)
                .memoryCache(new WeakMemoryCache())
                .build();
        ImageLoader.getInstance().init(config);
    }
}

