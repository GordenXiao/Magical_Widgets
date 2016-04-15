package gorden.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import gorden.widget.PolygonChart;
import gorden.widget.R;

/**
 * Created by Gorden on 2016/4/15.
 */
public class PolyGonChartActivity extends Activity{
    private PolygonChart polay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygonchart);
        polay= (PolygonChart) findViewById(R.id.polay);
    }

    public void refreshChart(View view){
        polay.setChartData(new float[]{77,98,90,83,95});
    }
    public void addExplain(View view){
        polay.setPointExplain(new String[]{"人脉关系","成交率","信誉度","好评率","行为"});
    }
    public void rotateChart(View view){
        polay.setChartDataAnimation(new float[]{77,98,90,83,95});
    }
    public void rotateChart1(View view){
        polay.setChartDataAnimation(new float[]{20,30,15,45,26});
    }
}