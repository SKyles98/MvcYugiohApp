package com.saleef.mvcyugiohapp.Utilities;


import com.saleef.mvcyugiohapp.ViewModel.YugiohCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// Class that abstracts away sorting details
public class DeckSorter {

        public DeckSorter(){

        }


        public List<YugiohCard> sortByName(List<YugiohCard> cards,boolean order){
            Collections.sort(cards,YugiohCard.byName);
            if (!order){
                Collections.reverse(cards);
            }
            return cards;
        }



        public List<YugiohCard> sortByAttack(List<YugiohCard> cards,boolean order){

            return new ArrayList<>(splitByType(cards,order,"Attack"));
        }

    public List<YugiohCard> sortByDefense(List<YugiohCard> yugiohCards,boolean order) {

        return new ArrayList<>(splitByType(yugiohCards,order,"Defense"));
    }

    public List<YugiohCard> sortByLevel(List<YugiohCard> yugiohCards,boolean order) {

        return new ArrayList<>(splitByType(yugiohCards,order,"Level"));
    }

    public List<YugiohCard> sortByAttribute(List<YugiohCard> yugiohCards,boolean order) {

        return new ArrayList<>(splitByType(yugiohCards,order,"Attribute"));
    }

    public List<YugiohCard> sortByRace(List<YugiohCard> yugiohCards,boolean order) {

        return new ArrayList<>(splitByType(yugiohCards,order,"Race"));
    }

    public List<YugiohCard> sortByType(List<YugiohCard> yugiohCards,boolean order) {

        return new ArrayList<>(splitByType(yugiohCards,order,"Race"));
    }

    public List<YugiohCard> justOrder(List<YugiohCard> yugiohCards){
            Collections.reverse(yugiohCards);
            return  yugiohCards;
    }

    /*
        Since spell and trap values default to zero and some monsters attacks are also zero we split the types up sort them and then
        bring them back together
        */
   private List<YugiohCard> splitByType(List<YugiohCard> yugiohCards,boolean order,String type){
       List<YugiohCard> monsters = new ArrayList<>();
       List<YugiohCard> spells = new ArrayList<>();
       List<YugiohCard> traps = new ArrayList<>();

       for (YugiohCard yugiohCard:yugiohCards){
           if (yugiohCard.getType().contains("Monster")){
               monsters.add(yugiohCard);
           } else if (yugiohCard.getType().contains("Spell")){
               spells.add(yugiohCard);
           } else if (yugiohCard.getType().contains("Trap")){
               traps.add(yugiohCard);
           }
       }

       switch (type){
           case "Attack":
               Collections.sort(monsters,YugiohCard.byAttack);
               break;
           case "Defense":
               Collections.sort(monsters,YugiohCard.byDefense);
               break;
           case "Attribute":
               Collections.sort(monsters,YugiohCard.byAttribute);
               break;
           case "Level":
               Collections.sort(monsters,YugiohCard.byLevel);
               break;
           case "Race":
               Collections.sort(monsters,YugiohCard.byRace);
               Collections.sort(spells,YugiohCard.byRace);
               Collections.sort(traps,YugiohCard.byRace);
               break;
           case "Type":
               Collections.sort(monsters,YugiohCard.byType);
               Collections.sort(spells,YugiohCard.byType);
               Collections.sort(traps,YugiohCard.byType);
               break;
       }
       if (!order){
           Collections.reverse(monsters);
       }

       List<YugiohCard> linkedList = new LinkedList<>();
       linkedList.addAll(monsters);
       linkedList.addAll(spells);
       linkedList.addAll(traps);
        return linkedList;
   }


}
