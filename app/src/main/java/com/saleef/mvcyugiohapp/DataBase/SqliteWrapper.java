package com.saleef.mvcyugiohapp.DataBase;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;
// Primary use of this class is to abstract out insertion and querying details from the Content provider class
public class SqliteWrapper {

        private final YugiohDataBase mYugiohDataBase;

        public SqliteWrapper(YugiohDataBase yugiohDataBase){
              mYugiohDataBase = yugiohDataBase;
        }


        public Uri insertYugiohCard(String tableName,ContentValues contentValues){
            SQLiteDatabase database = mYugiohDataBase.getWritableDatabase();
          // Insert the values in the dataBase and return -1(failure) or >0(Successfull insert)

            long rowId =  database.insert(tableName,null,contentValues);
            if (rowId>0){
                Uri uri = ContentUris.withAppendedId(YgoInfoFeeder.CONTENT_URI,rowId);
                return uri;
            }
            throw new SQLException("Failed to add row into" + rowId);
        }


        // When performing massive card insertion
        public void bulkCardInsertion(String tableName,ContentValues[] contentValues){
            SQLiteDatabase database = mYugiohDataBase.getWritableDatabase();
            String right = tableName.replaceFirst("/","");
            database.beginTransaction();

            for (ContentValues value : contentValues) {
                insertYugiohCard(right,value);
                Log.i("insertion",value.getAsString(YgoInfoFeeder.InfoFeeder.CARD_NAME));
            }
            database.setTransactionSuccessful();
            database.endTransaction();

        }
        // For single card insertions
        public long singleInsertion(ContentValues contentValues){
            SQLiteDatabase database = mYugiohDataBase.getWritableDatabase();

            return database.insert(YgoInfoFeeder.InfoFeeder.TABLE_NAME,null,contentValues);
        }



        public Cursor queryAllCards(String tableName,@Nullable String[] projection, @Nullable String selection,
                                 @Nullable String[] selectionArgs, @Nullable String sortOrder, String groupBy, String having){
            String right = tableName.replaceAll("/","");
            SQLiteDatabase database = mYugiohDataBase.getReadableDatabase();
            Cursor cursor = database.query(right,projection,selection,selectionArgs,groupBy,having,sortOrder);
            return cursor;
        }





}
