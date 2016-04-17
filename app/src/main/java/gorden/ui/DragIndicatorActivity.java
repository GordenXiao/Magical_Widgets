package gorden.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import gorden.widget.DragIndicatorView;
import gorden.widget.R;

/**
 * Created by Gorden on 2016/4/17.
 */
public class DragIndicatorActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragindicator);
        ((DragIndicatorView)findViewById(R.id.dragView)).setOnDismissAction(new DragIndicatorView.OnIndicatorDismiss() {
            @Override
            public void OnDismiss(DragIndicatorView view) {
                Toast.makeText(getApplicationContext(),"哈哈",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
