package com.saleef.mvcyugiohapp.Views.Dialogs.BottomSheets;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.saleef.mvcyugiohapp.Common.DependencyInjection.ControllerCompositionRoot;
import com.saleef.mvcyugiohapp.MainActivity;

public class BaseBottomSheetDialog extends BottomSheetDialogFragment {

    private ControllerCompositionRoot mControllerCompositionRoot;


    protected ControllerCompositionRoot getControllerCompositionRoot(){
        if (mControllerCompositionRoot==null){
            mControllerCompositionRoot = new ControllerCompositionRoot(((MainActivity) requireActivity()).getActivityCompositionRoot());
        }
        return mControllerCompositionRoot;
    }
}
