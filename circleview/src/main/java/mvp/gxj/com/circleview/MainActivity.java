package mvp.gxj.com.circleview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends Activity {
    private CircleView circleView1;
    private CircleView circleView2;
    private Handler handler ;
    private int mProgress01;//第一个圈的进度条
    private int mProgress02;

    private int delay01;
    private int delay02;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleView1 = (CircleView) this.findViewById(R.id.circle1);
        circleView2 = (CircleView) findViewById(R.id.circle2);

        circleView1.setBottomTextContent("nba");
        circleView2.setBottomTextContent("小白");
        delay01 = circleView1.getmSpeed();
        delay02 = circleView2.getmSpeed();

        handler = new Handler(){
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);
                switch (msg.what){
                    case 0:
                        mProgress01++;
                        if(mProgress01 > 300){
                            break;
                        }
                        circleView1.setmProgress(mProgress01);
                        handler.sendEmptyMessageDelayed(0,delay01);
                        break;
                    case 1:
                        mProgress02++;
                        if(mProgress02 > 300){
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
//        circleView1.setmProgress(0);
//        circleView2.setmProgress(0);

        handler.sendEmptyMessage(0);
        handler.sendEmptyMessage(1);



    }
}
