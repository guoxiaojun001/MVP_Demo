package mvp.gxj.com.viewpager_animaltor;

import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager vp=(ViewPager) findViewById(R.id.vp);
        //给viewpager设置一个PagerTransformer
        vp.setPageTransformer(true, new ViewPager.PageTransformer() {
            /**
             * 页面滑动时回调的方法,
             * @param page当前滑动的view
             * @param position 当从右向左滑的时候,左边page的position是[0一-1]变化的
             * 右边page的position是[1一0]变化的,再次滑动的时候,刚才变化到-1(即已经画出视野的page)將从-1变化到-2,
             * 而当前可见的page和右边滑过来的page的position将再次从[0一-1]变化 和 [1一0]变化   但是我们关心是position是[-1一1]变化的
             * page,所以处理动画的时候需要我们过滤一下
             */
            @Override
            public void transformPage(View page, float position) {
//				sink3D(page,position);//凹陷的3D效果
//				raised3D(page,position);//凸起的3D效果
//				seightDis(page,position);//视差的效果
//				imitateQQ(page,position);//仿QQ的缩放动画效果
                rollingPage(page,position);//仿掌阅的翻书动画效果
            }
        });
        vp.setAdapter(new MyAdapter(getSupportFragmentManager()));

    }

    /**
     * 动画效果1  凹陷的3D效果
     */
    public void sink3D(View view,float position){
        if(position>=-1&&position<=1){
            view.setPivotX(position<0?view.getWidth():0);
            view.setRotationY(-90*position);
        }
    }
    /**
     * 动画效果2  凸起的3D效果
     */
    public void raised3D(View view,float position){
        if(position>=-1&&position<=1){
            view.setPivotX(position<0?view.getWidth():0);//设置要旋转的Y轴的位置
            view.setRotationY(90*position);//开始设置属性动画值
        }
    }
    /**
     * 动画效果3  视差的效果
     * 这个地方要注意此处的view不是单纯的手指滑动的那个RelativeLayout
     * 因为对于FragmentpagerAdapter,使用时默认会在fragment布局的最外层套上一层
     * FrameLayout,所以你要是使用view.getChildAt()得到的是一个外层的FrameLayout
     * 而不是我们要进行视差动画的ImageView,所以就是用findViewById()来拿到跟布局RelativeLayout
     * 然后再调用getChildAt()方法来得到所有的ImageView
     */
    public void seightDis(View view,float position){
        if(position>=-1&&position<=1){
            ViewGroup vg = (ViewGroup) view.findViewById(R.id.rl);
            for(int i=0;i<vg.getChildCount();i++){
                View child=vg.getChildAt(i);
                child.setTranslationX(Math.abs(position)*child.getWidth()*2);
            }
        }
    }
    /**
     * 动画效果4  仿QQ的缩放动画效果
     */
    public void imitateQQ(View view,float position){
        if(position>=-1&&position<=1){
            view.setPivotX(position>0?0:view.getWidth()/2);
            //view.setPivotY(view.getHeight()/2);
            view.setScaleX((float)((1-Math.abs(position)<0.5)?0.5:(1-Math.abs(position))));
            view.setScaleY((float)((1-Math.abs(position)<0.5)?0.5:(1-Math.abs(position))));
        }
    }
    /**
     * 动画效果5  仿掌阅的翻书动画效果
     * 分析翻书的效果,可以分解为两部分:1.左边的view绕着左边的轴旋转,同时x方向上有缩放的效果
     * 要注意的是因为是viewpager左边的view在滑动的时候是要向左边移动的,但我们要的翻书效果在翻页完成前
     * 是一直在读者视角内的,所以左边的view在滑动的时候要进行向右的平移
     * 2.右边的view从可见的时候开始就一直在左view的下方,但是作为viewpager他是从右边慢慢滑到当前的位置的
     * 所以要达到这个效果就需要进行一个x方向的平移动画
     */
    public void rollingPage(View view,float position){
        if(position>=-1&&position<=1){
            view.setPivotX(0);
            if(position<0){
                view.setTranslationX(-position*view.getWidth());
                view.setRotationY(90*position);
                view.setScaleX(1-Math.abs(position));
            }
            else{
                view.setTranslationX(-position*view.getWidth());
            }

        }
    }

    public static final int[] lays={
            R.layout.fragment_01,
            R.layout.fragment_02,
            R.layout.fragment_03
    };
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            Fragment fg=new Fragment();
            Bundle bundle=new Bundle();
            bundle.putInt("layout_id", lays[position%3]);
            fg.setArguments(bundle);
            return fg;
        }

        @Override
        public int getCount() {
            return 6;
        }

    }
}
