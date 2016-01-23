package com.ericcode.xiaodepa.ui.dialog;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by zhoushengming on 16/1/23.
 */
public class CommonPageAdapter extends PagerAdapter {

	private final List<View> listImgs;

	public CommonPageAdapter(List<View> listImgs) {
		this.listImgs = listImgs;
	}

	@Override
	public int getCount() {
		return listImgs.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}
