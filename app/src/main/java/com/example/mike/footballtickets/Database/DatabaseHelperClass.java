package com.example.mike.footballtickets.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mike.footballtickets.Pojo.AccountObject;
import com.example.mike.footballtickets.Pojo.MainMatchObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 3/21/2017.
 */

public class DatabaseHelperClass extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FootballTicketDB";
    private static final String TABLE_NAME = "TicketDatabase";
    private static final String TICKETS_COLUMN_ID = "id";
    private static final String TICKETS_COLUMN_MATCH_ID = "matchId";
    private static final String TICKETS_COLUMN_HOME_NAME = "homeName";
    private static final String TICKETS_COLUMN_AWAY_NAME = "awayName";
    private static final String TICKETS_COLUMN_HOME_LOGO= "homeLogo";
    private static final String TICKETS_COLUMN_AWAY_LOGO = "awayLogo";
    private static final String TICKETS_COLUMN_TIME = "matchTime";
    private static final String TICKETS_COLUMN_TICKETPRICE = "ticketPrice";
    private static final String TICKETS_COLUMN_LOCATION = "matchLocation";

    private static final String CREATE_TABLE = "create table "+TABLE_NAME+" ("+TICKETS_COLUMN_ID+" integer primary key, "+
            TICKETS_COLUMN_MATCH_ID + " TEXT, "+
            TICKETS_COLUMN_HOME_NAME + " TEXT, "+
            TICKETS_COLUMN_AWAY_NAME + " TEXT, "+
            TICKETS_COLUMN_HOME_LOGO + " TEXT, "+
            TICKETS_COLUMN_AWAY_LOGO + " TEXT, "+
            TICKETS_COLUMN_TIME + " TEXT, "+
            TICKETS_COLUMN_TICKETPRICE + " TEXT, "+
            TICKETS_COLUMN_LOCATION + " TEXT)";
    private static final String ONUPDATE_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public DatabaseHelperClass(Context context) {
        super(context, DATABASE_NAME, null, 1);
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

    public Integer deleteContact (String matchId){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME,TICKETS_COLUMN_MATCH_ID+" = ?", new String[]{matchId});
    }

    public boolean insertTicket(MainMatchObject matchObject){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TICKETS_COLUMN_MATCH_ID, matchObject.getMatchId());
        contentValues.put(TICKETS_COLUMN_HOME_NAME, matchObject.getHomeName());
        contentValues.put(TICKETS_COLUMN_AWAY_NAME, matchObject.getAwayName());
        contentValues.put(TICKETS_COLUMN_HOME_LOGO, matchObject.getHomeLogo());
        contentValues.put(TICKETS_COLUMN_AWAY_LOGO, matchObject.getAwayLogo());
        contentValues.put(TICKETS_COLUMN_TIME, matchObject.getTime());
        contentValues.put(TICKETS_COLUMN_TICKETPRICE, String.valueOf(matchObject.getTicketPrice()));
        contentValues.put(TICKETS_COLUMN_LOCATION, matchObject.getLocation());
        database.insert(TABLE_NAME,null,contentValues);
        return true;
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
