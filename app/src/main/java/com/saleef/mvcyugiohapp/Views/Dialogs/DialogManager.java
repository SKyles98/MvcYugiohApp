package com.saleef.mvcyugiohapp.Views.Dialogs;



import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.Views.Dialogs.AddCardDialog.AddCardToDeckDialog;
import com.saleef.mvcyugiohapp.Views.Dialogs.BottomSheets.SortOptions.SortOptionsBottomSheetDialog;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;
import com.saleef.mvcyugiohapp.Views.Dialogs.CalculatorDialogs.CoinFlipDialog;
import com.saleef.mvcyugiohapp.Views.Dialogs.CalculatorDialogs.DiceRollDialog;
import com.saleef.mvcyugiohapp.Views.Dialogs.DeckEditDialogs.CreateDeckDialog;
import com.saleef.mvcyugiohapp.Views.Dialogs.DeckEditDialogs.DeleteDeckDialog;
import com.saleef.mvcyugiohapp.Views.Dialogs.DeckEditDialogs.RenameDialog;
import com.saleef.mvcyugiohapp.Views.Dialogs.NetworkDialogs.ErrorDialog;
import com.saleef.mvcyugiohapp.Views.Dialogs.NetworkDialogs.LoadingDialog;

import java.util.List;

// Abstracts away details of loading dialogs from our controllers(Activities,Fragments)
public class DialogManager {

    private final FragmentManager mFragmentManager;
    public DialogManager(FragmentManager fragmentManager){
        mFragmentManager = fragmentManager;
    }

    public void showLoadingDialog(){
        DialogFragment dialogFragment = new LoadingDialog();
        dialogFragment.show(mFragmentManager,"loading///");
    }

    public void showCreateDeckDialog(){
        DialogFragment dialogFragment = new CreateDeckDialog();

        dialogFragment.show(mFragmentManager,"createDeck");

    }


    public void showAddToDeckDialog(List<Deck>deckList, YugiohCard yugiohCard){
        DialogFragment dialogFragment = AddCardToDeckDialog.newAddToDeckDialog(deckList,yugiohCard);
        dialogFragment.show(mFragmentManager,"addCard");
    }

    public void showErrorDialog(String error,String tag){
        DialogFragment dialogFragment = ErrorDialog.newErrorDialog(error);
        dialogFragment.show(mFragmentManager,tag);
    }


    public void showDeckCompositionDialog(String deckType, Deck deck) {
        DialogFragment dialogFragment = DeckCompositionDialog.newDeckCompositionDialog(deckType,deck);
        dialogFragment.show(mFragmentManager,"deckStats");

    }

    public void showSortOptionsBottomSheetDialog(int currItem){
        BottomSheetDialogFragment bottomSheetDialogFragment = SortOptionsBottomSheetDialog.newInstance(currItem);
        bottomSheetDialogFragment.show(mFragmentManager,"sortTag");
    }

    public void showCoinFlipDialog(){
        DialogFragment dialogFragment = new CoinFlipDialog();
        dialogFragment.show(mFragmentManager,"coinFlip");
    }

    public void showDiceRollDialog(){
        DialogFragment dialogFragment = new DiceRollDialog();
        dialogFragment.show(mFragmentManager,"diceRoll");
    }

    public void showDeleteDeckDialog(List<Deck> decks){
        DialogFragment dialogFragment = DeleteDeckDialog.newInstance(decks);
        dialogFragment.show(mFragmentManager,"delete");
    }

    public void showRenameDialog(List<Deck> decks) {
        DialogFragment dialogFragment = RenameDialog.newInstance(decks);
        dialogFragment.show(mFragmentManager,"rename");
    }
}
