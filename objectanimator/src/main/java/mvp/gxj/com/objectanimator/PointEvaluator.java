package mvp.gxj.com.objectanimator;

import android.animation.TypeEvaluator;

/**
 * Created by gxj on 2016/5/13.
 */
public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x =  startPoint.getX() + fraction * (endPoint.getX() - startPoint.getY());
        float y =  startPoint.getY() + fraction * (endPoint.getX() - startPoint.getY());
        Point point = new Point(x, y);
        return point;
    }

}