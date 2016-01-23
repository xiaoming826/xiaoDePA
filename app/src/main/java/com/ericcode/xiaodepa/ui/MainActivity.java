package com.ericcode.xiaodepa.ui;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.ericcode.xiaodepa.R;
import com.ericcode.xiaodepa.ui.view.WaveformView;
import com.ericcode.xiaodepa.util.Logger;

public class MainActivity extends ActionBarActivity implements View.OnClickListener, View.OnTouchListener {

	private static final int MSG_GET_VOLUME = 0x1001;

	private static final String TAG = MainActivity.class.getSimpleName();
	private WaveformView mWaveformView;
	private Button mListenBtn;


	private RecordThread mRecordThread;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		mWaveformView = (WaveformView) findViewById(R.id.waveform_view);
		mWaveformView.setVisibility(View.GONE);
		mListenBtn = (Button) findViewById(R.id.btn_listen);
		mListenBtn.setOnClickListener(this);
		mListenBtn.setOnTouchListener(this);
	}


	@Override
	protected void onResume() {
		super.onResume();
		mRecordThread = new RecordThread(mHandler);
		mRecordThread.start();
	}

	@Override
	protected void onPause() {
		if (mRecordThread != null) {
			mRecordThread.pause();
			mRecordThread = null;
		}
		super.onPause();
	}

	private Handler mHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (msg.what == MSG_GET_VOLUME) {
				update((Float) msg.obj);
			}
			return true;
		}
	});

	private void update(final float volume) {
		if (mWaveformView.getVisibility()==View.GONE)
			return;
		mWaveformView.post(new Runnable() {
			@Override
			public void run() {
				mWaveformView.updateAmplitude(volume * 0.1f / 2000);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_listen:


				break;

			default:
				Logger.w(TAG, "click warning! ");
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == R.id.btn_listen) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mWaveformView.setVisibility(View.VISIBLE);
					break;
				case MotionEvent.ACTION_UP:
					mWaveformView.setVisibility(View.GONE);
					break;
			}
		}
		return false;
	}

	static class RecordThread extends Thread {
		private AudioRecord ar;
		private int bs;
		private final int SAMPLE_RATE_IN_HZ = 8000;
		private boolean isRun = false;
		private Handler mHandler;

		public RecordThread(Handler handler) {
			super();
			bs = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT);
			Logger.i(TAG, "RecordThread: AudioRecord.getMinBufferSize:" + bs);
			ar = new AudioRecord(MediaRecorder.AudioSource.MIC,
					SAMPLE_RATE_IN_HZ,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT, bs);
			mHandler = handler;
		}

		public void run() {
			super.run();
			ar.startRecording();
			byte[] buffer = new byte[bs];
			isRun = true;
			while (running()) {
				int r = ar.read(buffer, 0, bs);
				int v = 0;
				for (byte aBuffer : buffer) {
					v += aBuffer * aBuffer;
				}
				Message msg = mHandler.obtainMessage(MSG_GET_VOLUME, v * 1f / r);
				mHandler.sendMessage(msg);
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			ar.stop();
		}

		public synchronized void pause() {
			isRun = false;
		}

		private synchronized boolean running() {
			return isRun;
		}

		public void start() {
			if (!running()) {
				super.start();
			}
		}
	}

}

