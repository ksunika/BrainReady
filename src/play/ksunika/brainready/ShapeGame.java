package play.ksunika.brainready;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
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

public class ShapeGame extends Activity {

	Integer[] shapes = { R.drawable.circle, R.drawable.square,
			R.drawable.rombus, R.drawable.triangle };

	int currentShapeIndex = 0;
	int previousShapeIndex = 0;
	int initial_timer = 60;

	int timer = initial_timer;

	CountDownTimer myTimer;

	int currentScore = 0;
	int timesRight = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shape_game);
		// Show the Up button in the action bar.
		setupActionBar();

		timer = initial_timer;// seconds for countdown timer

		UpdateScore();

		showShape();
		showShape();

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
				// showMyAlertDialog(ShapeGame.this);

				Intent intent = new Intent(getBaseContext(),
						HighScoresShape.class);
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

	private void showShape() {

		Handler handler = new Handler();
		handler.postDelayed(new showNewShape(), 100);

	}

	private void UpdateScore() {

		final TextView scoreTextView = (TextView) findViewById(R.id.textViewScore);
		int Score = currentScore * 100;
		scoreTextView.setText(Integer.toString(Score));

	}

	private class showNewShape implements Runnable {

		@Override
		public void run() {

			ImageView shapeImageView = (ImageView) findViewById(R.id.shapeImage);

			ImageView image = (ImageView) findViewById(R.id.correctOrNot);
			image.setImageDrawable(null);

			ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
			viewFlipper.setDisplayedChild(0);

			// order of below is important

			previousShapeIndex = currentShapeIndex;
			// currentShapeIndex = RandomShape();
			shapeImageView.setImageResource(RandomShape());

			ViewFlipper viewFlipperColor = (ViewFlipper) findViewById(R.id.view_flipper_color);

			viewFlipperColor
					.setInAnimation(ShapeGame.this, R.anim.in_from_left);
			viewFlipperColor.setOutAnimation(ShapeGame.this,
					R.anim.out_to_right);
			// Show the next Screen
			viewFlipperColor.showNext();
		}
	}

	private Integer RandomShape() {

		Random rand = new Random();

		currentShapeIndex = rand.nextInt(shapes.length);
		int random = rand.nextInt(3);
		if (random == 0)
			currentShapeIndex = previousShapeIndex;

		return shapes[currentShapeIndex];

	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shape_game, menu);
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

		case R.id.shaperules:

			cancelTimer();

			Intent intent = new Intent(this, ShapeRules.class);
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
		// TextView colorTextView = (TextView) findViewById(R.id.textViewColor);

		if ((currentShapeIndex == previousShapeIndex && YN == 1)
				|| (currentShapeIndex != previousShapeIndex && YN == 0)) {

			ImageView image = (ImageView) findViewById(R.id.correctOrNot);
			image.setImageResource(R.drawable.correct);

			makeSound(R.raw.yes);

			timesRight++;

			for (int i = 0; i < timesRight / 10; i++)
				currentScore++;

			currentScore++;

			UpdateScore();
		}

		else {

			ImageView image = (ImageView) findViewById(R.id.correctOrNot);
			image.setImageResource(R.drawable.wrong);

			makeSound(R.raw.no);
			
			timesRight = 0;

		}
		showShape();
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
						.setInAnimation(ShapeGame.this, R.anim.in_from_right);
				viewFlipper.setOutAnimation(ShapeGame.this, R.anim.out_to_left);
				// Show The Previous Screen
				viewFlipper.showPrevious();
				isAnswerOK(0);

				// Toast.makeText(ColorGame.this, "Right to Left",
				// Toast.LENGTH_SHORT).show();

				return true;
			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				// From Left to Right

				viewFlipper.setInAnimation(ShapeGame.this, R.anim.in_from_left);
				viewFlipper
						.setOutAnimation(ShapeGame.this, R.anim.out_to_right);
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

	private void showMyAlertDialog(final ShapeGame shapeGame) {
		new AlertDialog.Builder(shapeGame).setTitle("Time is up")
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