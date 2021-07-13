package com.saleef.mvcyugiohapp.Views.HomeScreen;


import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.saleef.mvcyugiohapp.Common.Enums.DrawerItems;
import com.saleef.mvcyugiohapp.Common.Factories.ViewMvcFactory;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.Common.Controllers.DrawerToggleController;

import com.saleef.mvcyugiohapp.R;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;
import com.saleef.mvcyugiohapp.Views.Adapters.YugiohCardRecycler;


import java.util.List;


public class HomeScreenViewImpl extends BaseObservableMvc<HomeScreenMvc.Listener> implements HomeScreenMvc, YugiohCardRecycler.Listener {

     private final  DrawerLayout mDrawerLayout;

        private final DrawerToggleController mDrawerToggleController;
         private final ProgressBar mProgressBar;
         private final Toolbar mToolbar;
    private final YugiohCardRecycler mYugiohCardRecycler;
         private final TextView lastUpdate,lastBanList,totalCards;
         public HomeScreenViewImpl(LayoutInflater layoutInflater, ViewGroup viewGroup,
                                   DrawerToggleController drawerToggleController){
             setRootView(layoutInflater.inflate(R.layout.home_screen,viewGroup,false));
             lastBanList = findViewById(R.id.lastBanlistTxt);
             lastUpdate = findViewById(R.id.lastUpdateTxt);
             totalCards = findViewById(R.id.totalTxt);
             mDrawerLayout = findViewById(R.id.drawerLayout);
             mProgressBar = findViewById(R.id.progress);
             mToolbar = findViewById(R.id.toolbar);
             NavigationView navigationView = findViewById(R.id.nav_view);
             mDrawerToggleController = drawerToggleController;
             RecyclerView recyclerView = findViewById(R.id.newCardRecycler);
             mYugiohCardRecycler = new YugiohCardRecycler(new ViewMvcFactory(layoutInflater,drawerToggleController,null,null),this);
             recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
             recyclerView.setAdapter(mYugiohCardRecycler);

             initDrawer();
             navigationView.setNavigationItemSelectedListener(item -> {

                 switch (item.getItemId()){
                     case R.id.database:
                         onDrawerItemClicked(DrawerItems.DATABASE);
                         break;
                     case R.id.decks:
                         onDrawerItemClicked(DrawerItems.DECKS);
                         break;
                     case R.id.calculator:
                         onDrawerItemClicked(DrawerItems.CALCULATOR);
                         break;
                     default:
                         break;
                 }
                 return false;
             });
         }



         private void initDrawer(){
             mDrawerToggleController.initToggle(mDrawerLayout,mToolbar);
         }





    @Override
    public void bindLatestYugiohCards(List<YugiohCard> yugiohCards) {
          mYugiohCardRecycler.bindYugiohCards(yugiohCards);
    }

    @Override
    public void bindDateInfo(String data, String CardSize) {
           lastUpdate.setText(data);
           lastBanList.setText(R.string.banList);
           totalCards.setText(CardSize);
    }



    @Override
    public void showProgressNotification() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressNotification() {
      mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCardClicked(YugiohCard yugiohCard) {
             for (Listener listener:getListeners()){
                 listener.onCardClicked(yugiohCard);
             }
    }

   private void onDrawerItemClicked(DrawerItems drawerItems){
             switch (drawerItems){
                 case DECKS:
                     for (Listener listener:getListeners()){
                         listener.onDeckBuilderClicked();
                     }
                     break;
                 case DATABASE:
                     for (Listener listener:getListeners()){
                         listener.onDataBaseClicked();
                     }
                     break;
                 case DASHBOARD:
                     break;
                 case CALCULATOR:
                     for (Listener listener:getListeners()){
                         listener.onCalculatorClicked();
                     }
                     break;
             }
   }
}
