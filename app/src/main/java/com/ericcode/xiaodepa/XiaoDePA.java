package com.ericcode.xiaodepa;

import android.app.Application;

/**
 * Created by zhoushengming on 16/1/13.
 */
public class XiaoDePA extends Application {


	private static XiaoDePA mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public static XiaoDePA ins() {
		return mInstance;
	}
}
