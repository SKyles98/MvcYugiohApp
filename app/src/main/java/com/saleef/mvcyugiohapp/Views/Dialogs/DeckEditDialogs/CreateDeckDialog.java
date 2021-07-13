package com.saleef.mvcyugiohapp.Views.Dialogs.DeckEditDialogs;

import android.app.Dialog;
import android.os.Bundle;


import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.android.material.button.MaterialButton;
import com.saleef.mvcyugiohapp.Common.Base.BaseDialog;


import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.R;



public class CreateDeckDialog extends BaseDialog {


     private EditText editText;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.createdeck_dialog);



        editText   = dialog.findViewById(R.id.nameEdit);

        MaterialButton create = dialog.findViewById(R.id.createButton);
        MaterialButton cancel = dialog.findViewById(R.id.cancelButton);


        create.setOnClickListener(v -> {
            // Send Name to shared preferences
           onCreateClicked();
        });

        cancel.setOnClickListener(v -> onCancelClicked());
        return dialog;
    }

    protected void onCancelClicked(){
        dismiss();
    }

    protected void onCreateClicked(){
        Bundle bundle = new Bundle();
        String deckName = editText.getText().toString();
        bundle.putString("deckName",deckName);
        // When we call this line any registered listeners to setFragmentResult will be called
        getParentFragmentManager().setFragmentResult(Constant.CDDialog,bundle);
        dismiss();
    }
}
