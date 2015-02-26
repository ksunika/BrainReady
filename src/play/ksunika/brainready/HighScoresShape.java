package play.ksunika.brainready;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class HighScoresShape extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores_shape);
		// Show the Up button in the action bar.
		setupActionBar();
		TextView hs1 = (TextView) findViewById(R.id.hs1Shape);
		TextView hs2 = (TextView) findViewById(R.id.hs2Shape);
		TextView hs3 = (TextView) findViewById(R.id.hs3Shape);
		TextView hs4 = (TextView) findViewById(R.id.hs4Shape);
		TextView hs5 = (TextView) findViewById(R.id.hs5Shape);
		TextView hs6 = (TextView) findViewById(R.id.hs6Shape);
		TextView hs7 = (TextView) findViewById(R.id.hs7Shape);

		Bundle extras = getIntent().getExtras();
		int score = 0;

		if (extras != null) {
			score = extras.getInt("SCORE");
		}
		TextView header = (TextView) findViewById(R.id.headerHighScoresShape);
		String text = (String) header.getText();
		header.setText(text + score);

		SharedPreferences scores = getPreferences(0);

		int i1 = scores.getInt("hs1", 0);
		int i2 = scores.getInt("hs2", 0);
		int i3 = scores.getInt("hs3", 0);
		int i4 = scores.getInt("hs4", 0);
		int i5 = scores.getInt("hs5", 0);
		int i6 = scores.getInt("hs6", 0);
		int i7 = scores.getInt("hs7", 0);

		if (score > i1) {
			i7 = i6;
			i6 = i5;
			i5 = i4;
			i4 = i3;
			i3 = i2;
			i2 = i1;
			i1 = score;
			
			hs1.setBackgroundColor(Color.parseColor("#ffecc0"));
			
			
		} else

		if (score > i2) {
			i7 = i6;
			i6 = i5;
			i5 = i4;
			i4 = i3;
			i3 = i2;
			i2 = score;
			
			hs2.setBackgroundColor(Color.parseColor("#ffecc0"));
		} else

		if (score > i3) {
			i7 = i6;
			i6 = i5;
			i5 = i4;
			i4 = i3;
			i3 = score;
			
			hs3.setBackgroundColor(Color.parseColor("#ffecc0"));
		} else

		if (score > i4) {
			i7 = i6;
			i6 = i5;
			i5 = i4;
			i4 = score;
			
			hs4.setBackgroundColor(Color.parseColor("#ffecc0"));
		} else if (score > i5) {
			i7 = i6;
			i6 = i5;
			i5 = score;
			hs5.setBackgroundColor(Color.parseColor("#ffecc0"));
		} else if (score > i6) {
			i7 = i6;
			i6 = score;
			hs6.setBackgroundColor(Color.parseColor("#ffecc0"));
		} else if (score > i7) {
			i7 = score;
			hs7.setBackgroundColor(Color.parseColor("#ffecc0"));
		}

		SharedPreferences.Editor editor = scores.edit();
		editor.putInt("hs1", i1);
		editor.putInt("hs2", i2);
		editor.putInt("hs3", i3);
		editor.putInt("hs4", i4);
		editor.putInt("hs5", i5);
		editor.putInt("hs6", i6);
		editor.putInt("hs7", i7);
		editor.commit();
		
		String s1, s2, s3, s4, s5, s6, s7;

		if (i1 > 0)
			s1 = Integer.toString(i1);
		else
			s1 = " - ";

		if (i2 > 0)
			s2 = Integer.toString(i2);
		else
			s2 = " - ";

		if (i3 > 0)
			s3 = Integer.toString(i3);
		else
			s3 = " - ";

		if (i4 > 0)
			s4 = Integer.toString(i4);
		else
			s4 = " - ";

		if (i5 > 0)
			s5 = Integer.toString(i5);
		else
			s5 = " - ";

		if (i6 > 0)
			s6 = Integer.toString(i6);
		else
			s6 = " - ";

		if (i7 > 0)
			s7 = Integer.toString(i7);
		else
			s7 = " - ";
		
		
		hs1.setText("1. " + s1);
		hs2.setText("2. " + s2);
		hs3.setText("3. " + s3);
		hs4.setText("4. " + s4);
		hs5.setText("5. " + s5);
		hs6.setText("6. " + s6);
		hs7.setText("7. " + s7);
		
		
		Button mainMenuButton = (Button) findViewById(R.id.hsShapeMainMenu);
		Button playAgainButton = (Button) findViewById(R.id.hsShapePlayAgain);
		
		mainMenuButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				onBackPressed();

			}
		});
		
		playAgainButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getBaseContext(), ShapeGame.class);
				startActivity(intent);

			}
		});

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
		getMenuInflater().inflate(R.menu.high_scores, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		NavUtils.navigateUpFromSameTask(this);

	}

}
