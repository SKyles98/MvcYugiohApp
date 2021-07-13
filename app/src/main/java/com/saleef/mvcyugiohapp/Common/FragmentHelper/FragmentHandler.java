package com.saleef.mvcyugiohapp.Common.FragmentHelper;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class FragmentHandler {


    private final FragmentFrameHelper mFragmentFrameHelper;
    private final FragmentManager mFragmentManager;
    private final Activity mActivity;
    public FragmentHandler(FragmentFrameHelper fragmentFrameHelper, FragmentManager fragmentManager,Activity activity){
        mFragmentFrameHelper = fragmentFrameHelper;
        mFragmentManager = fragmentManager;
        mActivity = activity;
    }


    public void setNewFragment(Fragment fragment){
        setNewFragment(fragment,true,false);
    }

    public void replaceFragmentDontAddToBackstack(Fragment newFragment) {
        // Generally reserved for the homescreen fragment as we dont want to return to a white screen
        // after pressing backspace
        setNewFragment(newFragment, false, false);
    }

    public void replaceFragmentAndClearBackstack(Fragment newFragment) {
        setNewFragment(newFragment, false, true);
    }
    private int getFragmentFrameId() {
        // MainActivity controller holds the interface method and then calls the container view class
        // and gets the FrameLayout.
        return mFragmentFrameHelper.getFrameLayout().getId();
    }

    private void setNewFragment(Fragment newFragment,boolean addtoBackStack,boolean clearBackStack){
        if (clearBackStack) {
            if (mFragmentManager.isStateSaved()) {
                // If the state is saved we can't clear the back stack. Simply not doing this, but
                // still replacing fragment is a bad idea. Therefore we abort the entire operation.
                return;
            }
            mFragmentManager.popBackStackImmediate(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (addtoBackStack){
            //Might cause issues later and will probably need to change null to the fragment
            ft.addToBackStack(null);
        }

        ft.replace(getFragmentFrameId(),newFragment,null);
        if (mFragmentManager.isStateSaved()) {
            // We acknowledge the possibility of losing this transaction if the app undergoes
            // save&restore flow after it is committed.
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
    }

    public void navigateUp(){
        if (mFragmentManager.isStateSaved()) {
            return;
        }

        Fragment currentFragment = getCurrentFragment();

        if (mFragmentManager.getBackStackEntryCount() > 0) {

            // In a normal world, just popping back stack would be sufficient, but since android
            // is not normal, a call to popBackStack can leave the popped fragment on screen.
            // Therefore, we start with manual removal of the current fragment.
            // Description of the issue can be found here: https://stackoverflow.com/q/45278497/2463035
            removeCurrentFragment();

            if(mFragmentManager.popBackStackImmediate()){
                return;
            }
                 // navigated "up" in fragments back-stack

            if (currentFragment instanceof HierarchicalFragment) {
                Fragment parentFragment =
                        ((HierarchicalFragment) currentFragment).getHierarchicalParentFragment();
                if (parentFragment != null) {
                    setNewFragment(parentFragment, false, true);
                    return; // navigate "up" to hierarchical parent fragment
                }
            }
            if (mActivity.onNavigateUp()) {
                return; // navigated "up" to hierarchical parent activity
            }

            mActivity.onBackPressed(); // no "up" navigation targets - just treat UP as back press
        }
    }

    private void removeCurrentFragment() {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.remove(getCurrentFragment());
        ft.commitNow();
        // mFragmentManager.executePendingTransactions()
    }

    private Fragment getCurrentFragment() { // Gets the fragment that is currently in the FrameLayout
        return mFragmentManager.findFragmentById(getFragmentFrameId());
    }

    public Fragment findViewPagerFragment(int position){
       return  mFragmentManager.findFragmentByTag("f" + position);
    }
    }



