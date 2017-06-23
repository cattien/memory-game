package com.example.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class GameActivity extends Activity {
	private static int ROW_COUNT = -1;
	private static int COL_COUNT = -1;
	// context
	private Context context;
	private Drawable backItem;
	private int[][] items;
	// danh sach hinh
	private List<Drawable> topic;
	// doi tuong item
	private Items firstItem;
	private Items secondItem;
	private ButtonListener buttonListener;
	private static Object lock = new Object();
	private TableLayout mainTable;
	private UpdateItems update;
	private static final int NEW_MENU_ID = Menu.FIRST;
	private static final int EXIT_MENU_ID = Menu.FIRST + 1;
	private int count = 0;
	private int miss = 0;
	private MediaPlayer sound;
	private int level = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressDialog();
		update = new UpdateItems();
		topic = topic;
		// mac dinh chu de khi lan dau load la Cookie
		loadItems(1);
		// mo context menu chon chu de
		foodChoiceDialog();
		setContentView(R.layout.activity_main);
		// buuton chon chu de
		Button btnFood = (Button) findViewById(R.id.btnFood);
		btnFood.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// am thanh click
				playMenuSound();
				// mo item
				foodChoiceDialog();
			}
		});
		//button quay ve menu
				Button btnMenu = (Button) findViewById(R.id.btnMenu);
				btnMenu.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// am thanh click
						playMenuSound();
						// dong activity
						finish();
					}
				});
		// resource hinh cho item luc chua lat
		backItem = getResources().getDrawable(R.drawable.pans);
		buttonListener = new ButtonListener();
		mainTable = (TableLayout) findViewById(R.id.TableLayout03);
		context = mainTable.getContext();
		// khoi tao ma tran game 4x3 - easy luc moi bat dau
		newGame(4, 3);
	}

	// progressDialog khi vao game
	public void progressDialog() {
		final ProgressDialog progressDialog = ProgressDialog.show(
				GameActivity.this, "", "Please wait for minute...", true);

		new Thread(new Runnable() {

			public void run() {
				try {
					// thoi gian cua dialog
					Thread.sleep(1500);
					// close dialog
					progressDialog.dismiss();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * set man hinh cho moi man choi tham so truyen vao: so dong, so cot
	 */
	private void newGame(int collum, int row) {
		ROW_COUNT = row;
		COL_COUNT = collum;
		count = 0;
		miss = 0;

		// Mang item
		items = new int[COL_COUNT][ROW_COUNT];

		// remove tableRow01,02
		// mainTable.removeView(findViewById(R.id.TableRow01));
		mainTable.removeView(findViewById(R.id.TableRow02));

		// remove tat ca cac itm tren man hinh choi
		TableRow tr = ((TableRow) findViewById(R.id.TableRow03));
		tr.removeAllViews();

		// add mainTable vao tableRow03
		mainTable = new TableLayout(context);
		tr.addView(mainTable);

		// add item thanh tung dong
		for (int i = 0; i < ROW_COUNT; i++) {
			mainTable.addView(createRow(i));
		}

		firstItem = null;
		loadItems();

	}

	/**
	 * LOAD IMAGE CHO ITEM tao arraylist chua resource hinh anh trong drawble
	 * tuong ung voi chu de
	 */
	private void loadItems(int load) {
		topic = new ArrayList<Drawable>();
		//cookie
		if (load == 1) {
			topic.add(getResources().getDrawable(R.drawable.item2));
			topic.add(getResources().getDrawable(R.drawable.item3));
			topic.add(getResources().getDrawable(R.drawable.item4));
			topic.add(getResources().getDrawable(R.drawable.item5));
			topic.add(getResources().getDrawable(R.drawable.item6));
			topic.add(getResources().getDrawable(R.drawable.item7));
			topic.add(getResources().getDrawable(R.drawable.item8));
			topic.add(getResources().getDrawable(R.drawable.item9));
			topic.add(getResources().getDrawable(R.drawable.item10));
			topic.add(getResources().getDrawable(R.drawable.item11));
			topic.add(getResources().getDrawable(R.drawable.item12));
			topic.add(getResources().getDrawable(R.drawable.item13));
			topic.add(getResources().getDrawable(R.drawable.item14));
			topic.add(getResources().getDrawable(R.drawable.item15));
			topic.add(getResources().getDrawable(R.drawable.item16));
			topic.add(getResources().getDrawable(R.drawable.item17));
			topic.add(getResources().getDrawable(R.drawable.item18));
			topic.add(getResources().getDrawable(R.drawable.item19));
			topic.add(getResources().getDrawable(R.drawable.item20));
			topic.add(getResources().getDrawable(R.drawable.item21));
			topic.add(getResources().getDrawable(R.drawable.item25));
		} 
		//cake
		else if (load == 2) {
			topic.add(getResources().getDrawable(R.drawable.gato1));
			topic.add(getResources().getDrawable(R.drawable.gato2));
			topic.add(getResources().getDrawable(R.drawable.gato3));
			topic.add(getResources().getDrawable(R.drawable.gato4));
			topic.add(getResources().getDrawable(R.drawable.gato5));
			topic.add(getResources().getDrawable(R.drawable.gato6));
			topic.add(getResources().getDrawable(R.drawable.gato7));
			topic.add(getResources().getDrawable(R.drawable.gato8));
			topic.add(getResources().getDrawable(R.drawable.gato9));
			topic.add(getResources().getDrawable(R.drawable.gato13));
			topic.add(getResources().getDrawable(R.drawable.gato19));
			topic.add(getResources().getDrawable(R.drawable.gato20));
			topic.add(getResources().getDrawable(R.drawable.gato21));
			topic.add(getResources().getDrawable(R.drawable.gato22));
			topic.add(getResources().getDrawable(R.drawable.gato23));
			topic.add(getResources().getDrawable(R.drawable.gato24));
			topic.add(getResources().getDrawable(R.drawable.gato25));
			topic.add(getResources().getDrawable(R.drawable.gato26));
			topic.add(getResources().getDrawable(R.drawable.gato23));
			topic.add(getResources().getDrawable(R.drawable.gato10));
			topic.add(getResources().getDrawable(R.drawable.gato11));
		}
		//food
		else if (load == 3) {
			topic.add(getResources().getDrawable(R.drawable.food1));
			topic.add(getResources().getDrawable(R.drawable.food2));
			topic.add(getResources().getDrawable(R.drawable.food3));
			topic.add(getResources().getDrawable(R.drawable.food4));
			topic.add(getResources().getDrawable(R.drawable.food5));
			topic.add(getResources().getDrawable(R.drawable.food6));
			topic.add(getResources().getDrawable(R.drawable.food7));
			topic.add(getResources().getDrawable(R.drawable.food8));
			topic.add(getResources().getDrawable(R.drawable.food9));
			topic.add(getResources().getDrawable(R.drawable.food10));
			topic.add(getResources().getDrawable(R.drawable.food11));
			topic.add(getResources().getDrawable(R.drawable.food12));
			topic.add(getResources().getDrawable(R.drawable.food13));
			topic.add(getResources().getDrawable(R.drawable.food14));
			topic.add(getResources().getDrawable(R.drawable.food15));
			topic.add(getResources().getDrawable(R.drawable.food16));
			topic.add(getResources().getDrawable(R.drawable.food17));
			topic.add(getResources().getDrawable(R.drawable.food18));
			topic.add(getResources().getDrawable(R.drawable.food19));
			topic.add(getResources().getDrawable(R.drawable.food20));
			topic.add(getResources().getDrawable(R.drawable.food21));
		}
		//fastfood
		else if (load == 4) {
			topic.add(getResources().getDrawable(R.drawable.fastfood1));
			topic.add(getResources().getDrawable(R.drawable.fastfood2));
			topic.add(getResources().getDrawable(R.drawable.fastfood3));
			topic.add(getResources().getDrawable(R.drawable.fastfood4));
			topic.add(getResources().getDrawable(R.drawable.fastfood5));
			topic.add(getResources().getDrawable(R.drawable.fastfood6));
			topic.add(getResources().getDrawable(R.drawable.fastfood7));
			topic.add(getResources().getDrawable(R.drawable.fastfood8));
			topic.add(getResources().getDrawable(R.drawable.fastfood9));
			topic.add(getResources().getDrawable(R.drawable.fastfood10));
			topic.add(getResources().getDrawable(R.drawable.fastfood11));
			topic.add(getResources().getDrawable(R.drawable.fastfood12));
			topic.add(getResources().getDrawable(R.drawable.fastfood13));
			topic.add(getResources().getDrawable(R.drawable.fastfood14));
			topic.add(getResources().getDrawable(R.drawable.fastfood15));
			topic.add(getResources().getDrawable(R.drawable.fastfood16));
			topic.add(getResources().getDrawable(R.drawable.fastfood17));
			topic.add(getResources().getDrawable(R.drawable.fastfood18));
			topic.add(getResources().getDrawable(R.drawable.fastfood19));
			topic.add(getResources().getDrawable(R.drawable.fastfood20));
			topic.add(getResources().getDrawable(R.drawable.fastfood21));
		}
		//drink
		else if(load==5) {
			topic.add(getResources().getDrawable(R.drawable.drink1));
			topic.add(getResources().getDrawable(R.drawable.drink2));
			topic.add(getResources().getDrawable(R.drawable.drink3));
			topic.add(getResources().getDrawable(R.drawable.drink4));
			topic.add(getResources().getDrawable(R.drawable.drink5));
			topic.add(getResources().getDrawable(R.drawable.drink6));
			topic.add(getResources().getDrawable(R.drawable.drink7));
			topic.add(getResources().getDrawable(R.drawable.drink8));
			topic.add(getResources().getDrawable(R.drawable.drink9));
			topic.add(getResources().getDrawable(R.drawable.drink10));
			topic.add(getResources().getDrawable(R.drawable.drink11));
			topic.add(getResources().getDrawable(R.drawable.drink12));
			topic.add(getResources().getDrawable(R.drawable.drink13));
			topic.add(getResources().getDrawable(R.drawable.drink14));
			topic.add(getResources().getDrawable(R.drawable.drink15));
			topic.add(getResources().getDrawable(R.drawable.drink16));
			topic.add(getResources().getDrawable(R.drawable.drink17));
			topic.add(getResources().getDrawable(R.drawable.drink18));
			topic.add(getResources().getDrawable(R.drawable.drink19));
			topic.add(getResources().getDrawable(R.drawable.drink20));
			topic.add(getResources().getDrawable(R.drawable.drink21));
		}
		//ice-cream
		else {
			topic.add(getResources().getDrawable(R.drawable.cream1));
			topic.add(getResources().getDrawable(R.drawable.cream2));
			topic.add(getResources().getDrawable(R.drawable.cream3));
			topic.add(getResources().getDrawable(R.drawable.cream4));
			topic.add(getResources().getDrawable(R.drawable.cream5));
			topic.add(getResources().getDrawable(R.drawable.cream6));
			topic.add(getResources().getDrawable(R.drawable.cream7));
			topic.add(getResources().getDrawable(R.drawable.cream8));
			topic.add(getResources().getDrawable(R.drawable.cream9));
			topic.add(getResources().getDrawable(R.drawable.cream10));
			topic.add(getResources().getDrawable(R.drawable.cream11));
			topic.add(getResources().getDrawable(R.drawable.cream12));
			topic.add(getResources().getDrawable(R.drawable.cream13));
			topic.add(getResources().getDrawable(R.drawable.cream14));
			topic.add(getResources().getDrawable(R.drawable.cream15));
			topic.add(getResources().getDrawable(R.drawable.cream16));
			topic.add(getResources().getDrawable(R.drawable.cream17));
			topic.add(getResources().getDrawable(R.drawable.cream18));
			topic.add(getResources().getDrawable(R.drawable.cream19));
			topic.add(getResources().getDrawable(R.drawable.cream20));
			topic.add(getResources().getDrawable(R.drawable.cream21));
		}

	}

	/**
	 * Load cac item hien thi voi gia tri hinh random trong ma tran items[][]
	 */
	private void loadItems() {

		// kich co ma tran game play
		int size = ROW_COUNT * COL_COUNT;

		Log.i("loadItems()", "size=" + size);

		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < size; i++) {
			list.add(new Integer(i));
		}
		Random rand = new Random();

		for (int i = size - 1; i >= 0; i--) {
			int t = 0;

			if (i > 0) {
				t = rand.nextInt(i);
			}

			t = list.remove(t).intValue();
			items[i % COL_COUNT][i / COL_COUNT] = t % (size / 2);

			Log.i("loadItems()", "item[" + (i % COL_COUNT) + "]["
					+ (i / COL_COUNT) + "]="
					+ items[i % COL_COUNT][i / COL_COUNT]);
		}

	}

	/*
	 * Khoi tao dong item voi vi tri giua man hinh tham so truyen vao la so dong
	 */
	private TableRow createRow(int i) {
		TableRow row = new TableRow(context);
		row.setHorizontalGravity(Gravity.CENTER);

		for (int j = 0; j < COL_COUNT; j++) {
			row.addView(createImageButton(j, i));
		}
		return row;
	}

	/*
	 * Tao view load anh item luc ban dau (lock) dang ki su kien cho cac Button
	 */
	private View createImageButton(int x, int y) {
		Button button = new Button(context);
		button.setBackgroundDrawable(backItem);
		button.setId(100 * x + y);
		button.setOnClickListener(buttonListener);
		return button;
	}

	/**
	 * 
	 * CLASS SU KIEN KHI CHON ITEM
	 * 
	 */
	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			synchronized (lock) {
				if (firstItem != null && secondItem != null) {
					return;

				}

				int id = v.getId();
				int x = id / 100;
				int y = id % 100;
				turnItems((Button) v, x, y);

			}

		}

		/**
		 * Hinh item se duoc mo khi click vao
		 */
		private void turnItems(Button button, int x, int y) {
			// background cho button
			button.setBackgroundDrawable(topic.get(items[x][y]));
			// sound khi click 1 item
			playClickSound();
			// khoi tao cho doi tuong item khi click vao
			if (firstItem == null) {

				firstItem = new Items(button, x, y);
			} else {

				if (firstItem.x == x && firstItem.y == y) {
					return;
				}

				secondItem = new Items(button, x, y);

				TimerTask tt = new TimerTask() {

					@Override
					public void run() {
						synchronized (lock) {
							update.sendEmptyMessage(0);
						}
					}
				};

				Timer t = new Timer(false);
				t.schedule(tt, 800);
			}

		}

	}

	/**
	 * UPDATE GIA TRI CHO CAC ITEM Kiem tra ket qua choi
	 */
	class UpdateItems extends Handler {

		@Override
		public void handleMessage(Message msg) {
			synchronized (lock) {
				checkItems();
			}
		}

		/*
		 * Kiem tra sau moi lan chon mo 1 cap item
		 */
		public void checkItems() {

			// neu 2 item co cung gia tri thi se bien mat khoiman hinh
			if (items[secondItem.x][secondItem.y] == items[firstItem.x][firstItem.y]) {
				// sound khi mo trung 1 cap item
				playCorrectSound();
				firstItem.button.setVisibility(View.INVISIBLE);
				secondItem.button.setVisibility(View.INVISIBLE);

				count++;
				// Hien thi Dialog khi chien thang 1 man choi
				if (count == (ROW_COUNT * COL_COUNT) / 2) {
					playWinSound();
					winDialog();
				}
			} else {
				// khi chon khong trung nhau, 2 item vua chon se duoc up lai
				// voi background ban dau
				playInCorrectSound();
				secondItem.button.setBackgroundDrawable(backItem);
				firstItem.button.setBackgroundDrawable(backItem);
				miss++;
				// Hien thi dialog voi 2 lua chon: choi lai va thoat game khi mo
				// sai item qua so lan quy dinh
				if (miss == (ROW_COUNT * COL_COUNT) / 2) {
					TableRow tr = ((TableRow) findViewById(R.id.TableRow03));
					// remove toan bo item khoi man hinh
					tr.removeAllViews();
					playLoseSound();
					loseDialog();
				}
			}

			firstItem = null;
			secondItem = null;

		}
	}

	/**
	 * Am thanh khi mo 1 item, chon dung 1 cap item trung nhau
	 */
	public void playMenuSound() {
		stopSound();
		sound = MediaPlayer.create(this, R.raw.click);
		sound.start();
	}
	
	public void playClickSound() {
		stopSound();
		sound = MediaPlayer.create(this, R.raw.brickogg);
		sound.start();
	}

	public void playCorrectSound() {
		stopSound();
		sound = MediaPlayer.create(this, R.raw.eat);
		sound.start();
	}

	public void playInCorrectSound() {
		stopSound();
		sound = MediaPlayer.create(this, R.raw.uhoh);
		sound.start();

	}
	
	public void playWinSound() {
		stopSound();
		sound = MediaPlayer.create(this, R.raw.win);
		sound.start();

	}
	
	public void playLoseSound() {
		stopSound();
		sound = MediaPlayer.create(this, R.raw.lose);
		sound.start();

	}

	public void stopSound() {
		if (sound != null) {
			sound.stop();
			sound.release();
			sound = null;

		}
	}

	/**
	 * Dialog khi chien thang man choi
	 */
	private void winDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Thắng rồi! Chúc mừng bạn.\n Chơi tiếp level "+ (level+1)+" chứ?");
		
		alert.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				level++;
				nextLevel();

			}
		}).setNegativeButton("Menu", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		}).show();
	}

	/**
	 * Dialog khi chon sai qua nhieu
	 */
	private void loseDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Sorry! Game over. Bạn lật sai quá " + ((ROW_COUNT * COL_COUNT) / 2)
				+ " lần rồi. Chơi lại?");
		alert.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				level = 1;
				foodChoiceDialog();
			}
		})
				.setNegativeButton("Menu",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();

							}
						}).show();
	}

	/**
	 * NextLevel
	 */
	public void nextLevel() {
		switch (level) {
		case 1:
			newGame(4, 3);
			break;
		case 2:
			newGame(4, 4);
			break;
		case 3:
			newGame(4, 5);
			break;
		case 4:
			newGame(4, 6);
			break;
		case 5:
			newGame(5, 6);
			break;
		}
	}

	/**
	 * Context menu lua chon kieu choi
	 */
	public void foodChoiceDialog() {
		final CharSequence[] items = { "Cookie", "Cake", "Food", "Fast Food",
				"Drink", "Ice-cream" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Mời bạn chọn chủ đề");
		builder.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int item) {
						switch (item) {
						case 0: {
							loadItems(1);
							newGame(4, 3);
							break;
						}
						case 1: {
							loadItems(2);
							newGame(4, 3);
							break;
						}
						case 2: {
							loadItems(3);
							newGame(4, 3);
							break;
						}
						case 3: {
							loadItems(4);
							newGame(4, 3);
							break;
						}
						case 4: {
							loadItems(5);
							newGame(4, 3);
							break;
						}
						case 5: {
							loadItems(6);
							newGame(4, 3);
							break;
						}
						}

					}
				}).setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}

				});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
