package com.smartpill.dispenser.pill;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Slidetop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.smartpill.dispenser.Constant;
import com.smartpill.dispenser.R;
import com.smartpill.dispenser.database.DBHandler;
import com.smartpill.dispenser.model.Pill;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class ViewPillActivity extends AppCompatActivity {

    TextView viewName, viewDescription, viewCondition, viewConsumption, viewYear;
    ImageView viewImage;
    Button editPill, deletePill;
    String PillID;
    DBHandler dbHandler;
    private ArrayList<Pill> PillData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pill);

        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle(R.string.view_pill);

        viewName = findViewById(R.id.txt_view_name);
        viewDescription = findViewById(R.id.txt_view_description);
        viewCondition = findViewById(R.id.txt_view_condition);
        viewConsumption = findViewById(R.id.txt_view_consumption);
        viewYear = findViewById(R.id.txt_view_years);
        viewImage = findViewById(R.id.view_image);
        editPill = findViewById(R.id.btn_edit_pill);
        deletePill = findViewById(R.id.btn_delete_pill);

        dbHandler = new DBHandler(ViewPillActivity.this);

        PillID = getIntent().getExtras().getString(Constant.PILL_ID);
        getPill(PillID);

        deletePill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(ViewPillActivity.this);
                dialogBuilder
                        .withTitle(getString(R.string.delete))
                        .withMessage(R.string.delete_pill)
                        .withEffect(Slidetop)
                        .withDialogColor("#637ECF") //use color code for dialog
                        .withButton1Text(getString(R.string.yes))
                        .withButton2Text(getString(R.string.cancel))
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dbHandler.deletePill(PillID);
                                Toasty.success(ViewPillActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), PillActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                dialogBuilder.dismiss();
                                finish();
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

        editPill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewPillActivity.this, EditPillActivity.class);
                intent.putExtra(Constant.PILL_ID, PillID);
                startActivity(intent);
            }
        });
    }

    public void getPill(String PillID) {
        PillData = dbHandler.getPillsByID(PillID);

        final String Pill_id = PillData.get(0).getPillId();
        String name = PillData.get(0).getPillName();
        String description = PillData.get(0).getPillDescription();
        String consumption = PillData.get(0).getPillConsumption();
        String image = PillData.get(0).getPillImage();

        viewName.setText("Name : " + name);
        viewConsumption.setText("Consumption : " + consumption + " pills per day");
        viewDescription.setText("Description : " + description);

        File imageUrl = new File(image);

        if (image != null) {
            if (image.length() < 3) {
                viewImage.setImageResource(R.drawable.image_placeholder);
            } else {
                Glide.with(ViewPillActivity.this)
                        .load(imageUrl)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.image_placeholder)
                        .into(viewImage);
            }
        }
    }

    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}