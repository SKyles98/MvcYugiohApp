package com.saleef.mvcyugiohapp.UseCases;


import android.util.Log;


import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.Model.Card;
import com.saleef.mvcyugiohapp.Model.YugiohApi;
import com.saleef.mvcyugiohapp.Model.YugiohCardSchema;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FetchCardsUseCase extends BaseObservableMvc<FetchCardsUseCase.Listener> {

       public interface Listener{
          void onAllCardsRetrievalSuccess(List<YugiohCard> YugiohCards);
          void onAllCardsRetrievalFailure(String errorMessage);
    }

    private final YugiohApi mYugiohApi;

    public FetchCardsUseCase(YugiohApi yugiohApi){
        mYugiohApi = yugiohApi;
    }


    public void fetchCards(){
        Observable<YugiohCardSchema> observable = mYugiohApi.getAllYugiohCards();
        observable.map(YugiohCardSchema::getCardList).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::onComplete,this::onError);
    }

    private void onComplete(List<Card> cards){
        List<YugiohCard> yugiohCard = new ArrayList<>(cards.size());
        for (Card card:cards) {
            Log.i("name",card.getName());


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

                Log.i("Complete", yugiohCard.size() + "");
            }
        }

        for (Listener listener:getListeners()){
            listener.onAllCardsRetrievalSuccess(yugiohCard);
        }
    }


    private void onError(Throwable t){
          for (Listener listener:getListeners()){
              listener.onAllCardsRetrievalFailure(t.toString());
          }
    }
}
