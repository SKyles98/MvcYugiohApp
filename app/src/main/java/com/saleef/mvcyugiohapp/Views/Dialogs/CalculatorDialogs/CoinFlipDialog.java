package com.saleef.mvcyugiohapp.Views.Dialogs.CalculatorDialogs;

import android.app.Dialog;
import android.os.Bundle;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.saleef.mvcyugiohapp.Common.Base.BaseDialog;
import com.saleef.mvcyugiohapp.R;

import java.util.Random;

public class CoinFlipDialog extends BaseDialog {


   private  ImageView imageView;

    private final Random random = new Random();
   private final  int [] coins = {R.drawable.coinheads,R.drawable.tailsimage};
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.coin_flip_dialog);
        MaterialButton flipBtn = dialog.findViewById(R.id.flipButton);
        MaterialButton cancelButton = dialog.findViewById(R.id.cancelButton);
         imageView = dialog.findViewById(R.id.coinImage);

        cancelButton.setOnClickListener(v -> dismiss());

        flipBtn.setOnClickListener(v -> flipCoin());
        return dialog;
    }


    private void flipCoin(){
        for (int i = 0;i<10;i++){
            int rand = random.nextInt(2);
            imageView.setImageResource(coins[rand]);
        }

    }
}
