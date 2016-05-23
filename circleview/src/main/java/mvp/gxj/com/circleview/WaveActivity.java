package mvp.gxj.com.circleview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import mvp.gxj.com.circleview.view.WaveView;

/**
 * Created by gxj on 2016/5/23.
 */
public class WaveActivity extends AppCompatActivity {

    private WaveHelper mWaveHelper ,mWaveHelper2;

    private int mBorderColor = Color.BLUE;
    private int mBorderWidth = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);

        final WaveView waveView = (WaveView) findViewById(R.id.wave);
        final WaveView waveView2 = (WaveView) findViewById(R.id.wave2);
        waveView.setBorder(mBorderWidth, mBorderColor);
        waveView2.setBorder(mBorderWidth, mBorderColor);

        mWaveHelper = new WaveHelper(waveView);

        mWaveHelper2 = new WaveHelper(waveView2);

        ((RadioGroup) findViewById(R.id.shapeChoice))
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.shapeCircle:
                                waveView.setShapeType(WaveView.ShapeType.CIRCLE);
                                break;
                            case R.id.shapeSquare:
                                waveView.setShapeType(WaveView.ShapeType.SQUARE);
                                break;
                            default:
                                break;
                        }
                    }
                });

        ((SeekBar) findViewById(R.id.seekBar))
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        mBorderWidth = i;
                        waveView.setBorder(mBorderWidth, mBorderColor);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

        CompoundButtonCompat.setButtonTintList(
                (RadioButton) findViewById(R.id.colorDefault),
                getResources().getColorStateList(android.R.color.white));
        CompoundButtonCompat.setButtonTintList(
                (RadioButton) findViewById(R.id.colorRed),
                getResources().getColorStateList(R.color.red));
        CompoundButtonCompat.setButtonTintList(
                (RadioButton) findViewById(R.id.colorGreen),
                getResources().getColorStateList(R.color.green));
        CompoundButtonCompat.setButtonTintList(
                (RadioButton) findViewById(R.id.colorBlue),
                getResources().getColorStateList(R.color.blue));

        ((RadioGroup) findViewById(R.id.colorChoice))
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.colorRed:
                                waveView.setWaveColor(
                                        Color.parseColor("#28f16d7a"),
                                        Color.parseColor("#3cf16d7a"));
                                mBorderColor = Color.parseColor("#44f16d7a");
                                waveView.setBorder(mBorderWidth, mBorderColor);
                                break;
                            case R.id.colorGreen:
                                waveView.setWaveColor(
                                        Color.parseColor("#40b7d28d"),
                                        Color.parseColor("#80b7d28d"));
                                mBorderColor = Color.parseColor("#B0b7d28d");
                                waveView.setBorder(mBorderWidth, mBorderColor);
                                break;
                            case R.id.colorBlue:
                                waveView.setWaveColor(
                                        Color.parseColor("#88b8f1ed"),
                                        Color.parseColor("#b8f1ed"));
                                mBorderColor = Color.parseColor("#b8f1ed");
                                waveView.setBorder(mBorderWidth, mBorderColor);
                                break;
                            default:
                                waveView.setWaveColor(
                                        WaveView.DEFAULT_BEHIND_WAVE_COLOR,
                                        WaveView.DEFAULT_FRONT_WAVE_COLOR);
                                mBorderColor = Color.parseColor("#44FFFFFF");
                                waveView.setBorder(mBorderWidth, mBorderColor);
                                break;
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWaveHelper.cancel();

        mWaveHelper2.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWaveHelper.start();

        mWaveHelper2 .start();
    }
}
