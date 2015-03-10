package com.liuruining.rw.learn;

import android.os.Bundle;

import com.liuruining.rw.R;
import com.liuruining.rw.base.BaseActivity;

/**
 * Created by nielongyu on 15/3/9.
 */
public class WordDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, WordDetailFragment.newInstance())
                .commit();

    }
}
