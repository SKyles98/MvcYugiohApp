package com.saleef.mvcyugiohapp.DataBase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class YugiohDataBase extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Yugioh.db";
    private static final int DATABASE_VERSION = 1;

   private static final String ALL_CARDS_SQL_ENTRIES = "CREATE TABLE " + YgoInfoFeeder.InfoFeeder.TABLE_NAME
           + "( " + YgoInfoFeeder.InfoFeeder.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + YgoInfoFeeder.InfoFeeder.CARD_ID + " INTEGER,"
           +  YgoInfoFeeder.InfoFeeder.CARD_NAME + " TEXT," + YgoInfoFeeder.InfoFeeder.CARD_TYPE + " TEXT,"
           + YgoInfoFeeder.InfoFeeder.CARD_DESC + " TEXT," + YgoInfoFeeder.InfoFeeder.ATTACK + " INTEGER,"
           + YgoInfoFeeder.InfoFeeder.DEFENSE + " INTEGER," + YgoInfoFeeder.InfoFeeder.LEVEL + " INTEGER,"
           + YgoInfoFeeder.InfoFeeder.RACE + " TEXT," + YgoInfoFeeder.InfoFeeder.ATTRIBUTE + " TEXT,"
            + YgoInfoFeeder.InfoFeeder.LINK_VAL + " INTEGER DEFAULT 0,"
           + YgoInfoFeeder.InfoFeeder.SCALE + " INTEGER DEFAULT 0,"  + YgoInfoFeeder.InfoFeeder.CARD_JPG + " TEXT)";

    private static final String NEW_CARDS_SQL_ENTRIES = "CREATE TABLE " + YgoInfoFeeder.NewCards.TABLE_NAME
            + "( " + YgoInfoFeeder.NewCards.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + YgoInfoFeeder.NewCards.CARD_ID + " INTEGER,"
            +  YgoInfoFeeder.NewCards.CARD_NAME + " TEXT," + YgoInfoFeeder.NewCards.CARD_TYPE + " TEXT,"
            + YgoInfoFeeder.NewCards.CARD_DESC + " TEXT," + YgoInfoFeeder.NewCards.ATTACK + " INTEGER,"
            + YgoInfoFeeder.NewCards.DEFENSE + " INTEGER," + YgoInfoFeeder.NewCards.LEVEL + " INTEGER,"
            + YgoInfoFeeder.NewCards.RACE + " TEXT," + YgoInfoFeeder.NewCards.ATTRIBUTE + " TEXT,"
            + YgoInfoFeeder.NewCards.LINK_VAL + " INTEGER DEFAULT 0,"
            + YgoInfoFeeder.NewCards.SCALE + " INTEGER DEFAULT 0,"  + YgoInfoFeeder.NewCards.CARD_JPG + " TEXT)";



    private static final String BanList_TCG_CARDS_SQL_ENTRIES = "CREATE TABLE " + YgoInfoFeeder.BanListTcgCards.TABLE_NAME
            + "( " + YgoInfoFeeder.BanListTcgCards.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + YgoInfoFeeder.BanListTcgCards.CARD_ID + " INTEGER,"
            +  YgoInfoFeeder.BanListTcgCards.CARD_NAME + " TEXT," + YgoInfoFeeder.BanListTcgCards.CARD_TYPE + " TEXT,"
            + YgoInfoFeeder.BanListTcgCards.CARD_DESC + " TEXT," + YgoInfoFeeder.BanListTcgCards.ATTACK + " INTEGER,"
            + YgoInfoFeeder.BanListTcgCards.DEFENSE + " INTEGER," + YgoInfoFeeder.BanListTcgCards.LEVEL + " INTEGER,"
            + YgoInfoFeeder.BanListTcgCards.RACE + " TEXT," + YgoInfoFeeder.BanListTcgCards.ATTRIBUTE + " TEXT,"
            + YgoInfoFeeder.BanListTcgCards.LINK_VAL + " INTEGER DEFAULT 0,"
            + YgoInfoFeeder.BanListTcgCards.SCALE + " INTEGER DEFAULT 0," + YgoInfoFeeder.BanListTcgCards.BANLIST + " TEXT,"
            + YgoInfoFeeder.BanListTcgCards.CARD_JPG + " TEXT)";


    private static final String BanList_OCG_CARDS_SQL_ENTRIES = "CREATE TABLE " + YgoInfoFeeder.BanListOcgCards.TABLE_NAME
            + "( " + YgoInfoFeeder.BanListOcgCards.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + YgoInfoFeeder.BanListOcgCards.CARD_ID + " INTEGER,"
            +  YgoInfoFeeder.BanListOcgCards.CARD_NAME + " TEXT," + YgoInfoFeeder.BanListOcgCards.CARD_TYPE + " TEXT,"
            + YgoInfoFeeder.BanListOcgCards.CARD_DESC + " TEXT," + YgoInfoFeeder.BanListOcgCards.ATTACK + " INTEGER,"
            + YgoInfoFeeder.BanListOcgCards.DEFENSE + " INTEGER," + YgoInfoFeeder.BanListOcgCards.LEVEL + " INTEGER,"
            + YgoInfoFeeder.BanListOcgCards.RACE + " TEXT," + YgoInfoFeeder.BanListOcgCards.ATTRIBUTE + " TEXT,"
            + YgoInfoFeeder.BanListOcgCards.LINK_VAL + " INTEGER DEFAULT 0,"
            + YgoInfoFeeder.BanListOcgCards.SCALE + " INTEGER DEFAULT 0," + YgoInfoFeeder.BanListOcgCards.BANLIST + " TEXT,"
            + YgoInfoFeeder.BanListOcgCards.CARD_JPG + " TEXT)";

    private static final String ALL_CARDS_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + YgoInfoFeeder.InfoFeeder.TABLE_NAME;


    private static final String NEW_CARDS_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + YgoInfoFeeder.NewCards.TABLE_NAME;

    private static final String BANLIST_TCG_CARDS_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + YgoInfoFeeder.BanListTcgCards.TABLE_NAME;

    private static final String BANLIST_OCG_CARDS_DELETE_ENTRIES =   "DROP TABLE IF EXISTS " + YgoInfoFeeder.BanListOcgCards.TABLE_NAME;
    public YugiohDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(ALL_CARDS_SQL_ENTRIES);
            db.execSQL(NEW_CARDS_SQL_ENTRIES);
            db.execSQL(BanList_TCG_CARDS_SQL_ENTRIES);
            db.execSQL(BanList_OCG_CARDS_SQL_ENTRIES);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ALL_CARDS_DELETE_ENTRIES);
        db.execSQL(NEW_CARDS_DELETE_ENTRIES);
        db.execSQL(BANLIST_TCG_CARDS_DELETE_ENTRIES);
        db.execSQL(BANLIST_OCG_CARDS_DELETE_ENTRIES);
            onCreate(db);
    }
}
