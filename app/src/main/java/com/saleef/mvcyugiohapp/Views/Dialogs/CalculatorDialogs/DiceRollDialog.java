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

public class DiceRollDialog extends BaseDialog {


    private MaterialButton rollButton;
    private final int[] diceImages = {R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,
            R.drawable.dice4,R.drawable.dice5,R.drawable.dice6};
    private ImageView mImageView;
    private final Random mRandom = new Random();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.diceroll_dialog);
        MaterialButton cancelButton = dialog.findViewById(R.id.cancelButton);
        rollButton = dialog.findViewById(R.id.rollButton);
        mImageView = dialog.findViewById(R.id.diceImage);
        cancelButton.setOnClickListener(v -> dismiss());
        rollButton.setOnClickListener(v -> rollDice());
        return dialog;
    }


    private void rollDice(){
        for (int i = 0;i<10;i++){
            rollButton.setClickable(false);
            int random = mRandom.nextInt(6);
            mImageView.setImageResource(diceImages[random]);
        }
        rollButton.setClickable(true);
    }
}
