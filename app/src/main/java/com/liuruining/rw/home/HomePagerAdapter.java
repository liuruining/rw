package com.liuruining.rw.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.liuruining.rw.R;
import com.liuruining.rw.examination.ExaminationFragment;
import com.liuruining.rw.learn.LearnFragment;
import com.liuruining.rw.wordsnote.WordsNoteFragment;


/**
 * Created by nielongyu on 15/3/9.
 */
public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public HomePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = getLearnFragment();
                break;
            case 1:
                fragment = ExaminationFragment.newInstance();
                break;
            case 2:
                fragment = WordsNoteFragment.newInstance();
                break;
            default:
                fragment = ExaminationFragment.newInstance();
                break;
        }
        return fragment;
    }

    private Fragment getLearnFragment() {
        LearnFragment fragment = LearnFragment.newInstance();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = mContext.getString(R.string.home_tab_title_learn);
                break;
            case 1:
                title = mContext.getString(R.string.home_tab_title_examination);
                break;
            case 2:
                title = mContext.getString(R.string.home_tab_title_wordsNote);
                break;
        }
        return title;
    }


}
