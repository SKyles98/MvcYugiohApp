package com.saleef.mvcyugiohapp.Views.Dialogs.AddCardDialog;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.R;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.ArrayList;
import java.util.List;
// View class for our add to deck dialog
public class AddCardViewImpl extends BaseObservableMvc<AddCardViewMvc.Listener> implements AddCardViewMvc {

    private final Spinner deckSpinner,amountSpinner,deckTypeSpinner;
    private final MaterialButton addButton,cancelButton;
    private List<Deck> mDecks;
    private YugiohCard mYugiohCard;


    public AddCardViewImpl(LayoutInflater inflater, ViewGroup viewGroup){
        setRootView(inflater.inflate(R.layout.deck_adder_dialog,viewGroup,false));
        deckSpinner = findViewById(R.id.decksSpinner);
        amountSpinner = findViewById(R.id.cardAmountSpinner);
        deckTypeSpinner = findViewById(R.id.mseSpinner);
        addButton = findViewById(R.id.addButton);
        cancelButton = findViewById(R.id.CancelButton);
        initListeners();
    }

     private void initListeners(){
        addButton.setOnClickListener(v -> {
            for (Listener listener:getListeners()){
                listener.onAddClicked();
            }
        });
        cancelButton.setOnClickListener(v -> {
            for (Listener listener:getListeners()){
                listener.onCancelClicked();
            }
        });

     }

    @Override
    public void setDecks(List<Deck> decks) {
        mDecks = decks;
    }

    @Override
    public void setYugiohCard(YugiohCard yugiohCard) {
        mYugiohCard = yugiohCard;
    }

    public List<Deck> getDecks() {
        return mDecks;
    }

    public YugiohCard getYugiohCard() {
        return mYugiohCard;
    }
    // This spinner is populated from result of shared preferences
    @Override
    public void setDeckSpinner(){
        ArrayAdapter<Deck> deckArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, getDecks());
        deckSpinner.setAdapter(deckArrayAdapter);
        deckSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     for (Listener listener:getListeners()){
                         listener.onSpinnerDeckClicked();
                     }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    // This spinners items is solely determined by the type of card
    @Override
    public void setDeckTypeSpinner(){
       ArrayList<String> arrayList = new ArrayList<>();
        String cardType = getCardType();
        if (cardType.contains("Monster")) {
            if (extraDeckCheck(cardType)) {
                arrayList.add("Extra");
            } else{
                arrayList.add("Main");
            }
        } else{
            arrayList.add("Main");
        }
        arrayList.add("Side");
        ArrayAdapter<String> deckTypeAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,arrayList);
        deckTypeSpinner.setAdapter(deckTypeAdapter);
        deckTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   for (Listener listener:getListeners()){
                       listener.onSpinnerDeckTypeClicked();
                   }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public void setAmountSpinner(){

        ArrayList<Integer> adds = new ArrayList<>();
        switch ((String)deckTypeSpinner.getSelectedItem()){
            case "Extra":
                adds = setAddableCopies(eSearch());
                break;
            case "Main":
                adds = setAddableCopies(mSearch());
                break;
            case "Side":
                adds = setAddableCopies(sSearch());
                break;
            default:
                break;
        }

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>
                (getContext(),R.layout.support_simple_spinner_dropdown_item, adds);
        amountSpinner.setAdapter(arrayAdapter);
    }

    @Override
    public Deck getSelectedDeck() {
        return (Deck) deckSpinner.getSelectedItem();
    }

    @Override
    public int getSelectedAmount() {
         return (int) amountSpinner.getSelectedItem();
    }

    @Override
    public String getSelectedDeckType() {
         return (String) deckTypeSpinner.getSelectedItem();
    }

    private boolean extraDeckCheck(String cardType){
        return (cardType.contains("Link") || cardType.contains("Fusion") || cardType.contains("Synchro") || cardType.contains("XYZ"));
    }
    // Searches extra deck
    private int eSearch(){
        Deck deck =  (Deck) deckSpinner.getSelectedItem();
        String cardName = getYugiohCard().getName();
        // Holds copies for extraDeck and side

        int EDcopies = 0;

        for (YugiohCard extraDeckCard:deck.getExtraDeck()){
            if (extraDeckCard.getName().equals(cardName)){
                EDcopies++;
            }
        }


        return EDcopies;
    }

    private int sSearch(){
        Deck deck =  (Deck) deckSpinner.getSelectedItem();
        String cardName = getYugiohCard().getName();
        int SDcopies = 0;
        for (YugiohCard sideDeckCard:deck.getSideDeck()){
            if (sideDeckCard.getName().equals(cardName)){
                SDcopies++;
            }
        }
      return SDcopies;
    }

    private int mSearch(){
        Deck deck =  (Deck) deckSpinner.getSelectedItem();
        String cardName = getYugiohCard().getName();
        int MDcopies = 0;
        for (YugiohCard mainDeckCard:deck.getMainDeck()){
            if (mainDeckCard.getName().equals(cardName)){
                MDcopies++;
            }
        }
        return MDcopies;
    }

    private ArrayList<Integer>  setAddableCopies(int copies){
        ArrayList<Integer> cope = new ArrayList<>();
        switch (copies){
            case 0:
              cope.add(1);
              cope.add(2);
              cope.add(3);
              break;
            case 1:
                cope.add(1);
                cope.add(2);
                break;
            case 2:
                cope.add(1);
                break;
            case 3:
                cope.add(0);
                break;
            default:
                return null;

        }
        return cope;
    }

    private String getCardType(){
        return getYugiohCard().getType();
    }
}
