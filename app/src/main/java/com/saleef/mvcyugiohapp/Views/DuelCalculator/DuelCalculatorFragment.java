package com.saleef.mvcyugiohapp.Views.DuelCalculator;




import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.saleef.mvcyugiohapp.Common.Base.BaseFragment;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;
import com.saleef.mvcyugiohapp.Views.Dialogs.DialogManager;


public class DuelCalculatorFragment extends BaseFragment implements DuelCalculatorMvc.Listener {


    private ScreenNavigator mScreenNavigator;
    private DialogManager mDialogManager;

    private DuelCalculatorMvc mDuelCalculatorMvc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScreenNavigator = getControllerCompositionRoot().getScreenNavigator();
        mDialogManager = getControllerCompositionRoot().getDialogManger();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDuelCalculatorMvc = getControllerCompositionRoot().getViewMvcFactory().getDuelCalculatorViewImpl(container);
        return mDuelCalculatorMvc.getRootView();
    }


    @Override
    public void onStart() {
        super.onStart();
        mDuelCalculatorMvc.registerListener(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        mDuelCalculatorMvc.unregisterListener(this);
    }



    @Override
    public void onNavigateUpClicked() {
         mScreenNavigator.navigateUp();
    }



    @Override
    public void onCoinTossClicked() {
            mDialogManager.showCoinFlipDialog();
    }

    @Override
    public void onDiceRollClicked() {
             mDialogManager.showDiceRollDialog();
    }




}
