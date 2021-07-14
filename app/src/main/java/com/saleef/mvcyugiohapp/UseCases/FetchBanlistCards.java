package com.saleef.mvcyugiohapp.UseCases;


import android.util.Log;

import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.Model.Card;
import com.saleef.mvcyugiohapp.Model.YugiohApi;
import com.saleef.mvcyugiohapp.Model.YugiohCardSchema;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;


import io.reactivex.rxjava3.schedulers.Schedulers;

public class FetchBanlistCards extends BaseObservableMvc<FetchBanlistCards.Listener> {

    public interface Listener{
        void onRetrievalSuccess(List<YugiohCard> yugiohCards);
        void onRetrievalFailure(String errorMessage);
    }

    private final YugiohApi mYugiohApi;
    private String mformat;
    public FetchBanlistCards(YugiohApi yugiohApi){
        mYugiohApi = yugiohApi;
    }


    public void fetchBannedCards(String format){
        mformat = format;
        Observable<YugiohCardSchema> observable = mYugiohApi.getBanListCards(format);


        observable.map(YugiohCardSchema::getCardList).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess,this::onError);
    }



    private void onSuccess(List<Card> cards) {

        Set<YugiohCard> cardSet = new HashSet<>(cards.size());
        for (Card card : cards) {
            if (!card.getType().contains("Skill Card") && !card.getType().contains("Token")) {
                if (card.getType().contains("Link")) { // Link Monsters
                    YugiohCard yCard = new YugiohCard(card.getId(), card.getName(), card.getType(),
                            card.getDesc(), card.getAtk(), 0, 0, card.getRace(),
                            card.getAttribute(), 0,
                            card.getCardImages().get(0).getImageUrl(), card.getLinkRating());

                    if (mformat.equals("tcg")){
                        yCard.setTcgBanlistInfo(card.getBanListInfo().getTcgBan());
                    } else{
                        yCard.setOcgBanlistInfo(card.getBanListInfo().getOcgBan());
                    }
                    cardSet.add(yCard);
                } else if (card.getType().contains("Pendulum")) { // Pendulum Monsters
                    YugiohCard yCard = new YugiohCard(card.getId(), card.getName(), card.getType(),
                            card.getDesc(), card.getAtk(), card.getDef(), card.getLevel(), card.getRace(),
                            card.getAttribute(), card.getScale(),
                            card.getCardImages().get(0).getImageUrl(), 0);
                    if (mformat.equals("tcg")){
                        yCard.setTcgBanlistInfo(card.getBanListInfo().getTcgBan());
                    } else{
                        yCard.setOcgBanlistInfo(card.getBanListInfo().getOcgBan());
                    }
                    cardSet.add(yCard);
                    Log.i("PendulumCard", card.getName());
                } else if (card.getType().contains("Spell") || card.getType().contains("Trap")) { // Spells and traps
                    YugiohCard yCard = new YugiohCard(card.getId(), card.getName(), card.getType(),
                            card.getDesc(), 0, 0, 0, card.getRace(),
                            card.getType(), 0,
                            card.getCardImages().get(0).getImageUrl(), 0);
                    if (mformat.equals("tcg")){
                        yCard.setTcgBanlistInfo(card.getBanListInfo().getTcgBan());
                    } else{
                        yCard.setOcgBanlistInfo(card.getBanListInfo().getOcgBan());
                    }
                    cardSet.add(yCard);
                } else { // Fusion,Normal,Effect Monsters
                    YugiohCard yCard = new YugiohCard(card.getId(), card.getName(), card.getType(),
                            card.getDesc(), card.getAtk(), card.getDef(), card.getLevel(), card.getRace(),
                            card.getAttribute(), 0,
                            card.getCardImages().get(0).getImageUrl(), 0);
                    if (mformat.equals("tcg")){
                        yCard.setTcgBanlistInfo(card.getBanListInfo().getTcgBan());
                    } else{
                        yCard.setOcgBanlistInfo(card.getBanListInfo().getOcgBan());
                    }
                    cardSet.add(yCard);
                }
            }
        }
        for (Listener listener:getListeners()){
            listener.onRetrievalSuccess(new ArrayList<>(cardSet));
        }
    }


    private void onError(Throwable throwable){
        for(Listener listener:getListeners()){
            listener.onRetrievalFailure(throwable.toString());
        }
    }

}


