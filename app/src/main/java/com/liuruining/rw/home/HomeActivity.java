package com.liuruining.rw.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.liuruining.rw.R;
import com.liuruining.rw.base.BaseActivity;
import com.liuruining.rw.common.CustomPageIndicator;

import roboguice.inject.InjectView;

/**
 * Created by nielongyu on 15/3/9.
 */
public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @InjectView(R.id.pager)
    private ViewPager pager;

    private CustomPageIndicator indicator;
    private String[] titles = new String[]{"学习", "测验", "生词本"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager.setAdapter(new HomePagerAdapter(this, getSupportFragmentManager()));
        pager.setOnPageChangeListener(this);
        indicator = new CustomPageIndicator(this, new int[]{R.mipmap.icon_tabbar_book_learn_nor, R.mipmap.icon_tabbar_statistics_nor, R.mipmap.icon_tabbar_memo_nor}, new int[]{R.mipmap.icon_tabbar_book_learn_press, R.mipmap.icon_tabbar_statistics_press, R.mipmap.icon_tabbar_memo_press}, new String[]{"学习", "测验", "生词本"});
        indicator.setViewPager(pager);

        LinearLayout mainLayout = (LinearLayout) this.findViewById(R.id.content);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mainLayout.addView(indicator, params);
        mainLayout.requestLayout();

        getSupportActionBar().setTitle("学习");
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        if (getIntent().hasExtra("selected")) {
            pager.setCurrentItem(getIntent().getIntExtra("selected", 0));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int i) {
        indicator.setSelectedTab(i);
        getSupportActionBar().setTitle(titles[i]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
