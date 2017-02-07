package com.appli.chacalprog;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;


// In this case, the fragment displays simple text based on the page
public class FragmentVente extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
private ArrayList<Produit> tableauProduits;
    private TextView totalTexte;

    public static android.support.v4.app.Fragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        android.support.v4.app.Fragment fragment = new FragmentVente();
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        tableauProduits=((EcranSaisieVente)getActivity()).getTableauProduits();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ecran_saisie, container, false);

        LinearLayout layout = (LinearLayout) view;
        int i,j;
        for ( i=0;i<tableauProduits.size() && i < 10;i++){

            Produit produit = tableauProduits.get(i);
            int resID = getResources().getIdentifier("produit"+((int)i+1), "id", "com.appli.chacalprog");
            ComposantProduit composantProduit= (ComposantProduit) layout.findViewById(resID);
            composantProduit.setLibelle(produit.getLibelle());
            composantProduit.setPrix(produit.getPrix());
            composantProduit.setQuantite(0);
            composantProduit.setFragmentVente(this);
        }

        for( j=i;j<10;j++){
            int resID = getResources().getIdentifier("produit"+((int)j+1), "id", "com.appli.chacalprog");
            ComposantProduit composantProduit= (ComposantProduit) layout.findViewById(resID);
            composantProduit.setVisibility(View.INVISIBLE);
        }

        totalTexte=(TextView) layout.findViewById(R.id.totalVente);

        return view;
    }


    public void calculeTotal(){
totalTexte.setText("30");
    }


}