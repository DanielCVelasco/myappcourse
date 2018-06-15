package com.example.admin.navigatordrawer.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.navigatordrawer.MainActivity;
import com.example.admin.navigatordrawer.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        Log.d("App", "updateAppWidget");
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, "Actualizando clima...");
        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent intent= new Intent(context, MainActivity.class);
        //intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        //intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetId);

        PendingIntent pendingIntent= PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        String url= "http://ec2-18-216-251-7.us-east-2.compute.amazonaws.com/dev/dmx/public/weather";

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("App", "Response: " + response);
                    try{
                        JSONObject jsonObject= new JSONObject(response);
                        String message= jsonObject.getString("Message");
                        views.setTextViewText(R.id.appwidget_text, message);

                    }catch (Exception e){
                        views.setTextViewText(R.id.appwidget_text, "Intenta mas tarde");
                    }
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("App", "Error: " + error);
                views.setTextViewText(R.id.appwidget_text, "Intenta mas tarde");
            }
        });

        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d("App", "onUpdate");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            views.setTextViewText(R.id.appwidget_text, "Actualizando clima...");

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.d("App", "onEnabled");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.d("App", "onDisabled");
    }
}

