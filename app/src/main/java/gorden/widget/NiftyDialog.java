package gorden.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gorden.effects.BaseEffects;

/**
 * Created by gorden on 2016/4/14.
 */
public class NiftyDialog extends Dialog{
    private final String defTextColor="#FFFFFFFF";

    private final String defDividerColor="#11000000";

    private final String defMsgColor="#FFFFFFFF";

    private final String defDialogColor="#FFE74C3C";

    private static Context mContext;
    private static NiftyDialog instance;

    private LinearLayout mLinearLayoutView;

    private RelativeLayout mRelativeLayoutView;

    private LinearLayout mLinearLayoutMsgView;

    private LinearLayout mLinearLayoutTopView;

    private FrameLayout mFrameLayoutCustomView;

    private View mDialogView;

    private View mDivider;

    private TextView mTitle;

    private TextView mMessage;

    private ImageView mIcon;

    private Button mButton1;

    private Button mButton2;

    private int mDuration = -1;
    private Effectstype type=null;
    private boolean isCancelable=true;



    public NiftyDialog(Context context) {
        this(context,0);


    }

    public NiftyDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width  = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
    }
    public static NiftyDialog getInstance(Context context) {
        if (instance == null || !mContext.equals(context)) {
            synchronized (NiftyDialog.class) {
                if (instance == null || !mContext.equals(context)) {
                    instance = new NiftyDialog(context, R.style.dialog_tran);
                }
            }
        }
        mContext = context;
        return instance;
    }

    private void init(Context context) {
        mDialogView = View.inflate(context, R.layout.dialog_layout, null);
        mLinearLayoutView=(LinearLayout)mDialogView.findViewById(R.id.parentPanel);
        mRelativeLayoutView=(RelativeLayout)mDialogView.findViewById(R.id.main);
        mLinearLayoutTopView=(LinearLayout)mDialogView.findViewById(R.id.topPanel);
        mLinearLayoutMsgView=(LinearLayout)mDialogView.findViewById(R.id.contentPanel);
        mFrameLayoutCustomView=(FrameLayout)mDialogView.findViewById(R.id.customPanel);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mMessage = (TextView) mDialogView.findViewById(R.id.message);
        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
        mDivider = mDialogView.findViewById(R.id.titleDivider);
        mButton1=(Button)mDialogView.findViewById(R.id.button1);
        mButton2=(Button)mDialogView.findViewById(R.id.button2);

        setContentView(mDialogView);
        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                mLinearLayoutView.setVisibility(View.VISIBLE);
                if(type==null){
                    type=Effectstype.Slidetop;
                }
                start(type);


            }
        });
        mRelativeLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCancelable)dismiss();
            }
        });
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void toDefault(){
        mTitle.setTextColor(Color.parseColor(defTextColor));
        mDivider.setBackgroundColor(Color.parseColor(defDividerColor));
        mMessage.setTextColor(Color.parseColor(defMsgColor));
        mLinearLayoutView.setBackgroundColor(Color.parseColor(defDialogColor));
    }

    public NiftyDialog withDividerColor(String colorString) {
        mDivider.setBackgroundColor(Color.parseColor(colorString));
        return this;
    }
    public NiftyDialog withDividerColor(int color) {
        mDivider.setBackgroundColor(color);
        return this;
    }


    public NiftyDialog withTitle(CharSequence title) {
        toggleView(mLinearLayoutTopView,title);
        mTitle.setText(title);
        return this;
    }

    public NiftyDialog withTitleColor(String colorString) {
        mTitle.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public NiftyDialog withTitleColor(int color) {
        mTitle.setTextColor(color);
        return this;
    }

    public NiftyDialog withMessage(int textResId) {
        toggleView(mLinearLayoutMsgView,textResId);
        mMessage.setText(textResId);
        return this;
    }

    public NiftyDialog withMessage(CharSequence msg) {
        toggleView(mLinearLayoutMsgView,msg);
        mMessage.setText(msg);
        return this;
    }
    public NiftyDialog withMessageColor(String colorString) {
        mMessage.setTextColor(Color.parseColor(colorString));
        return this;
    }
    public NiftyDialog withMessageColor(int color) {
        mMessage.setTextColor(color);
        return this;
    }

    public NiftyDialog withDialogColor(String colorString) {
        mLinearLayoutView.getBackground().setColorFilter(getColorFilter(Color.parseColor(colorString)));
        return this;
    }

    public NiftyDialog withDialogColor(int color) {
        mLinearLayoutView.getBackground().setColorFilter(getColorFilter(color));
        return this;
    }

    public NiftyDialog withIcon(int drawableResId) {
        mIcon.setImageResource(drawableResId);
        return this;
    }

    public NiftyDialog withIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
        return this;
    }

    public NiftyDialog withDuration(int duration) {
        this.mDuration=duration;
        return this;
    }

    public NiftyDialog withEffect(Effectstype type) {
        this.type=type;
        return this;
    }

    public NiftyDialog withButtonDrawable(int resid) {
        mButton1.setBackgroundResource(resid);
        mButton2.setBackgroundResource(resid);
        return this;
    }
    public NiftyDialog withButton1Text(CharSequence text) {
        mButton1.setVisibility(View.VISIBLE);
        mButton1.setText(text);

        return this;
    }
    public NiftyDialog withButton2Text(CharSequence text) {
        mButton2.setVisibility(View.VISIBLE);
        mButton2.setText(text);
        return this;
    }
    public NiftyDialog setButton1Click(View.OnClickListener click) {
        mButton1.setOnClickListener(click);
        return this;
    }
    public NiftyDialog setCustomView(int resId, Context context) {
        View customView = View.inflate(context, resId, null);
        if (mFrameLayoutCustomView.getChildCount()>0){
            mFrameLayoutCustomView.removeAllViews();
        }
        mFrameLayoutCustomView.addView(customView);
        return this;
    }
    public NiftyDialog setCustomView(View view, Context context) {
        if (mFrameLayoutCustomView.getChildCount()>0){
            mFrameLayoutCustomView.removeAllViews();
        }
        mFrameLayoutCustomView.addView(view);

        return this;
    }
    public NiftyDialog isCancelableOnTouchOutside(boolean cancelable) {
        this.isCancelable=cancelable;
        this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public NiftyDialog isCancelable(boolean cancelable) {
        this.isCancelable=cancelable;
        this.setCancelable(cancelable);
        return this;
    }

    public NiftyDialog setButton2Click(View.OnClickListener click) {
        mButton2.setOnClickListener(click);
        return this;
    }

    private void toggleView(View view,Object obj){
        if (obj==null){
            view.setVisibility(View.GONE);
        }else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        mButton1.setVisibility(View.GONE);
//        mButton2.setVisibility(View.GONE);
    }

    private void start(Effectstype type){
        BaseEffects animator = type.getAnimator();
        if(mDuration != -1){
            animator.setDuration(Math.abs(mDuration));
        }
        animator.start(mRelativeLayoutView);
    }

    private ColorFilter getColorFilter(int color) {
        ColorMatrixColorFilter colorFilter;
        int red = (color & 0xFF0000) / 0xFFFF;
        int green = (color & 0xFF00) / 0xFF;
        int blue = color & 0xFF;

        float[] matrix = { 0, 0, 0, 0, red
                , 0, 0, 0, 0, green
                , 0, 0, 0, 0, blue
                , 0, 0, 0, 1, 0 };

        colorFilter = new ColorMatrixColorFilter(matrix);

        return colorFilter;
    }
}
