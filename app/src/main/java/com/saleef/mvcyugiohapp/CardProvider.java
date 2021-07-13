package com.saleef.mvcyugiohapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;

import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;

import android.net.Uri;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.saleef.mvcyugiohapp.DataBase.SqliteWrapper;
import com.saleef.mvcyugiohapp.DataBase.YgoInfoFeeder;
import com.saleef.mvcyugiohapp.DataBase.YugiohDataBase;


// Content providers store and retrieve Data
// It can also be used in just a single application
// The utility works great for database operations because the data can be accessed using our unique authority url
// The database can be accessed anywhere where there is a context object (services,threads,activities);
public class CardProvider extends ContentProvider {



   private SqliteWrapper mSqliteWrapper;


   // Idea is the CardList with the uri matcher will fetch the whole list outside of the id
    private static final int CARD_LIST = 0;
    private static final int CARD_ID = 1;

    static final UriMatcher mMatcher;
    static{
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(YgoInfoFeeder.PROVIDER_NAME,"CardEntries",CARD_LIST);
        mMatcher.addURI(YgoInfoFeeder.PROVIDER_NAME,"NewCardEntries",CARD_LIST);
        mMatcher.addURI(YgoInfoFeeder.PROVIDER_NAME,"BanListTcgCardEntries",CARD_LIST);
        mMatcher.addURI(YgoInfoFeeder.PROVIDER_NAME,"BanListOcgCardEntries",CARD_LIST);
        mMatcher.addURI(YgoInfoFeeder.PROVIDER_NAME,"CardEntries/*",CARD_ID);
    }



    @Override
    public boolean onCreate() {
        mSqliteWrapper = new SqliteWrapper(new YugiohDataBase(getContext()));
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        if (mMatcher.match(uri) == CARD_LIST) {
            return mSqliteWrapper.queryAllCards(uri.getPath(), projection, selection, selectionArgs, sortOrder, null, null);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long value =  mSqliteWrapper.singleInsertion(values);

        /**
         * If record is added successfully
         */
        if (value > 0) {
            Uri _uri = ContentUris.withAppendedId(YgoInfoFeeder.CONTENT_URI, value);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        if (mMatcher.match(uri) == CARD_LIST) {
            mSqliteWrapper.bulkCardInsertion(uri.getPath(), values);
        } else {
            throw new IllegalArgumentException("Unknown Uri" + uri);
        }
        return 0;
    }



}
