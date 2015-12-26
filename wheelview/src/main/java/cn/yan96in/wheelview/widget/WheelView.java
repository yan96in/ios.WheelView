package cn.yan96in.wheelview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import cn.yan96in.wheelview.R;

/**
 * Created by yan96in on 2015/12/26.
 * You can find me in github
 */
public class WheelView extends View{
    int totalScrollY;
    Context context;
    int textSize;
    int maxTextWidth,maxTextHeight;
    int colorGray,colorBlack,colorGrayLight;
    float lineSpacingMultiplier;
    boolean isLoop;
    int firstLineY,secondeLineY;
    int preCurrentIndex;
    int initPosition;
    int itemCount;
    int measureHeight;
    int halfCircumference;
    int radius;
    int measuredWidth;
    int change;
    float y1,y2,dy;
    ArrayList arrayList;
    int j2,k1,l1;


    public WheelView(Context context) {
        super(context);
        init();
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textSize=0;
        colorGray=context.getResources().getColor(R.color.gray);
        colorBlack=context.getResources().getColor(R.color.black);
        colorGrayLight=context.getResources().getColor(R.color.light_gray);
        lineSpacingMultiplier = 2.0F;
        isLoop=false;
        initPosition=-1;
        itemCount=7;
        y1=0.0f;
        y2=0.0f;
        dy=0.0f;
        totalScrollY =0;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        String as[];
        if (arrayList==null) {
            super.onDraw(canvas);
            return;
        }
        as=new String[itemCount];
        change=(int)(totalScrollY/(lineSpacingMultiplier*maxTextHeight));
        preCurrentIndex=initPosition+change%arrayList.size();
        if (!isLoop) {
            if(preCurrentIndex<0){
                preCurrentIndex=0;
            }
            if (preCurrentIndex>arrayList.size()-1) {
                preCurrentIndex=arrayList.size()-1;
            }
        }else {
            if(preCurrentIndex<0){
                preCurrentIndex+=arrayList.size();
            }
            if (preCurrentIndex>arrayList.size()-1) {
                preCurrentIndex-=arrayList.size();
            }
        }

        j2=(int)(totalScrollY%(lineSpacingMultiplier*maxTextHeight));
        k1=0;
        while (k1<itemCount){
            if (isLoop) {
                l1=preCurrentIndex-(itemCount/2-k1);
                if(l1<0){
                    l1+=arrayList.size();
                }
                if (l1>arrayList.size()-1) {
                    l1-=arrayList.size();
                }
                as[k1]= ((String) arrayList.get(l1));
            } else if (l1 < 0) {
                as[k1] = "";
            } else if (l1 > arrayList.size() - 1) {
                as[k1] = "";
            } else {
                as[k1] = (String) arrayList.get(l1);
            }
            k1++;
        }
        int left=(measuredWidth-maxTextWidth)/2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initData();
    }

    private void initData() {
        if (arrayList==null) {
            return;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y1=event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                y2=event.getRawY();
                dy=y1-y2;
                y1=y2;
                totalScrollY =(int)((float) totalScrollY +dy);
                if (!isLoop) {
                    int initPositionCircleLength=(int)(initPosition*(lineSpacingMultiplier*maxTextHeight));
                    int initPositionStartY=-1*initPositionCircleLength;
                    if(totalScrollY < initPositionStartY){
                        totalScrollY =initPositionStartY;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                default:
                    return true;
        }
        if (!isLoop) {
            int circleLength=(int)((float) (arrayList.size() - 1 - initPosition) * (lineSpacingMultiplier * maxTextHeight));
            if (totalScrollY >= circleLength) {
                totalScrollY = circleLength;
            }
        }
        invalidate();
        return true;
    }

    public void setLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }
}
