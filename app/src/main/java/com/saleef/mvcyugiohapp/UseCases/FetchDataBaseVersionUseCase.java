package com.saleef.mvcyugiohapp.UseCases;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.Model.DataBaseVersion;
import com.saleef.mvcyugiohapp.Model.YugiohApi;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class FetchDataBaseVersionUseCase extends BaseObservableMvc<FetchDataBaseVersionUseCase.Listener> {
    
   public interface Listener{
        void onUpdateRetrievalSuccess(String dataBaseUpdate);
        void onUpdateRetrievalFailure(String errorMessage);

   }
    
    private final YugiohApi mYugiohApi;
    
    public FetchDataBaseVersionUseCase(YugiohApi yugiohApi){
        mYugiohApi = yugiohApi;
    }
    
    
    
    
    public void fetchDataBaseVer(){

        Observable<List<DataBaseVersion>> observables = mYugiohApi.getDataBaseVersion();
        
        observables.map(dataBaseVersions -> dataBaseVersions.get(0).getLast_update().split(" ")[0])
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::onSuccess,this::onFailure);
    }


    private void onSuccess(String version){

        for (Listener listener:getListeners()){
            listener.onUpdateRetrievalSuccess(version);
        }
    }

    private void onFailure(Throwable Error) {

        for (Listener listener : getListeners()) {
            listener.onUpdateRetrievalFailure(Error.toString());
        }
    }
}
