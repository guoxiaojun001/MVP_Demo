package mvp.gxj.com.circleview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import mvp.gxj.com.circleview.view.CustomWaterWaveView;

/**
 * Created by gxj on 2016/5/20.
 */
public class ThirdActivity extends Activity{

    private CustomWaterWaveView mWaterWaveView;
    private SeekBar mSeekBar;
    private TextView power;
    private int max = 1024;
    private int min = 102;
    int auto = 0;


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                int num = msg.getData().getInt("progress");
                Log.i("num", num + "");
                power.setText((float) num / 100 * max + "M/" + max + "M");
            }else if (msg.what == 0){
                handler.removeMessages(0);
                ++auto;
                if(null != mWaterWaveView){
                    if(auto >= 100){
                        mWaterWaveView.setmWaterLevel(1);
                        power.setText("100%" );
                        return;
                    }
                    mWaterWaveView.setmWaterLevel((float) auto / 100);
                    power.setText(auto + "%" );
                    handler.sendEmptyMessageDelayed(0,20);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        power = (TextView) findViewById(R.id.power);
        power.setText(min + "M/" + max + "M");

        handler.sendEmptyMessageDelayed(0,1000);//自动增加

        mWaterWaveView = (CustomWaterWaveView) findViewById(R.id.customwave_view);
        // 设置多高，float，0.1-1F
        mWaterWaveView.setmWaterLevel(0.1F);
        // 开始执行
        mWaterWaveView.startWave();

        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                mWaterWaveView.setmWaterLevel((float) progress / 100);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putInt("progress", progress);
                message.setData(bundle);
                handler.sendMessage(message);
                message.what = 1;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        mWaterWaveView.stopWave();
        mWaterWaveView = null;
        super.onDestroy();
    }

}
