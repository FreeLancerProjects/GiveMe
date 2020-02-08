package com.endpoint.giveme.activities_fragments.bill_activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.endpoint.giveme.R;
import com.endpoint.giveme.language.Language_Helper;
import com.endpoint.giveme.models.BillModel;
import com.endpoint.giveme.tags.Tags;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import io.paperdb.Paper;

public class BillActivity extends AppCompatActivity {

    private ImageView arrow,image;
    private TextView tv_cost,tv_delivery_cost,tv_total;
    private String current_lang;

    @Override
    protected void attachBaseContext(Context base)
    {

        super.attachBaseContext(Language_Helper.updateResources(base,Language_Helper.getLanguage(base)));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        initView();
        getDataFromIntent();
    }




    private void initView() {
        Paper.init(this);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

        arrow = findViewById(R.id.arrow);
        if (current_lang.equals("ar"))
        {
            arrow.setRotation(180.0f);
        }
        image = findViewById(R.id.image);
        tv_cost = findViewById(R.id.tv_cost);
        tv_delivery_cost = findViewById(R.id.tv_delivery_cost);
        tv_total = findViewById(R.id.tv_total);

        arrow.setOnClickListener(v -> finish());
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            BillModel billModel = (BillModel) intent.getSerializableExtra("data");
            tv_cost.setText(String.format("%s %s",getString(R.string.bill_price),billModel.getBill_cost()));
            double total = Double.parseDouble(billModel.getBill_cost())+ Double.parseDouble(billModel.getDelivery_cost());
            tv_delivery_cost.setText(String.format("%s %s %s",getString(R.string.delivery_cost),":",billModel.getDelivery_cost()));
            tv_total.setText(String.format("%s %s",getString(R.string.total), String.valueOf(total)));
            Picasso.with(this).load(Uri.parse(Tags.IMAGE_URL+billModel.getBill_image())).fit().into(image);
        }
    }
}
