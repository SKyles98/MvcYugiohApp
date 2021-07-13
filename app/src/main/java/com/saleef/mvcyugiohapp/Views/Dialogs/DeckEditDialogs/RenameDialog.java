package com.saleef.mvcyugiohapp.Views.Dialogs.DeckEditDialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.saleef.mvcyugiohapp.Common.Base.BaseDialog;
import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.R;

import com.saleef.mvcyugiohapp.ViewModel.Deck;

import java.util.ArrayList;
import java.util.List;

public class RenameDialog extends BaseDialog {


  private MaterialButton cancelButton,okBtn;
  private EditText nameEdit;
  private Spinner deckSPinner;



  public static RenameDialog  newInstance(List<Deck> deckList){
              RenameDialog renameDialog = new RenameDialog();
              Bundle bundle = new Bundle();
              bundle.putParcelableArrayList(Constant.DKEY, (ArrayList<? extends Parcelable>) deckList);
              renameDialog.setArguments(bundle);
              return renameDialog;
          }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.rename_dialog);
        cancelButton = dialog.findViewById(R.id.cancelButton);
        okBtn = dialog.findViewById(R.id.okButton);
        nameEdit = dialog.findViewById(R.id.nameEdt);
        initListeners();
        deckSPinner = dialog.findViewById(R.id.deckSpinner);
        ArrayAdapter<Deck> deckArrayAdapter = new ArrayAdapter<>(dialog.getContext(),R.layout.support_simple_spinner_dropdown_item
                ,getArguments().getParcelableArrayList(Constant.DKEY));
        deckSPinner.setAdapter(deckArrayAdapter);
        return dialog;
    }



    private void initListeners(){
        okBtn.setOnClickListener(v->renameDeck());
        cancelButton.setOnClickListener(v->dismiss());
    }

    private void renameDeck(){
        Deck deck = (Deck) deckSPinner.getSelectedItem();
        if (nameEdit.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Deck Name cant be Empty", Toast.LENGTH_SHORT).show();
        } else{
            Bundle bundle = new Bundle();
            bundle.putString("Old",deck.getDeckName());
            bundle.putString("New",nameEdit.getText().toString());
            getParentFragmentManager().setFragmentResult(Constant.RENAMEKEY,bundle);
            dismiss();
        }
    }
}
