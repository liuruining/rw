package com.liuruining.rw.examination;

import android.os.Bundle;

import com.liuruining.rw.R;
import com.liuruining.rw.base.BaseActivity;

/**
 * Created by nielongyu on 15/3/10.
 */
public class ExamActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);
        String title = getIntent().getStringExtra("list_name");
        setTitle("测验: " + title);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, ExamFragment.newInstance())
                .commit();
    }
}
