package myk.project.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import myk.project.Pojo.TicketBoughtItem;

/**
 * Created by Mike on 3/21/2017.
 */

public class DatabaseHelperClass extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FootballTicketDB";
    private static final String TABLE_NAME = "TicketDatabase";
    private static final String TICKETS_COLUMN_ID = "id";
    private static final String TICKET_USER = "user_id";
    private static final String TICKET_CODE = "ticket_code";

    private static final String TICKET_COUNT = "count";
    private static final String CREATE_TABLE = "create table "+TABLE_NAME+" ("+TICKETS_COLUMN_ID+" integer primary key, "+
            TICKET_USER + " TEXT, "+
            TICKET_COUNT + " TEXT, "+
            TICKET_CODE + " TEXT)";

    private static final String ONUPDATE_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public DatabaseHelperClass(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ONUPDATE_TABLE);
        onCreate(db);
    }

    public boolean insertTicket(TicketBoughtItem ticketObject){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TICKET_USER, ticketObject.getUserId());
        contentValues.put(TICKET_CODE, ticketObject.getTicketcode());
        if (ticketObject.isSeason())
            contentValues.put(TICKET_COUNT, 11);
        else
            contentValues.put(TICKET_COUNT, 1);
            database.insert(TABLE_NAME,null,contentValues);
        return true;
    }


    public boolean verifyTicket(TicketBoughtItem ticketBoughtItem){
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery("select * from "+TABLE_NAME+" WHERE "+ TICKET_USER + " = "+ticketBoughtItem.getUserId(), null);
            cursor.moveToFirst();
            if (cursor.getCount()>0){
                do{
                    if (ticketBoughtItem.getTicketcode().contentEquals(cursor.getString(cursor.getColumnIndex(TICKET_CODE)))){
                        Log.d("ScannerinsertedDB", String.valueOf(cursor.getCount()));
                        return false;
                    }else {
                        Log.d("Scanner inserted", String.valueOf(cursor.getCount()));
                        return true;
                    }}
                while (cursor.moveToNext());
            }
            else {
                return true;
            }
        }catch (SQLiteException exception){
            return true;

        }

    }

//    public List<AccountObject> getAllTickets(){
//        List<AccountObject> accountObjects = new ArrayList<>();
//
//        SQLiteDatabase database = this.getReadableDatabase();
//        Cursor cursor = database.rawQuery("select * from "+TABLE_NAME, null);
//        cursor.moveToFirst();
//
//        while (cursor.isAfterLast() == false){
//            AccountObject object = new AccountObject();
//            object.setMatchId(cursor.getString(cursor.getColumnIndex(TICKETS_COLUMN_MATCH_ID)));
//            object.setHomeName(cursor.getString(cursor.getColumnIndex(TICKETS_COLUMN_HOME_NAME)));
//            object.setAwayName(cursor.getString(cursor.getColumnIndex(TICKETS_COLUMN_AWAY_NAME)));
//            object.setHomeLogo(cursor.getString(cursor.getColumnIndex(TICKETS_COLUMN_HOME_LOGO)));
//            object.setAwayLogo(cursor.getString(cursor.getColumnIndex(TICKETS_COLUMN_AWAY_LOGO)));
//            object.setTime(cursor.getString(cursor.getColumnIndex(TICKETS_COLUMN_TIME)));
//            object.setTicketCode(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TICKETS_COLUMN_TICKETPRICE))));
//            object.setLocation(cursor.getString(cursor.getColumnIndex(TICKETS_COLUMN_LOCATION)));
//            accountObjects.add(object);
//            cursor.moveToNext();
//        }
//
//        return accountObjects;
//    }
}
