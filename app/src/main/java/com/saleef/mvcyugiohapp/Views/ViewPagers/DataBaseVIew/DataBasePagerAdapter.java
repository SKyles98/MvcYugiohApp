package com.saleef.mvcyugiohapp.Views.ViewPagers.DataBaseVIew;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.saleef.mvcyugiohapp.Views.BanListScreen.BanListFragment;
import com.saleef.mvcyugiohapp.Views.DataBaseScreen.DataBaseFragment;

public class DataBasePagerAdapter extends FragmentStateAdapter {


    public DataBasePagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0){
            return new DataBaseFragment();
        } else{
            return new BanListFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
