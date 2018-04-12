package asfand.ahmed.code_ezy_;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asfand.ahmed.code_ezy_.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import asfand.ahmed.code_ezy_.bl.PuzzleManager;
import asfand.ahmed.code_ezy_.bl.Score;
import asfand.ahmed.terlici.dragndroplist.DragNDropListView;
import asfand.ahmed.terlici.dragndroplist.DragNDropListener;
import asfand.ahmed.terlici.dragndroplist.DragNDropSimpleAdapter;

public class MainActivity extends Activity {
	protected List<String> puzzleList,answerList;
	protected DragNDropSimpleAdapter adaptor;
	protected DragNDropListView list;
	protected View view;
	public boolean win=false;
	public Score score;
	public String level;
	public Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_main);
		context = this;
		score = new Score();
		startPuzzle();
	}
	
	public static boolean compareList(List ls1,List ls2){
        return ls1.equals(ls2) ? true : false;
    }
	
	public void startPuzzle()
	{
			PuzzleManager pm 	= new PuzzleManager(getApplicationContext());
			level 			    = getLevel();
			pm.createDatabase();
			pm.open();
			List<String> puzzleList = pm.get_puzzleList(level);
			setPuzzleAnswerLists(puzzleList);
			ArrayList<Map<String, Object>> listItems = getListViewItems();
			buildListView(listItems);
			pm.close();
	}
	public void buildListView(ArrayList<Map<String, Object>> items)
	{
		this.list = (DragNDropListView)findViewById(android.R.id.list);
		this.adaptor = new DragNDropSimpleAdapter(getApplicationContext(), items, R.layout.row, new String[]{"puzzle"}, new int[]{R.id.text}, R.id.handler);
		LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.row, null);
		this.list.setDragNDropAdapter(adaptor);
		this.list.setOnItemDragNDropListener(mDragNDropListener);
	}
	private DragNDropListener mDragNDropListener =
			new DragNDropListener()
	{
		public void onItemDrop(DragNDropListView parent, View view, int startPosition, int endPosition, long id)
		{
			super.onItemDrop(parent, view, startPosition, endPosition, id);
			if(startPosition > endPosition){
				for(int i = startPosition; i > endPosition; --i)
					Collections.swap(puzzleList, i, i-1);
			}else if(startPosition <  endPosition){
				for(int i = startPosition; i < endPosition; ++i)
					Collections.swap(puzzleList, i, i+1);
			}
			score.numOfSteps++;
			if(compareList(puzzleList, answerList)){
				endPuzzle();
			}
		}

	};
	protected void  endPuzzle(){
		win = true;
		int total = score.calculate(level);
		boolean isHighScore = score.setHighScore(getBaseContext(), total);
		String msg = (isHighScore) ? "High Score" : "Your Score";
		msg += ": " + total;
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		Handler hd = new Handler();
		hd.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(context, Menu.class);
				startActivity(i);
			}

		}, 1000);
	}
	public String getLevel()
	{
		SharedPreferences appPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		return appPrefs.getString("levelPref", "1");
	}
	public void setPuzzleAnswerLists(final List<String> l)
	{
					puzzleList	=	new ArrayList<String>(l);
					answerList	=	new ArrayList<String>(l);
					Collections.shuffle(puzzleList);
	}
	public ArrayList<Map<String, Object>> getListViewItems()
	{
		ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
					for(int i = 0; i < puzzleList.size(); ++i) {
						HashMap<String, Object> item = new HashMap<String, Object>();
						item.put("puzzle", puzzleList.get(i));
						items.add(item);
					}
		return items;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
