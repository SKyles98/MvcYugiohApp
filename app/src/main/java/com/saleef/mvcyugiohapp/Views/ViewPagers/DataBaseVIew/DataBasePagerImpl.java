package com.saleef.mvcyugiohapp.Views.ViewPagers.DataBaseVIew;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;

import com.saleef.mvcyugiohapp.R;


public class DataBasePagerImpl extends BaseObservableMvc<DataBasePagerImpl.Listener> {



          interface Listener{
              void onNavigateUpClicked();
          }

    private final TabLayout mTabLayout;
    private final ViewPager2 mViewPager2;
    private final Toolbar mToolbar;


    public DataBasePagerImpl(LayoutInflater layoutInflater, ViewGroup viewGroup, DataBasePagerAdapter dataBasePagerAdapter ) {
        setRootView(layoutInflater.inflate(R.layout.deck_details_viewpager, viewGroup, false));
        mTabLayout = findViewById(R.id.deckTabs);
        mViewPager2 = findViewById(R.id.deckPager);
        mToolbar = findViewById(R.id.toolbar);
        mViewPager2.setAdapter(dataBasePagerAdapter);
        initToolBar();

        for (int i = 1; i <= 2; i++) {
            setTabs(i);
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager2.setCurrentItem(tab.getPosition());

                Log.i("et", mViewPager2.getCurrentItem() + "");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        initPagerCallBack();
        // Callback is invoked when user switches tabs by scrolling or tab Selection

    }






    private void initToolBar(){
        mToolbar.setTitle("DataBase");
        mToolbar.setNavigationOnClickListener(v -> {
            for (Listener listener:getListeners())
                listener.onNavigateUpClicked();
        });

    }


    private void setTabs(int i){
        switch (i){
            case 1:
                mTabLayout.addTab(mTabLayout.newTab().setText("All Cards"));
                break;
            case 2:
                mTabLayout.addTab(mTabLayout.newTab().setText("BanList"));
                break;

        }
    }

    private void initPagerCallBack(){
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTabLayout.selectTab(mTabLayout.getTabAt(position));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);


            }
        });
    }



}
