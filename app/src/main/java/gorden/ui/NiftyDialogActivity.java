package gorden.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gorden.widget.Effectstype;
import gorden.widget.NiftyDialog;
import gorden.widget.R;

/**
 * Created by Gorden on 2016/4/14.
 */
public class NiftyDialogActivity extends Activity{
    NiftyDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niftydialog);
        dialog=NiftyDialog.getInstance(this)
                .withTitle("提示")
                .withTitleColor(Color.RED)
                .withMessage("你好，只有商家才能发布信息，认真商家可对接全国人才，解决业务需求，是否去认证")
                .withButton1Text("OK")
                .withButton2Text("Cancel")
                .withIcon(R.mipmap.ic_launcher);
    }
    public void FadeIn(View view){
        dialog.withEffect(Effectstype.Fadein).show();
    };
    public void Fall(View view){
        dialog.withEffect(Effectstype.Fall).show();
    };
    public void FlipH(View view){
        dialog.withEffect(Effectstype.Fliph).show();
    };
    public void FlipV(View view){
        dialog.withEffect(Effectstype.Flipv).show();
    };
    public void NewsPaper(View view){
        dialog.withEffect(Effectstype.Newspager).show();
    };
    public void RotateBottom(View view){
        dialog.withEffect(Effectstype.RotateBottom).show();
    };
    public void RotateLeft(View view){
        dialog.withEffect(Effectstype.RotateLeft).show();
    };
    public void Shake(View view){
        dialog.withEffect(Effectstype.Shake).show();
    };
    public void SideFall(View view){
        dialog.withEffect(Effectstype.Sidefill).show();
    };
    public void SlideBottom(View view){
        dialog.withEffect(Effectstype.SlideBottom).show();
    };
    public void SlideLeft(View view){
        dialog.withEffect(Effectstype.Slideleft).show();
    };
    public void SlideRight(View view){
        dialog.withEffect(Effectstype.Slideright).show();
    };
    public void SlideTop(View view){
        dialog.withEffect(Effectstype.Slidetop).show();
    };
    public void Slit(View view){
        dialog.withEffect(Effectstype.Slit).show();
    };
}
