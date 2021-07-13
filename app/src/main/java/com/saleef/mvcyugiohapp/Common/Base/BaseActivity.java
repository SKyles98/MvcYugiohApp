package com.saleef.mvcyugiohapp.Common.Base;

import androidx.appcompat.app.AppCompatActivity;

import com.saleef.mvcyugiohapp.Common.DependencyInjection.ActivityCompositionRoot;
import com.saleef.mvcyugiohapp.Common.DependencyInjection.ControllerCompositionRoot;
import com.saleef.mvcyugiohapp.CustomApplication;

public class BaseActivity extends AppCompatActivity {

    private ActivityCompositionRoot mActivityCompositionRoot;
    private ControllerCompositionRoot mControllerCompositionRoot;


    public ActivityCompositionRoot getActivityCompositionRoot() {
       if (mActivityCompositionRoot==null){
           mActivityCompositionRoot = new ActivityCompositionRoot(this,this,
                   ((CustomApplication) getApplication()).getCompositionRoot());
       }
       return mActivityCompositionRoot;
    }


    protected ControllerCompositionRoot getControllerCompositionRoot() {
        if (mControllerCompositionRoot == null){
            mControllerCompositionRoot = new ControllerCompositionRoot(getActivityCompositionRoot());
        }
        return mControllerCompositionRoot;
    }
}
