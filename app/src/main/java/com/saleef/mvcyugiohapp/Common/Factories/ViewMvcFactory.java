package com.saleef.mvcyugiohapp.Common.Factories;

import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.saleef.mvcyugiohapp.Views.BanListScreen.BanListViewImpl;

import com.saleef.mvcyugiohapp.Views.CardDetailScreen.ZoomedImage.ZoomedImageViewImpl;
import com.saleef.mvcyugiohapp.Views.DataBaseScreen.DataBaseViewImpl;
import com.saleef.mvcyugiohapp.Views.DeckInfo.DeckInfoViewImpl;

import com.saleef.mvcyugiohapp.Views.Dialogs.BottomSheets.SortOptions.SortItemsViewImpl;


import com.saleef.mvcyugiohapp.Views.DuelCalculator.DuelCalculatorViewImpl;
import com.saleef.mvcyugiohapp.Views.ExtraDeckList.ExtraDeckViewImpl;
import com.saleef.mvcyugiohapp.Views.MainDeckList.MainDeckViewImpl;

import com.saleef.mvcyugiohapp.Views.SideDeckList.SideDeckVIewImpl;
import com.saleef.mvcyugiohapp.Views.ViewPagers.DataBaseVIew.DataBasePagerAdapter;
import com.saleef.mvcyugiohapp.Views.ViewPagers.DataBaseVIew.DataBasePagerImpl;
import com.saleef.mvcyugiohapp.Views.ViewPagers.DeckView.DeckViewPagerImpl;
import com.saleef.mvcyugiohapp.Views.ViewPagers.DeckView.DeckViewPagerAdapter;
import com.saleef.mvcyugiohapp.Views.Dialogs.AddCardDialog.AddCardViewImpl;
import com.saleef.mvcyugiohapp.Views.ViewHolders.DeckScreenViewHolderImpl;
import com.saleef.mvcyugiohapp.Views.DecksScreen.DecksScreenImpl;
import com.saleef.mvcyugiohapp.Views.CardDetailScreen.CardDetailViewImpl;
import com.saleef.mvcyugiohapp.Views.ViewHolders.CardViewHolderImpl;
import com.saleef.mvcyugiohapp.Views.ViewContainer.ContainerViewImpl;
import com.saleef.mvcyugiohapp.Common.Controllers.DrawerToggleController;
import com.saleef.mvcyugiohapp.Views.HomeScreen.HomeScreenViewImpl;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;
    private final DrawerToggleController mDrawerToggleController;

    private final DeckViewPagerAdapter mDeckViewPagerAdapter;
    private final DataBasePagerAdapter mDataBasePagerAdapter;


    public ViewMvcFactory(LayoutInflater layoutInflater, DrawerToggleController drawerToggleController
            , DeckViewPagerAdapter deckViewPagerAdapter, DataBasePagerAdapter dataBasePagerAdapter){
        mLayoutInflater = layoutInflater;
        mDrawerToggleController = drawerToggleController;
        mDeckViewPagerAdapter = deckViewPagerAdapter;
       mDataBasePagerAdapter = dataBasePagerAdapter;
    }



    public HomeScreenViewImpl getHomeScreenViewImpl(ViewGroup viewGroup){
        return new HomeScreenViewImpl(mLayoutInflater,viewGroup,mDrawerToggleController);
    }

    public CardViewHolderImpl getCardViewHolderImpl(ViewGroup viewGroup){
        return new CardViewHolderImpl(mLayoutInflater,viewGroup);
    }

    public ContainerViewImpl getContainerViewImpl(ViewGroup viewGroup){
        return new ContainerViewImpl(mLayoutInflater,viewGroup);
    }

    public CardDetailViewImpl getCardDetailViewImpl(ViewGroup viewGroup){
        return new CardDetailViewImpl(mLayoutInflater,viewGroup);
    }

    public DecksScreenImpl getDeckScreenImpl(ViewGroup viewGroup){
        return new DecksScreenImpl(mLayoutInflater,viewGroup);
    }
    public DeckScreenViewHolderImpl getDeckScreenViewHolderImpl(ViewGroup viewGroup){
        return new DeckScreenViewHolderImpl(mLayoutInflater,viewGroup);
    }

    public AddCardViewImpl getAddCardViewImpl(ViewGroup viewGroup){
        return new AddCardViewImpl(mLayoutInflater,viewGroup);
    }

    public DeckViewPagerImpl getDeckDetailsViewImpl(ViewGroup viewGroup){
        return new DeckViewPagerImpl(mLayoutInflater,viewGroup, mDeckViewPagerAdapter);
    }


    public DeckInfoViewImpl getDeckInfoViewImpl(ViewGroup viewGroup){
        return new DeckInfoViewImpl(mLayoutInflater,viewGroup);
    }

    public SortItemsViewImpl getSortItemsViewImpl(ViewGroup viewGroup){
        return new SortItemsViewImpl(mLayoutInflater,viewGroup);
    }

    public MainDeckViewImpl getMainDeckViewImpl(ViewGroup viewGroup){
        return new MainDeckViewImpl(mLayoutInflater,viewGroup);
    }

    public SideDeckVIewImpl getSideDeckViewImpl(ViewGroup viewGroup) {
        return new SideDeckVIewImpl(mLayoutInflater,viewGroup);
    }

    public ExtraDeckViewImpl getExtraDeckViewImpl(ViewGroup viewGroup) {
        return new ExtraDeckViewImpl(mLayoutInflater,viewGroup);
    }

    public DataBaseViewImpl getDataBaseViewImpl(ViewGroup viewGroup){
        return new DataBaseViewImpl(mLayoutInflater,viewGroup);
    }

    public BanListViewImpl getBanListViewImpl(ViewGroup viewGroup) {
        return new BanListViewImpl(mLayoutInflater,viewGroup);
    }

    public DataBasePagerImpl getDataBasePagerImpl(ViewGroup viewGroup) {
        return new DataBasePagerImpl(mLayoutInflater,viewGroup,mDataBasePagerAdapter);
    }

    public DuelCalculatorViewImpl getDuelCalculatorViewImpl(ViewGroup container) {
        return new DuelCalculatorViewImpl(mLayoutInflater,container);
    }

    public ZoomedImageViewImpl getZoomedImageViewImpl(ViewGroup viewGroup) {
        return new ZoomedImageViewImpl(mLayoutInflater,viewGroup);
    }
}
