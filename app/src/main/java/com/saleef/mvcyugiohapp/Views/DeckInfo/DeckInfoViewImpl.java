package com.saleef.mvcyugiohapp.Views.DeckInfo;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saleef.mvcyugiohapp.Common.MvcSkeleton.BaseObservableMvc;
import com.saleef.mvcyugiohapp.ViewModel.Deck;
import com.saleef.mvcyugiohapp.R;

import java.util.ArrayList;
import java.util.List;

public class DeckInfoViewImpl extends BaseObservableMvc<DeckInfoViewMvc.Listener> implements DeckInfoViewMvc, OnChartValueSelectedListener {

    private Deck mDeck;
    private final PieChart mPieChart;
    private  FloatingActionButton mFloatingActionButton;
    public DeckInfoViewImpl(LayoutInflater inflater, ViewGroup viewGroup){
        setRootView(inflater.inflate(R.layout.deck_info_screen,viewGroup,false));
        mPieChart = findViewById(R.id.deckChart);
        mPieChart.setUsePercentValues(false);
        // Listens to particular slices being clicked
        mPieChart.setOnChartValueSelectedListener(this);
    }

    @Override
    public void bindDeckInfo(Deck deck) {
        mDeck = deck;
        setPieChartValues();
        setUpPieChart();
    }


    @Override
    public void shareFab(FloatingActionButton floatingActionButton) {
        mFloatingActionButton = floatingActionButton;
    }
    private void setPieChartValues(){
//       float mf =  (float)mDeck.getMainDeck().size()/100;
//       float ef = (float)mDeck.getExtraDeck().size()/100;
//       float sf = (float)mDeck.getSideDeck().size()/100;
       int m = mDeck.getMainDeck().size();
        int e = mDeck.getExtraDeck().size();
        int s = mDeck.getSideDeck().size();
        List<PieEntry> pieEntries = new ArrayList<>(3);
        pieEntries.add(new PieEntry(m,"Main"));
        pieEntries.add(new PieEntry(e,"Extra"));
        pieEntries.add(new PieEntry(s,"Side"));
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Deck Composition");
        pieDataSet.setColors(colors);
        PieData data = new PieData(pieDataSet);
        data.setDrawValues(true);
        data.setValueTextSize(12f);
        data.setValueTextColor(ContextCompat.getColor(getContext(),R.color.black));

        mPieChart.setData(data);
        mPieChart.invalidate();
        mPieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    private void setUpPieChart(){
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setEntryLabelTextSize(12);
        mPieChart.setEntryLabelColor(ContextCompat.getColor(getContext(),R.color.black));
        mPieChart.setCenterText("Deck Composition");
        mPieChart.setCenterTextColor(ContextCompat.getColor(getContext(),R.color.black));
        mPieChart.setCenterTextSize(24);
        mPieChart.getDescription().setEnabled(false);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry entry = (PieEntry) e;
        String label = entry.getLabel();
        for (Listener listener:getListeners()){
            listener.onPieChartClicked(label);
        }
    }

    @Override
    public void onNothingSelected() {
        // Do nothing
    }

    @Override
    public void nullFabReference() {
        if (mFloatingActionButton!=null){
            mFloatingActionButton = null;
        }
    }
}
