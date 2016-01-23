package com.ericcode.xiaodepa.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.ericcode.xiaodepa.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoushengming on 16/1/23.
 */
public class ImgScanDialog  extends Dialog implements
		android.view.View.OnClickListener {

	private List<String> mListImgUrls;
	private Integer[] mImgIds;
	private Context mContext;
	private ViewPager mViewPager;
	private int mClickItem;

	public ImgScanDialog(Context context, Integer[] imgIds, int clickItem) {
		// 设置自定义样式
		super(context, R.style.CustomDialog_fill);
		this.mContext = context;
		this.mImgIds = imgIds;
		this.mClickItem = clickItem;
		initView();
	}

	public ImgScanDialog(Context context, List<String> imgUrlss, int clickItem) {
		// 设置自定义样式
		super(context, R.style.CustomDialog_fill);
		this.mContext = context;
		this.mListImgUrls = imgUrlss;
		this.mClickItem = clickItem;
		initView();
	}

	private void initView() {
		mViewPager = new ViewPager(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mViewPager.setLayoutParams(params);
		mViewPager.setBackgroundColor(0xFF000000);
		setContentView(mViewPager);
		setParams();
		initViewPager();
	}

	private void setParams() {
		LayoutParams lay = this.getWindow().getAttributes();
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
		Rect rect = new Rect();
		View view = getWindow().getDecorView();
		view.getWindowVisibleDisplayFrame(rect);
		lay.height = dm.heightPixels - rect.top;
		lay.width = dm.widthPixels;
	}

	private void initViewPager() {
		if (mImgIds != null && mImgIds.length > 0) {
			List<View> listImgs = new ArrayList<View>();
			for (int i = 0; i < mImgIds.length; i++) {
				ImageView iv = new ImageView(mContext);
				LayoutParams params = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				iv.setLayoutParams(params);
				listImgs.add(iv);
				iv.setOnClickListener(this);
				iv.setImageResource(mImgIds[i]);
				// 加载网络图片
				// BitmapHelper.getInstance(mContext).display(iv,
				// mListImgUrls.get(i));
			}
			if (listImgs.size() > 0) {
				CommonPageAdapter pageAdapter = new CommonPageAdapter(listImgs);
				mViewPager.setAdapter(pageAdapter);
				mViewPager.setCurrentItem(mClickItem);
			}
		}
	}

	@Override
	public void onClick(View v) {
		this.dismiss();
	}

	@Override
	public void dismiss() {
		super.dismiss();

	}

}