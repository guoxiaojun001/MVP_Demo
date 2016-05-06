package mvp.gxj.com.circleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by gxj on 2016/5/5.
 */
public class CircleView extends View{

    private int mFirstColor;//第一圈的颜色
    private int mSecondColor;//第二圈的颜色
    private int mCircleWidth;//圈的宽度
    private Paint mPaint;

    private int centerTextColor;//中心文字颜色
    private float centerTextSize;//中心文字大小
    private String centerTextContent;

    private int bottomTextColor;//底部文字颜色
    private float bottomTextSize;//底部文字大小
    private String bottomTextContent;

    private int mProgress;//当前进度
    private int mSpeed;//速度
    private boolean isNext = false;//是否应该开始下一个

    protected Paint textPaint;

    public String getCenterTextContent() {
        return centerTextContent;
    }

    public void setCenterTextContent(String centerTextContent) {
        this.centerTextContent = centerTextContent;
        invalidate();
    }

    public String getBottomTextContent() {
        return bottomTextContent;
    }

    public void setBottomTextContent(String bottomTextContent) {
        this.bottomTextContent = bottomTextContent;
        invalidate();
    }

    public int getmFirstColor() {
        return mFirstColor;
    }

    public void setmFirstColor(int mFirstColor) {
        this.mFirstColor = mFirstColor;
        invalidate();
    }

    public int getmSecondColor() {
        return mSecondColor;
    }

    public void setmSecondColor(int mSecondColor) {
        this.mSecondColor = mSecondColor;
        invalidate();
    }

    public int getmCircleWidth() {
        return mCircleWidth;
    }

    public void setmCircleWidth(int mCircleWidth) {
        this.mCircleWidth = mCircleWidth;
        invalidate();
    }

    public int getCenterTextColor() {
        return centerTextColor;
    }

    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
        invalidate();
    }

    public float getCenterTextSize() {
        return centerTextSize;
    }

    public void setCenterTextSize(float centerTextSize) {
        this.centerTextSize = centerTextSize;
    }

    public int getBottomTextColor() {
        return bottomTextColor;
    }

    public void setBottomTextColor(int bottomTextColor) {
        this.bottomTextColor = bottomTextColor;
        invalidate();
    }

    public float getBottomTextSize() {
        return bottomTextSize;
    }

    public void setBottomTextSize(float bottomTextSize) {
        this.bottomTextSize = bottomTextSize;
        invalidate();
    }

    public int getmProgress() {
        return mProgress;
    }

    public void setmProgress(int mProgress) {
        this.mProgress = mProgress;
        invalidate();
    }

    public int getmSpeed() {
        return mSpeed;
    }

    public void setmSpeed(int mSpeed) {
        this.mSpeed = mSpeed;
    }

    public Paint getmPaint() {
        return mPaint;
    }

    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }

    public Paint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //TypedArray typedArray0 = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CircleView,defStyle,0);
        TypedArray typedArray = context.obtainStyledAttributes
                (attrs,R.styleable.CircleView,defStyle,0);

        int length = typedArray.getIndexCount();
        for (int i = 0; i < length;i++){// 获取属性的方法1： 遍历属性，找到对应的赋值
            int attr = typedArray.getIndex(i);

            switch (attr){
                case R.styleable.CircleView_firstColor:
                    mFirstColor = typedArray.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.CircleView_secondColor:
                    mSecondColor = typedArray.getColor(attr,Color.RED);
                    break;
                case R.styleable.CircleView_circleWidth:
                    mCircleWidth = typedArray.getDimensionPixelOffset(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20,
                                    getResources().getDisplayMetrics()));
                    break;

                case R.styleable.CircleView_speed:
                    mSpeed = typedArray.getInt(attr,20);
                    break;
            }
        }

        //获取属性的方法2：直接找到属性 赋值
        centerTextColor = typedArray.getColor(R.styleable.CircleView_centerTextColor, Color.BLUE);
        centerTextSize = typedArray.getDimension(R.styleable.CircleView_centerTextSize,12);
        centerTextContent = typedArray.getString(R.styleable.CircleView_centerTextContent);

        bottomTextColor = typedArray.getColor(R.styleable.CircleView_centerTextColor, Color.BLUE);
        bottomTextSize = typedArray.getDimension(R.styleable.CircleView_bottomTextSize,12);
        bottomTextContent = typedArray.getString(R.styleable.CircleView_bottomTextContent);

        typedArray.recycle();

        mPaint = new Paint();
        textPaint = new TextPaint();


//        new Thread(){
//            public void run() {
//                while(true){
//                    mProgress++;
//                    if (mProgress > 300) {
//                        //mProgress = 0;
//                        if (!isNext)
//                            isNext = true;
//                        else
//                            isNext = false;
//                        return;
//                    }
//
//                    postInvalidate();//非ui线程中更新ui
//
//                    try {
//                        sleep(mSpeed);
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        }.start();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = centre - mCircleWidth / 2;// 半径

        mPaint.setStrokeWidth(mCircleWidth);//设置圆环的宽度
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);//空心
        mPaint.setStrokeCap(Paint.Cap.ROUND);//边缘圆弧

        textPaint.setColor(bottomTextColor);
        textPaint.setTextSize(bottomTextSize);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);//设置文字居中显示，否则是第一个字符居中

        RectF oval = new RectF(
                centre - radius,
                centre - radius,
                centre + radius,
                centre + radius);


//        if (!isNext) {// 第一颜色的圈完整，第二颜色跑
            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
            mPaint.setColor(mSecondColor); // 设置圆环的颜色
            canvas.drawArc(oval, 120, mProgress , false, mPaint); // 根据进度画圆弧

            canvas.drawText(
                    centerTextContent,
                    centre,
                    centre*2 - DensityUtil.dp2px(getResources(),5), textPaint);

            textPaint.setColor(centerTextColor);
            textPaint.setTextSize(centerTextSize);
            canvas.drawText(
                    mProgress/3 + "%",
                    centre,
                    centre, textPaint);
//        } else {
//            mPaint.setColor(mSecondColor); // 设置圆环的颜色
//            canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
//            mPaint.setColor(mFirstColor); // 设置圆环的颜色
//            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
//        }

    }


    @Override
    protected void onLayout(boolean changed, int left, int top,
                            int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
