package com.smartpill.dispenser.pill;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartpill.dispenser.R;
import com.smartpill.dispenser.database.DBHandler;
import com.smartpill.dispenser.utils.BaseActivity;

import es.dmoral.toasty.Toasty;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

public class AddPillActivity extends BaseActivity {

    EditText etxtpillName, etxtpillDescription, etxtpillConsumption;
    TextView txtAddpill;
    ImageView imgpill;
    DBHandler dbHandler;
    String mediaPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pill);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle(R.string.add_pill);

        etxtpillName = findViewById(R.id.etxt_pill_name);
        etxtpillDescription = findViewById(R.id.etxt_pill_description);
        etxtpillConsumption = findViewById(R.id.etxt_pill_consumption);
        imgpill = findViewById(R.id.image_pill);
        txtAddpill = findViewById(R.id.txt_add_pill);

        dbHandler = new DBHandler(AddPillActivity.this);

        txtAddpill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etxtpillName.getText().toString().trim();
                String description = etxtpillDescription.getText().toString().trim();
                String consumption = etxtpillConsumption.getText().toString().trim();

                if (name.isEmpty()) {
                    etxtpillName.setError(getString(R.string.please_enter_pill_name));
                    etxtpillName.requestFocus();
                } else if (description.isEmpty()) {
                    etxtpillDescription.setError(getString(R.string.please_enter_pill_description));
                    etxtpillDescription.requestFocus();
                } else if (consumption.isEmpty()) {
                    etxtpillConsumption.setError(getString(R.string.please_enter_pill_consumption));
                    etxtpillConsumption.requestFocus();
                } else {
                    dbHandler.addPills(name, description, consumption, mediaPath);
                    Toasty.success(AddPillActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddPillActivity.this, PillActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });

        imgpill.setOnClickListener(v -> {

            Intent intent = new Intent(AddPillActivity.this, ImageSelectActivity.class);
            intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
            intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
            intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
            startActivityForResult(intent, 1213);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1213 && resultCode == RESULT_OK && null != data) {
                mediaPath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
                Bitmap selectedImage = BitmapFactory.decodeFile(mediaPath);
                imgpill.setImageBitmap(selectedImage);
            }
        } catch (Exception e) {
            Toasty.error(this, R.string.something_went_wrong, Toast.LENGTH_LONG).show();
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