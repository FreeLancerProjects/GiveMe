package com.endpoint.giveme.activities_fragments.activity_home.client_home.fragments.fragment_home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.endpoint.giveme.R;
import com.endpoint.giveme.activities_fragments.activity_home.client_home.activity.ClientHomeActivity;
import com.endpoint.giveme.activities_fragments.bill_activity.BillActivity;
import com.endpoint.giveme.models.BillModel;
import com.endpoint.giveme.models.ChatUserModel;
import com.endpoint.giveme.models.OrderDataModel;
import com.endpoint.giveme.remote.Api;
import com.endpoint.giveme.share.Common;
import com.endpoint.giveme.tags.Tags;
import com.google.android.material.appbar.AppBarLayout;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Client_Order_Details extends Fragment {
    private static final String TAG = "ORDER";
    private ClientHomeActivity activity;
    private ImageView image_back, image_chat, image_call,order_image,image_bill;
    private LinearLayout ll_back, ll_delegate_data_container,ll_shipment;
    private CircleImageView image;
    private TextView tv_delegate_name, tv_rate;
    private SimpleRatingBar rateBar;
    private String current_lang;
    private TextView tv_not_approved, tv_order_details,tv_location_pickup,tv_location_dropoff,tv_delegete;
    private RelativeLayout rl;
    private LinearLayout ll;
    private AppBarLayout app_bar;
    private Button btn_follow_order;

    ////////////////////////////////
    private ImageView image1, image2, image3, image4, image5;
    private TextView tv1, tv2, tv3, tv4, tv5, tv_order_id;
    private View view1, view2, view3, view4;
    private Button btn_order_cancel;

    ////////////////////////////////
    private OrderDataModel.OrderModel order;
    private BillModel billModel=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_order_details, container, false);
        initView(view);
        return view;
    }

    public static Fragment_Client_Order_Details newInstance(OrderDataModel.OrderModel order) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG, order);
        Fragment_Client_Order_Details fragment_client_order_details = new Fragment_Client_Order_Details();
        fragment_client_order_details.setArguments(bundle);
        return fragment_client_order_details;
    }

    private void initView(View view) {
        activity = (ClientHomeActivity) getActivity();
        Paper.init(activity);
        current_lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

        image_back = view.findViewById(R.id.image_back);
        if (current_lang.equals("ar")) {
            image_back.setImageResource(R.drawable.ic_right_arrow);
        } else {
            image_back.setImageResource(R.drawable.ic_left_arrow);

        }
        btn_order_cancel=view.findViewById(R.id.btn_order_cancel);
        order_image = view.findViewById(R.id.order_image);
        image_bill = view.findViewById(R.id.image_bill);

        ll_delegate_data_container = view.findViewById(R.id.ll_delegate_data_container);
        ll_shipment = view.findViewById(R.id.ll_shipment);
        tv_location_pickup = view.findViewById(R.id.tv_location_pickup);
        tv_location_dropoff = view.findViewById(R.id.tv_location_dropoff);

        tv_not_approved = view.findViewById(R.id.tv_not_approved);
        tv_order_details = view.findViewById(R.id.tv_order_details);
        btn_follow_order = view.findViewById(R.id.btn_follow_order);
        tv_delegete=view.findViewById(R.id.tv_delegete);
        /////////////////////////////////////////////////
        app_bar = view.findViewById(R.id.app_bar);
        rl = view.findViewById(R.id.rl);
        ll = view.findViewById(R.id.ll);

        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalRang = appBarLayout.getTotalScrollRange();

                if ((totalRang + verticalOffset) <= 30) {
                    ll.setVisibility(View.GONE);
                    rl.setVisibility(View.GONE);
                } else {
                    ll.setVisibility(View.VISIBLE);
                    rl.setVisibility(View.VISIBLE);
                }
            }
        });

        /////////////////////////////////////////////////
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        image4 = view.findViewById(R.id.image4);
        image5 = view.findViewById(R.id.image5);
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);
        tv5 = view.findViewById(R.id.tv5);
        tv_order_id = view.findViewById(R.id.tv_order_id);
        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);
        view4 = view.findViewById(R.id.view4);

        /////////////////////////////////////////////////
        image_chat = view.findViewById(R.id.image_chat);
        image_call = view.findViewById(R.id.image_call);
        ll_back = view.findViewById(R.id.ll_back);
        image = view.findViewById(R.id.image);
        tv_delegate_name = view.findViewById(R.id.tv_delegate_name);
        tv_rate = view.findViewById(R.id.tv_rate);
        rateBar = view.findViewById(R.id.rateBar);


        Bundle bundle = getArguments();
        if (bundle != null) {
            this.order = (OrderDataModel.OrderModel) bundle.getSerializable(TAG);
            UpdateUI(this.order);
        }

        image_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "0"+order.getDriver_user_phone()));
                activity.startActivity(intent);
            }
        });

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();
            }
        });

        image_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatUserModel chatUserModel = new ChatUserModel(order.getDriver_user_full_name(),order.getDriver_user_image(),order.getDriver_id(),order.getRoom_id_fk(),order.getDriver_user_phone_code(),order.getDriver_user_phone(),order.getOrder_id(),order.getDriver_offer());
                activity.NavigateToChatActivity(chatUserModel,"from_fragment");
            }
        });

        btn_follow_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.DisplayFragmentMapFollowOrder(order);
            }
        });

        image_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (billModel!=null)
                {
                    Intent intent = new Intent(activity, BillActivity.class);
                    intent.putExtra("data",billModel);
                    startActivity(intent);
                }

            }
        });


    }


    private void UpdateUI(OrderDataModel.OrderModel order) {

        if (order != null) {
            if (order.getOrder_image()==null)
            {
                
                order_image.setVisibility(View.GONE);
            }else
                {

                    Picasso.with(activity).load(Uri.parse(Tags.IMAGE_URL+order.getOrder_image())).fit().into(order_image);
                    order_image.setVisibility(View.VISIBLE);

                }

            tv_delegate_name.setText(order.getDriver_user_full_name());
            Picasso.with(activity).load(Uri.parse(Tags.IMAGE_URL + order.getDriver_user_image())).placeholder(R.drawable.logo).fit().into(image);
            tv_rate.setText("(" + order.getRate() + ")");
            tv_order_details.setText(order.getOrder_details());

            if (order.getOrder_type().equals("1"))
            {
                ll_shipment.setVisibility(View.GONE);
            }else if (order.getOrder_type().equals("2"))
            {
                tv_location_pickup.setText(order.getPlace_address());
                tv_location_dropoff.setText(order.getClient_address());
                ll_shipment.setVisibility(View.VISIBLE);

            }


            SimpleRatingBar.AnimationBuilder builder = rateBar.getAnimationBuilder();
            builder.setDuration(1500);
            builder.setRepeatCount(0);
            builder.setRatingTarget((float) order.getRate());
            builder.setInterpolator(new AccelerateInterpolator());
            builder.start();

            Log.e("status",order.getOrder_status());
            if (order.getOrder_status().equals(String.valueOf(Tags.STATE_ORDER_NEW))||order.getOrder_status().equals(String.valueOf(Tags.STATE_DELEGATE_SEND_OFFER))) {
                tv_delegete.setVisibility(View.VISIBLE);

            }
            else {
                tv_delegete.setVisibility(View.GONE);

            }
            if (order.getOrder_status().equals(String.valueOf(Tags.STATE_ORDER_NEW))) {
                ll_delegate_data_container.setVisibility(View.GONE);
                image_chat.setVisibility(View.GONE);
                image_call.setVisibility(View.GONE);
                tv_not_approved.setVisibility(View.VISIBLE);

                updateStepView(0);
            } else{

                btn_follow_order.setVisibility(View.VISIBLE);
                ll_delegate_data_container.setVisibility(View.VISIBLE);
                image_chat.setVisibility(View.VISIBLE);
                image_call.setVisibility(View.VISIBLE);
                tv_not_approved.setVisibility(View.GONE);

                updateStepView(Integer.parseInt(order.getOrder_status()));


            }

            tv_order_id.setText(getString(R.string.order_number) + " #" + order.getOrder_id());

        }

    }

    private void getBillData() {
        ProgressDialog dialog = Common.createProgressDialog(activity,getString(R.string.wait));
        dialog.show();
        Api.getService(Tags.base_url)
                .getBillData(order.getOrder_id())
                .enqueue(new Callback<BillModel>() {
                    @Override
                    public void onResponse(Call<BillModel> call, Response<BillModel> response) {
                        dialog.dismiss();

                        if (response.body() != null) {
                            billModel = response.body();
                            Log.e("bill_cost",billModel.getDelivery_cost()+"_");
                            if (billModel.getBill_image()!=null&&billModel.getBill_cost()!=null)
                            {
                                image_bill.setVisibility(View.VISIBLE);
                            }else
                                {
                                    image_bill.setVisibility(View.GONE);

                                }
                        } else {
                            try {
                                Log.e("code",response.code()+"_"+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                            Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BillModel> call, Throwable t) {
                        try {
                            Log.e("error",t.getMessage());
                            dialog.dismiss();
                            Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                        }
                    }
                });
    }

    public void updateStepView(int completePosition) {
        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setCancelable(true)
                .create();
        Log.e("completePosition",completePosition+"__");
        switch (completePosition) {
            case Tags.STATE_ORDER_NEW:
                ClearStepUI();

                break;
            case Tags.STATE_CLIENT_ACCEPT_OFFER:
                image1.setBackgroundResource(R.drawable.step_green_circle);
                image1.setImageResource(R.drawable.step_green_true);
                view1.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv1.setTextColor(ContextCompat.getColor(activity, R.color.green_text));
                image_bill.setVisibility(View.GONE);
                btn_order_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        activity.clientCancelOrder(order.getOrder_id());
                    }
                });

                break;
            case Tags.STATE_DELEGATE_COLLECTING_ORDER:
                image1.setBackgroundResource(R.drawable.step_green_circle);
                image1.setImageResource(R.drawable.step_green_true);
                view1.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv1.setTextColor(ContextCompat.getColor(activity, R.color.green_text));

                image2.setBackgroundResource(R.drawable.step_green_circle);
                image2.setImageResource(R.drawable.step_green_list);
                view2.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv2.setTextColor(ContextCompat.getColor(activity, R.color.green_text));
                image_bill.setVisibility(View.GONE);
                btn_order_cancel.setVisibility(View.GONE);
                break;
            case Tags.STATE_DELEGATE_COLLECTED_ORDER:
                image1.setBackgroundResource(R.drawable.step_green_circle);
                image1.setImageResource(R.drawable.step_green_true);
                view1.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv1.setTextColor(ContextCompat.getColor(activity, R.color.green_text));

                image2.setBackgroundResource(R.drawable.step_green_circle);
                image2.setImageResource(R.drawable.step_green_list);
                view2.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv2.setTextColor(ContextCompat.getColor(activity, R.color.green_text));

                image3.setBackgroundResource(R.drawable.step_green_circle);
                image3.setImageResource(R.drawable.step_green_box);
                view3.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv3.setTextColor(ContextCompat.getColor(activity, R.color.green_text));
                image_bill.setVisibility(View.VISIBLE);
                billModel = new BillModel(order.getBill_image(),order.getBill_cost(),order.getDriver_offer());
                btn_order_cancel.setVisibility(View.GONE);

                getBillData();
                break;
            case Tags.STATE_DELEGATE_DELIVERING_ORDER:
                image1.setBackgroundResource(R.drawable.step_green_circle);
                image1.setImageResource(R.drawable.step_green_true);
                view1.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv1.setTextColor(ContextCompat.getColor(activity, R.color.green_text));

                image2.setBackgroundResource(R.drawable.step_green_circle);
                image2.setImageResource(R.drawable.step_green_list);
                view2.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv2.setTextColor(ContextCompat.getColor(activity, R.color.green_text));

                image3.setBackgroundResource(R.drawable.step_green_circle);
                image3.setImageResource(R.drawable.step_green_box);
                view3.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv3.setTextColor(ContextCompat.getColor(activity, R.color.green_text));

                image4.setBackgroundResource(R.drawable.step_green_circle);
                image4.setImageResource(R.drawable.step_green_truck);
                view4.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv4.setTextColor(ContextCompat.getColor(activity, R.color.green_text));
                image_bill.setVisibility(View.VISIBLE);
                btn_order_cancel.setVisibility(View.GONE);

                billModel = new BillModel(order.getBill_image(),order.getBill_cost(),order.getDriver_offer());

                break;
            case Tags.STATE_DELEGATE_DELIVERED_ORDER:
                image1.setBackgroundResource(R.drawable.step_green_circle);
                image1.setImageResource(R.drawable.step_green_true);
                view1.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv1.setTextColor(ContextCompat.getColor(activity, R.color.green_text));

                image2.setBackgroundResource(R.drawable.step_green_circle);
                image2.setImageResource(R.drawable.step_green_list);
                view2.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv2.setTextColor(ContextCompat.getColor(activity, R.color.green_text));

                image3.setBackgroundResource(R.drawable.step_green_circle);
                image3.setImageResource(R.drawable.step_green_box);
                view3.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv3.setTextColor(ContextCompat.getColor(activity, R.color.green_text));

                image4.setBackgroundResource(R.drawable.step_green_circle);
                image4.setImageResource(R.drawable.step_green_truck);
                view4.setBackgroundColor(ContextCompat.getColor(activity, R.color.green_text));
                tv4.setTextColor(ContextCompat.getColor(activity, R.color.green_text));

                image5.setBackgroundResource(R.drawable.step_green_circle);
                image5.setImageResource(R.drawable.step_green_heart);
                tv5.setTextColor(ContextCompat.getColor(activity, R.color.green_text));
                image_bill.setVisibility(View.VISIBLE);
                btn_order_cancel.setVisibility(View.GONE);
                billModel = new BillModel(order.getBill_image(),order.getBill_cost(),order.getDriver_offer());

                break;

        }
    }



    private void ClearStepUI() {
        image1.setBackgroundResource(R.drawable.gray_circle);
        image1.setImageResource(R.drawable.step_gray_true);
        view1.setBackgroundColor(ContextCompat.getColor(activity, R.color.gray3));
        tv1.setTextColor(ContextCompat.getColor(activity, R.color.gray3));

        image2.setBackgroundResource(R.drawable.gray_circle);
        image2.setImageResource(R.drawable.step_gray_list);
        view2.setBackgroundColor(ContextCompat.getColor(activity, R.color.gray3));
        tv2.setTextColor(ContextCompat.getColor(activity, R.color.gray3));

        image3.setBackgroundResource(R.drawable.gray_circle);
        image3.setImageResource(R.drawable.step_gray_box);
        view3.setBackgroundColor(ContextCompat.getColor(activity, R.color.gray3));
        tv3.setTextColor(ContextCompat.getColor(activity, R.color.gray3));

        image4.setBackgroundResource(R.drawable.gray_circle);
        image4.setImageResource(R.drawable.step_gray_truck);
        view4.setBackgroundColor(ContextCompat.getColor(activity, R.color.gray3));
        tv4.setTextColor(ContextCompat.getColor(activity, R.color.gray3));

        image5.setBackgroundResource(R.drawable.gray_circle);
        image5.setImageResource(R.drawable.step_gray_heart);
        tv5.setTextColor(ContextCompat.getColor(activity, R.color.gray3));
        image_bill.setVisibility(View.GONE);

    }


}
