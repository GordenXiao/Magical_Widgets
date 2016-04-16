package gorden.widget;

import android.animation.TypeEvaluator;
import android.graphics.Point;

/**
 * Created by panyi on 16/4/5.
 *
 */
public class PointEvaluator implements TypeEvaluator<Point> {

    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        float x = startValue.x + fraction * (endValue.x - startValue.x);
        float y = startValue.y + fraction * (endValue.y - startValue.y);
        Point point = new Point((int) x, (int)y);
        return point;
    }
}//end class
