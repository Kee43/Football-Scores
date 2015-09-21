
package barqsoft.footballscores.widget;


// Google sample used as reference at: http://docs.huihoo.com/android/3.0/resources/samples/StackWidget/index.html
// Reference used for widget classes: https://github.com/laaptu/appwidget-listview/tree/appwidget-listview2

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import barqsoft.footballscores.R;
import barqsoft.footballscores.service.myFetchService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new barqsoft.footballscores.widget.RemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class RemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<WidgetItem> mWidgetItems = new ArrayList<WidgetItem>();

    private static final int mCount = 10;
    private Context mContext;
    private int mAppWidgetId;

    public RemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

       populateListItem();
    }

    private void populateListItem() {
        Log.d("mFetchService", "populateListItem");
       if(myFetchService.listItemList !=null )

            mWidgetItems = (ArrayList<WidgetItem>) myFetchService.listItemList
                    .clone();
        else
            mWidgetItems = new ArrayList<WidgetItem>();
    }

    public void onCreate() {
    }

    public void onDestroy() {
        mWidgetItems.clear();
    }

    public int getCount() {
        return mWidgetItems.size();
    }

    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_scores_list_item);
        rv.setTextViewText(R.id.widget_home_name, mWidgetItems.get(position).home_name);
        rv.setImageViewResource(R.id.widget_home_crest, mWidgetItems.get(position).home_crest);
        rv.setTextViewText(R.id.widget_away_name, mWidgetItems.get(position).away_name);
        rv.setImageViewResource(R.id.widget_away_crest, mWidgetItems.get(position).away_crest);
        rv.setTextViewText(R.id.widget_score_textview, mWidgetItems.get(position).score_textview);
        rv.setTextViewText(R.id.widget_data_textview, mWidgetItems.get(position).data_textview);

        Bundle extras = new Bundle();
        extras.putInt(WidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_home_crest, fillInIntent);
        rv.setOnClickFillInIntent(R.id.widget_home_name, fillInIntent);
        rv.setOnClickFillInIntent(R.id.widget_away_crest, fillInIntent);
        rv.setOnClickFillInIntent(R.id.widget_away_name, fillInIntent);
        rv.setOnClickFillInIntent(R.id.widget_score_textview, fillInIntent);
        rv.setOnClickFillInIntent(R.id.widget_data_textview, fillInIntent);

        return rv;
    }

    public RemoteViews getLoadingView() {return null;}

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {}
}