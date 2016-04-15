package gorden.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import gorden.widget.PolygonChart;
import gorden.widget.R;

/**
 * Created by gorden on 2016/4/15.
 */
public class ToggleButtonActivity extends Activity{
    private PolygonChart polay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_togglebutton);
        polay= (PolygonChart) findViewById(R.id.polay);
    }

    public void bindData(View view){
        polay.setChartData(new float[]{95,75,86,48,91});
    }
    public void bindData1(View view){
        polay.setChartData(new float[]{50,50,50,50,50});
    }
    public void bindData2(View view){
        polay.setChartData(new float[]{10,9,30,20,15});
    }
    public void bindData3(View view){
        polay.setPointExplain(new String[]{"人脉关系","成交率","信誉度","好评率","行为"});
    }
//
}
