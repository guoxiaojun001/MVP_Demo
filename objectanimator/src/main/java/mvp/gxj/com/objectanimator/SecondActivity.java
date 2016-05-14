package mvp.gxj.com.objectanimator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by gxj on 2016/5/14.
 */
public class SecondActivity extends Activity {

    TextView textview ,text2 ,text3,text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textview = (TextView) findViewById(R.id.text);

        ObjectAnimator moveIn = ObjectAnimator.ofFloat(textview, "translationX", -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(textview, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(textview, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);//多个动画组合
        animSet.setDuration(5000);
        animSet.start();


        text2 = (TextView) findViewById(R.id.text2);
        ObjectAnimator animator = ObjectAnimator.ofFloat(text2, "scaleY", 1f, 3f, 1f);
        animator.setDuration(5000);
        //animator.start();


        text3 = (TextView) findViewById(R.id.text3);
        showAnimation(text3,true,300);

        text4 = (TextView) findViewById(R.id.text4);
        hideAnimation(text4,300);

    }

    private void showAnimation(final View view,final boolean isShow,final int height){
        /**
         * 相比于ValueAnimator，ObjectAnimator可能才是我们最常接触到的类，
         * 因为ValueAnimator只不过是对值进行了一个平滑的动画过渡，但我们实际使用到这种功能的场景好像并不多。
         * 而ObjectAnimator则就不同了，它是可以直接对任意对象的任意属性进行动画操作的，比如说View的alpha属性。
         * 不过虽说ObjectAnimator会更加常用一些，但是它其实是继承自ValueAnimator的，
         * 底层的动画实现机制也是基于ValueAnimator来完成的，因此ValueAnimator仍然是整个属性动画当
         * 中最核心的一个类。那么既然是继承关系，说明ValueAnimator中可以使用的方法在ObjectAnimator中
         * 也是可以正常使用的，它们的用法也非常类似，这里如果我们想要将一个TextView在5秒中内从常规变换成全透明，
         * 再从全透明变换成常规，就可以这样写：
         */
        ValueAnimator valueAnimator = isShow ? ValueAnimator.ofInt(0, height) :
                ValueAnimator.ofInt(height,0);

        valueAnimator.setRepeatCount(0);//重复次数

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            FloatEvaluator mEvaluator = new FloatEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                view.getLayoutParams().height = value;
                float scale = value / Float.valueOf(height);
                float alpha = mEvaluator.evaluate(scale,0,1);//透明度渐变
                view.setAlpha(alpha);
                view.requestLayout();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(isShow){
                    view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(isShow ? View.VISIBLE : View.GONE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(5000).start();
    }


    private void hideAnimation(final View view,final int height){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(height,0);

        valueAnimator.setRepeatCount(3);//重复次数

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            FloatEvaluator mEvaluator = new FloatEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                view.getLayoutParams().height = value;
                float scale = value / Float.valueOf(height);
                float alpha = mEvaluator.evaluate(scale,0,1);//透明度渐变
                view.setAlpha(alpha);
                view.requestLayout();
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(3000).start();
    }


}
