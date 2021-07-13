package com.saleef.mvcyugiohapp.Common.DependencyInjection;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.saleef.mvcyugiohapp.Common.Factories.ViewMvcFactory;
import com.saleef.mvcyugiohapp.Common.Controllers.DrawerToggleController;
import com.saleef.mvcyugiohapp.DataBase.SharedPrefs;
import com.saleef.mvcyugiohapp.UseCases.FetchBanlistCards;
import com.saleef.mvcyugiohapp.UseCases.FetchDataBaseVersionUseCase;
import com.saleef.mvcyugiohapp.UseCases.FetchNewCardsUseCase;
import com.saleef.mvcyugiohapp.Common.FragmentHelper.FragmentFrameHelper;
import com.saleef.mvcyugiohapp.Common.FragmentHelper.FragmentHandler;
import com.saleef.mvcyugiohapp.Common.ScreenNavigators.ScreenNavigator;
import com.saleef.mvcyugiohapp.UseCases.FetchCardsUseCase;
import com.saleef.mvcyugiohapp.Model.YugiohApi;
import com.saleef.mvcyugiohapp.Views.ViewPagers.DataBaseVIew.DataBasePagerAdapter;
import com.saleef.mvcyugiohapp.Views.ViewPagers.DeckView.DeckViewPagerAdapter;
import com.saleef.mvcyugiohapp.Views.Dialogs.DialogManager;

public class ControllerCompositionRoot {

    private final ActivityCompositionRoot mActivityCompositionRoot;


    public ControllerCompositionRoot(ActivityCompositionRoot activityCompositionRoot){
        mActivityCompositionRoot = activityCompositionRoot;
    }



    public YugiohApi getYugiohApi(){
        return mActivityCompositionRoot.getCompositionRoot().getYugiohApi();
    }

    private FragmentActivity getFragmentActivity(){
        return mActivityCompositionRoot.getFragmentActivity();
    }
    private AppCompatActivity getAppCompatActivity(){
        return mActivityCompositionRoot.getAppCompatActivity();
    }

    private LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(mActivityCompositionRoot.getFragmentActivity());
    }

    public FetchCardsUseCase getFetchCardsUseCase(){
        return new FetchCardsUseCase(getYugiohApi());
    }
    public FetchNewCardsUseCase getFetchNewCardsUseCase(){
        return new FetchNewCardsUseCase(getYugiohApi());
    }

    public FetchBanlistCards getFetchBanListCardsUseCase() {
        return new FetchBanlistCards(getYugiohApi());
    }

    public FetchDataBaseVersionUseCase getFetchDataBaseVersionUseCase() {
        return new FetchDataBaseVersionUseCase(getYugiohApi());
    }
    private DrawerToggleController getDrawerToggleController(){
          return new DrawerToggleController(getFragmentActivity());
    }



    private FragmentFrameHelper getFragmentFrameHelper(){
        return  (FragmentFrameHelper) getFragmentActivity();
    }

    private FragmentManager getFragmentManager(){
        return  getFragmentActivity().getSupportFragmentManager();
    }

    private FragmentHandler getFragmentHandler(){
        return new FragmentHandler(getFragmentFrameHelper(),getFragmentManager(),getFragmentActivity());
    }

    public ScreenNavigator getScreenNavigator(){
        return new ScreenNavigator(getFragmentHandler(),getFragmentActivity());
    }

    private DeckViewPagerAdapter getViewPagerAdapter(){
        return new DeckViewPagerAdapter(getFragmentManager(),getFragmentActivity().getLifecycle());
    }

    private DataBasePagerAdapter getDataBasePagerAdapter(){
        return new DataBasePagerAdapter(getFragmentManager(),getFragmentActivity().getLifecycle());
    }

    public ViewMvcFactory getViewMvcFactory(){
        return new ViewMvcFactory(getLayoutInflater(),getDrawerToggleController()
                ,getViewPagerAdapter(),getDataBasePagerAdapter());
    }

    public DialogManager getDialogManger(){
        return new DialogManager(getFragmentActivity(),getFragmentManager());
    }

    private SharedPreferences getSharedPreferences(){
       return getFragmentActivity().getSharedPreferences(getFragmentActivity().getPackageName(), Context.MODE_PRIVATE);
    }
    public SharedPrefs getSharedPrefs(){
        return new SharedPrefs(getSharedPreferences());
    }


    public ContentResolver getContentResolver(){
        return getFragmentActivity().getContentResolver();
    }



}
