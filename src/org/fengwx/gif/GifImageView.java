/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fengwx.gif;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.IOException;

public class GifImageView extends ImageView {

    static final String ANDROID_NS="http://schemas.android.com/apk/res/android";

    public GifImageView(Context context) {
        this(context, null);
    }

    public GifImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GifImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        trySetGifDrawable(attrs, getResources());
    }

    @Override
    public void setImageResource(int resId) {
        setResource(true, resId, getResources());
    }

    @Override
    public void setBackgroundResource(int resId) {
        setResource(false, resId, getResources());
    }

    void trySetGifDrawable(AttributeSet attrs, Resources res) {
        int resId=attrs.getAttributeResourceValue(ANDROID_NS, "src", -1);
        if (resId>0&&"drawable".equals(res.getResourceTypeName(resId))) {
            setResource(true, resId, res);
        }

        resId=attrs.getAttributeResourceValue(ANDROID_NS, "background", -1);
        if (resId>0&&"drawable".equals(res.getResourceTypeName(resId))) {
            setResource(false, resId, res);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    //new method not avalilable on older API levels
    public void setResource(boolean isSrc, int resId, Resources res) {
        try {
            GifDrawable d=new GifDrawable(res, resId);
            if (isSrc) {
                setImageDrawable(d);
            } else if (Build.VERSION.SDK_INT>=16) {
                setBackground(d);
            } else {
                setBackgroundDrawable(d);
            }
            return;
        } catch (IOException e) {
            //ignored
        } catch (NotFoundException e) {
            //ignored
        }
        if (isSrc) {
            super.setImageResource(resId);
        } else {
            super.setBackgroundResource(resId);
        }
    }

    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Log.d(VIEW_LOG_TAG, "onMeasure:"+widthMeasureSpec+" height:"+heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize;
        int heightSize;

        int width=getWidth();
        int height=getHeight();

        width=Math.max(width, getSuggestedMinimumWidth());
        height=Math.max(height, getSuggestedMinimumHeight());

        *//*Log.d(VIEW_LOG_TAG, "width:"+width+" height:"+height   +" getWidth():"+getWidth()
            +" getMeasuredWidth:"+getMeasuredWidth()+" getMeasuredHeight:"+getMeasuredHeight());*//*
        widthSize=getMeasuredWidth();
        heightSize=getMeasuredHeight();

        if (height>heightSize||width>widthSize) {
            float inSampleSize=1f;
            if (width>height) {
                inSampleSize=((float) height/(float) heightSize);
            } else {
                inSampleSize=((float) width/(float) widthSize);
            }
            widthSize*=inSampleSize;
            heightSize*=inSampleSize;
        }
        *//*Log.d(VIEW_LOG_TAG, " width:"+width+" height:"+height
            +" widthSize:"+widthSize+" heightSize:"+heightSize);*//*

        setMeasuredDimension(widthSize, heightSize);
    }*/

        /*onMeasure:1073742592 height:1073742862
        width:0 height:0 getWidth():0 getMeasuredWidth:768 getMeasuredHeight:1038
        width:0 height:0 widthSize:768 heightSize:1038
        onMeasure:1073742592 height:1073742862
        width:0 height:0 getWidth():0 getMeasuredWidth:768 getMeasuredHeight:1038
        onMeasure:1073742592 height:1073742862
        width:768 height:1038 getWidth():768 getMeasuredWidth:768 getMeasuredHeight:1038
        width:768 height:1038 widthSize:768 heightSize:1038
        onMeasure:1073742592 height:1073742862
        width:768 height:1038 getWidth():768 getMeasuredWidth:768 getMeasuredHeight:1038
        width:768 height:1038 widthSize:768 heightSize:1038*/

}
