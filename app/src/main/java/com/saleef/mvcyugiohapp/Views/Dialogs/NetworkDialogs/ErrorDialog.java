package com.saleef.mvcyugiohapp.Views.Dialogs.NetworkDialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.saleef.mvcyugiohapp.Common.Base.BaseDialog;
import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.R;

public class ErrorDialog extends BaseDialog {






    public static ErrorDialog newErrorDialog(String error){
        ErrorDialog dialog = new ErrorDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ERRORKEY,error);
        dialog.setArguments(bundle);
        return dialog;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.errordialog);
        MaterialButton materialButton = dialog.findViewById(R.id.okButton);
        TextView errorTxt = dialog.findViewById(R.id.errorMessage);
        errorTxt.setText(getArguments().getString(Constant.ERRORKEY));
        materialButton.setOnClickListener(v -> {
            getParentFragmentManager().setFragmentResult(Constant.ERRORKEY,new Bundle());
            dismiss();
        });
        return dialog;
    }
}
