package com.saleef.mvcyugiohapp.Views.DuelCalculator;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.R;

// Calculator view class that allows you to keep track of you and your opponents lifepoints. Comes with Dice rolls and coin flips
public class DuelCalculatorViewImpl extends BaseObservableMvc<DuelCalculatorMvc.Listener> implements DuelCalculatorMvc, View.OnClickListener {

     private String currPlayer = "Player1";
     private final MaterialButton oneBtn,twoBtn,threeBtn,fourBtn,fiveBtn,sixBtn,sevenBtn,eightBtn,nineBtn,
             tenBtn,hundredBtn,thousandBtn,halfBtn,otkBtn,clearBtn,addButton,substractBtn;
    private final StringBuilder sb;
    private final TextView player1Txt,player2Txt,amountTxt;
    private final LinearLayout p1,p2;
    private final Toolbar mToolbar;
    private int p2LifePoints = 8000;
    private int p1LifePoints = 8000;
    private final Animation anim;
    public DuelCalculatorViewImpl(LayoutInflater layoutInflater, ViewGroup viewGroup){
        setRootView(layoutInflater.inflate(R.layout.duel_calculator,viewGroup,false));
        oneBtn = findViewById(R.id.oneBtn);
        twoBtn = findViewById(R.id.twoBtn);
        threeBtn = findViewById(R.id.threeBtn);
        fourBtn = findViewById(R.id.fourBtn);
        fiveBtn = findViewById(R.id.fiveBtn);
        sixBtn = findViewById(R.id.sixBtn);
        sevenBtn = findViewById(R.id.sevenBtn);
        eightBtn = findViewById(R.id.eightBtn);
        nineBtn = findViewById(R.id.nineBtn);
        tenBtn = findViewById(R.id.tensBtn);
        clearBtn = findViewById(R.id.clearButton);
        addButton = findViewById(R.id.additionBtn);
        substractBtn = findViewById(R.id.minusBtn);
        hundredBtn = findViewById(R.id.hundredBtn);
        thousandBtn = findViewById(R.id.thousandBtn);
        halfBtn = findViewById(R.id.halfBtn);
        otkBtn = findViewById(R.id.OTKBtn);
        player1Txt = findViewById(R.id.player1Life);
        player2Txt = findViewById(R.id.player2Life);
        p1 = findViewById(R.id.player1layout);
        p2 = findViewById(R.id.player2layout);
        amountTxt = findViewById(R.id.amountTxt);
        mToolbar = findViewById(R.id.calculatorToolbar);
        sb = new StringBuilder();
       anim =  AnimationUtils.loadAnimation(getContext(),R.anim.blinker);
          initToolBarNavigation();
          initToolBarMenu();
          initBtnListeners();
          manageP1Animation(true);
         p1.setClickable(false);
          setUpPlayers();
    }


    private void initToolBarNavigation(){
        mToolbar.setNavigationOnClickListener(v -> {
            for (Listener listener:getListeners()){
                listener.onNavigateUpClicked();
            }
        });

    }

    private void initToolBarMenu(){
        mToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.coin:
                    for (Listener listener:getListeners()){
                        listener.onCoinTossClicked();
                    }
                    break;
                case R.id.dice:
                    for (Listener listener:getListeners()){
                        listener.onDiceRollClicked();
                    }
                    break;
                case R.id.reset:
                     resetValues();
                    break;


            }
            return false;
        });
    }

   private void initBtnListeners(){
        oneBtn.setOnClickListener(this);
       twoBtn.setOnClickListener(this);
       threeBtn.setOnClickListener(this);
       fourBtn.setOnClickListener(this);
       fiveBtn.setOnClickListener(this);
       sixBtn.setOnClickListener(this);
       sevenBtn.setOnClickListener(this);
       eightBtn.setOnClickListener(this);
       nineBtn.setOnClickListener(this);
       tenBtn.setOnClickListener(this);
       hundredBtn.setOnClickListener(this);
       thousandBtn.setOnClickListener(this);
       halfBtn.setOnClickListener(this);
       otkBtn.setOnClickListener(this);
       clearBtn.setOnClickListener(this);
       addButton.setOnClickListener(this);
       substractBtn.setOnClickListener(this);
   }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.hundredBtn:
                setValues("00");
                break;
            case R.id.thousandBtn:
                setValues("000");
                break;
            case R.id.tensBtn:
                setValues("0");
                break;
            case R.id.oneBtn:
                setValues("1");
                break;
            case R.id.twoBtn:
                setValues("2");
                break;
            case R.id.threeBtn:
                setValues("3");
                break;
            case R.id.fourBtn:
                setValues("4");
                break;
            case R.id.fiveBtn:
                setValues("5");
                break;
            case R.id.sixBtn:
                setValues("6");
                break;
            case R.id.sevenBtn:
                setValues("7");
                break;
            case R.id.eightBtn:
                setValues("8");
                break;
            case R.id.nineBtn:
                setValues("9");
                break;
            case R.id.additionBtn:
                operate("+");
                break;
            case R.id.minusBtn:
                operate("-");
                break;
            case R.id.clearButton:
                clearAmount();
                break;
            case R.id.halfBtn:
                operate("half");
                break;
            case R.id.OTKBtn:
                operate("otk");
                break;
        }
    }

   // Activate blinking animation for currently selected player
   private void setUpPlayers(){
        p1.setOnClickListener(v -> {
            manageP1Animation(true);
            manageP2Animation(false);
            currPlayer = "Player1";
            p1.setClickable(false);
            p2.setClickable(true);
        });

       p2.setOnClickListener(v -> {
           manageP1Animation(false);
           manageP2Animation(true);
           p1.setClickable(true);
           p2.setClickable(false);
           currPlayer = "Player2";
       });
   }


    @Override
    public void setAmount(String amount) {
          amountTxt.setText(amount);
    }

    @Override
    public void clearAmount() {
        sb.setLength(0);
        amountTxt.setText("0");
    }






    private void setValues(String value){

        if ( sb.length() == 0 && !value.startsWith("0")){ // Prevents first number from being zero
            sb.append(value);
            setAmount(sb.toString());
        } else if (sb.length() <=6 && !sb.toString().startsWith("0") && !sb.toString().isEmpty() ){
            sb.append(value);
            setAmount(sb.toString());
        } else{
            Toast.makeText(getContext(), "Enter a valid number", Toast.LENGTH_SHORT).show();
        }
    }

    private void operate(String operation){


        if (amountTxt.getText().toString().isEmpty()){
            return;
        }

        switch(operation){
            case "+":
                  setLifePoints(Integer.parseInt(amountTxt.getText().toString()));
                 clearAmount();
                break;
            case "-":
                    setLifePoints(Integer.parseInt(amountTxt.getText().toString()) * -1);
                   clearAmount();
                break;
            case "otk":
                 setLifePoints(currPlayer.equals("Player1") ? p1LifePoints *-1:p2LifePoints*-1);
                 break;
            case "half":
                 setLifePoints(currPlayer.equals("Player1") ? (p1LifePoints/2) *-1:(p2LifePoints/2)*-1);
                 break;
        }
    }
    private void manageP1Animation(boolean start) {
        if (start) {

            p1.startAnimation(anim);
        }else{
//            p1.getAnimation().cancel();
           p1.clearAnimation();
        }
    }

    private void manageP2Animation(boolean start) {
        if (start) {
            p2.startAnimation(anim);
        }else{

            p2.clearAnimation();
        }
    }


    private void setLifePoints(int amount){

        String lifepoints;
        switch (currPlayer){
            case "Player1":
                p1LifePoints = p1LifePoints + amount;
                if (p1LifePoints<0){
                    p1LifePoints = 0;
                }
                lifepoints = "Player 1:" + p1LifePoints;
                player1Txt.setText(lifepoints);
                break;
            case "Player2":
                p2LifePoints = p2LifePoints + amount;
                if (p2LifePoints<0){
                    p2LifePoints = 0;
                }
                 lifepoints = "Player 2:" + p2LifePoints;
                player2Txt.setText(lifepoints);
                break;


        }
        clearAmount();
    }

    private void resetValues(){
        clearAmount();
        p1LifePoints = 8000;
        p2LifePoints = 8000;
        String one = "Player 1: 8000";
        String two = "Player 2: 8000";
        player1Txt.setText(one);
        player2Txt.setText(two);
    }
}
