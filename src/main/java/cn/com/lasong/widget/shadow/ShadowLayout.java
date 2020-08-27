package cn.com.lasong.widget.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import cn.com.lasong.widget.R;

/**
 * Author: zhusong
 * Email: song.zhu@lasong.com.cn
 * Date: 2020/8/27
 * Description:
 */
public class ShadowLayout extends FrameLayout {

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    private ShadowView shadowView;
    public ShadowLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        shadowView = new ShadowView(getContext());
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        shadowView.bgColor = ta.getColor(R.styleable.ShadowLayout_bgColor, Color.TRANSPARENT);
        shadowView.bgRadius = ta.getDimension(R.styleable.ShadowLayout_bgRadius, 0);
        shadowView.mPaint.setColor(shadowView.bgColor);
        shadowView.dx = ta.getDimension(R.styleable.ShadowLayout_shadowDx, 0);
        shadowView.dy = ta.getDimension(R.styleable.ShadowLayout_shadowDy, 0);
        shadowView.shadowColor = ta.getColor(R.styleable.ShadowLayout_shadowColor, Color.TRANSPARENT);
        shadowView.shadowRadius = ta.getDimension(R.styleable.ShadowLayout_shadowRadius, 0);
        ta.recycle();
        LayoutParams lp = generateDefaultLayoutParams();
        lp.width = 0;
        lp.height = 0;
        int margin = -(int) shadowView.shadowRadius;
        lp.setMargins(margin, margin, margin, margin);
        shadowView.setLayoutParams(lp);
        addView(shadowView, 0);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setClipChildren(false);
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).setClipChildren(false);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (null != shadowView) {
            LayoutParams lp = (LayoutParams) shadowView.getLayoutParams();
            if (null == lp) {
                lp = generateDefaultLayoutParams();
            }
            lp.width = (int) ((right - left) + shadowView.shadowRadius * 2);
            lp.height = (int) ((bottom - top) + shadowView.shadowRadius * 2);
            int margin = -(int) shadowView.shadowRadius;
            lp.setMargins(margin, margin, margin, margin);
            shadowView.setLayoutParams(lp);
        }
    }
}