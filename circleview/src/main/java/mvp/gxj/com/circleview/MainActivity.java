package mvp.gxj.com.circleview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import mvp.gxj.com.circleview.view.CircleView;

public class MainActivity extends Activity implements CircleView.MyListener{
    private CircleView circleView1;
    private CircleView circleView2;
    private Handler handler ;
    private int mProgress01;//第一个圈的进度条
    private int mProgress02;

    private int delay01;
    private int delay02;

    private Button btn,go_third,go_other , go_wave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleView1 = (CircleView) this.findViewById(R.id.circle1);
        circleView2 = (CircleView) findViewById(R.id.circle2);

        btn = (Button) findViewById(R.id.go_second);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DataMonitorActivity.class));
            }
        });

        go_third  = (Button) findViewById(R.id.go_third);
        go_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ThirdActivity.class));
            }
        });

        go_other = (Button) findViewById(R.id.go_other);
        go_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoadImageToCircleActivity.class));
            }
        });

        go_wave = (Button) findViewById(R.id.go_wave);
        go_wave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WaveActivity.class));
            }
        });

        circleView1.setBottomTextContent("nba");
        circleView2.setBottomTextContent("小白");

        delay01 = circleView1.getmSpeed();
        delay02 = circleView2.getmSpeed();

        circleView2.setListener(this);

        handler = new Handler(){
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);
                switch (msg.what){
                    case 0:
                        mProgress01++;
                        if(mProgress01 > 300){
                            circleView1.setCenterTextContent("1重新扫描");
                            circleView1.setCenterTextSize(DensityUtil.sp2px(getResources(),10));
                            break;
                        }
                        circleView1.setmProgress(mProgress01);
                        handler.sendEmptyMessageDelayed(0,delay01);
                        break;
                    case 1:
                        mProgress02++;
                        if(mProgress02 > 300){
                            circleView2.setCenterTextColor(Color.BLACK);
                            circleView2.setCenterTextContent("2重新扫描");
                            circleView2.setCenterTextSize(DensityUtil.sp2px(getResources(),20));
                            break;
                        }
                        circleView2.setmProgress(mProgress02);
                        handler.sendEmptyMessageDelayed(1,delay02);
                        break;
                    default:
                        break;
                }

            }
        };
//        circleView1.setmProgress(50);
//        circleView2.setmProgress(0);

        handler.sendEmptyMessage(0);
        handler.sendEmptyMessage(1);

    }

    @Override
    public void start() {
        Log.d("AA","------点击成功！------- ");
        handler.removeMessages(1);
        mProgress02 = 0;
        handler.sendEmptyMessage(1);
    }
}