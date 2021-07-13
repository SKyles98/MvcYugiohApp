package com.saleef.mvcyugiohapp.Common.MvcSkeleton;

import android.view.View;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseObservableMvc<ListenerType> extends BaseViewMvc implements ObservableMvc<ListenerType>  {

    private Set<ListenerType> listeners = new HashSet<>();
    @Override
    public void registerListener(ListenerType listenerType) {
         listeners.add(listenerType);
    }

    @Override
    public void unregisterListener(ListenerType listenerType) {
         listeners.remove(listenerType);
    }

    protected Set<ListenerType> getListeners(){
        return Collections.unmodifiableSet(listeners);
    }


}
