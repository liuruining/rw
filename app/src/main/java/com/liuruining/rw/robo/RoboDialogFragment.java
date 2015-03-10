package com.liuruining.rw.robo;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import roboguice.RoboGuice;

/**
 * Created by nielongyu on 15/3/9.
 */
public class RoboDialogFragment extends DialogFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectMembersWithoutViews(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectViewMembers(this);
    }
}
