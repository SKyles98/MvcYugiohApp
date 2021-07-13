package com.saleef.mvcyugiohapp.Common.Base;

import androidx.fragment.app.Fragment;


import com.saleef.mvcyugiohapp.Common.DependencyInjection.ControllerCompositionRoot;
import com.saleef.mvcyugiohapp.MainActivity;

public class BaseFragment extends Fragment {

    private ControllerCompositionRoot mControllerCompositionRoot;


    protected ControllerCompositionRoot getControllerCompositionRoot() {
        if (mControllerCompositionRoot==null){
            mControllerCompositionRoot = new ControllerCompositionRoot(((MainActivity) requireActivity()).getActivityCompositionRoot());
        }
        return mControllerCompositionRoot;
    }




}
