package com.umberto.backingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.umberto.backingapp.data.Recipe;

public class BackingWidgetService extends RemoteViewsService {
    Recipe recipe;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        return new BackingDataProvider(getApplicationContext(),intent);


    }
}
