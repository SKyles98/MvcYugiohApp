package com.saleef.mvcyugiohapp.Views.Dialogs.NetworkDialogs;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.saleef.mvcyugiohapp.Common.Base.BaseDialog;
import com.saleef.mvcyugiohapp.R;
// Display a please wait loading dialog when user opens the app
// While displayed we update the database or if user first time enter we perform bulk insertion(11,000 cards)
public class LoadingDialog extends BaseDialog {


    public LoadingDialog(){

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.loading_dialog);
        ProgressBar progressBar = dialog.findViewById(R.id.progress);

        progressBar.setVisibility(View.VISIBLE);
       return dialog;
    }


}
