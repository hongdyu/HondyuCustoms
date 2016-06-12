package com.yhd.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class CustomerVideoView extends VideoView {

	public CustomerVideoView(Context paramContext) {
		super(paramContext);
	}

	public CustomerVideoView(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public CustomerVideoView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	protected void onMeasure(int paramInt1, int paramInt2) {
		setMeasuredDimension(getDefaultSize(800, paramInt1),
				getDefaultSize(480, paramInt2));
	}
}