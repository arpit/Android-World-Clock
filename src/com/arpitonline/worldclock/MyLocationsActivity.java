package com.arpitonline.worldclock;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class MyLocationsActivity extends SherlockFragmentActivity {
	
	public static final String TAG = "MyLocationsActivity";
	
	private Dialog introDialog;
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.my_locations_activity);
		this.getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.my_locations_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.search_mi:
			this.onSearchRequested();
			return true;
		case R.id.feedback_menuitem:
			Intent gotoFeedback = new Intent(Intent.ACTION_VIEW,
					Uri.parse(getString(R.string.feedback_url)));
			startActivity(gotoFeedback);
			return true;
		case R.id.about:

			LayoutInflater inflater = (LayoutInflater) this
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.about_dialog,
					(ViewGroup) findViewById(R.id.layout_root));

			TextView t1 = (TextView) layout.findViewById(R.id.by);
			t1.setMovementMethod(LinkMovementMethod.getInstance());
			t1.setText(Html.fromHtml(getString(R.string.developed_by)));

			TextView t2 = (TextView) layout.findViewById(R.id.feedback_at);
			t2.setMovementMethod(LinkMovementMethod.getInstance());
			t2.setText(Html.fromHtml(getString(R.string.feedback_at)));

			TextView t3 = (TextView) layout.findViewById(R.id.source_at);
			t3.setMovementMethod(LinkMovementMethod.getInstance());
			t3.setText(Html.fromHtml(getString(R.string.source_at)));

			TextView versionTxt = (TextView) layout.findViewById(R.id.version);

			try {
				PackageManager manager = this.getPackageManager();
				PackageInfo info = manager.getPackageInfo(
						this.getPackageName(), 0);
				versionTxt.setText("Version: " + info.versionName);
			} catch (Exception e) {
				versionTxt.setText("x");
			}

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setView(layout);
			builder.setPositiveButton("Cool", null);
			AlertDialog alert = builder.create();
			alert.setTitle("About " + getString(R.string.app_name));

			alert.show();

			return true;
		case R.id.clear:
			MyLocationsFragment fragment = (MyLocationsFragment) getSupportFragmentManager()
					.findFragmentById(R.id.my_locations);
			fragment.clearLocations();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
