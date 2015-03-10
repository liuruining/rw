package com.liuruining.rw.robo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import roboguice.RoboGuice;
import roboguice.activity.event.OnActivityResultEvent;
import roboguice.activity.event.OnConfigurationChangedEvent;
import roboguice.activity.event.OnContentChangedEvent;
import roboguice.activity.event.OnCreateEvent;
import roboguice.activity.event.OnDestroyEvent;
import roboguice.activity.event.OnNewIntentEvent;
import roboguice.activity.event.OnPauseEvent;
import roboguice.activity.event.OnRestartEvent;
import roboguice.activity.event.OnResumeEvent;
import roboguice.activity.event.OnStartEvent;
import roboguice.activity.event.OnStopEvent;
import roboguice.event.EventManager;
import roboguice.inject.RoboInjector;

/**
 * Created by nielongyu on 15/3/9.
 */
public class RoboDelegate {
    private EventManager eventManager;
    private Activity activity;

    public RoboDelegate(Activity activity) {
        this.activity = activity;
    }

    public void create() {
        final RoboInjector injector = RoboGuice.getInjector(activity);
        eventManager = injector.getInstance(EventManager.class);
        injector.injectMembersWithoutViews(this);
    }

    public void dispatchCreate(Bundle savedInstanceState) {
        eventManager.fire(new OnCreateEvent(savedInstanceState));
    }

    public void dispatchRestart() {
        eventManager.fire(new OnRestartEvent());
    }

    public void dispatchStart() {
        eventManager.fire(new OnStartEvent());
    }

    public void dispatchResume() {
        eventManager.fire(new OnResumeEvent());
    }

    public void dispatchPause() {
        eventManager.fire(new OnPauseEvent());
    }

    public void dispatchNewIntent(Intent intent) {
        eventManager.fire(new OnNewIntentEvent());
    }

    public void dispatchStop() {
        eventManager.fire(new OnStopEvent());
    }

    public void dispatchDestroy() {
        eventManager.fire(new OnDestroyEvent());
    }

    public void destory() {
        RoboGuice.destroyInjector(activity);
    }

    public void dispatchConfigurationChanged(Configuration newConfig) {
        final Configuration currentConfig = activity.getResources().getConfiguration();
        eventManager.fire(new OnConfigurationChangedEvent(currentConfig, newConfig));
    }


    public void dispatchSupportContentChanged() {
        try {
            RoboGuice.getInjector(activity).injectViewMembers(activity);
            eventManager.fire(new OnContentChangedEvent());
        } catch (Exception e) {
        }
    }

    public void dispatchActivityResult(int requestCode, int resultCode, Intent data) {
        eventManager.fire(new OnActivityResultEvent(requestCode, resultCode, data));
    }

}
