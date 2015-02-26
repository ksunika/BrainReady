package play.ksunika.brainready;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button colorButton = (Button) findViewById(R.id.colorButton);
		Button shapeButton = (Button) findViewById(R.id.shapeButton);

		colorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				startColorGame();

			}
		});

		shapeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				startShapeGame();

			}
		});
	}

	protected void startColorGame() {
		Intent intent = new Intent(this, ColorRules.class);
		startActivity(intent);
		
	}
	
	protected void startShapeGame() {
		Intent intent = new Intent(this, ShapeRules.class);
		startActivity(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.uninstall:
			Uri packageUri = Uri.parse("package:play.ksunika.brainready");
			Intent myActivity = new Intent(Intent.ACTION_UNINSTALL_PACKAGE,
					packageUri);
			startActivity(myActivity);

			return true;
		}
		return false;
	}

}
