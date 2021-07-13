package com.saleef.mvcyugiohapp.ViewModel;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Deck implements Parcelable {





    private List<YugiohCard> mainDeck;
    private List<YugiohCard> extraDeck;
    private List<YugiohCard> sideDeck;
    private String deckName;

    public Deck(String deckName){
        this.deckName = deckName;
        mainDeck = new ArrayList<>(60);
        extraDeck = new ArrayList<>(15);
        sideDeck = new ArrayList<>(15);
    }


    protected Deck(Parcel in) {
        mainDeck = in.createTypedArrayList(YugiohCard.CREATOR);
        extraDeck = in.createTypedArrayList(YugiohCard.CREATOR);
        sideDeck = in.createTypedArrayList(YugiohCard.CREATOR);
        deckName = in.readString();
    }

    public static final Creator<Deck> CREATOR = new Creator<Deck>() {
        @Override
        public Deck createFromParcel(Parcel in) {
            return new Deck(in);
        }

        @Override
        public Deck[] newArray(int size) {
            return new Deck[size];
        }
    };

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public List<YugiohCard> getExtraDeck() {
        return extraDeck;
    }

    public List<YugiohCard> getMainDeck() {
        return mainDeck;
    }

    public List<YugiohCard> getSideDeck() {
        return sideDeck;
    }

    public void setExtraDeck(List<YugiohCard> extraDeck) {
        this.extraDeck = extraDeck;
    }

    public void setMainDeck(List<YugiohCard> mainDeck) {
        this.mainDeck = mainDeck;
    }

    public void setSideDeck(List<YugiohCard> sideDeck) {
        this.sideDeck = sideDeck;
    }
    // Will take a String of deck from the addCardtoDeck dialog and a integer of how many copies
    public void addtoDeck(String deckType,YugiohCard yugiohCard,int copies){

        for (int i = 0;i<copies;i++) {
            switch (deckType) {
                case "Main":
                    getMainDeck().add(yugiohCard);
                    break;
                case "Extra":
                    getExtraDeck().add(yugiohCard);
                    break;
                case "Side":
                    getSideDeck().add(yugiohCard);
                    break;
                default:
                    break;
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        return deckName;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mainDeck);
        dest.writeTypedList(extraDeck);
        dest.writeTypedList(sideDeck);
        dest.writeString(deckName);
    }
}
