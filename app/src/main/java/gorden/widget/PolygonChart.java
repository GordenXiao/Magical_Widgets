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
import android.view.animation.Animation;

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
    private final float DEFAULT_CHARTDATA=50;


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

    private float[] chartData;

    public PolygonChart(Context context) {
        this(context,null);
    }

    public PolygonChart(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PolygonChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.PolygonChart);
        sideNum=array.getInt(R.styleable.PolygonChart_side_num,DEFAULT_SIDENUM);
        chartData=new float[sideNum];
        for(int i=0;i<sideNum;i++){
            chartData[i]=DEFAULT_CHARTDATA;
        }
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
        if(charts==null||charts.length!=sideNum){
            Log.d("PolygonChart","setChartData()数值为空，或数值位数不正确");
        }else{
            chartData=charts;
            invalidate();
        }
    }
    public void setChartDataAnimation(float[] charts){
        postDelayed(new ChartDataRunnable(charts),10);
    }

    private class ChartDataRunnable implements Runnable{
        float[] charts;
        boolean complete=false;


        public ChartDataRunnable(float[] charts) {
            this.charts = charts;
        }

        @Override
        public void run() {
            for(int i=0;i<sideNum;i++){
                if(chartData[i]<charts[i])
                    chartData[i]+=1;
                if(chartData[i]>charts[i])
                    chartData[i]-=1;
            }
            invalidate();
            if(chartData[0]==charts[0]&&chartData[1]==charts[1]&&chartData[2]==charts[2]&&chartData[3]==charts[3]&&chartData[4]==charts[4]){
                complete=true;
            }
            if(!complete){
                postDelayed(this,10);
            }
        }
    }

    /**
     * 设置每个点的描述
     * @param explains
     */
    public void setPointExplain(String[] explains){
        if(explains==null||explains.length!=sideNum){
            Log.d("PolygonChart","setPointExplain() 描述为空，或描述数量不对");
        }else{
            pointExplain=explains;
            invalidate();
        }
    }
    public void rotateChart(float rotate){
        animate().rotation(rotate).start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        viewWidth=getMeasuredSize(widthMeasureSpec,true);
        viewHeight=getMeasuredSize(heightMeasureSpec,false);
        setMeasuredDimension(viewWidth,viewHeight);
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
            retSize = specSize;
            if(retSize<2*calculatePadding(isWidth)){
                retSize = (int) (specSize+2*calculatePadding(isWidth));
            }
        }else{                              // 如使用wrap_content
            retSize = (int) (isWidth? 200+2*calculatePadding(true) : 200 + 2*calculatePadding(false));
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
        PointF circle=pointfList.get(pointfList.size()-1);
        for(int i=0;i<pointfList.size()-1;i++){
            PointF tmpPoint=pointfList.get(i);
            float drawX=tmpPoint.x;
            float drawY=tmpPoint.y;
            if(tmpPoint.x<circle.x){
                drawX=tmpPoint.x-textPaint.measureText(pointExplain[i])-textPadding;
            }
            if(tmpPoint.x>circle.x){
                drawX=tmpPoint.x+textPadding;
            }
            if(Math.abs(tmpPoint.x-circle.x)<5){
                drawX=tmpPoint.x-textPaint.measureText(pointExplain[i])/2;
            }
            if(tmpPoint.y<circle.y){
                drawY=tmpPoint.y-textPadding;
            }
            if(tmpPoint.y>circle.y){
                //TODO 字体高度
                drawY=tmpPoint.y+textPadding+textSize;
            }
            if(Math.abs(tmpPoint.y-circle.y)<2){
                drawY=tmpPoint.y+textSize/2;

            }
            canvas.drawText(pointExplain[i],drawX,drawY,textPaint);
        }
    }

    /**
     * 填充数据
     * @param canvas
     */
    private void drawChart(Canvas canvas) {
        List<PointF> pointfList=calculatePoints();
        Path path=new Path();
        float multiple=chartData[0]/maxValue;;
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

    /**
     * 计算多边形的顶点
     * @return
     */
    private List<PointF> calculatePoints(){
        float angle=360/sideNum;
        float radio=Math.min(viewHeight/2-calculatePadding(false),viewWidth/2-calculatePadding(true));//多边形半径
        float circleX=viewWidth/2;
        float circleY=viewHeight/2;
        float maxY=0;
        float minY=circleY;
        List<PointF> points=new ArrayList<PointF>();
        for(int i=0;i<sideNum;i++){
            float tmpAngle=angle*i;
            float tmpX= (float) (Math.sin(tmpAngle*Math.PI/180)*radio);
            float tmpY= (float) (Math.cos(tmpAngle*Math.PI/180)*radio);
            minY=Math.min(circleY-tmpY,minY);
            maxY=Math.max(circleY-tmpY,maxY);
            points.add(new PointF(tmpX+circleX,-tmpY+circleY));
        }
        points.add(new PointF(circleX,circleY));
        float offsetY=(viewHeight-maxY-minY)/2;
        for(int i=0;i<points.size();i++){
            points.set(i,new PointF(points.get(i).x,points.get(i).y+offsetY));
        }
        return points;
    }

    /**
     * 计算顶点描述需要留的padding
     * @return
     */
    private float calculatePadding(boolean isWidth){
        int padding=0;
        if(pointExplain==null)return textPadding;
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
