package mvp.gxj.com.circleview;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by gxj on 2016/5/21.
 * 可以在线加载网络图 然后展示位圆形，可以带圆环，间接解决了地图上显示用户头像问题
 */
public class LoadImageToCircleActivity extends AppCompatActivity {

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)//如果只是简单显示圆形，直接用下面这句话就可以
                //.displayer(new RoundedBitmapDisplayer(DensityUtil.dip2px(this, 50)))
                .build();

        image = (ImageView) findViewById(R.id.image);

        ImageSize mImageSize = new ImageSize(200, 200);

        ImageLoader.getInstance().loadImage(
                "http://www.pp3.cn/uploads/allimg/111115/103QR503-1.jpg",
//                mImageSize,
                options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason){}

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
                        Bitmap localBitmap = Bitmap.createBitmap(loadedImage.getWidth(),
                                loadedImage.getHeight(), localConfig);

                        int height = localBitmap.getHeight() / 2;
                        int width = localBitmap.getWidth() / 2;
                        int radius = Math.min(height, width);
                        int strokeWidth = 10;

                        Paint mBitmapPaint = new Paint();
                        Paint mBorderPaint = new Paint();

                        BitmapShader mBitmapShader = new BitmapShader(loadedImage,
                                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                        mBitmapPaint.setShader(mBitmapShader);
                        mBitmapPaint.setAntiAlias(true);
                        mBitmapPaint.setStyle(Paint.Style.FILL);

                        mBorderPaint.setStyle(Paint.Style.STROKE);
                        mBorderPaint.setAntiAlias(true);
                        mBorderPaint.setColor(Color.GREEN);
                        mBorderPaint.setStrokeWidth(strokeWidth);

                        Canvas canvas = new Canvas(localBitmap);
                        //绘制圆形图片
                        canvas.drawCircle(width, height, radius-strokeWidth , mBitmapPaint);
                        //绘制外环
                        canvas.drawCircle(width, height, radius-strokeWidth/2, mBorderPaint);

                        image.setImageBitmap(localBitmap);

                        //如果在地图中也可是使用，显示用户圆形头像
                        /*markerOption.icon(
                                BitmapDescriptorFactory.fromBitmap(localBitmap));
                        markerOptionlst.add(markerOption);
                        markerlst = aMap.addMarkers(markerOptionlst, true);
                        marker2 = markerlst.get(0);*/
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });

    }
}
