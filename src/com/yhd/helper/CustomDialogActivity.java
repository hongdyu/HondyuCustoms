package com.yhd.helper;

import com.yhd.R;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

public class CustomDialogActivity extends AlertDialog {

	public CustomDialogActivity(Context context) {
		super(context);
	}
	
	public CustomDialogActivity(Context context,int theme) {
		super(context,theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_line_item);
	}


}
