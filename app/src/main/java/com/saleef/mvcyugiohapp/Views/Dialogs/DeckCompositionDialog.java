package com.saleef.mvcyugiohapp.Views.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.saleef.mvcyugiohapp.Common.Base.BaseDialog;
import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.R;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

public class DeckCompositionDialog extends BaseDialog {


    private  TextView title,mDeckAmount,sDeckAmount,tDeckAmount,synchDeckAmount,xyzDeckAmount,fusionDeckAmount,linkDeckAmount;
    private Deck mDeck;

    public static DeckCompositionDialog newDeckCompositionDialog(String deckType, Deck deck){
               DeckCompositionDialog deckCompositionDialog = new DeckCompositionDialog();
                  Bundle bundle = new Bundle();
                  bundle.putParcelable(Constant.DKEY,deck);
                  bundle.putString(Constant.DECKTYPEKEY,deckType);
                   deckCompositionDialog.setArguments(bundle);
                   return deckCompositionDialog;
           }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.deck_composition_dialog);
        mDeck = getArguments().getParcelable(Constant.DKEY);
        title = dialog.findViewById(R.id.title);
        Button button = dialog.findViewById(R.id.okButton);
        mDeckAmount = dialog.findViewById(R.id.mNumberTxt);
        sDeckAmount = dialog.findViewById(R.id.sNumberTxt);
        tDeckAmount = dialog.findViewById(R.id.trapNumberTxt);
        synchDeckAmount = dialog.findViewById(R.id.synchNumberTxt);
        xyzDeckAmount = dialog.findViewById(R.id.xyzNumberTxt);
        fusionDeckAmount = dialog.findViewById(R.id.fNumberTxt);
        linkDeckAmount = dialog.findViewById(R.id.linkNumberTxt);

        switch (getArguments().getString(Constant.DECKTYPEKEY)){
            case "Main":
                setMainView();
                break;
            case "Extra":
                setExtraView();
                break;
            case "Side":
                setSideView();
                break;
        }

        button.setOnClickListener(v -> dismiss());
        return dialog;
    }



    private void removeExtraDeckVisibility() {
        synchDeckAmount.setVisibility(View.GONE);
        xyzDeckAmount .setVisibility(View.GONE);
        fusionDeckAmount.setVisibility(View.GONE);
        linkDeckAmount.setVisibility(View.GONE);
    }

    private void removeMainDeckVisibility(){
          mDeckAmount.setVisibility(View.GONE);
        sDeckAmount.setVisibility(View.GONE);
        tDeckAmount.setVisibility(View.GONE);
    }

    private void setSideView() {
        String header = "Side Deck Composition";
        title.setText(header);
        sideLogic();

    }
    private void setExtraView() {
        String header = "Extra Deck Composition";
        title.setText(header);
        removeMainDeckVisibility();
     extraLogic();
    }

    private void setMainView() {
        String header = "Main Deck Composition";
        title.setText(header);
        removeExtraDeckVisibility();
       mainLogic();
    }

    private void mainLogic(){
        int monsterCounter = 0;
        int spellCounter = 0;
        int trapCounter = 0;

        for (YugiohCard card:mDeck.getMainDeck()) {
            String type = card.getType();
            if (type.contains("Monster")) {
                monsterCounter++;

            } else if (type.contains("Spell")){
                spellCounter++;
            } else if (type.contains("Trap")){
                trapCounter++;
            }
        }
        String monsters = "Monsters: " + monsterCounter;
        String spells = "Spells: " + spellCounter;
        String traps = "Traps: " + trapCounter;
        mDeckAmount.setText(monsters);
        sDeckAmount.setText(spells);
        tDeckAmount.setText(traps);
    }

    private void extraLogic(){
        int fusionCounter = 0;
        int linkCounter = 0;
        int synchroCounter = 0;
        int xyzCounter = 0;
        for (YugiohCard card:mDeck.getExtraDeck()) {
            String type = card.getType();
            if (type.contains("Monster")) {
                if (type.contains("Fusion")) {
                    fusionCounter++;
                } else if (type.contains("XYZ")) {
                    xyzCounter++;
                } else if (type.contains("Link")){
                    linkCounter++;
                } else if (type.contains("Synchro")){
                    synchroCounter++;
                }
            }
        }
        String synchros = "Synchro: " + synchroCounter;
        String fusions = "Fusions: " + fusionCounter;
        String links = "Links: " + linkCounter;
        String xyzs = "XYZS: " + xyzCounter;
        synchDeckAmount.setText(synchros);
        xyzDeckAmount.setText(xyzs);
        fusionDeckAmount.setText(fusions);
        linkDeckAmount.setText(links);
    }

    private void sideLogic(){
        int monsterCounter = 0;
        int spellCounter = 0;
        int trapCounter = 0;

        for (YugiohCard card:mDeck.getSideDeck()) {
            String type = card.getType();
            if (type.contains("Monster")) {
                monsterCounter++;

            } else if (type.contains("Spell")){
                spellCounter++;
            } else if (type.contains("Trap")){
                trapCounter++;
            }
        }
        String monsters = "Monsters: " + monsterCounter;
        String spells = "Spells: " + spellCounter;
        String traps = "Traps: " + trapCounter;
        mDeckAmount.setText(monsters);
        sDeckAmount.setText(spells);
        tDeckAmount.setText(traps);
    }
}
