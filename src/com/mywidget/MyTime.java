package com.mywidget;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyTime extends AppWidgetProvider {

	Timer timer;
	Context context;

	// onUpdate
	@Override
	public void onUpdate(Context con, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		context = con;
		timer = new Timer();
		timer.schedule(timertask, 0, 1000);
	}

	// MyService·þÎñ³ÌÐò
	public static class MyService extends Service {
		@Override
		public void onStart(Intent intent, int startId) {
			RemoteViews remoteViews = new RemoteViews(getPackageName(),
					R.layout.my_time);
			remoteViews.setTextViewText(R.id.TextView01,
					new Date().toLocaleString());
			ComponentName thisWidget = new ComponentName(this, MyTime.class);
			AppWidgetManager manager = AppWidgetManager.getInstance(this);
			manager.updateAppWidget(thisWidget, remoteViews);
		}

		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}
	};

	// Handler
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Intent intent = new Intent(context, MyService.class);
			context.startService(intent);
		}
	};

	private TimerTask timertask = new TimerTask() {
		public void run() {
			Message message = new Message();
			handler.sendMessage(message);
		}
	};

}