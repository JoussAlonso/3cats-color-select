package com.threecats.colorlantern;

/*
 *  Copyright 2011 3Cats Software <rumburake@gmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  see NOTICE and LICENSE files in the top level project folder.
 */

import java.util.Random;

import com.threecats.colorlantern.R;
import com.threecats.colorselect.ColorSelectDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ThreeCatsColorLantern extends Activity {
    /** Called when the activity is first created. */
	
	int color = 0xFFFF0000;
	Random random = new Random();
	View view;
	Dialog currentDialog = null;
	
	private void selectColor() {
		currentDialog = new ColorSelectDialog(this, color, new ColorSelectDialog.OnColorSelectListener() {
			
			// Color selected by user is available in this function in parameter color.
			// If the user didn't select a new color this function is not called.
			public void onNewColor(int color) {
				// I choose to save this color in my app's permanent storage
		        SharedPreferences settings = ThreeCatsColorLantern.this.getPreferences(0);
		        SharedPreferences.Editor settingsEditor = settings.edit();
		        settingsEditor.putInt("color", color);
		        settingsEditor.commit();
		        // I save color into this activity's class member and also set the background to it.
				ThreeCatsColorLantern.this.color = color;
				ThreeCatsColorLantern.this.view.setBackgroundColor(color);
			}
		});
		currentDialog.show();
	}
		
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != currentDialog) {
			currentDialog.dismiss();
			currentDialog = null;
		}
		Log.w("cucu", "onDestroy was called!");
	}

	private View.OnClickListener clickListener = new View.OnClickListener() {
		public void onClick(View v) {
	        Log.w("cucu", "Click!");
	        selectColor();
	        Log.w("cucu", "show dialog");
		}
	};
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        w.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        SharedPreferences settings = getPreferences(0);
        color = settings.getInt("color", 0xFF00AA00);
        
        setContentView(R.layout.main);
        view = findViewById(R.id.surface);
        view.setOnClickListener(clickListener);
        view.setBackgroundColor(color);
        Log.w("cucu", "Color is" + color);
    }
}