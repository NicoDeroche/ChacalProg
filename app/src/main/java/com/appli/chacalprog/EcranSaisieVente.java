package com.appli.chacalprog;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.DialogInterface;


import java.io.IOException;
import java.util.ArrayList;

public class EcranSaisieVente extends AppCompatActivity {

    DialogInterface.OnClickListener dialogClickListener;

    private Button btnQuitter;

    public ArrayList<Produit> getTableauProduits() {
        return tableauProduits;
    }

    private ArrayList<Produit> tableauProduits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecran_saisie_container  );


       /// Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentVenteAdapter(getSupportFragmentManager(),
                EcranSaisieVente.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        btnQuitter = (Button) findViewById(R.id.vente_quitter_id);

        btnQuitter.setOnClickListener(

                new View.OnClickListener() {

                    public void onClick(View arg0) {
                        doQuitter();
                    }
                }

        );

        Bundle extra = getIntent().getBundleExtra("TABLEAU_PRODUITS");
        tableauProduits= (ArrayList<Produit>) extra.getSerializable("PRODUITS");

         dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                switch (choice) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(getApplicationContext(), "A bientôt !", Toast.LENGTH_LONG).show();
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

    }




    protected void doQuitter() {
        showAlert();
    }


    private void showAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez-vous quitter l'application ? \r\n Les données en cours ne seront pas sauvegardées.")
                .setPositiveButton("Oui", dialogClickListener)
                .setNegativeButton("Non", dialogClickListener).show();



    }


}
