package com.example.jan455app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String CARD_TABLE = "CARD_TABLE";
    public static final String COLUMN_ID = "COLUMN_ID";
    public static final String COLUMN_CARD_NAME = "COLUMN_CARD_NAME";
    public static final String COLUMN_CARD_COUNT = "COLUMN_CARD_COUNT";
    public static final String COLUMN_FOIL = "COLUMN_FOIL";
    public static final String COLUMN_SIGNED = "COLUMN_SIGNED";
    public static final String COLUMN_REQUIRED = "COLUMN_REQUIRED";
    public static final String COLUMN_ID_DECK = "COLUMN_ID_DECK";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "myCards.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cmdTableCardCreate = "CREATE TABLE " + CARD_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CARD_NAME + " TEXT, " + COLUMN_CARD_COUNT + " INTEGER, " + COLUMN_FOIL + " BOOL, " + COLUMN_SIGNED + " BOOL, "
                + COLUMN_REQUIRED + " BOOL, " + COLUMN_ID_DECK + " INTEGER)";

        db.execSQL(cmdTableCardCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean addCard(EntityCard addedCard) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CARD_NAME,addedCard.getCardName() );
        cv.put(COLUMN_CARD_COUNT,addedCard.getCardCount() );
        cv.put(COLUMN_FOIL,addedCard.getFoil() );
        cv.put(COLUMN_SIGNED,addedCard.getSigned() );
        cv.put(COLUMN_REQUIRED,addedCard.getRequired() );

        long insert = db.insert(CARD_TABLE, null, cv);

        return (insert >0 ? true : false);
    }

    public List<EntityCard> getAll() {
        List<EntityCard> result = new ArrayList<EntityCard>();
        String cmdGetAllQuery = "SELECT * FROM " + CARD_TABLE ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(cmdGetAllQuery, null);

        if(cursor.moveToFirst()) {

            do {
                int cardId = cursor.getInt(0);
                String cardName = cursor.getString(1);
                int cardCount = cursor.getInt(2);
                Boolean isFoil = cursor.getInt(3) == 1 ? true : false;
                Boolean isSigned = cursor.getInt(4) == 1 ? true : false;
                Boolean idRequired = cursor.getInt(5) == 1 ? true : false;

                EntityCard readedCard = new EntityCard(cardId, cardName, cardCount, isFoil, isSigned, idRequired, 0);
                result.add(readedCard);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return  result;
    }

    public List<EntityCard> getOnlyRequired() {
        List<EntityCard> result = new ArrayList<EntityCard>();
        String cmdGetAllQuery = "SELECT * FROM " + CARD_TABLE + " WHERE " + COLUMN_REQUIRED + " = 1" ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(cmdGetAllQuery, null);

        if(cursor.moveToFirst()) {

            do {
                int cardId = cursor.getInt(0);
                String cardName = cursor.getString(1);
                int cardCount = cursor.getInt(2);
                Boolean isFoil = cursor.getInt(3) == 1 ? true : false;
                Boolean isSigned = cursor.getInt(4) == 1 ? true : false;
                Boolean idRequired = cursor.getInt(5) == 1 ? true : false;

                EntityCard readedCard = new EntityCard(cardId, cardName, cardCount, isFoil, isSigned, idRequired, 0);
                result.add(readedCard);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return  result;
    }

    public boolean delete(EntityCard deletedCard){
        SQLiteDatabase db = this.getWritableDatabase();
        String cmdDelete = "DELETE FROM " + CARD_TABLE + " WHERE " + COLUMN_ID + " = " + deletedCard.getId();

        Cursor cu = db.rawQuery(cmdDelete, null);
        if(cu.moveToFirst())
            return  true;
        else
            return false;
    }
}
