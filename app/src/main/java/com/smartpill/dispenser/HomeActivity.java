package com.smartpill.dispenser;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Slidetop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.smartpill.dispenser.auth.LoginActivity;
import com.smartpill.dispenser.database.DBHandler;
import com.smartpill.dispenser.profile.ProfileActivity;
import com.smartpill.dispenser.utils.BaseActivity;

public class HomeActivity extends BaseActivity {

    CardView cardAppliances, cardProfile, cardTarrifs, cardTokens, cardThresholds, cardLogout, cardLoadShedding, cardStage;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    DBHandler dbHandler;
    String alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setElevation(0);

        cardLogout = findViewById(R.id.card_logout);
        cardAppliances = findViewById(R.id.card_appliances);
        cardProfile = findViewById(R.id.card_profile);
        cardTarrifs = findViewById(R.id.card_tarrifs);
        cardLoadShedding = findViewById(R.id.card_loadshedding);
        cardStage = findViewById(R.id.card_stages);

        alarm = "m";

        dbHandler = new DBHandler(HomeActivity.this);

        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        cardAppliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(HomeActivity.this, PillActivity.class);
//                startActivity(intent);
            }
        });

        cardAppliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(HomeActivity.this, PillActivity.class);
//                startActivity(intent);
            }
        });

        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(HomeActivity.this);
                dialogBuilder
                        .withTitle(getString(R.string.logout))
                        .withMessage(R.string.want_to_logout_from_app)
                        .withEffect(Slidetop)
                        .withDialogColor("#637ECF") //use color code for dialog
                        .withButton1Text(getString(R.string.yes))
                        .withButton2Text(getString(R.string.cancel))
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                editor.putString(Constant.SP_EMAIL, "");
                                editor.putString(Constant.SP_PASSWORD, "");
                                editor.apply();

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                dialogBuilder.dismiss();
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogBuilder.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
}