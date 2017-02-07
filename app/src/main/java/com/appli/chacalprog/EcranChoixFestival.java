package com.appli.chacalprog;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.appli.chacalprog.R;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class EcranChoixFestival extends AppCompatActivity {

	
	 final Random rnd = new Random();

	private Button btnQuitter;
	private Button btnContinuer;
	private Spinner listeFestival;
	private final DatePickerHelper dateHelper = new DatePickerHelper(this);
	private TextView txtDateFestival;
private ImageView randomImage;
	List<String> tableauFestivals ;
	List<Produit> tableauProduits ;
	static final int timerDelay = 5000;
	private Context context;
	
	
	private Handler myHandler;
	private Runnable myRunnable = new Runnable() {
	@Override
	public void run() {
	    // Code à éxécuter de façon périodique
		setImage();
	    myHandler.postDelayed(this,timerDelay);
	    }
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ecran_choix_festival);


		
		Bundle extraFestivals = getIntent().getBundleExtra("TABLEAU_FESTIVALS");
		tableauFestivals= (ArrayList<String>) extraFestivals.getSerializable("FESTIVALS");
		Bundle extraProduits = getIntent().getBundleExtra("TABLEAU_PRODUITS");
		tableauProduits= (ArrayList<Produit>) extraProduits.getSerializable("PRODUITS");

		listeFestival = (Spinner) findViewById(R.id.listeFestival);
		txtDateFestival = (TextView) findViewById(R.id.saisieDateFestival);
		

		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item, tableauFestivals);
		adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
		listeFestival.setAdapter(adapter);

		dateHelper.init(txtDateFestival);

		btnQuitter = (Button) findViewById(R.id.festival_quitter_id);
		btnContinuer = (Button) findViewById(R.id.festival_continuer_id);

		btnQuitter.setOnClickListener(

				new OnClickListener() {

					public void onClick(View arg0) {
						doQuitter();
					}
				}

		);

		btnContinuer.setOnClickListener(

				new OnClickListener() {

					public void onClick(View arg0) {
						goToEcranSaisie();
					}
				}

		);
		
		 context = EcranChoixFestival.this;
		randomImage = (ImageView) findViewById(R.id.imgRandom);

		setImage();
	        
	    myHandler = new Handler();
	    myHandler.postDelayed(myRunnable,timerDelay); // on redemande toute les 500ms
	}

	
	private void setImage(){
		  
		
	        // I have 3 images named img_0 to img_2, so...
	        String str = "img_" + rnd.nextInt(4);
	        Resources resources = context.getResources();
    		 int resourceId = resources.getIdentifier(str, "drawable", 
    		   context.getPackageName());
	        randomImage.setImageDrawable
	        (
	        		
	        		 resources.getDrawable(resourceId)
	            );
	        
		
	}
	
	
	 protected final static int getResourceID
	    (final String resName, final String resType, final Context ctx)
	    {
	        final int ResourceID =
	            ctx.getResources().getIdentifier(resName, resType,
	                ctx.getApplicationInfo().packageName);
	        if (ResourceID == 0)
	        {
	            throw new IllegalArgumentException
	            (
	                "No resource string found with name " + resName
	                );
	        }
	        else
	        {
	            return ResourceID;
	        }
	    }
	 
	public void onPause() {
	    super.onPause();
	    if(myHandler != null){
	        myHandler.removeCallbacks(myRunnable); // On arrete le callback
	    }
	}
	
	
	protected void goToEcranSaisie() {

		Intent intent = new Intent();
		intent.setClass(this, EcranSaisieVente.class);

		Bundle extraTableauFestivals = new Bundle();
		extraTableauFestivals.putSerializable("FESTIVALS", (Serializable) tableauFestivals);
		Bundle extraTableauProduits = new Bundle();
		extraTableauProduits.putSerializable("PRODUITS", (Serializable) tableauProduits);

		intent.putExtra("TABLEAU_FESTIVALS", extraTableauFestivals);
		intent.putExtra("TABLEAU_PRODUITS", extraTableauProduits);
		startActivity(intent);
		finish();
	}

	
	

	protected void doQuitter() {
		Toast.makeText(this, "A bientôt !", Toast.LENGTH_LONG).show();
		finish();
	}

	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = dateHelper.createDialog(id);
		if (dialog == null) {
			dialog = super.onCreateDialog(id);
		}
		return dialog;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



}
