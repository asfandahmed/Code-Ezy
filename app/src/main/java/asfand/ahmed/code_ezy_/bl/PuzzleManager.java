package asfand.ahmed.code_ezy_.bl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import asfand.ahmed.code_ezy_.dal.DbHelper;

public class PuzzleManager {

	private  final Context context;
	private SQLiteDatabase db;
	private DbHelper dbHelper;
	private final String TBL			=		dbHelper.TB_PUZZLES;
	private static final String ID		=		"_id";
	private static final String PUZZLE	=		"puzzle";
	private static final String LEVEL	=		"level";
	
	public PuzzleManager(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.dbHelper = new DbHelper(context);
	}

	public PuzzleManager createDatabase() throws SQLException
	{
		try
		{
			dbHelper.createDatabase();
		}
		catch (IOException mIOException)
		{
			Log.e("IOError", mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public PuzzleManager open() throws SQLException
	{
		try
		{
			dbHelper.openDatabase();
			dbHelper.close();
			db = dbHelper.getWritableDatabase();
		}
		catch (SQLException mSQLException)
		{
			Log.e("DBError", mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close()
	{
		db.close();
	}


	public long insertPuzzle(String puzzle, String level)
	{
		ContentValues values = new ContentValues();
		values.put(PUZZLE, puzzle);
		values.put(LEVEL, level);
		return db.insert(TBL, null, values);
	}
	public boolean deletePuzzle(long id)
	{
		return db.delete(TBL, ID + "=" + id, null) > 0;
	}
	public Cursor getAllPuzzles()
	{
		return db.query(TBL, new String[] {ID, PUZZLE,
				LEVEL}, null, null, null, null, null);
	}
	public Cursor getPuzzleById(long id, String level)
	{
		String query = "select * from " + TBL + " WHERE " + ID + " = " + id + " AND " + LEVEL + " = " + level;
		return this.getPuzzle(query);
	}
	public Cursor getRandomPuzzle(String level)
	{
		String query = "select * from " + TBL + " WHERE " + LEVEL + " = " + level + " ORDER BY RANDOM() LIMIT 1";
		return this.getPuzzle(query);
	}
	public Cursor getPuzzle(String query) throws SQLException
	{
		Cursor c = db.rawQuery(query, null);
		if (c != null) {
			c.moveToFirst();
		}
		db.close();
		return c;
	}
	public boolean updatePuzzle(long id, String puzzle, int level)
	{
		ContentValues val = new ContentValues();
		val.put(PUZZLE, puzzle);
		val.put(LEVEL, level);
		return db.update(TBL, val, ID + "=" + id, null) > 0;
	}
	public List<String> get_puzzleList(String level)
	{
		Cursor cursor 		= this.getRandomPuzzle(level);
		List<String> list 	= new ArrayList<String>();
		if(cursor != null && cursor.moveToFirst()){
			String s = cursor.getString(cursor.getColumnIndex(PUZZLE));
			list = Arrays.asList(s.split(","));
		}
		cursor.close();
		return list;
	}
}
