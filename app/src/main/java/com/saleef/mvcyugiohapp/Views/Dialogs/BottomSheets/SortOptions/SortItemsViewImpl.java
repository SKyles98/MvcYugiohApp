package com.saleef.mvcyugiohapp.Views.Dialogs.BottomSheets.SortOptions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.R;

public class SortItemsViewImpl extends BaseObservableMvc<SortItemsViewMvc.Listener> implements SortItemsViewMvc {


    private final RadioGroup sortGroup,orderGroup;
    private int currSortChecked,currOrderChecked;
    private final Button mButton;
    public SortItemsViewImpl(LayoutInflater layoutInflater, ViewGroup viewGroup){
        setRootView(layoutInflater.inflate(R.layout.sort_options_bottom_sheet,viewGroup,false));
        sortGroup = findViewById(R.id.sortGroup);
        orderGroup = findViewById(R.id.orderGroup);
        mButton = findViewById(R.id.apply);
        currOrderChecked = 0;
        currSortChecked = 0;
        initSort();
        initOrder();
        initBtn();
    }

    private void initSort(){
        sortGroup.setOnCheckedChangeListener((group, checkedId) -> currSortChecked = checkedId);
    }

    private void initOrder(){
       orderGroup.setOnCheckedChangeListener((group, checkedId) -> currOrderChecked = checkedId);
    }

   private void initBtn(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Listener listener:getListeners()){
                    listener.onApplyClicked(currSortChecked,currOrderChecked);
                }
            }
        });
   }
}
