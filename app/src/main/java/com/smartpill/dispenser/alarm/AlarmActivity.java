package com.smartpill.dispenser.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smartpill.dispenser.R;
import com.smartpill.dispenser.adapter.AlarmAdapter;
import com.smartpill.dispenser.database.DBHandler;
import com.smartpill.dispenser.model.Alarm;
import com.smartpill.dispenser.utils.BaseActivity;
import com.smartpill.dispenser.utils.Utils;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class AlarmActivity extends BaseActivity {

    AlarmAdapter alarmAdapter;
    DBHandler dbHandler;
    ImageView imgNoProduct;
    EditText etxtSearch;
    FloatingActionButton fabAdd;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        fabAdd = findViewById(R.id.fab_add);
        etxtSearch = findViewById(R.id.etxt_search);
        imgNoProduct = findViewById(R.id.image_no_product);

        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient));
        getSupportActionBar().setTitle("Alarms");

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        //set color of swipe refresh
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        recyclerView = findViewById(R.id.product_recyclerview);
        imgNoProduct = findViewById(R.id.image_no_product);

        // set a GridLayoutManager with default vertical orientation and 3 number of columns
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setHasFixedSize(true);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, AddAlarmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Utils utils = new Utils();
        dbHandler = new DBHandler(AlarmActivity.this);

        //swipe refresh listeners
        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            if (utils.isNetworkAvailable(AlarmActivity.this)) {
//                getProductsData("",shopID,ownerId);
            } else {
                Toasty.error(AlarmActivity.this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
            }

            //after shuffle id done then swife refresh is off
            mSwipeRefreshLayout.setRefreshing(false);
        });

        ArrayList<Alarm> alarmArrayList;
        alarmArrayList = dbHandler.getAlarms();

        if (alarmArrayList.isEmpty()) {

            recyclerView.setVisibility(View.GONE);
            imgNoProduct.setVisibility(View.VISIBLE);
            imgNoProduct.setImageResource(R.drawable.not_found);
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);

        } else {
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            imgNoProduct.setVisibility(View.GONE);
            alarmAdapter = new AlarmAdapter(AlarmActivity.this, alarmArrayList);
            recyclerView.setAdapter(alarmAdapter);
        }

        etxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("data", s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                ArrayList<Pill> applianceArrayList1;
//                if (s.length() > 1) {
////                    //search data from server
//                    applianceArrayList1 = dbHandler.getPillSearch(s.toString());
//                } else {
//                    applianceArrayList1 = dbHandler.getPillSearch("");
//                }

//                if (applianceArrayList1.isEmpty()) {
//
//                    recyclerView.setVisibility(View.GONE);
//                    imgNoProduct.setVisibility(View.VISIBLE);
//                    imgNoProduct.setImageResource(R.drawable.not_found);
//                    mShimmerViewContainer.stopShimmer();
//                    mShimmerViewContainer.setVisibility(View.GONE);
//
//                } else {
//                    mShimmerViewContainer.stopShimmer();
//                    mShimmerViewContainer.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                    imgNoProduct.setVisibility(View.GONE);
//                    alarmAdapter = new AlarmAdapter(AlarmActivity.this, alarmArrayList);
//                    recyclerView.setAdapter(alarmAdapter);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("data", s.toString());
            }
        });
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