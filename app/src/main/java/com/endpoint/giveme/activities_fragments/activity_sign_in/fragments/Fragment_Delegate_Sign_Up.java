package com.endpoint.giveme.activities_fragments.activity_sign_in.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.endpoint.giveme.R;
import com.endpoint.giveme.activities_fragments.activity_sign_in.activity.SignInActivity;
import com.endpoint.giveme.share.Common;
import com.endpoint.giveme.tags.Tags;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.listeners.OnCountryPickerListener;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import io.paperdb.Paper;

public class Fragment_Delegate_Sign_Up extends Fragment implements DatePickerDialog.OnDateSetListener , OnCountryPickerListener {

    private FloatingActionButton fab;
    private SignInActivity activity;
    private EditText edt_name, edt_email;
    private ImageView image_personal, image_icon1, image_back_photo;
    private LinearLayout ll_back, ll_birth_date;
    private TextView tv_birth_date;
    private final String read_permission = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private SegmentedButtonGroup segmentGroup;
    private String current_language;
    private int gender = Tags.MALE;
    private long date_of_birth = 0;
    private ImageView arrow;
    private LinearLayout ll_country;
    private TextView tv_country,tv_code,tv_note;
    //
    private EditText edtusername,edtpass,edt_phone;
    private CountryPicker picker;
    private String code = "";
    private String country_code="sa";
    private ImageView image_id, image_id_icon, image_license, image_license_icon,car_front_image,car_front_icon,car_back_image,car_back_icon;
    private FrameLayout fl_id_image, fl_license_image,fl_car_front_image,fl_car_back_image;
    private EditText edt_national_num, edt_address;
    private final int IMG_REQ1 = 1, IMG_REQ2 = 2,IMG_REQ3=3,IMG_REQ4=4,IMG_REQ5=5;
    private Uri uri = null,imgUri1 = null, imgUri2 = null,imgUri3 = null,imgUri4 = null;
    private int selectedType = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delegate_sign_up, container, false);
        initView(view);
        return view;
    }

    public static Fragment_Delegate_Sign_Up newInstance() {
        return new Fragment_Delegate_Sign_Up();
    }

    private void initView(View view) {

        activity = (SignInActivity) getActivity();
        Paper.init(activity);
        current_language = Paper.book().read("lang", Locale.getDefault().getLanguage());
        ll_back = view.findViewById(R.id.ll_back);
        image_back_photo = view.findViewById(R.id.image_back_photo);
        if (current_language.equals("ar")) {
            image_back_photo.setImageResource(R.drawable.ic_right_arrow);
            image_back_photo.setColorFilter(ContextCompat.getColor(activity, R.color.black), PorterDuff.Mode.SRC_IN);

        } else {
            image_back_photo.setImageResource(R.drawable.ic_left_arrow);
            image_back_photo.setColorFilter(ContextCompat.getColor(activity, R.color.black), PorterDuff.Mode.SRC_IN);



        }
        arrow = view.findViewById(R.id.arrow);
//        ll_country = view.findViewById(R.id.ll_country);
//        tv_country = view.findViewById(R.id.tv_country);
        tv_code = view.findViewById(R.id.tvCode);
        tv_note = view.findViewById(R.id.tv_note);

        edt_phone = view.findViewById(R.id.edtPhone);
        edtusername=view.findViewById(R.id.edtName);
        edtpass=view.findViewById(R.id.edtPassword);
        ll_birth_date = view.findViewById(R.id.ll_birth_date);
        tv_birth_date = view.findViewById(R.id.tv_birth_date);

        edt_name = view.findViewById(R.id.edt_name);
        edt_email = view.findViewById(R.id.edt_email);
        image_personal = view.findViewById(R.id.image_personal);
        image_icon1 = view.findViewById(R.id.image_icon1);
        segmentGroup = view.findViewById(R.id.segmentGroup);
        fab = view.findViewById(R.id.fab);

        ///////////////////////////////////////////////
        image_id = view.findViewById(R.id.image_id);
        image_id_icon = view.findViewById(R.id.image_id_icon);
        image_license = view.findViewById(R.id.image_license);
        image_license_icon = view.findViewById(R.id.image_license_icon);
        ll_back = view.findViewById(R.id.ll_back);
        edt_national_num = view.findViewById(R.id.edt_national_num);
        edt_address = view.findViewById(R.id.edt_address);

        fl_id_image = view.findViewById(R.id.fl_id_image);
        fl_license_image = view.findViewById(R.id.fl_license_image);


        fl_car_front_image = view.findViewById(R.id.fl_car_front_image);
        fl_car_back_image = view.findViewById(R.id.fl_car_back_image);
        car_front_image = view.findViewById(R.id.car_front_image);
        car_front_icon = view.findViewById(R.id.car_front_icon);
        car_back_image = view.findViewById(R.id.car_back_image);
        car_back_icon = view.findViewById(R.id.car_back_icon);

        ///////////////////////////////////////////////



        image_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateImageAlertDialog(IMG_REQ5);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckData();
            }
        });
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();
            }
        });
        ll_birth_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDateDialog();
            }
        });
        segmentGroup.setOnClickedButtonListener(new SegmentedButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(int position) {
                if (position == 0) {
                    gender = Tags.MALE;
                } else {
                    gender = Tags.FEMALE;
                }
            }
        });

        fl_id_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateImageAlertDialog(IMG_REQ1);
            }
        });

        fl_license_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateImageAlertDialog(IMG_REQ2);
            }
        });

        fl_car_front_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateImageAlertDialog(IMG_REQ3);
            }
        });
        fl_car_back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateImageAlertDialog(IMG_REQ4);
            }
        });



        if (current_language.equals("ar"))
        {
            arrow.setImageResource(R.drawable.ic_left_arrow);
            arrow.setColorFilter(ContextCompat.getColor(activity,R.color.black), PorterDuff.Mode.SRC_IN);
        }else
        {
            arrow.setImageResource(R.drawable.ic_right_arrow);
            arrow.setColorFilter(ContextCompat.getColor(activity,R.color.black), PorterDuff.Mode.SRC_IN);

        }

        CreateCountryDialog();

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.showDialog(activity);
            }


        });


    }
    private void CreateCountryDialog() {
        CountryPicker.Builder builder = new CountryPicker.Builder()
                .canSearch(true)
                .with(activity)
                .listener(this);
        picker = builder.build();

        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            if (picker.getCountryFromSIM() != null) {
                updateUi(picker.getCountryFromSIM());

            } else if (telephonyManager != null && picker.getCountryByISO(telephonyManager.getNetworkCountryIso()) != null)
            {
                updateUi(picker.getCountryByISO(telephonyManager.getNetworkCountryIso()));


            }

            else if (Locale.getDefault()!=null&&picker.getCountryByLocale(Locale.getDefault()) != null) {
                updateUi(picker.getCountryByLocale(Locale.getDefault()));

            }else
            {
                tv_code.setText("+966");
                tv_country.setText("Saudi Arabia");
                this.country_code = "sa";

            }
        }catch (Exception e)
        {
            tv_code.setText("+966");
            tv_country.setText("Saudi Arabia");
            this.country_code = "sa";
        }





    }
    @Override
    public void onSelectCountry(Country country) {
        updateUi(country);
    }

    private void updateUi(Country country) {
        country_code = country.getCode();
        tv_code.setText(country.getDialCode());
        code = country.getDialCode();




    }

    private void CreateDateDialog() {
        Calendar calendar = Calendar.getInstance(new Locale(current_language));
        Calendar calendar_now = Calendar.getInstance(new Locale(current_language));

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.YEAR, (calendar_now.get(Calendar.YEAR) - 18));

        DatePickerDialog dialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.setOkText(getString(R.string.select));
        dialog.setCancelText(getString(R.string.cancel));
        dialog.setAccentColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        dialog.setOkColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        dialog.setCancelColor(ContextCompat.getColor(activity, R.color.gray4));
        dialog.setLocale(new Locale(current_language));
        dialog.setVersion(DatePickerDialog.Version.VERSION_1);
        dialog.setMaxDate(calendar);
        dialog.show(activity.getFragmentManager(), "");

    }

    private void CheckData() {
        String m_name = edt_name.getText().toString().trim();
        String m_email = edt_email.getText().toString().trim();
        String m_national_id = edt_national_num.getText().toString().trim();
        String m_address = edt_address.getText().toString().trim();
        String phone=edt_phone.getText().toString().trim();
        String name = edtusername.getText().toString().trim();
        String pass=edtpass.getText().toString().trim();
        if (phone.startsWith("0"))
        {
            phone = phone.replaceFirst("0","");
        }


        if (!TextUtils.isEmpty(m_name) &&
//                !TextUtils.isEmpty(m_email) &&
//                Patterns.EMAIL_ADDRESS.matcher(m_email).matches() &&
                !TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass) &&pass.length()>=6&&!TextUtils.isEmpty(phone)&&

                date_of_birth != 0&&
                !TextUtils.isEmpty(m_national_id) &&
                !TextUtils.isEmpty(m_address)&&
                imgUri1!=null&&
                imgUri2!=null&&
                imgUri3!=null&&
                imgUri4!=null
        ) {
            Common.CloseKeyBoard(activity, edt_name);
            edt_name.setError(null);
         //   edt_email.setError(null);
            edt_address.setError(null);
            edt_national_num.setError(null);
            tv_birth_date.setError(null);
            edtusername.setError(null);
            edtpass.setError(null);
            edt_phone.setError(null);
            if (uri == null) {
                activity.signUpDelegateWithoutImage(m_name,name,pass,phone,code.replace("+","00"),country_code, m_email, gender, date_of_birth,m_national_id,m_address,imgUri1,imgUri2,imgUri3,imgUri4);

            } else {
                activity.signUpDelegateWithImage(m_name,name,pass,phone,code.replace("+","00"),country_code, m_email, gender, date_of_birth,m_national_id,m_address,uri,imgUri1,imgUri2,imgUri3,imgUri4);

            }

        } else {
            if (TextUtils.isEmpty(m_name)) {
                edt_name.setError(getString(R.string.field_req));
            } else {
                edt_name.setError(null);

            }
            if (TextUtils.isEmpty(name)) {
                edtusername.setError(getString(R.string.field_req));
            } else
            {
                edtusername.setError(null);
            }
            if (TextUtils.isEmpty(phone)) {
                edt_phone.setError(getString(R.string.field_req));
            } else
            {
                edt_phone.setError(null);
            }
            if (TextUtils.isEmpty(pass)) {
                edtpass.setError(getString(R.string.field_req));
            }
            else   if (pass.length()<6) {
                edtpass.setError(getString(R.string.password_short));
            }
            else
            {
                edtpass.setError(null);
            }
          /*  if (TextUtils.isEmpty(m_email)) {
                edt_email.setError(getString(R.string.field_req));
            } else if (!Patterns.EMAIL_ADDRESS.matcher(m_email).matches()) {
                edt_email.setError(getString(R.string.inv_email));
            } else {
                edt_email.setError(null);

            }*/

            if (date_of_birth == 0) {
                tv_birth_date.setError(getString(R.string.field_req));
            } else {
                tv_birth_date.setError(null);
            }

            if (TextUtils.isEmpty(m_national_id))
            {
                edt_national_num.setError(getString(R.string.field_req));

            }else
            {
                edt_national_num.setError(null);

            }

            if (TextUtils.isEmpty(m_address))
            {
                edt_address.setError(getString(R.string.field_req));

            }else
            {
                edt_address.setError(null);

            }

            if (imgUri1==null)
            {
                Toast.makeText(activity, R.string.choose_identity_card_image, Toast.LENGTH_SHORT).show();
            }


            if (imgUri2==null)
            {
                Toast.makeText(activity, R.string.choose_license_image, Toast.LENGTH_SHORT).show();
            }

            if (imgUri3==null)
            {
                Toast.makeText(activity, R.string.ch_img_front, Toast.LENGTH_SHORT).show();
            }

            if (imgUri4==null)
            {
                Toast.makeText(activity, R.string.ch_img_behind, Toast.LENGTH_SHORT).show();
            }


        }
    }


    private void CreateImageAlertDialog(final int img_req)
    {

        final AlertDialog dialog = new AlertDialog.Builder(activity)
                .setCancelable(true)
                .create();


        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_select_image,null);
        Button btn_camera = view.findViewById(R.id.btn_camera);
        Button btn_gallery = view.findViewById(R.id.btn_gallery);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);



        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                selectedType = 2;
                Check_CameraPermission(img_req);

            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                selectedType = 1;
                CheckReadPermission(img_req);



            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().getAttributes().windowAnimations= R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);
        dialog.show();
    }
    private void CheckReadPermission(int img_req)
    {
        if (ActivityCompat.checkSelfPermission(activity, read_permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{read_permission}, img_req);
        } else {
            SelectImage(1,img_req);
        }
    }

    private void Check_CameraPermission(int img_req)
    {
        if (ContextCompat.checkSelfPermission(activity,camera_permission)!= PackageManager.PERMISSION_GRANTED&& ContextCompat.checkSelfPermission(activity,write_permission)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity,new String[]{camera_permission,write_permission},img_req);
        }else
        {
            SelectImage(2,img_req);

        }

    }
    private void SelectImage(int type,int img_req) {

        Intent intent = new Intent();

        if (type == 1)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            }else
            {
                intent.setAction(Intent.ACTION_GET_CONTENT);

            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");
            startActivityForResult(intent,img_req);

        }else if (type ==2)
        {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,img_req);
            }catch (SecurityException e)
            {
                Toast.makeText(activity,R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(activity,R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

            }


        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == IMG_REQ1) {

            if (selectedType ==1)
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ1);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }else
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ1);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == IMG_REQ2) {
            if (selectedType ==1)
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ2);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }else
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ2);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }
        }

        else if (requestCode == IMG_REQ3) {

            if (selectedType ==1)
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ3);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }else
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ3);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }


        }
        else if (requestCode == IMG_REQ4) {
            if (selectedType ==1)
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ4);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }else
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ4);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if (requestCode == IMG_REQ5) {
            if (selectedType ==1)
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ5);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }else
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    SelectImage(selectedType,IMG_REQ5);
                } else {
                    Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQ1 && resultCode == Activity.RESULT_OK && data != null) {
            if (selectedType == 1)
            {
                imgUri1 = data.getData();
                image_id_icon.setVisibility(View.GONE);

                try {

                    File file = new File(Common.getImagePath(activity, imgUri1));
                    Picasso.with(activity).load(file).fit().into(image_id);
                }
                catch (Exception e){
                    image_id_icon.setVisibility(View.VISIBLE);

                    Toast.makeText(activity, "invalid image", Toast.LENGTH_SHORT).show();
                }

            }else if (selectedType ==2)
            {
                image_id_icon.setVisibility(View.GONE);

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                imgUri1 = getUriFromBitmap(bitmap);
                if (imgUri1 != null) {
                    String path = Common.getImagePath(activity, imgUri1);

                    if (path != null) {
                        Picasso.with(activity).load(new File(path)).fit().into(image_id);

                    } else {
                        Picasso.with(activity).load(imgUri1).fit().into(image_id);

                    }
                }
            }




        } else if (requestCode == IMG_REQ2 && resultCode == Activity.RESULT_OK && data != null) {

            if (selectedType == 1)
            {
                imgUri2 = data.getData();
                image_license_icon.setVisibility(View.GONE);

                try {
                    File file = new File(Common.getImagePath(activity, imgUri2));

                    Picasso.with(activity).load(file).fit().into(image_license);

                }catch (Exception e)
                {
                    image_license_icon.setVisibility(View.VISIBLE);

                    Toast.makeText(activity, "invalid image", Toast.LENGTH_SHORT).show();

                }

            }else if (selectedType ==2)
            {

                image_license_icon.setVisibility(View.GONE);

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                imgUri2 = getUriFromBitmap(bitmap);
                if (imgUri2 != null) {
                    String path = Common.getImagePath(activity, imgUri2);

                    if (path != null) {
                        Picasso.with(activity).load(new File(path)).fit().into(image_license);

                    } else {
                        Picasso.with(activity).load(imgUri2).fit().into(image_license);

                    }
                }
            }



        }
        else if (requestCode == IMG_REQ3 && resultCode == Activity.RESULT_OK && data != null) {

            if (selectedType == 1)
            {
                imgUri3 = data.getData();
                car_front_icon.setVisibility(View.GONE);


                try {
                    File file = new File(Common.getImagePath(activity, imgUri3));

                    Picasso.with(activity).load(file).fit().into(car_front_image);

                }catch (Exception e)
                {
                    car_front_icon.setVisibility(View.VISIBLE);

                    Toast.makeText(activity, "invalid image", Toast.LENGTH_SHORT).show();

                }

            }else if (selectedType ==2)
            {

                car_front_icon.setVisibility(View.GONE);

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                imgUri3 = getUriFromBitmap(bitmap);
                if (imgUri3 != null) {
                    String path = Common.getImagePath(activity, imgUri3);

                    if (path != null) {
                        Picasso.with(activity).load(new File(path)).fit().into(car_front_image);

                    } else {
                        Picasso.with(activity).load(imgUri3).fit().into(car_front_image);

                    }
                }



            }



        }
        else if (requestCode == IMG_REQ4 && resultCode == Activity.RESULT_OK && data != null) {

            if (selectedType == 1)
            {
                imgUri4 = data.getData();
                car_back_icon.setVisibility(View.GONE);


                try {
                    File file = new File(Common.getImagePath(activity, imgUri4));

                    Picasso.with(activity).load(file).fit().into(car_back_image);

                }catch (Exception e)
                {
                    car_back_icon.setVisibility(View.VISIBLE);

                    Toast.makeText(activity, "invalid image", Toast.LENGTH_SHORT).show();

                }
            }else if (selectedType ==2)
            {

                car_back_icon.setVisibility(View.GONE);

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                imgUri4 = getUriFromBitmap(bitmap);
                if (imgUri4 != null) {
                    String path = Common.getImagePath(activity, imgUri4);

                    if (path != null) {
                        Picasso.with(activity).load(new File(path)).fit().into(car_back_image);

                    } else {
                        Picasso.with(activity).load(imgUri4).fit().into(car_back_image);

                    }
                }
            }
        }else if (requestCode == IMG_REQ5 && resultCode == Activity.RESULT_OK && data != null) {

            if (selectedType == 1)
            {
               // imgUri2 = data.getData();

                uri = data.getData();
                image_icon1.setVisibility(View.GONE);
                try {
                    File file = new File(Common.getImagePath(activity, uri));
                    Picasso.with(activity).load(file).fit().into(image_personal);

                }catch (Exception e)
                {
                    image_icon1.setVisibility(View.VISIBLE);
                    Toast.makeText(activity, "invalid image", Toast.LENGTH_SHORT).show();
                }

            }else if (selectedType ==2)
            {

                image_icon1.setVisibility(View.GONE);

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                uri = getUriFromBitmap(bitmap);
                if (uri != null) {
                    String path = Common.getImagePath(activity, uri);

                    if (path != null) {
                        Picasso.with(activity).load(new File(path)).fit().into(image_personal);

                    } else {
                        Picasso.with(activity).load(uri).fit().into(image_personal);

                    }
                }
            }

        }
    }

    private Uri getUriFromBitmap(Bitmap bitmap) {
        String path = "";
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, "title", null);
            return Uri.parse(path);

        } catch (SecurityException e) {
            Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(activity, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();

        }
        return null;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        tv_birth_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, monthOfYear);
        date_of_birth = calendar.getTimeInMillis() / 1000;
    }
}
