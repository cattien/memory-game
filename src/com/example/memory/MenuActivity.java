package com.example.memory;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuActivity extends Activity {

	private MediaPlayer sound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_layout);
		//play
		findViewById(R.id.btnPlay).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSound();
				Intent intent = new Intent(MenuActivity.this,
						GameActivity.class);
				startActivity(intent);
			}
		});
		//help
		findViewById(R.id.btnHelp).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSound();
				Intent intent = new Intent(MenuActivity.this,
						HelpActivity.class);
				startActivity(intent);
			}
		});
		//quit game
		findViewById(R.id.btnQuit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSound();
				quitDialog();
			}
		});
	}
	
	/**
	 * Dialog xac nhan khi chon thoat game
	 */
	private void quitDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Ban khong choi nua a!");
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();

			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		}).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	public void playSound() {
		stopSound();
		sound = MediaPlayer.create(this, R.raw.click);
		sound.start();

	}

	public void stopSound() {
		if (sound != null) {
			sound.stop();
			sound.release();
			sound = null;

		}
	}

}
