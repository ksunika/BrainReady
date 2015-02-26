package play.ksunika.brainready;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ColorRules extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_rules);
		// Show the Up button in the action bar.
		setupActionBar();
		Button startButton = (Button) findViewById(R.id.startButton);

		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				startColorGame();

			}
		});
	}

	protected void startColorGame() {
		Intent intent = new Intent(this, ColorGame.class);
		startActivity(intent);
		
	}
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onBackPressed() {
		NavUtils.navigateUpFromSameTask(this);

	}
}
