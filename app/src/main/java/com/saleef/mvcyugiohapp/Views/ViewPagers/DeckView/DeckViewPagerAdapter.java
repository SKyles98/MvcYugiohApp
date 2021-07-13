package com.saleef.mvcyugiohapp.Views.ViewPagers.DeckView;





import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.Views.DeckInfo.DeckInfoFragment;

import com.saleef.mvcyugiohapp.Views.ExtraDeckList.ExtraDeckFragment;
import com.saleef.mvcyugiohapp.Views.MainDeckList.MainDeckFragment;
import com.saleef.mvcyugiohapp.Views.SideDeckList.SideDeckFragment;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;


public class DeckViewPagerAdapter extends FragmentStateAdapter {

    private Deck mDeck;

    public DeckViewPagerAdapter(@NonNull FragmentManager fragmentManager,@NonNull Lifecycle lifecycle) {
        super(fragmentManager,lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //BUT the ViewPager creates the second fragment when the first is visible
        // in order to cache the second fragment and makes it visible when the user starts swiping.


           switch (position){
               case 0:
                   return DeckInfoFragment.newInstance(mDeck);
               case 1:
                   return MainDeckFragment.newInstance(mDeck);
               case 2:
                   return ExtraDeckFragment.newInstance(mDeck);
               case 3:
                   return SideDeckFragment.newInstance(mDeck);

           }
             return DeckInfoFragment.newInstance(mDeck);
    }




    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mDeck = null;
    }

    public void setDeck(Deck deck){
        mDeck = deck;

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        return POSITION_NONE;
    }
}