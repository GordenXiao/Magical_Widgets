package gorden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorden on 2016/4/15.
 */
public class PolygonChart extends View{
    private final int DEFAULT_SIDENUM=5;
    private final int DEFAULT_MAXVALUE=100;
    private final int DEFAULT_BORDERCOLOR=0xFF30ce60;
    private final int DEFAULT_BORDERSIZE=2;
    private final int DEFAULT_FILLCOLOR=0x5530ce60;
    private final int DEFAULT_TEXTCOLOR=0xFF000000;


    private int sideNum;
    private int maxValue;
    private int borderColor;
    private int textColor;
    private float borderSize;
    private int fillColor;
    private float textSize=22;
    private float textPadding=10;
    private String[] pointExplain=new String[]{"人脉关系","成交率","信誉度","好评率","行为"};


    private int viewWidth,viewHeight;
    private Paint borderPaint;
    private Paint fillPaint;
    private Paint textPaint;

    private float[] chartData=new float[]{50,50,50,50,50};

    public PolygonChart(Context context) {
        this(context,null);
    }

    public PolygonChart(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PolygonChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.PolygonChart);
        sideNum=DEFAULT_SIDENUM;
        textPadding=array.getDimension(R.styleable.PolygonChart_textPadding,10);
        maxValue=array.getInt(R.styleable.PolygonChart_max_value,DEFAULT_MAXVALUE);
        borderColor=array.getColor(R.styleable.PolygonChart_border_color,DEFAULT_BORDERCOLOR);
        borderSize=array.getDimension(R.styleable.PolygonChart_border_size,DEFAULT_BORDERSIZE);
        textSize=array.getDimension(R.styleable.PolygonChart_text_size,22);
        fillColor=array.getColor(R.styleable.PolygonChart_fill_color,DEFAULT_FILLCOLOR);
        textColor=array.getColor(R.styleable.PolygonChart_text_color,DEFAULT_TEXTCOLOR);
        init();
    }

    private void init() {
        borderPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderSize);
        borderPaint.setStyle(Paint.Style.STROKE);

        fillPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(fillColor);

        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);;
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
    }

    /**
     * 设置数值
     * @param charts
     */
    public void setChartData(float[] charts){
        if(charts==null||charts.length!=5){
            Log.d("PolygonChart","setChartData()数值为空，或数值不是5位");
        }else{
            chartData=charts;
            invalidate();
        }
    }

    /**
     * 设置每个点的描述
     * @param explains
     */
    public void setPointExplain(String[] explains){
        if(explains==null||explains.length!=5){
            Log.d("PolygonChart","setPointExplain() 描述为空，或描述不为5位");
        }else{
            pointExplain=explains;
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        viewWidth=getMeasuredSize(widthMeasureSpec,true);
        viewHeight=getMeasuredSize(heightMeasureSpec,false);
        viewWidth=Math.max(viewWidth,viewHeight);
        setMeasuredDimension(viewWidth,viewHeight);
        Log.e("XXXXXXXXXXXX","onMeasure:"+viewWidth+" : "+viewHeight);
    }

    /**
     * 计算控件的实际大小
     * @param length onMeasure方法的参数，widthMeasureSpec或者heightMeasureSpec
     * @param isWidth 是宽度还是高度
     * @return int 计算后的实际大小
     */
    private int getMeasuredSize(int length, boolean isWidth){
        int specMode = MeasureSpec.getMode(length);
        int specSize = MeasureSpec.getSize(length);
        // 计算所得的实际尺寸，要被返回
        int retSize = 0;
        // 对不同的指定模式进行判断
        if(specMode==MeasureSpec.EXACTLY){  // 显式指定大小，如40dp或fill_parent
            retSize = specSize+2*calculatePadding(isWidth);
        }else{                              // 如使用wrap_content
            retSize = isWidth? 200+2*calculatePadding(true) : 200 + 2*calculatePadding(false);
            if(specMode==MeasureSpec.UNSPECIFIED){
                retSize = Math.min(retSize, specSize);
            }
        }
        return retSize;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawChart(canvas);
        if(pointExplain!=null){
            drawExplain(canvas);
        }
    }

    /**
     * 添加描述词
     * @param canvas
     */
    private void drawExplain(Canvas canvas) {
        List<PointF> pointfList=calculatePoints();
        canvas.drawText(pointExplain[0],pointfList.get(0).x-textPaint.measureText(pointExplain[0])/2,pointfList.get(0).y-textPadding,textPaint);
        canvas.drawText(pointExplain[1],pointfList.get(1).x+textPadding,pointfList.get(1).y,textPaint);
        canvas.drawText(pointExplain[2],pointfList.get(2).x-textPaint.measureText(pointExplain[2])/2,pointfList.get(2).y+textSize+textPadding,textPaint);
        canvas.drawText(pointExplain[3],pointfList.get(3).x-textPaint.measureText(pointExplain[3])/2,pointfList.get(3).y+textPadding+textSize,textPaint);
        canvas.drawText(pointExplain[4],pointfList.get(4).x-textPaint.measureText(pointExplain[4])-textPadding,pointfList.get(4).y,textPaint);
    }

    /**
     * 填充数据
     * @param canvas
     */
    private void drawChart(Canvas canvas) {
        List<PointF> pointfList=calculatePoints();
        Path path=new Path();
        float multiple=chartData[0]/maxValue;
        path.moveTo(pointfList.get(pointfList.size()-1).x+(pointfList.get(0).x-pointfList.get(pointfList.size()-1).x)*multiple,pointfList.get(pointfList.size()-1).y-(pointfList.get(pointfList.size()-1).y-pointfList.get(0).y)*multiple);
        for(int i=1;i<pointfList.size()-1;i++){
            multiple=chartData[i]/maxValue;
            float dx=pointfList.get(pointfList.size()-1).x+(pointfList.get(i).x-pointfList.get(pointfList.size()-1).x)*multiple;
            float dy=pointfList.get(pointfList.size()-1).y-(pointfList.get(pointfList.size()-1).y-pointfList.get(i).y)*multiple;
            path.lineTo(dx,dy);
        }
        path.close();
        canvas.drawPath(path,fillPaint);
    }

    /**
     * 画正多边形
     * @param canvas
     */
    private void drawPolygon(Canvas canvas) {
        List<PointF> pointfList=calculatePoints();
        Path path=new Path();
        path.moveTo(pointfList.get(0).x,pointfList.get(0).y);
        canvas.drawLine(pointfList.get(pointfList.size()-1).x,pointfList.get(pointfList.size()-1).y,pointfList.get(0).x,pointfList.get(0).y,borderPaint);
        for(int i=1;i<pointfList.size()-1;i++){
            path.lineTo(pointfList.get(i).x,pointfList.get(i).y);
            canvas.drawLine(pointfList.get(pointfList.size()-1).x,pointfList.get(pointfList.size()-1).y,pointfList.get(i).x,pointfList.get(i).y,borderPaint);
        }
        path.close();
        canvas.drawPath(path,borderPaint);
    }
    private List<PointF> calculatePoints(){
        double angle=360/sideNum;
        float radio=viewHeight/2-calculatePadding(false);
        float polygonWidth= (float) (2*Math.sin(angle*Math.PI/180)*radio);
        float polygonHeight= (float) (viewHeight/2-calculatePadding(false)+Math.cos(angle/2*Math.PI/180)*radio);

        float offsetX=(viewWidth-polygonWidth)/2;
        float offsetY=(viewHeight-polygonHeight)/2;
        Log.e("XXXXXX","offsetX    "+offsetX+" : "+offsetY+"     "+polygonWidth+" : "+polygonHeight);
        List<PointF> points=new ArrayList<PointF>();
        points.add(new PointF(viewWidth/2,offsetY));
        points.add(new PointF(polygonWidth+offsetX, (float) (offsetY+radio-Math.cos(angle*Math.PI/180)*radio)));

        points.add(new PointF(viewWidth/2+(float) (Math.sin(angle/2*Math.PI/180)*radio),polygonHeight+offsetY));
        points.add(new PointF(viewWidth/2-(float) (Math.sin(angle/2*Math.PI/180)*radio),polygonHeight+offsetY));
        points.add(new PointF(offsetX, (float) (offsetY+radio-Math.cos(angle*Math.PI/180)*radio)));
        points.add(new PointF(viewWidth/2,radio+offsetY));
        return points;
    }

    /**
     * 计算描述需要溜边
     * @return
     */
    private int calculatePadding(boolean isWidth){
        int padding=0;
        if(pointExplain==null)return padding;
        if(isWidth){
            for(String ss:pointExplain){
                padding= Math.max((int) textPaint.measureText(ss),padding);
            }
            padding+=textPadding;
        }else{
            padding= (int) (textSize+textPadding);
        }
        return padding;
    }
}
