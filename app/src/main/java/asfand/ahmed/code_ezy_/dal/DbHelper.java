package asfand.ahmed.code_ezy_.dal;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DbHelper extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/asfand.ahmed.code_ezy_/databases/";
	private static final String DATABASE 		= 	"code_ezy";
	private static final int VERSION			=	2;
	public static final String TB_PUZZLES 	= 	"puzzles";
	public static final String TB_TAG_INFO 	= 	"tag_info";
	/*private final String TABLE_CREATE_PUZZLES	=	"CREATE TABLE IF NOT EXISTS " + TB_PUZZLES + " (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, puzzle	TEXT,  level INTEGER);";
	private final String TABLE_CREATE_TAG_INFO	=	"CREATE TABLE IF NOT EXISTS " + TB_TAG_INFO + " (_id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,tag TEXT, description TEXT);";
	private final String INSERTLEVEL1			=	"INSERT INTO " + TB_PUZZLES + "(puzzle, level) VALUES ('<html>,<body>,<h1>,</h1>,</body>,</html>',1),('<html>,<body>,<div>,</div>,</body>,</html>',1);";
	private final String INSERTLEVEL2			=	"INSERT INTO " + TB_PUZZLES + "(puzzle, level) VALUES ('<html>,<head>,<title>,</title>,</head>,<body>,<h1>,</h1>,</body>,</html>',2), ('<html>,<head>,<title>,</title>,</head>,<body>,<div>,<span>,</span>,</div>,</body>,</html>',2), ('<body>,<div>,<span>,</span>,</div>,</body>',2);";*/
	protected static SQLiteDatabase db;
	private final  Context context;

	public DbHelper(Context context) {
		super(context, DATABASE, null, VERSION);
		// TODO Auto-generated constructor stub
		this.db = this.getWritableDatabase();
		this.context = context;
	}

	public void createDatabase() throws IOException
	{
		boolean dbExists = checkDatabase();
		if(!dbExists)
		{
			this.getWritableDatabase();
			try
			{
				copyDatabase();
			}
			catch (IOException mIOException)
			{
				Log.d("DBError","copy not succeed");
				throw new Error("ErrorCopyingDataBase");
			}
		}
	}

	private boolean checkDatabase()
	{
		SQLiteDatabase CheckDb = null;
		try
		{
			String path = DB_PATH + DATABASE;
			CheckDb = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		}
		catch(SQLiteException mSQLiteException)
		{
			Log.e("DBError", "DatabaseNotFound " + mSQLiteException.toString());
		}

		if(CheckDb != null)
		{
			CheckDb.close();
		}
		return CheckDb != null;
	}

	private void copyDatabase() throws IOException
	{
		InputStream mInput = context.getResources().getAssets().open(DATABASE);

		String outFileName = DB_PATH + DATABASE;
		File createOutFile = new File(outFileName);
		if(!createOutFile.exists()){
			createOutFile.mkdir();
		}
		OutputStream mOutput = new FileOutputStream(outFileName);
		byte[] mBuffer = new byte[1024];
		int mLength;
		while ((mLength = mInput.read(mBuffer))>0)
		{
			mOutput.write(mBuffer, 0, mLength);
		}
		mOutput.flush();
		mOutput.close();
		mInput.close();
	}

	public boolean openDatabase() throws SQLException
	{
		String path = DB_PATH + DATABASE;
		db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		return db != null;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TB_PUZZLES);
		db.execSQL("DROP TABLE IF EXISTS " + TB_TAG_INFO);
		onCreate(db);
	}

	public synchronized void close()
	{
		if(db!=null)
			db.close();
		super.close();
	}

}
