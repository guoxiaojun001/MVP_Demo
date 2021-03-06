package mvp.gxj.com.circleview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import mvp.gxj.com.circleview.DensityUtil;
import mvp.gxj.com.circleview.R;

/**
 * @category View必须是正方形
 *
 * 可以自定义大小，可以选择是否有波浪
 * 
 */
public class CustomWaterWaveView extends View {

	private Context mContext;

	private int mScreenWidth;
	private int mScreenHeight;

	private Paint mRingPaint;
	private Paint mCirclePaint;
	private Paint mWavePaint;
	private Paint linePaint;
	private Paint flowPaint;
	private Paint leftPaint;

	private int mRingSTROKEWidth = (int) DensityUtil.dp2px(getResources(),10);;
	private int mCircleSTROKEWidth = 2;
	private int mLineSTROKEWidth = 1;

	private int mCircleColor = Color.WHITE;
	private int mRingColor = Color.WHITE;
	private int mWaveColor = Color.WHITE;

	private Handler mHandler;
	private long c = 0L;
	private boolean mStarted = false;
	private final float f = 0.033F;
	private int mAlpha = 50;// 透明度
	private float mAmplitude = 10.0F; // 振幅
	private float mWaterLevel = 0.5F;// 水高(0~1)
	private Path mPath;

	//绘制文字显示在圆形中间，只是我没有设置，我觉得写在布局上也挺好的
	private String flowNum = "";
	private String flowLeft = "还剩余";



	//************************
	private float mDefaultAmplitude;
	private float mDefaultWaterLevel;
	private float mDefaultWaveLength;
	private double mDefaultAngularFrequency;

	private float mAmplitudeRatio = DEFAULT_AMPLITUDE_RATIO;
	private float mWaveLengthRatio = DEFAULT_WAVE_LENGTH_RATIO;
	private float mWaterLevelRatio = DEFAULT_WATER_LEVEL_RATIO;
	private float mWaveShiftRatio = DEFAULT_WAVE_SHIFT_RATIO;

	private int mBehindWaveColor = DEFAULT_BEHIND_WAVE_COLOR;
	private int mFrontWaveColor = DEFAULT_FRONT_WAVE_COLOR;

	private static final float DEFAULT_AMPLITUDE_RATIO = 0.05f;
	private static final float DEFAULT_WAVE_LENGTH_RATIO = 1.0f;
	private static final float DEFAULT_WATER_LEVEL_RATIO = 0.5f;
	private static final float DEFAULT_WAVE_SHIFT_RATIO = 0.0f;

	public static final int DEFAULT_BEHIND_WAVE_COLOR = Color.parseColor("#28FFFFFF");
	public static final int DEFAULT_FRONT_WAVE_COLOR = Color.parseColor("#3CFFFFFF");
	public static final ShapeType DEFAULT_WAVE_SHAPE = ShapeType.CIRCLE;
	public enum ShapeType {
		CIRCLE,
		SQUARE
	}

	private BitmapShader mWaveShader;
	//************************

	/**
	 * @param context
	 */
	public CustomWaterWaveView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		init(mContext);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CustomWaterWaveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		init(mContext);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public CustomWaterWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		mContext = context;
		init(mContext);
	}

	public void setmWaterLevel(float mWaterLevel) {
		this.mWaterLevel = mWaterLevel;
	}

	private void init(Context context) {
		mRingPaint = new Paint();
		mRingPaint.setColor(mRingColor);
		mRingPaint.setAlpha(50);
		mRingPaint.setStyle(Paint.Style.STROKE);
		mRingPaint.setAntiAlias(true);
		mRingPaint.setStrokeWidth(mRingSTROKEWidth);

		mCirclePaint = new Paint();
		mCirclePaint.setColor(mCircleColor);
		mCirclePaint.setStyle(Paint.Style.STROKE);
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setStrokeWidth(mCircleSTROKEWidth);

		linePaint = new Paint();
		linePaint.setColor(mCircleColor);
		linePaint.setStyle(Paint.Style.STROKE);
		linePaint.setAntiAlias(true);
		linePaint.setStrokeWidth(mLineSTROKEWidth);

		flowPaint = new Paint();
		flowPaint.setColor(mCircleColor);
		flowPaint.setStyle(Paint.Style.FILL);
		flowPaint.setAntiAlias(true);
		flowPaint.setTextSize(36);

		leftPaint = new Paint();
		leftPaint.setColor(mCircleColor);
		leftPaint.setStyle(Paint.Style.FILL);
		leftPaint.setAntiAlias(true);
		leftPaint.setTextSize(36);

		mWavePaint = new Paint();
		mWavePaint.setStrokeWidth(1.0F);
		mWavePaint.setColor(mWaveColor);
		mWavePaint.setAlpha(mAlpha);
		mPath = new Path();

		mHandler = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
				if (msg.what == 0) {
					if(mWaterLevel >= 1){//如果达到百分百，则停止绘画，提高效率
						return;
					}
					invalidate();
					if (mStarted) {
						// 不断发消息给自己，使自己不断被重绘
						mHandler.sendEmptyMessageDelayed(0, 10L);
					}
				}
			}
		};
	}


	/**
	 * @category 测量
	 * @param measureSpec
	 * @param isWidth
	 * @return
	 */
	private int measure(int measureSpec, boolean isWidth) {
		int result;
		int mode = MeasureSpec.getMode(measureSpec);
		int size = MeasureSpec.getSize(measureSpec);
		int padding = isWidth ? getPaddingLeft() + getPaddingRight()
				: getPaddingTop() + getPaddingBottom();
		if (mode == MeasureSpec.EXACTLY) {
			result = size;
		} else {
			result = isWidth ? getSuggestedMinimumWidth()
					: getSuggestedMinimumHeight();
			result += padding;
			if (mode == MeasureSpec.AT_MOST) {
				if (isWidth) {
					result = Math.max(result, size);
				} else {
					result = Math.min(result, size);
				}
			}
		}
		return result;
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		createShader();
	}

	private void createShader() {
		mDefaultAngularFrequency = 2.0f * Math.PI / DEFAULT_WAVE_LENGTH_RATIO / getWidth();
		mDefaultAmplitude = getHeight() * DEFAULT_AMPLITUDE_RATIO;
		mDefaultWaterLevel = getHeight() * DEFAULT_WATER_LEVEL_RATIO;
		mDefaultWaveLength = getWidth();

		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);

		Paint wavePaint = new Paint();
		wavePaint.setStrokeWidth(2);
		wavePaint.setAntiAlias(true);

		// Draw default waves into the bitmap
		// y=Asin(ωx+φ)+h
		final int endX = getWidth() + 1;
		final int endY = getHeight() + 1;

		float[] waveY = new float[endX];

		wavePaint.setColor(mBehindWaveColor);
		for (int beginX = 0; beginX < endX; beginX++) {
			double wx = beginX * mDefaultAngularFrequency;
			float beginY = (float) (mDefaultWaterLevel + mDefaultAmplitude * Math.sin(wx));
			canvas.drawLine(beginX, beginY, beginX, endY, wavePaint);

			waveY[beginX] = beginY;
		}

		wavePaint.setColor(mFrontWaveColor);
		final int wave2Shift = (int) (mDefaultWaveLength / 4);
		for (int beginX = 0; beginX < endX; beginX++) {
			canvas.drawLine(beginX, waveY[(beginX + wave2Shift) % endX], beginX, endY, wavePaint);
		}

		// use the bitamp to create the shader
		mWaveShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
		mWavePaint.setShader(mWaveShader);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Log.d("AAAA",">>>>>>CustomWaterWaveView>>>>onDraw>>>");
		// 得到控件的宽高

		mScreenWidth = getWidth();
		mScreenHeight = getHeight();

		int width = getWidth();
		int height = getHeight();
		setBackgroundColor(mContext.getResources().getColor(R.color.main_bg));
		// 计算当前油量线和水平中线的距离
		float centerOffset = Math.abs(mScreenWidth / 2 * mWaterLevel
				- mScreenWidth / 4);
		// 计算油量线和与水平中线的角度
		float horiAngle = (float) (Math.asin(centerOffset / (mScreenWidth / 4)) * 180 / Math.PI);
		// 扇形的起始角度和扫过角度
		float startAngle, sweepAngle;
		if (mWaterLevel > 0.5F) {
			startAngle = 360F - horiAngle;
			sweepAngle = 180F + 2 * horiAngle;
		} else {
			startAngle = horiAngle;
			sweepAngle = 180F - 2 * horiAngle;
		}

//		canvas.drawLine(mScreenWidth * 3 / 8, mScreenHeight * 5 / 8,
//				mScreenWidth * 5 / 8, mScreenHeight * 5 / 8, linePaint);
		
		float num = flowPaint.measureText(flowNum);
		canvas.drawText(flowNum, mScreenWidth * 4 / 8 - num / 2,
				mScreenHeight * 4 / 8, flowPaint);
		float left = leftPaint.measureText(flowLeft);
		canvas.drawText(flowLeft, mScreenWidth * 4 / 8 - left / 2,
				mScreenHeight * 3 / 8, leftPaint);

		// 如果未开始（未调用startWave方法）,绘制一个扇形
		if ((!mStarted) || (mScreenWidth == 0) || (mScreenHeight == 0)) {
			// 绘制,即水面静止时的高度
			RectF oval = new RectF(mScreenWidth / 4, mScreenHeight / 4,
					mScreenWidth * 3 / 4, mScreenHeight * 3 / 4);
			canvas.drawArc(oval, startAngle, sweepAngle, false, mWavePaint);
			return;
		}
		// 绘制,即水面静止时的高度
		// 绘制,即水面静止时的高度
		RectF oval = new RectF(mRingSTROKEWidth / 2, mRingSTROKEWidth / 2,
				mScreenWidth - mRingSTROKEWidth / 2, mScreenHeight - mRingSTROKEWidth / 2);
		canvas.drawArc(oval, startAngle, sweepAngle, false, mWavePaint);

		if (this.c >= 8388607L) {
			this.c = 0L;
		}
		// 每次onDraw时c都会自增
		c = (1L + c);
		float f1 = mScreenHeight * (1.0F - (0.25F + mWaterLevel / 2))
				- mAmplitude;
		// 当前油量线的长度
		float waveWidth = (float) Math.sqrt(mScreenWidth * mScreenWidth / 16
				- centerOffset * centerOffset);
		// 与圆半径的偏移量
		float offsetWidth = mScreenWidth / 4 - waveWidth;

		int top = (int) (f1 + mAmplitude);
		mPath.reset();
		// 起始振动X坐标，结束振动X坐标
		int startX, endX;
		if (mWaterLevel > 0.50F) {
			startX = (int) (mScreenWidth / 4 + offsetWidth);
			endX = (int) (mScreenWidth / 2 + mScreenWidth / 4 - offsetWidth);
		} else {
			startX = (int) (mScreenWidth / 4 + offsetWidth - mAmplitude);
			endX = (int) (mScreenWidth / 2 + mScreenWidth / 4 - offsetWidth + mAmplitude);
		}
		// 波浪效果
//		while (startX < endX) {
//			int startY = (int) (f1 - mAmplitude
//					* Math.sin(Math.PI
//							* (2.0F * (startX + this.c * width * this.f))
//							/ width));
//			canvas.drawLine(startX, startY, startX, top, mWavePaint);
//			startX++;
//		}

		canvas.drawCircle(mScreenWidth / 2, mScreenHeight / 2, mScreenWidth / 2
				- mRingSTROKEWidth / 2, mRingPaint);

		canvas.drawCircle(mScreenWidth / 2, mScreenHeight / 2,
				mScreenWidth / 2, mCirclePaint);
		canvas.restore();
	}

	@Override
	public Parcelable onSaveInstanceState() {
		// Force our ancestor class to save its state
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);
		ss.progress = (int) c;
		return ss;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());
		c = ss.progress;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		// 关闭硬件加速，部分手机 会出现异常 防止异常unsupported operation exception
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

	/**
	 * @category 开始波动
	 */
	public void startWave() {
		if (!mStarted) {
			this.c = 0L;
			mStarted = true;
			this.mHandler.sendEmptyMessage(0);
		}
	}

	/**
	 * @category 停止波动
	 */
	public void stopWave() {
		if (mStarted) {
			this.c = 0L;
			mStarted = false;
			this.mHandler.removeMessages(0);
		}
	}

	/**
	 * @category 保存状态
	 */
	static class SavedState extends BaseSavedState {
		int progress;

		/**
		 * Constructor called from {@link ProgressBar#onSaveInstanceState()}
		 */
		SavedState(Parcelable superState) {
			super(superState);
		}

		/**
		 * Constructor called from {@link #CREATOR}
		 */
		private SavedState(Parcel in) {
			super(in);
			progress = in.readInt();
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeInt(progress);
		}

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

}
