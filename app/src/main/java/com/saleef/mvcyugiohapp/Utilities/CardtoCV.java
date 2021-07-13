package com.saleef.mvcyugiohapp.Utilities;

import android.content.ContentValues;
import android.database.Cursor;

import com.saleef.mvcyugiohapp.DataBase.YgoInfoFeeder;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.ArrayList;
import java.util.List;

// Class that abstracts away details for content value conversion
public class CardtoCV {



     public CardtoCV(){

     }

    public ContentValues[] convertToContentValues(List<YugiohCard> yugiohs){
        return cardsToContentValues(yugiohs);
    }

    public ContentValues[] convertToBanlistContentValues(List<YugiohCard> yugiohs,String format){
        return cardsToBanlistContentValues(yugiohs,format);
    }
    //Conversion method for cards to bulk content values
    private ContentValues[] cardsToContentValues(List<YugiohCard> yugiohCards){
        ContentValues[] bulkValues = new ContentValues[yugiohCards.size()];


        for (int i = 0;i<yugiohCards.size();i++){
            ContentValues contentValues = new ContentValues();
            contentValues.put(YgoInfoFeeder.InfoFeeder.CARD_ID,yugiohCards.get(i).getId());
            contentValues.put(YgoInfoFeeder.InfoFeeder.CARD_NAME,yugiohCards.get(i).getName());
            contentValues.put(YgoInfoFeeder.InfoFeeder.CARD_TYPE,yugiohCards.get(i).getType());
            contentValues.put(YgoInfoFeeder.InfoFeeder.CARD_DESC,yugiohCards.get(i).getDesc());
            contentValues.put(YgoInfoFeeder.InfoFeeder.ATTACK,yugiohCards.get(i).getAtk());
            contentValues.put(YgoInfoFeeder.InfoFeeder.DEFENSE,yugiohCards.get(i).getDef());
            contentValues.put(YgoInfoFeeder.InfoFeeder.LEVEL,yugiohCards.get(i).getLevel());
            contentValues.put(YgoInfoFeeder.InfoFeeder.RACE,yugiohCards.get(i).getRace());
            contentValues.put(YgoInfoFeeder.InfoFeeder.ATTRIBUTE,yugiohCards.get(i).getAttribute());
            contentValues.put(YgoInfoFeeder.InfoFeeder.LINK_VAL,yugiohCards.get(i).getLinkRating());
            contentValues.put(YgoInfoFeeder.InfoFeeder.SCALE,yugiohCards.get(i).getScale());
            contentValues.put(YgoInfoFeeder.InfoFeeder.CARD_JPG,yugiohCards.get(i).getCardImageUrl());
            bulkValues[i] = contentValues;
        }
        return bulkValues;
    }

    private ContentValues[] cardsToBanlistContentValues(List<YugiohCard> yugiohCards,String format){
        ContentValues[] bulkValues = new ContentValues[yugiohCards.size()];


        for (int i = 0;i<yugiohCards.size();i++){
            ContentValues contentValues = new ContentValues();
            contentValues.put(YgoInfoFeeder.InfoFeeder.CARD_ID,yugiohCards.get(i).getId());
            contentValues.put(YgoInfoFeeder.InfoFeeder.CARD_NAME,yugiohCards.get(i).getName());
            contentValues.put(YgoInfoFeeder.InfoFeeder.CARD_TYPE,yugiohCards.get(i).getType());
            contentValues.put(YgoInfoFeeder.InfoFeeder.CARD_DESC,yugiohCards.get(i).getDesc());
            contentValues.put(YgoInfoFeeder.InfoFeeder.ATTACK,yugiohCards.get(i).getAtk());
            contentValues.put(YgoInfoFeeder.InfoFeeder.DEFENSE,yugiohCards.get(i).getDef());
            contentValues.put(YgoInfoFeeder.InfoFeeder.LEVEL,yugiohCards.get(i).getLevel());
            contentValues.put(YgoInfoFeeder.InfoFeeder.RACE,yugiohCards.get(i).getRace());
            contentValues.put(YgoInfoFeeder.InfoFeeder.ATTRIBUTE,yugiohCards.get(i).getAttribute());
            contentValues.put(YgoInfoFeeder.InfoFeeder.LINK_VAL,yugiohCards.get(i).getLinkRating());
            contentValues.put(YgoInfoFeeder.InfoFeeder.SCALE,yugiohCards.get(i).getScale());
            contentValues.put(YgoInfoFeeder.BanListTcgCards.BANLIST,format.equals("tcg") ? yugiohCards.get(i).getTcgBanlistInfo()
                    :yugiohCards.get(i).getOcgBanlistInfo());
            contentValues.put(YgoInfoFeeder.InfoFeeder.CARD_JPG,yugiohCards.get(i).getCardImageUrl());
            bulkValues[i] = contentValues;
        }
        return bulkValues;
    }
    public List<YugiohCard> convertToCards(Cursor cursor){
              return   convertCursorToCards(cursor);
    }

    public List<YugiohCard> convertBanlistToCards(Cursor cursor,String format){
        return   convertCursorToBanlistCards(cursor,format);
    }
    private List<YugiohCard> convertCursorToCards(Cursor cursor){
          List<YugiohCard> yugiohCards = new ArrayList<>();
         if (cursor.moveToFirst()){

           do{
               int id = cursor.getInt(1);
               String name = cursor.getString(2);
               String type = cursor.getString(3);
               String desc = cursor.getString(4);
               int attack = cursor.getInt(5);
               int def = cursor.getInt(6);
               int level = cursor.getInt(7);
               String race = cursor.getString(8);
               String attribute = cursor.getString(9);
               int linkval = cursor.getInt(10);
               int scale = cursor.getInt(11);
               String imageUrl = cursor.getString(12);
                YugiohCard yugiohCard = new YugiohCard(id,name,type,desc,attack,def,level,race,attribute,scale,imageUrl,linkval);
                yugiohCards.add(yugiohCard);
               } while (cursor.moveToNext());
           }
         cursor.close();
         return yugiohCards;
         }


    private List<YugiohCard> convertCursorToBanlistCards(Cursor cursor,String format){
        List<YugiohCard> yugiohCards = new ArrayList<>();
        if (cursor.moveToFirst()){

            do{
                int id = cursor.getInt(1);
                String name = cursor.getString(2);
                String type = cursor.getString(3);
                String desc = cursor.getString(4);
                int attack = cursor.getInt(5);
                int def = cursor.getInt(6);
                int level = cursor.getInt(7);
                String race = cursor.getString(8);
                String attribute = cursor.getString(9);
                int linkval = cursor.getInt(10);
                int scale = cursor.getInt(11);
                String banned = cursor.getString(12);
                String imageUrl = cursor.getString(13);
                YugiohCard yugiohCard = new YugiohCard(id,name,type,desc,attack,def,level,race,attribute,scale,imageUrl,linkval);
                if (format.equals("tcg")) {
                    yugiohCard.setTcgBanlistInfo(banned);
                } else {
                    yugiohCard.setOcgBanlistInfo(banned);
                }
                yugiohCards.add(yugiohCard);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return yugiohCards;
    }
    }

