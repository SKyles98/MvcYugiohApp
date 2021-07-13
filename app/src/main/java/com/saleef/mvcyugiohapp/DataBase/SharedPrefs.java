package com.saleef.mvcyugiohapp.DataBase;


import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;


import java.util.ArrayList;

import java.util.List;
import java.util.Map;


// Using shared Preferences to save user created decks
public class SharedPrefs {


    private final SharedPreferences mSharedPreferences;


    public SharedPrefs(SharedPreferences sharedPreferences){
        mSharedPreferences = sharedPreferences;
    }




    public boolean upDateDatabaseDate(String newDate){
        String oldDate = mSharedPreferences.getString("date","");
        SharedPreferences.Editor  editor  = mSharedPreferences.edit();
        if (oldDate.equals("")){ // First time user opens the app
            editor.putString("date",newDate);
            editor.apply();
            return true;
        }

        switch (oldDate.compareTo(newDate)){
            case 0: // equal dates do nothing
                return false;
            case 1: // old date is greater dont update
                return false;
            case -1: // database was updated get the latest cards
                editor.putString("date",newDate);
                editor.apply();
                return true;
            default: // Something went wrong
                return false;
        }

    }

    public String getUpdateDate(){
        return mSharedPreferences.getString("date","");
    }

    public void saveDataBaseSize(int size){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("Size",size + "");
        editor.apply();
    }

    public String getDataBaseSize(){
        return mSharedPreferences.getString("Size","");
    }
     //Create a deck class holding three list of type Yugioh card a main deck a side deck and a extra deck
    // This class will be converted to a gson format with a deck name key
    public void createDeck(String deckName){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Gson gson = new Gson();
        String gsonConvertedDeck = gson.toJson(new Deck(deckName));
        editor.putString(deckName,gsonConvertedDeck);
        editor.apply();
        Log.i("created",gsonConvertedDeck + "was created");
    }




    // When user clicks on a deck
    public Deck getDeck(String deckName){
        String savedDeck = mSharedPreferences.getString(deckName,"");
        if (savedDeck==null){
            //Deck doesnt exist in sharedPrefs do something
            return null;
        }

        Gson gson = new Gson();

        //should return the corresponding deck for sharedPrefs
        return gson.fromJson(savedDeck,Deck.class);
    }
    // When user invokes the addCardDialog on the detail screen
    public void addCardtoDeck(String deckType, String deckName, int copies, YugiohCard yugiohCard){
        String savedDeck = mSharedPreferences.getString(deckName,"");
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (savedDeck==null){
            return;
        }
        Gson gson = new Gson();
        Deck deck = gson.fromJson(savedDeck,Deck.class);
        deck.addtoDeck(deckType,yugiohCard,copies);

        // Reconvert back to json and store in same key which essentially updates value
        String gsonConvertedDeck = gson.toJson(deck);
        editor.putString(deckName,gsonConvertedDeck);
        editor.apply();
        Log.i("added",yugiohCard.getName() + "added");
    }


    // Gets all the decks from Shared Preferences
    public List<Deck> getAllDecks() {
        List<Deck> decks = new ArrayList<>();
        Map<String, ?> prefs = mSharedPreferences.getAll();
       Gson gson = new Gson();
        for (Map.Entry<String, ?> entry : prefs.entrySet()) {
            try {
                // Gets the gson String from the prefs then convert gotten string back to deck
                String s = mSharedPreferences.getString(entry.getKey(),"");
                Log.i("something",s);
                if (!s.equals("Date") && !s.equals("Size")) {
                    Deck deck = gson.fromJson(s, Deck.class);
                    decks.add(deck);
                }
            } catch (IllegalStateException | JsonSyntaxException exception) {
                Log.i("error",exception.toString());
            }

    }
        return decks;
    }

    public boolean findEquivalentKey(String deckName){
        Map<String,?> prefs = mSharedPreferences.getAll();

        for (Map.Entry<String,?> entry:prefs.entrySet()){
            if (deckName.equals(entry.getKey())){
                return true;
            }
        }
        return false;
    }

    public void deleteDeck(String deckName){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(deckName);
        editor.apply();
    }

    /*
    Gets the old deck and set its name value to the newDeckName and then puts this deck in a new pocket with new Name Key
    finally we delete the old deck;
     */
    public void renameDeck(String olddeckName,String newDeckName){

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Deck deck =  getDeck(olddeckName);
        deck.setDeckName(newDeckName);
        Gson gson = new Gson();
       String newDeck =  gson.toJson(deck);
        editor.putString(newDeckName,newDeck);
        deleteDeck(olddeckName);
        editor.apply();
    }

    /*
    Removes card containing the passed in name
     */
    public void deleteCard(String deckName, String cardName,String type) {
           String savedDeck = mSharedPreferences.getString(deckName,"");
           SharedPreferences.Editor editor = mSharedPreferences.edit();
           Gson gson = new Gson();
           Deck deck = gson.fromJson(savedDeck,Deck.class);
           switch (type){
               case "Main":
                    deck.setMainDeck(deleteCard(deck.getMainDeck(),cardName));
                   break;
               case "Extra":
                   deck.setExtraDeck(deleteCard(deck.getExtraDeck(),cardName));
                   break;
               case "Side":
                   deck.setSideDeck(deleteCard(deck.getSideDeck(),cardName));
                   break;
           }
          String jsonDeck =  gson.toJson(deck);
           editor.putString(deckName,jsonDeck);
           editor.apply();

    }
    private List<YugiohCard> deleteCard(List<YugiohCard> cards, String cardName){
        for (YugiohCard card:cards){
            if (card.getName().equals(cardName)){
                cards.remove(card);
                break; // Break because its a one copy remove
            }
        }
        return cards;
    }
}
