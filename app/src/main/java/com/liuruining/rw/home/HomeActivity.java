package com.liuruining.rw.home;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.liuruining.model.dao.DaoSession;
import com.liuruining.model.dao.WordLocation;
import com.liuruining.rw.R;
import com.liuruining.rw.base.BaseActivity;
import com.liuruining.rw.common.BaseConfig;
import com.liuruining.rw.common.CustomPageIndicator;
import com.liuruining.rw.learn.WordDetailActivity;

import java.util.List;

import roboguice.RoboGuice;
import roboguice.inject.InjectView;

/**
 * Created by nielongyu on 15/3/9.
 */
public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @InjectView(R.id.pager)
    private ViewPager pager;
    private View contentView;
    private static final int LOAD_LOCATION = 1011;
    private CustomPageIndicator indicator;
    private String[] titles = new String[]{"学习", "测验", "生词本"};
    private PopupWindow popupWindow;
    private WordLocation wordLocation;

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

        View customView = LayoutInflater.from(this).inflate(R.layout.layout_filter_and_more, null);
        final View filterButton = customView.findViewById(R.id.last_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
                contentView = inflater.inflate(R.layout.item_word_location, null);

                popupWindow = new PopupWindow(contentView, BaseConfig.dp2px(250), ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);

                TextView indexView = (TextView) contentView.findViewById(R.id.hint_index);
                String text = "";
                if (wordLocation != null) {
                    switch (wordLocation.getBook()) {
                        case 1:
                            text = "MBA(上) 第";
                            break;
                        case 2:
                            text = "MBA(中) 第";
                            break;
                        case 3:
                            text = "MBA(下) 第";
                            break;
                    }
                    indexView.setText(text + (wordLocation.getIndex() + 1) + "词");
                } else {
                    indexView.setText("还没有背诵记录~");
                }

                contentView.findViewById(R.id.hint_index).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (wordLocation != null) {
                            Intent lastLocationIntent = new Intent(HomeActivity.this, WordDetailActivity.class);
                            lastLocationIntent.putExtra("location", wordLocation);
                            startActivity(lastLocationIntent);
                            if (popupWindow != null) {
                                popupWindow.dismiss();
                            }
                        }
                    }
                });

                popupWindow.showAsDropDown(filterButton, -BaseConfig.dp2px(110), 0);
            }

        });
        ActionBar.LayoutParams paramsActionBar = new ActionBar.LayoutParams(Gravity.RIGHT);
        getSupportActionBar().setCustomView(customView, paramsActionBar);
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

    @Override
    protected void onStart() {
        super.onStart();
        getSupportLoaderManager().initLoader(LOAD_LOCATION, null, lastCallback);
    }

    LoaderManager.LoaderCallbacks<WordLocation> lastCallback = new LoaderManager.LoaderCallbacks<WordLocation>() {
        @Override
        public Loader<WordLocation> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<WordLocation>(HomeActivity.this) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    forceLoad();
                }

                @Override
                public WordLocation loadInBackground() {
                    if (!isFinishing()) {
                        List<WordLocation> wordLocationList = RoboGuice.getInjector(HomeActivity.this).getInstance(DaoSession.class).getWordLocationDao().loadAll();
                        if (wordLocationList != null && wordLocationList.size() >= 1) {
                            return wordLocationList.get(0);
                        }
                    }
                    return null;
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<WordLocation> loader, WordLocation data) {
            if (data == null) {
                return;
            }
            wordLocation = data;
        }

        @Override
        public void onLoaderReset(Loader<WordLocation> loader) {

        }
    };
}
