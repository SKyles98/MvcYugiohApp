package com.saleef.mvcyugiohapp.Views.Dialogs.BottomSheets.SortOptions;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.Views.Dialogs.BottomSheets.BaseBottomSheetDialog;



public class SortOptionsBottomSheetDialog extends BaseBottomSheetDialog implements SortItemsViewMvc.Listener {


    private SortItemsViewMvc mSortItemsViewMvc;


    public static SortOptionsBottomSheetDialog newInstance(int currItem){
        SortOptionsBottomSheetDialog sortOptionsBottomSheetDialog = new SortOptionsBottomSheetDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("KEY",currItem);
        sortOptionsBottomSheetDialog.setArguments(bundle);
        return sortOptionsBottomSheetDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSortItemsViewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mSortItemsViewMvc.unregisterListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSortItemsViewMvc = getControllerCompositionRoot().getViewMvcFactory().getSortItemsViewImpl(container);
        return mSortItemsViewMvc.getRootView();
    }



    @Override
    public void onApplyClicked(int sortId, int orderId) {
        String requestKey;
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.SORTKEY,sortId);
        bundle.putInt(Constant.ORDERKEY,orderId);
        // We need different keys for listeners to this dialog or the most recent one will always be called
        switch (requireArguments().getInt("KEY")){
            case 1:
                requestKey = "ICHI";
                break;
            case 2:
                requestKey = "Ni";
                break;
            case 3:
                requestKey = "SAN";
                break;
            case 4:
                requestKey = "JU";
                break;
            default:
                requestKey = "WHOOPS";
        }
        getParentFragmentManager().setFragmentResult(requestKey,bundle);
        dismiss();
    }
}
