package com.appli.chacalprog;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by nderoche on 31/01/2017.
 */

public class ComposantProduit extends LinearLayout{

    private TextView libelleTextView;
    private TextView prixTextView;
    private TextView quantiteTextView;
    private Button plusButton;
    private Button moinsButton;
    private FragmentVente fragmentVente;

    public ComposantProduit(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public ComposantProduit(Context context,
                       AttributeSet attrs,
                       int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context);
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context
     *           the current context for the view.
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ecran_produit, this);
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

   libelleTextView = (TextView) this.findViewById(R.id.libelleProduit);
        libelleTextView = (TextView) this.findViewById(R.id.libelleProduit);
        prixTextView = (TextView) this.findViewById(R.id.prixProduit);
        quantiteTextView = (TextView) this.findViewById(R.id.quantiteProduit);
        plusButton = (Button) this.findViewById(R.id.plus);
        moinsButton = (Button) this.findViewById(R.id.moins);

        plusButton.setOnClickListener(

                new OnClickListener() {

                    public void onClick(View arg0) {
                        incremente();
                    }
                }

        );

        moinsButton.setOnClickListener(

                new OnClickListener() {

                    public void onClick(View arg0) {
                        decremente();
                    }
                }

        );

    }
public void setFragmentVente(FragmentVente fragVente){
    fragmentVente=fragVente;
}
    private void incremente(){
        setQuantite(getQuantite()+1);
fragmentVente.calculeTotal();

    }

    private void decremente(){
        if(getQuantite()>0) {
            setQuantite(getQuantite() - 1);
            fragmentVente.calculeTotal();
        }

    }

    public String  getLibelle() {
        return libelleTextView.getText().toString();
    }

    public void setLibelle(String text) {
        this.libelleTextView.setText(text);
    }

    public double  getPrix() {
        return Double.parseDouble(prixTextView.getText().toString().substring(0,quantiteTextView.length()-2));
    }

    public void setPrix(double prix) {
        if (prix % 1 == 0) {//teste si entier
            this.prixTextView.setText(Integer.toString((int)prix) + " €");
        }else {
            this.prixTextView.setText(Double.toString(prix) + " €");
        }



    }


    public int  getQuantite() {
        return Integer.parseInt(quantiteTextView.getText().toString().substring(0,quantiteTextView.length()-4));
    }

    public void setQuantite(int quantite) {
        this.quantiteTextView.setText(Integer.toString(quantite) + " ex.");
    }
}




