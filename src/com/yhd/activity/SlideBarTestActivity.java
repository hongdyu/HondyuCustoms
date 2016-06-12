package com.yhd.activity;

import com.yhd.R;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SlideBarTestActivity extends BaseActivity implements
		OnSeekBarChangeListener {

	private TextView tv_count;
	private SeekBar mSeekbar;
	private int curTempProgress = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide_bar_test);
		initView();
	}

	private void initView() {
		tv_count = (TextView) findViewById(R.id.tv_count);
		mSeekbar = (SeekBar) findViewById(R.id.seekbar);
		mSeekbar.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int seekProgress = mSeekbar.getProgress() - 25;
		if (seekProgress < -22) {
			curTempProgress = -25;
		} else if (seekProgress >= -22 && seekProgress < -17) {
			curTempProgress = -20;
		} else if (seekProgress >= -17 && seekProgress < -12) {
			curTempProgress=-15;
		} else if (seekProgress >= -12 && seekProgress < -7) {
			curTempProgress=-10;
		} else if (seekProgress >= -7 && seekProgress < -2) {
			curTempProgress=-5;
		}else if (seekProgress >= -2 && seekProgress < 3) {
			curTempProgress=0;
		}else if (seekProgress >= 3 && seekProgress < 8) {
			curTempProgress=5;
		}else if (seekProgress >= 8 && seekProgress < 13) {
			curTempProgress=10;
		}else if (seekProgress >= 13 && seekProgress < 18) {
			curTempProgress=15;
		}else if (seekProgress >= 18 && seekProgress < 23) {
			curTempProgress=20;
		}else if (seekProgress >= 23) {
			curTempProgress=25;
		}
		mSeekbar.setProgress(curTempProgress+25);
		tv_count.setText(String.valueOf(curTempProgress));
	}

}
