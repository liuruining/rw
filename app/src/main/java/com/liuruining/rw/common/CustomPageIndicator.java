package com.liuruining.rw.common;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuruining.rw.R;


/**
 * Created by nielongyu on 15/3/8.
 */
public class CustomPageIndicator extends ViewGroup {
    private static final int MAX_TIME_LINE_HEIGHT = 0;
    private int[] resources;
    private int[] selectedResources;
    private String[] titles;
    private Context mContext;
    private int mIndex = -1;

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    private ViewPager viewPager;

    public CustomPageIndicator(Context context, int[] resources, int[] selectedResources, String[] titles) {
        super(context);
        if (resources == null || selectedResources == null || titles == null ||
                resources.length != titles.length || resources.length != selectedResources.length || selectedResources.length != titles.length ||
                mContext instanceof ViewPager.OnPageChangeListener) {
            throw new IllegalArgumentException("Resource and titles must match and should not be null");
        }
        this.resources = resources;
        this.selectedResources = selectedResources;
        this.titles = titles;
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        for (int i = 0; i < resources.length; i++) {
            View tabView = inflater.inflate(R.layout.home_tab_view, null);
            ImageView tabImage = (ImageView) tabView.findViewById(R.id.tab_image);
            TextView tabText = (TextView) tabView.findViewById(R.id.tab_text);
            tabImage.setBackgroundResource(resources[i]);
            tabText.setText(titles[i]);
            final int index = i;
            tabView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewPager != null && index != mIndex) {
                        viewPager.setCurrentItem(index);
                        setSelectedTab(index);
                    }
                }
            });
            this.addView(tabView);
        }
        setSelectedTab(0);
    }

    public void setSelectedTab(int index) {
        if (index == this.mIndex || index < 0 || index >= resources.length) {
            return;
        }

        for (int i = 0; i < resources.length; i++) {
            View tabView = this.getChildAt(i);
            ImageView tabImage = (ImageView) tabView.findViewById(R.id.tab_image);
            TextView tabText = (TextView) tabView.findViewById(R.id.tab_text);

            if (index == i) {
                tabImage.setImageResource(selectedResources[i]);
                tabText.setTextColor(mContext.getResources().getColor(R.color.blue));
            } else {
                tabImage.setImageResource(resources[i]);
                tabText.setTextColor(mContext.getResources().getColor(R.color.hint_and_tips));
            }
        }
        this.mIndex = index;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //宽度必须指定或match_parent，否则不显示
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            throw new IllegalArgumentException("Time line layout must be match parent");
        }

        float realDivider = titles.length;
        int elementWidth = (int) (widthSize / realDivider);
        int elementWidthSpec = MeasureSpec.makeMeasureSpec(elementWidth, MeasureSpec.EXACTLY);
        int elementHeightSpec = MeasureSpec.makeMeasureSpec(BaseConfig.dp2px(MAX_TIME_LINE_HEIGHT), MeasureSpec.UNSPECIFIED);
        int elementHeight = 0;
        for (int i = 0, n = getChildCount(); i < n; i++) {
            View elementView = getChildAt(i);
            elementView.measure(elementWidthSpec, elementHeightSpec);
            elementHeight = elementView.getMeasuredHeight();
        }
        setMeasuredDimension(widthSize, elementHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int x = 0, y = 0;
        for (int i = 0; i < this.getChildCount(); i++) {
            View elementView = getChildAt(i);
            elementView.layout(x, y, x = x + elementView.getMeasuredWidth(), y + elementView.getMeasuredHeight());
        }
    }
}
