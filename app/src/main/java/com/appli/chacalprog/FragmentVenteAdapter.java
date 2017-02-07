package com.appli.chacalprog;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FragmentVenteAdapter extends FragmentPagerAdapter {

    private ArrayList<String> commandes;
    private Context context;

    public FragmentVenteAdapter(android.support.v4.app.FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        //une commande par défaut
        commandes=new ArrayList<String>();
        int numVente=1;
        commandes.add(numVente-1,"Vente "+numVente);
        numVente=2;
        commandes.add(numVente-1,"Vente "+numVente);
    }

    @Override
    public int getCount() {
        return commandes.size();
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        return FragmentVente.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return commandes.get(position);
    }
}