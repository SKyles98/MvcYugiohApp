package com.saleef.mvcyugiohapp.Views.ViewPagers.DeckView;

import android.os.Bundle;

import androidx.annotation.NonNull;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saleef.mvcyugiohapp.Common.Base.BaseFragment;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;
import com.saleef.mvcyugiohapp.Common.Constants.Constant;
import com.saleef.mvcyugiohapp.ViewModel.Deck;

// Inflates our View class that has the ViewPager in it
//TODO FINISH Implementing sorting features for decks
public class DeckViewPagerFragment extends BaseFragment implements DeckViewPagerImpl.Listener {


   private DeckViewPagerImpl deckDetailsView;
    private ScreenNavigator mScreenNavigator;

    public DeckViewPagerFragment() {
        // Required empty public constructor
    }



    public static DeckViewPagerFragment newInstance(Deck deck) {
        DeckViewPagerFragment fragment = new DeckViewPagerFragment();
        Bundle args = new Bundle();
       args.putParcelable(Constant.DKEY,deck);

        fragment.setArguments(args);
        return fragment;
    }

    private Deck getDeck(){
        return getArguments().getParcelable(Constant.DKEY);
    }

    @Override
    public void onStart() {
        super.onStart();
        deckDetailsView.registerListener(this);
        deckDetailsView.bindDeck(getDeck());
    }

    @Override
    public void onStop() {
        super.onStop();
        deckDetailsView.unregisterListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           mScreenNavigator = getControllerCompositionRoot().getScreenNavigator();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.i("problem?","ah this was it");
//        deckDetailsView.bindDeck(getDeck());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        deckDetailsView = getControllerCompositionRoot().getViewMvcFactory().getDeckDetailsViewImpl(container);
        return deckDetailsView.getRootView();
    }

    @Override
    public void onNavigateUpClicked() {
        mScreenNavigator.navigateUp();
    }


    // Recieve a position from the tab and instantiate a fragment based off of that position using fragment manager
    //

}