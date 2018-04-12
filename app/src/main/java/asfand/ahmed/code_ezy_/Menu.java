package asfand.ahmed.code_ezy_;

import asfand.ahmed.code_ezy_.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_menu);
	}
	public void play(View v)
	{
		Intent i = new Intent(v.getContext(),MainActivity.class);
		startActivity(i);
	}
	public void high_score(View v)
	{
		Intent i = new Intent(v.getContext(),Score.class);
		startActivity(i);
	}
	public void settings(View v)
	{
		Intent i = new Intent(v.getContext(),Settings.class);
		startActivity(i);
	}
	public void credits(View v)
	{
		Intent i = new Intent(v.getContext(),Credits.class);
		startActivity(i);
	}
	public void quit(View v)
	{
		System.exit(0);
	}
}
