package com.saleef.mvcyugiohapp.Common.Base;

import androidx.fragment.app.DialogFragment;

import com.saleef.mvcyugiohapp.Common.DependencyInjection.ControllerCompositionRoot;
import com.saleef.mvcyugiohapp.MainActivity;

public class BaseDialog extends DialogFragment {


    private ControllerCompositionRoot mControllerCompositionRoot;
    protected ControllerCompositionRoot getControllerCompositionRoot() {
        if (mControllerCompositionRoot==null){
            mControllerCompositionRoot = new ControllerCompositionRoot(((MainActivity) requireActivity()).getActivityCompositionRoot());
        }
        return mControllerCompositionRoot;
    }
}
