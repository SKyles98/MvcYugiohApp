package com.saleef.mvcyugiohapp.Views.ViewPagers.DeckView;



import android.util.Log;
import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.R;


//TODO CURRENT BUGS: Floating action disappears when we navigate back to viewpager from card details

// View class that loads all our tabs
public class DeckViewPagerImpl extends BaseObservableMvc<DeckViewPagerImpl.Listener> {


    interface Listener {
        void onNavigateUpClicked();
    }

    private final TabLayout mTabLayout;
    private final ViewPager2 mViewPager2;
    private final Toolbar mToolbar;
    private final DeckViewPagerAdapter mDeckViewPagerAdapter;
    private Deck mDeck;



    public DeckViewPagerImpl(LayoutInflater layoutInflater, ViewGroup viewGroup, DeckViewPagerAdapter deckViewPagerAdapter) {
        setRootView(layoutInflater.inflate(R.layout.deck_details_viewpager, viewGroup, false));
        mTabLayout = findViewById(R.id.deckTabs);
        mViewPager2 = findViewById(R.id.deckPager);
        mToolbar = findViewById(R.id.toolbar);
        mDeckViewPagerAdapter = deckViewPagerAdapter;
        mViewPager2.setAdapter(mDeckViewPagerAdapter);


        for (int i = 1; i <= 4; i++) {
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
        mToolbar.setTitle(mDeck.getDeckName());
        mToolbar.setNavigationOnClickListener(v -> {
            for (Listener listener:getListeners())
                listener.onNavigateUpClicked();
        });

    }


    private void setTabs(int i){
        switch (i){
            case 1:
                mTabLayout.addTab(mTabLayout.newTab().setText("Info"));
                break;
            case 2:
                mTabLayout.addTab(mTabLayout.newTab().setText("Main Deck"));
                break;
            case 3:
                mTabLayout.addTab(mTabLayout.newTab().setText("Extra Deck"));
                break;
            case 4:
                mTabLayout.addTab(mTabLayout.newTab().setText("Side Deck"));
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


   public void bindDeck(Deck deck){
        mDeck = deck;
        mDeckViewPagerAdapter.setDeck(mDeck);
        initToolBar();
   }

   // Current fragment in our tab






}
