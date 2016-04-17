package gorden.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gorden.widget.Effectstype;
import gorden.widget.NiftyDialog;
import gorden.widget.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void NiftyDialog(View view){
        startActivity(new Intent(this,NiftyDialogActivity.class));
    }
    public void ToggleButton(View view){
        startActivity(new Intent(this,ToggleButtonActivity.class));
    }
    public void PolyGonChart(View view){
        startActivity(new Intent(this,PolyGonChartActivity.class));
    }
    public void DragIndicator(View view){
        startActivity(new Intent(this,DragIndicatorActivity.class));
    }

}
