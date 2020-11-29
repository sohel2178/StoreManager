package com.forbitbd.storeapp.ui.store.consumedAdd;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.forbitbd.androidutils.dialog.DatePickerListener;
import com.forbitbd.androidutils.dialog.MyDatePickerFragment;
import com.forbitbd.androidutils.models.Consume;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.Task;
import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.utils.Constant;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ConsumedActivity extends PrebaseActivity implements ConsumedContract.View, View.OnClickListener {

    private ConsumedPresenter mPresenter;

    private Project project;
    private Consume consume;

    private TextInputLayout tiDate,tiIssueTo,tiName,tiUnit,tiAmount,tiWhereUsed;
    private EditText etDate,etIssueTo,etAmount;
    private MaterialButton btnBrowse,btnSave;
    private ImageView ivImage;

    private AutoCompleteTextView etUnit,etName,etWhereUsed;


    private Date date;

    private byte[] bytes;

    private List<String> units,names;
    private ArrayAdapter<Task> taskAdapter;
    private int selectedPosition=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumed);
        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        this.consume = (Consume) getIntent().getSerializableExtra(Constant.CONSUME);

        this.units = getIntent().getStringArrayListExtra(Constant.UNITS);
        this.names = getIntent().getStringArrayListExtra(Constant.NAMES);

        mPresenter = new ConsumedPresenter(this);

        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle("Material Consumption Form");

        setupBannerAd(R.id.adView);

        tiDate = findViewById(R.id.ti_date);
        tiIssueTo = findViewById(R.id.ti_issue_to);
        tiName = findViewById(R.id.ti_name);
        tiUnit = findViewById(R.id.ti_unit);
        tiAmount = findViewById(R.id.ti_amount);
        tiWhereUsed = findViewById(R.id.ti_where_used);


        etDate = findViewById(R.id.date);
        etIssueTo = findViewById(R.id.issue_to);
        taskAdapter = new ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1);

        etWhereUsed = findViewById(R.id.where_used);
        etWhereUsed.setAdapter(taskAdapter);
        etWhereUsed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPosition=i;
            }
        });

        //etWhereUsed.setThreshold(1);

        etName = findViewById(R.id.name);
        etName.setThreshold(1);
        etName.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names));


        etUnit = findViewById(R.id.unit);
        etUnit.setThreshold(1);
        etUnit.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,units));

        etAmount = findViewById(R.id.amount);

        date = new Date();

        etDate.setText(MyUtil.getStringDate(date));

        ivImage = findViewById(R.id.image);

        btnBrowse = findViewById(R.id.browse);
        btnSave = findViewById(R.id.save);

        if(consume!=null){
            mPresenter.bind();
        }

        etDate.setOnClickListener(this);
        btnBrowse.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        mPresenter.getAllTask(project.get_id());




    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppPreference.getInstance(this).getCounter()> com.forbitbd.androidutils.utils.Constant.COUNTER){
            showInterAd();
        }
    }

    @Override
    public void bind() {
        etDate.setText(MyUtil.getStringDate(consume.getDate()));
        etIssueTo.setText(consume.getIssue_to());
        etWhereUsed.setText(consume.getWhere_used().getName());
        etName.setText(consume.getName());
        etUnit.setText(consume.getUnit());
        etAmount.setText(String.valueOf(consume.getQuantity()));

        Picasso.with(this).load(consume.getImage()).into(ivImage);

        btnSave.setText(getString(R.string.update));

    }

    @Override
    public void checkBeforeSave() {
        String issueTo = etIssueTo.getText().toString().trim();
        //String whereUsed = etWhereUsed.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();

        if(consume==null){
            consume = new Consume();
        }

        Task whereUsed = null;

        if(selectedPosition!=-1){
            whereUsed = taskAdapter.getItem(selectedPosition);
            consume.setWhere_used(whereUsed);
        }





        consume.setProject(project.get_id());
        consume.setDate(date);
        consume.setIssue_to(issueTo);

        consume.setName(name);
        consume.setUnit(unit);

        try {
            consume.setQuantity(Double.parseDouble(amountStr));
        }catch (Exception e){
            consume.setQuantity(0);
            e.printStackTrace();
        }

        boolean valid = mPresenter.validate(consume);

        if(!valid){
            return;
        }

       /* if(ivImage.getDrawable()==null){
            Toast.makeText(this, "Browse to Select/Take an Invoice Image", Toast.LENGTH_SHORT).show();
            return;
        }*/

        if(!isOnline()){
            Toast.makeText(this, "Turn on Your Internet Connection to Perform this Operations", Toast.LENGTH_SHORT).show();
        }else{
            if(consume.get_id()==null){
                mPresenter.saveConsume(consume,bytes);
            }else{
                mPresenter.updateConsume(consume,bytes);
            }
        }

    }

    @Override
    public void openCalendar() {
        MyDatePickerFragment myDateDialog = new MyDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE,getString(R.string.select_material_consumption_date));
        myDateDialog.setArguments(bundle);
        myDateDialog.setCancelable(false);
        myDateDialog.setDatePickerListener(new DatePickerListener() {
            @Override
            public void onDatePick(long time) {
                date = new Date(time);
                etDate.setText(MyUtil.getStringDate(new Date(time)));
            }
        });
        myDateDialog.show(getSupportFragmentManager(),"FFFF");

    }

    @Override
    public void openCamera() {
        openCropImageActivity(16,25);
    }

    @Override
    public void clearPreError() {
        tiDate.setErrorEnabled(false);
        tiIssueTo.setErrorEnabled(false);
        tiName.setErrorEnabled(false);
        tiUnit.setErrorEnabled(false);
        tiAmount.setErrorEnabled(false);
        tiWhereUsed.setErrorEnabled(false);
    }

    @Override
    public void showError(String message, int fieldId) {
        switch (fieldId){

            case 1:
                tiName.setError(message);
                etName.requestFocus();
                break;

            case 2:
                tiUnit.setError(message);
                etUnit.requestFocus();
                break;

            case 3:
                tiIssueTo.setError(message);
                etIssueTo.requestFocus();
                break;

            case 4:
                tiWhereUsed.setError(message);
                etWhereUsed.requestFocus();
                break;

            case 5:
                tiAmount.setError(message);
                etAmount.requestFocus();
                break;
        }

    }

    @Override
    public void complete(Consume consume) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.CONSUME,consume);
        intent.putExtras(bundle);

        setResult(RESULT_OK,intent);
        finish();

    }

    @Override
    public void updateTaskAdapter(List<Task> taskList) {
        taskAdapter.addAll(taskList);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if(view==etDate){
            mPresenter.openCalendar();
        }else if(view==btnBrowse){
            mPresenter.openCamera();
        }else if(view==btnSave){
            mPresenter.checkBeforeSave();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {

                    Bitmap bitmap= null;

                    if(Build.VERSION.SDK_INT<28){
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                    }else{
                        ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), result.getUri());
                        bitmap = ImageDecoder.decodeBitmap(source);
                    }

                    if(bitmap.getWidth()<576){
                        showToast("Should Select Larger Image !");
                        return;
                    }

//                    Bitmap scaledBitMap = MyUtil.getScaledBitmap(bitmap,200,200);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos);
                    bytes = bos.toByteArray();
                    ivImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
