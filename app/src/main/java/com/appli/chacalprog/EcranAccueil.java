package com.appli.chacalprog;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.appli.chacalprog.R.layout.test2;

public class EcranAccueil extends AppCompatActivity {

	private EditText edtNom;
	private Button btnQuitter;
	private Button btnContinuer;
	List<String> tableauFestivals ;
	List<Produit> tableauProduits;
    private String pathDir;

    public static final int PRODUITS = 1;
    public static final int FESTIVALS = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ecran_accueil);

		

		tableauFestivals  = new ArrayList<String>();
		tableauProduits  = new ArrayList<Produit>();
		// edtNom = (EditText) findViewById(R.id.nomsaisi_id);
		btnQuitter = (Button) findViewById(R.id.accueil_quitter_id);
		btnContinuer = (Button) findViewById(R.id.accueil_continuer_id);

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
						goToEcranFestival();
					}
				}

		);

		if (isStoragePermissionGranted()) {
			ecrireFichiers();
		}

		

	}

	protected void doQuitter() {
		Toast.makeText(this, "A bientôt !", Toast.LENGTH_LONG).show();
		finish();
	}


	protected void goToEcranFestival() {
		Bundle extraTableauFestivals = new Bundle();
		extraTableauFestivals.putSerializable("FESTIVALS", (Serializable) tableauFestivals);
        Bundle extraTableauProduits = new Bundle();
        extraTableauProduits.putSerializable("PRODUITS", (Serializable) tableauProduits);
		Intent intent = new Intent();
		intent.setClass(this, EcranChoixFestival.class);
		 intent.putExtra("TABLEAU_FESTIVALS", extraTableauFestivals);
        intent.putExtra("TABLEAU_PRODUITS", extraTableauProduits);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean isStoragePermissionGranted() {
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(
					android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
			//	Toast.makeText(this, "écriture autorisée", Toast.LENGTH_LONG).show();
				return true;
			} else {
				// Toast.makeText(this, "écriture interdite",
				// Toast.LENGTH_LONG).show();
				ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
				return false;
			}
		} else { // permission is automatically granted on sdk<23 upon
					// installation
					// Toast.makeText(this, "version antérieure",
					// Toast.LENGTH_LONG).show();
			return true;
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			// resume tasks needing this permission
			// Toast.makeText(this, "droits accordés",
			// Toast.LENGTH_LONG).show();
			ecrireFichiers();
		}
	}

	protected void ecrireFichiers() {

		String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();

		 pathDir = baseDir + "/Android/data/com.appli.chacalprog/";
		//Toast.makeText(this, pathDir, Toast.LENGTH_LONG).show();
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
				&& !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {

			File myDir = new File(pathDir);

			Boolean success = true;
			if (!myDir.exists()) {
				success = myDir.mkdir(); // On crée le répertoire (s'il n'existe
											// pas!!)
			}
			if (success) {

				ecrireFichier(PRODUITS) ;
				ecrireFichier(FESTIVALS);


			}
		}
	}


	protected void ecrireFichier(int typeFichier) {

        String filename;
        int id;
        List tableau;
        if(typeFichier == PRODUITS){
            filename = "liste_produits.txt";
            id=  R.raw.liste_produits;
            tableau = tableauProduits;
        }
        else{

            filename="liste_festivals.txt";
            id = R.raw.liste_festivals;
            tableau=tableauFestivals;
        }



				File f = new File(pathDir + File.separator + filename);

				if (!f.exists()) {

					//Toast.makeText(this, "écriture", Toast.LENGTH_LONG).show();
					InputStream databaseInputStream = getResources().openRawResource(id);

					FileOutputStream output;
					try {
						output = new FileOutputStream(f, true);
						copyStream(databaseInputStream, output);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}


				FileInputStream input;
				try {
					input = new FileInputStream(f);
					Scanner scanner = new Scanner(input);

					while(scanner.hasNextLine()){
						// TODO : controles : 2 colonnes, entier, pas plus de 10 lignes...

                        if(typeFichier==PRODUITS){
                            String ligne = scanner.nextLine();
                            String[] split = ligne.split(";");
                            if(split.length==2){
                                Produit produit= new Produit(split[0],Double.parseDouble(split[1]));
                                tableau.add(produit);
                            }
                        }else {
                            tableau.add(scanner.nextLine());
                        }
					}

					scanner.close();


				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



	}

	public static void copyStream(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[1024]; // Adjust if you want
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

	/*
	 * protected void onResume(){ super.onResume(); Intent intent=getIntent();
	 * if(intent!=null){ String nom=intent.getStringExtra("NOM"); if
	 * (nom!=null){ edtNom.setText(nom); } } }
	 */
}
