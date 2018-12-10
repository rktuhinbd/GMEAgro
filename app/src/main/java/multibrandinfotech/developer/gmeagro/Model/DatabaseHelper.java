package multibrandinfotech.developer.gmeagro.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gmeagro.db";
    private static final String LOGIN_TABLE_NAME = "LOGIN";
    private static final String INDENT_TABLE_NAME = "INDENT";
    private static final String ID = "ID";
    private static final String USER_NAME = "UserName";
    private static final String PASSWORD = "Password";
    private static final int VERSION = 1;
    private static final String CREATE_LOGIN_TABLE = "CREATE TABLE " + LOGIN_TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME + " VARCHAR(50) UNIQUE, " + PASSWORD + " VARCHAR(50));";
    private static final String CREATE_INDENT_TABLE = "CREATE TABLE " + INDENT_TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME + " VARCHAR(50) UNIQUE, " + PASSWORD + " VARCHAR(50));";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_LOGIN_TABLE);
            Toast.makeText(context, "Table Created", Toast.LENGTH_LONG).show();
            //Log.e("Query", CREATE_TABLE);
            db.execSQL("INSERT INTO " + LOGIN_TABLE_NAME + "("+ USER_NAME +", " + PASSWORD + ") VALUES('superadmin', 'superadmin');");
        }
        catch (Exception e){
            Toast.makeText(context, "Exception: " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE_NAME);
        onCreate(db);
    }

    public Cursor fetchLoginData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + LOGIN_TABLE_NAME, null);
        return cursor;
    }

//    public long insertData(String userName, String Password){
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(USER_NAME, userName);
//        contentValues.put(PASSWORD, Password);
//
//        long input = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
//        Toast.makeText(context, "Data inserted", Toast.LENGTH_LONG).show();
//        return input;
//    }
}
