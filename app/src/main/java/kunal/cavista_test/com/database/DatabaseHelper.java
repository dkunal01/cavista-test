package kunal.cavista_test.com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CAVISTA_TEST";
    private static final int DATABASE_VERSION = 1;
    Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }


    public void saveComment(String link, String comment) {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("LINK", link);
        values.put("COMMENT", comment);
        sqlitedatabase.insert("COMMENTS_TBL", null, values);
    }

    public ArrayList<String> getAllComments(String link) {
        ArrayList<String> List = new ArrayList<String>();
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        String query = "SELECT * FROM COMMENTS_TBL WHERE LINK = '" + link + "'";
        Cursor cur = sqlitedatabase.rawQuery(query, null);
        if (cur != null) {
            if (cur.moveToFirst()) {
                while (!cur.isAfterLast()) {
                    List.add(cur.getString(cur.getColumnIndex("COMMENT")));
                    cur.moveToNext();
                }
            }
        }
        return List;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS COMMENTS_TBL(_id INTEGER PRIMARY KEY,LINK VARCHAR(200),COMMENT VARCHAR(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS COMMENTS_TBL");
        onCreate(sqLiteDatabase);
    }
}
