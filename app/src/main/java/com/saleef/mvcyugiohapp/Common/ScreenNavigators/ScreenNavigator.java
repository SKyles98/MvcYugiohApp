package com.saleef.mvcyugiohapp.Common.ScreenNavigators;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.Views.CardDetailScreen.ZoomedImage.ZoomedImageFragment;
import com.saleef.mvcyugiohapp.Views.DuelCalculator.DuelCalculatorFragment;
import com.saleef.mvcyugiohapp.Views.ViewPagers.DataBaseVIew.DataBasePagerFragment;
import com.saleef.mvcyugiohapp.Views.ViewPagers.DeckView.DeckViewPagerFragment;
import com.saleef.mvcyugiohapp.Views.DecksScreen.DecksScreenFragment;
import com.saleef.mvcyugiohapp.Views.CardDetailScreen.CardDetailFragment;
import com.saleef.mvcyugiohapp.Common.FragmentHelper.FragmentHandler;
import com.saleef.mvcyugiohapp.Views.HomeScreen.HomeScreenFragment;
import com.saleef.mvcyugiohapp.MainActivity;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;
// Handles screen Transitions abstracting away details of how to move to different screens.
public class ScreenNavigator {


    private final FragmentHandler mFragmentHandler;
    private final FragmentActivity mFragmentActivity;

    public ScreenNavigator(FragmentHandler fragmentHandler,FragmentActivity fragmentActivity){
          mFragmentActivity = fragmentActivity;
          mFragmentHandler = fragmentHandler;
    }

    public void leaveSplashScreen(){
        mFragmentActivity.startActivity(new Intent(mFragmentActivity, MainActivity.class));
        mFragmentActivity.finish();
    }

    public void navigateToHomeScreen(){
             mFragmentHandler.replaceFragmentDontAddToBackstack(new HomeScreenFragment());
    }

    public void navigateToDataBase(){
        mFragmentHandler.setNewFragment(new DataBasePagerFragment());
    }

    public void navigateToCardDetail(YugiohCard yugiohCard){
          mFragmentHandler.setNewFragment(CardDetailFragment.newInstance(yugiohCard));
    }


    public void navigateToDuelCalculator(){
          mFragmentHandler.setNewFragment(new DuelCalculatorFragment());
    }

    // Gets the current Activity calls the backPressed
    public void navigateUp(){
//        Handler uiHandler = new Handler();
//        uiHandler.postDelayed(mFragmentHandler::navigateUp,26000);
            mFragmentHandler.navigateUp();
        //mFragmentActivity.onBackPressed();
    }
    public void navigateToDecks(){
        mFragmentHandler.setNewFragment(DecksScreenFragment.newInstance());
    }

    public void navigateToDeckDetails(Deck deck){
      mFragmentHandler.setNewFragment(DeckViewPagerFragment.newInstance(deck));
    }

    public void navigateToZoomedImage(String cardUrl) {
        mFragmentHandler.setNewFragment(ZoomedImageFragment.newInstance(cardUrl));
    }

//    public Fragment getViewPagerFrag(int position) {
//       return  mFragmentHandler.findViewPagerFragment(position);
//    }
}
