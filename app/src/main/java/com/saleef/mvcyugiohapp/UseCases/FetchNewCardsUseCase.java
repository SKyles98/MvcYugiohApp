package com.saleef.mvcyugiohapp.UseCases;

import android.util.Log;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.Model.Card;
import com.saleef.mvcyugiohapp.Model.YugiohApi;
import com.saleef.mvcyugiohapp.Model.YugiohCardSchema;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FetchNewCardsUseCase extends BaseObservableMvc<FetchNewCardsUseCase.Listener> {

    public interface Listener{
        void onRetrievalSuccess(List<YugiohCard> newestCards);
        void onRetrievalFailure(String errorMessage);
    }

    private final YugiohApi mYugiohApi;
    public FetchNewCardsUseCase(YugiohApi yugiohApi){
        mYugiohApi = yugiohApi;
    }



    public void fetchNewCards(){

        Observable<YugiohCardSchema> observable;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            observable = mYugiohApi.getNewestYugiohCard(Map.of("sort","new","num","58","offset","0"));

        } else {
            observable = mYugiohApi.getNewestYugiohCard(new HashMap<String, String>() {
                {
                    put("sort", "new");
                    put("num", "58");
                    put("offset", "0");
                }
            });
        }
        observable.map(YugiohCardSchema::getCardList).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::onSuccess,this::onFailure);
    }




    private void onSuccess(List<Card> cards) {
        List<YugiohCard> yugiohCard = new ArrayList<>(cards.size());

        for (Card card:cards) {
            if (!card.getType().contains("Skill Card") && !card.getType().contains("Token")) {
                if (card.getType().contains("Link")) { // Link Monsters
                    YugiohCard yCard = new YugiohCard(card.getId(), card.getName(), card.getType(),
                            card.getDesc(), card.getAtk(), 0, 0, card.getRace(),
                            card.getAttribute(), 0,
                            card.getCardImages().get(0).getImageUrl(), card.getLinkRating());
                    yugiohCard.add(yCard);
                } else if (card.getType().contains("Pendulum")) { // Pendulum Monsters
                    YugiohCard yCard = new YugiohCard(card.getId(), card.getName(), card.getType(),
                            card.getDesc(), card.getAtk(), card.getDef(), card.getLevel(), card.getRace(),
                            card.getAttribute(), card.getScale(),
                            card.getCardImages().get(0).getImageUrl(), 0);
                    yugiohCard.add(yCard);
                    Log.i("PendulumCard", card.getName());
                } else if (card.getType().contains("Spell") || card.getType().contains("Trap")) { // Spells and traps
                    YugiohCard yCard = new YugiohCard(card.getId(), card.getName(), card.getType(),
                            card.getDesc(), 0, 0, 0, card.getRace(),
                            card.getType(), 0,
                            card.getCardImages().get(0).getImageUrl(), 0);
                    yugiohCard.add(yCard);
                } else { // Fusion,Normal,Effect Monsters
                    YugiohCard yCard = new YugiohCard(card.getId(), card.getName(), card.getType(),
                            card.getDesc(), card.getAtk(), card.getDef(), card.getLevel(), card.getRace(),
                            card.getAttribute(), 0,
                            card.getCardImages().get(0).getImageUrl(), 0);
                    yugiohCard.add(yCard);
                }

            }
        }
        for (Listener listener:getListeners()){
            listener.onRetrievalSuccess(yugiohCard);
        }
    }
    private void onFailure(Throwable throwable){
        for (Listener listener:getListeners()){
            listener.onRetrievalFailure(throwable.toString());
        }
    }
}
