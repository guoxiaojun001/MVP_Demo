package mvp.gxj.com.objectanimator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CustomAnimView mv;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        float screenHeight = outMetrics.heightPixels;

        mv = (CustomAnimView) findViewById(R.id.mv);

        Point point1 = new Point(0, 0);
        Point point2 = new Point(screenWidth, screenHeight);
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), point1, point2);
        anim.setDuration(1000);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("TAG", "onAnimationStart" );
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("TAG", "onAnimationEnd" );
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d("TAG", "onAnimationCancel" );
            }
        });

        anim.start();


        mv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"qqq",Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);

            }
        });
    }

}
