package com.saleef.mvcyugiohapp.Common.MvcSkeleton;

public interface ObservableMvc<ListenerType> extends ViewMvc {

    void registerListener(ListenerType listenerType);
    void unregisterListener(ListenerType listenerType);

}
