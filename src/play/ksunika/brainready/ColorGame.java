package play.ksunika.brainready;

import java.util.Random;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ColorGame extends Activity {

	String[] words = new String[5];// = { "BLUE","PURPLE", "GREEN", "YELLOW", "RED"};
	String[] colors = { "#33B5E5", "#AA66CC", "#99CC00", "#FFBB33", "#FF4444" };

	int currentWordIndex = -1;
	int currentColorIndex = -1;
	int initial_timer = 60;

	int timer = initial_timer;

	CountDownTimer myTimer;

	int currentScore = 0;
	int timesRight = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_game);
		// Show the Up button in the action bar.
		setupActionBar();

		words = new String[] {getString(R.string.blue), getString(R.string.purple), getString(R.string.green), getString(R.string.yellow), getString(R.string.red)};		
		
		timer = initial_timer;// seconds for countdown timer

		UpdateScore();

		showColor();

		myTimer = new CountDownTimer(timer * 1000, 1000) {

			TextView textViewCountdown = (TextView) findViewById(R.id.textViewCountdown);

			public void onTick(long millisUntilFinished) {
				if (millisUntilFinished / 1000 > 9)
					textViewCountdown.setText("00 : " + millisUntilFinished
							/ 1000);
				else
					textViewCountdown.setText("00 : 0" + millisUntilFinished
							/ 1000);
			}

			public void onFinish() {
				textViewCountdown.setText("00 : 00");
				// showMyAlertDialog(ColorGame.this);

				Intent intent = new Intent(getBaseContext(),
						HighScoresColor.class);
				intent.putExtra("SCORE", currentScore * 100);
				startActivity(intent);

			}
		}.start();

		ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);

		final GestureDetector gestureDetector = new GestureDetector(this,
				new GestureListener());

		viewFlipper.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event))
					return true;
				else
					return false;
			}
		});

	}

	private void showColor() {
		Handler handler = new Handler();
		handler.postDelayed(new showNewColor(), 100);

	}

	private void UpdateScore() {

		final TextView scoreTextView = (TextView) findViewById(R.id.textViewScore);
		int Score = currentScore * 100;
		scoreTextView.setText(Integer.toString(Score));

	}

	private class showNewColor implements Runnable {

		@Override
		public void run() {
			TextView colorTextView = (TextView) findViewById(R.id.textViewColor);
			TextView colorTextView2 = (TextView) findViewById(R.id.textViewColor2);

			ImageView image = (ImageView) findViewById(R.id.correctOrNot);
			image.setImageDrawable(null);

			ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
			viewFlipper.setDisplayedChild(0);

			// order of below is important
			String color = RandomColor();
			colorTextView.setTextColor(Color.parseColor(color));

			color = RandomColor();
			colorTextView2.setTextColor(Color.parseColor(color));
			// current color is the color of colorTextView2

			String word = RandomText();
			colorTextView2.setText(word);

			word = RandomText();
			colorTextView.setText(word); // current text is the text of
											// colorTextView

			ViewFlipper viewFlipperColor = (ViewFlipper) findViewById(R.id.view_flipper_color);

			viewFlipperColor
					.setInAnimation(ColorGame.this, R.anim.in_from_left);
			viewFlipperColor.setOutAnimation(ColorGame.this,
					R.anim.out_to_right);
			// Show the next Screen
			viewFlipperColor.showNext();
		}
	}

	private String RandomText() {

//		Random rand = new Random();
//
//		currentWordIndex = rand.nextInt(words.length);
//		
//		int random = rand.nextInt(3); // make it more dependent on the other
//										// word, less random
//		if (random == 0)
//			currentWordIndex = currentColorIndex;
		
		Random rand = new Random();
		
		if (rand.nextInt(2) == 0)
			currentWordIndex = currentColorIndex;
		else 
			currentWordIndex = rand.nextInt(words.length-1);
		
		
		return words[currentWordIndex];

	}

	private String RandomColor() {

		Random rand = new Random();

		currentColorIndex = rand.nextInt(colors.length);
		return colors[currentColorIndex];

	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.color_game, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		leave();

	}
	@Override
	public void onStop() {
		cancelTimer();
		super.onStop();
		finish();

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

			leave();
			return true;

		case R.id.gamerules:

			cancelTimer();

			Intent intent = new Intent(this, ColorRules.class);
			startActivity(intent);
			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	private void leave() {
		cancelTimer();
		NavUtils.navigateUpFromSameTask(this);

	}

	private void cancelTimer() {
		if (myTimer != null) {
			myTimer.cancel();
			myTimer = null;
		}
	}

	private void isAnswerOK(int YN) {

		if ((currentWordIndex == currentColorIndex && YN == 1)
				|| (currentWordIndex != currentColorIndex && YN == 0)) {

			ImageView image = (ImageView) findViewById(R.id.correctOrNot);
			image.setImageResource(R.drawable.correct);

			makeSound(R.raw.yes); // insert Yes Sound here

			timesRight++;
			
			for (int i = 0; i< timesRight/10; i++)
				currentScore++;
			
			currentScore++;
			
			UpdateScore();

		}

		else {

			ImageView image = (ImageView) findViewById(R.id.correctOrNot);
			image.setImageResource(R.drawable.wrong);

			makeSound(R.raw.no); // insert No Sound here
			
			timesRight = 0;

		}
		showColor();

	}

	private void makeSound(int resid) {
		MediaPlayer mp = MediaPlayer.create(this, resid);

		mp.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.reset();
				mp.release();
			}

		});
		mp.start();
	}

	public class GestureListener extends SimpleOnGestureListener {

		private static final int SWIPE_MIN_DISTANCE = 20;
		private static final int SWIPE_THRESHOLD_VELOCITY = 20;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);

			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				// From Right to Left

				viewFlipper
						.setInAnimation(ColorGame.this, R.anim.in_from_right);
				viewFlipper.setOutAnimation(ColorGame.this, R.anim.out_to_left);
				// Show The Previous Screen
				viewFlipper.showPrevious();
				isAnswerOK(0);

				// Toast.makeText(ColorGame.this, "Right to Left",
				// Toast.LENGTH_SHORT).show();

				return true;
			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				// From Left to Right

				viewFlipper.setInAnimation(ColorGame.this, R.anim.in_from_left);
				viewFlipper
						.setOutAnimation(ColorGame.this, R.anim.out_to_right);
				// Show the next Screen
				viewFlipper.showNext();
				isAnswerOK(1);

				// Toast.makeText(ColorGame.this, "Left to Right",
				// Toast.LENGTH_SHORT).show();
				return true;
			}

			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			// always return true since all gestures always begin with onDown
			// and<br>
			// if this returns false, the framework won't try to pick up onFling
			// for example.
			return true;
		}
	}

	private void showMyAlertDialog(final ColorGame colorGame) {
		new AlertDialog.Builder(colorGame).setTitle("Time is up")
				.setMessage("Your score is: " + currentScore * 100)
				.setIcon(null)
				.setCancelable(false)
				// set three option buttons
				.setPositiveButton("Play Again",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// actions serving "YES" button go here
								currentScore = 0;
								recreate();

							}
						})// setPositiveButton

				.setNeutralButton("Main Menu",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// actions serving "OK" button go here

								finish();

							}// OnClick
						})// setNeutralButton

				.create().show();

	}// showMyAlertDialog

	

}
