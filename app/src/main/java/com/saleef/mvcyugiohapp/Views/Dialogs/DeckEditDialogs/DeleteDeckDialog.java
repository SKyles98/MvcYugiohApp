package com.saleef.mvcyugiohapp.Views.Dialogs.DeckEditDialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.saleef.mvcyugiohapp.Common.Base.BaseDialog;
import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.R;
import com.saleef.mvcyugiohapp.ViewModel.Deck;

import java.util.ArrayList;
import java.util.List;


public class DeleteDeckDialog extends BaseDialog {


    private MaterialButton okBtn,cancelBtn;
    private Spinner mSpinner;

    public static DeleteDeckDialog newInstance(List<Deck> decks){
        DeleteDeckDialog deleteDeckDialog = new DeleteDeckDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constant.DKEY, (ArrayList<? extends Parcelable>) decks);
        deleteDeckDialog.setArguments(bundle);
        return  deleteDeckDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
         Dialog dialog = new Dialog(requireContext());
         dialog.setContentView(R.layout.delete_deck_dialog);

         okBtn = dialog.findViewById(R.id.okButton);
         cancelBtn = dialog.findViewById(R.id.cancelButton);
         mSpinner = dialog.findViewById(R.id.deckSpinner);
         initButtonListeners();
        ArrayAdapter<Deck> deckArrayAdapter = new ArrayAdapter<>(dialog.getContext(),R.layout.support_simple_spinner_dropdown_item
                 ,getArguments().getParcelableArrayList(Constant.DKEY));
        mSpinner.setAdapter(deckArrayAdapter);


        return dialog;
    }

    private void deleteDeck(){
        Deck deck = (Deck) mSpinner.getSelectedItem();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.DKEY,deck.getDeckName());
        getParentFragmentManager().setFragmentResult(Constant.DELETEKEY,bundle);
        dismiss();
    }

    private void initButtonListeners(){

        okBtn.setOnClickListener(v -> deleteDeck());

        cancelBtn.setOnClickListener(v -> dismiss());
    }
}
