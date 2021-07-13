package com.saleef.mvcyugiohapp.Views.ViewHolders;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.R;
import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;


public class CardViewHolderImpl extends BaseObservableMvc<CardViewHolderMvc.Listener> implements CardViewHolderMvc {



     private final ImageView symbolImage,attributeImage,cardImage,starImage;
     private final TextView cardNameText,cardLevelText,cardStatsText,banListTxt;
     private final RelativeLayout mRelativeLayout;
     private YugiohCard mYugiohCard;
    private RequestOptions mRequestOptions;
     public CardViewHolderImpl(LayoutInflater layoutInflater, ViewGroup viewGroup){
         setRootView(layoutInflater.inflate(R.layout.card_viewholder,viewGroup,false));
         symbolImage = findViewById(R.id.symbolImg);
         attributeImage = findViewById(R.id.cardAttributeImg);
         cardImage = findViewById(R.id.cardImg);
         starImage = findViewById(R.id.starImg);
         cardNameText = findViewById(R.id.cardNameTxt);
         cardLevelText = findViewById(R.id.cardLevel);
         cardStatsText = findViewById(R.id.monsterStatsTxt);
         banListTxt = findViewById(R.id.banlistTxt);
         mRelativeLayout = findViewById(R.id.cardLayout);
         mRequestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.monstercarddefault).
                 error(R.drawable.monstercarddefault);
         getRootView().setOnClickListener(v -> {
             for (Listener listener:getListeners()){
                 listener.onCardClicked(mYugiohCard);
             }
         });
     }







    @Override
    public void bindYugiohCards(YugiohCard card) {
         mYugiohCard = card;

         if (!card.getOcgBanlistInfo().equals("empty")){
             setUpBanlistImage("OCG");
         } else if (!card.getTcgBanlistInfo().equals("empty")){
             setUpBanlistImage("TCG");
         } else{
             banListTxt.setVisibility(View.GONE);
         }
         Log.i("yugiohCard",mYugiohCard.getName());
         mRelativeLayout.setBackgroundColor(getBackGroundColor());
         cardNameText.setText(card.getName());
         setUpLogic();
         setUpPlaceHolders();
         loadImage();
    }


    private void setUpLogic(){
         String type = mYugiohCard.getType();
         String race = mYugiohCard.getRace();
         String level = mYugiohCard.getLevel() + "";
         String effectStats = race + " " + "/ " + "Effect";

         if (type.contains("Effect") && type.contains("Monster")) { // Effect Monsters
            monsterVisibility();
             attributeLogic();
             cardStatsText.setText(effectStats);
             cardLevelText.setText(level);
             if(type.contains("Link")){
                 cardLevelText.setVisibility(View.GONE);
                 starImage.setVisibility(View.GONE);
             }
         } else if (type.contains("Spell") || type.contains("Trap")){ //Spell or Trap cards
             SpellORTrapVisibility();
             attributeLogic();
             nonMonsterRaceLogic();
         } else { // Normal Monster Card
             monsterVisibility();
             attributeLogic();
             cardStatsText.setText(race);
             cardLevelText.setText(level);
         }
    }
    // Changes placeholder images based off the card type
    private void setUpPlaceHolders(){
        String cardType = mYugiohCard.getType();

        if (cardType.contains("Monster")){
            if (cardType.contains("Link")){
                   mRequestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.linkmonsterdefault).error(R.drawable.linkmonsterdefault);
            } else {
               mRequestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.monstercarddefault).error(R.drawable.monstercarddefault);
            }
        } else if (cardType.contains("Spell")){
                mRequestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.spellcarddefault).error(R.drawable.spellcarddefault);
        } else if (cardType.contains("Trap")){
            mRequestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.trapcarddefault).error(R.drawable.trapcarddefault);
        }

    }

   private void loadImage(){
       Glide.with(getContext()).
               load(mYugiohCard.getCardImageUrl()) // loads image based off a url
               .apply(mRequestOptions). // apply image based off options
               into(cardImage);
   }
    private void monsterVisibility(){
        cardStatsText.setVisibility(View.VISIBLE);
        cardLevelText.setVisibility(View.VISIBLE);
        starImage.setVisibility(View.VISIBLE);
        symbolImage.setVisibility(View.GONE);
    }
    private void SpellORTrapVisibility(){
        symbolImage.setVisibility(View.VISIBLE);
        cardStatsText.setVisibility(View.GONE);
        cardLevelText.setVisibility(View.GONE);
        starImage.setVisibility(View.GONE);
    }
    // Handles the type of spell or trap it is
    private void nonMonsterRaceLogic(){
         String race = mYugiohCard.getRace();
         switch (race){
             case "Continuous":
                 symbolImage.setImageResource(R.drawable.continuous);
                 break;
             case "Counter":
                 symbolImage.setImageResource(R.drawable.countertrap);
                 break;
             case "Ritual":
                 symbolImage.setImageResource(R.drawable.ritualspell);
                 break;
             case "Quick-Play":
                 symbolImage.setImageResource(R.drawable.quickplayspell);
                 break;
             case "Equip":
                 symbolImage.setImageResource(R.drawable.equipspell);
                 break;
             case "Field":
                 symbolImage.setImageResource(R.drawable.fieldspell);
                 break;
             default:
                 symbolImage.setVisibility(View.GONE);
                 break;
         }
    }

    private void setUpBanlistImage(String bType){
       String banType = bType.equals("TCG") ? mYugiohCard.getTcgBanlistInfo():mYugiohCard.getOcgBanlistInfo();
       String text;
         switch(banType){
             case "Limited":
                 text = "Limited (1)";
                 banListTxt.setText(text);
                 break;
             case "Semi-Limited":
                 text = "Semi-Limited (2)";
                 banListTxt.setText(text);
                 break;
             case "Banned":
                 text = "Banned";
                 banListTxt.setText(text);
                 break;
         }
    }



    // Handles the attribute of the monster
    private void attributeLogic(){
       String attribute =  mYugiohCard.getAttribute();

       switch (attribute){
           case "FIRE":
               attributeImage.setImageResource(R.drawable.fireattribute);
               break;
           case "WIND":
               attributeImage.setImageResource(R.drawable.windattribute);
               break;
           case "LIGHT":
               attributeImage.setImageResource(R.drawable.lightattribute);
               break;
           case "EARTH":
                attributeImage.setImageResource(R.drawable.earthattribute);
                break;
           case "DIVINE":
               attributeImage.setImageResource(R.drawable.divineattribute);
               break;
           case "WATER":
               attributeImage.setImageResource(R.drawable.waterattribute);
               break;
           case "DARK":
               attributeImage.setImageResource(R.drawable.darkattribute);
               break;
           default:
               if (mYugiohCard.getType().contains("Spell")){
                   attributeImage.setImageResource(R.drawable.spellattribute);
               } else{
                   attributeImage.setImageResource(R.drawable.trapattribute);
               }
               break;
       }
    }

    // Sets the backGround color based on card type
    private int getBackGroundColor(){
        String cardType = mYugiohCard.getType();

        if (cardType.contains("Monster")){
            if (cardType.contains("Ritual")){
                return ContextCompat.getColor(getContext(),R.color.light_blue);
            } else {
                return ContextCompat.getColor(getContext(), R.color.orange);
            }
        } else if (cardType.contains("Spell")){
            return ContextCompat.getColor(getContext(),R.color.light_green);
        } else if (cardType.contains("Trap")){
            return ContextCompat.getColor(getContext(),R.color.dark_pink);
        }

        return 1;
    }
}
