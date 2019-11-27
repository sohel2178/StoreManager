package com.forbitbd.storeapp.ui.store.receivedAdd;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.forbitbd.androidutils.dialog.DatePickerListener;
import com.forbitbd.androidutils.dialog.MyDatePickerFragment;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;
import com.forbitbd.storeapp.utils.Constant;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ReceivedActivity extends PrebaseActivity
        implements ReceivedContract.View, View.OnClickListener {

    private ReceivedPresenter mPresenter;

    private Project project;
    private Receive receive;
    private List<Supplier> supplierList;

    private AppCompatSpinner spSupplier;

    private TextInputLayout tiDate,tiInvoiceNo,tiName,tiUnit,tiAmount;
    private EditText etDate,etInvoiceNo,etAmount;
    private MaterialButton btnBrowse,btnSave;
    private ImageView ivImage;

    private AppCompatAutoCompleteTextView etUnit,etName;


    private Date date;

    private byte[] bytes;

    private List<String> units,names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);
        mPresenter = new ReceivedPresenter(this);
        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        this.receive = (Receive) getIntent().getSerializableExtra(Constant.RECEIVED);
        this.supplierList = (List<Supplier>) getIntent().getSerializableExtra(Constant.SUPPLIER_LIST);
        this.units = getIntent().getStringArrayListExtra(Constant.UNITS);
        this.names = getIntent().getStringArrayListExtra(Constant.NAMES);


        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle("Material Received Form");

        spSupplier = findViewById(R.id.sp_supplier);

        ArrayAdapter<Supplier> supplierArrayAdapter =
                new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,supplierList);

        spSupplier.setAdapter(supplierArrayAdapter);

        tiDate = findViewById(R.id.ti_date);
        tiInvoiceNo = findViewById(R.id.ti_invoice_no);
        tiName = findViewById(R.id.ti_name);
        tiUnit = findViewById(R.id.ti_unit);
        tiAmount = findViewById(R.id.ti_amount);


        etDate = findViewById(R.id.date);
        etInvoiceNo = findViewById(R.id.invoice_no);

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

        if(receive!=null){
            mPresenter.bind();
        }

        etDate.setOnClickListener(this);
        btnBrowse.setOnClickListener(this);
        btnSave.setOnClickListener(this);


    }

    @Override
    public void bind() {
        spSupplier.setSelection(getPosition(receive.getReceived_from()));
        etDate.setText(MyUtil.getStringDate(receive.getDate()));
        etInvoiceNo.setText(receive.getInvoice_no());
        etName.setText(receive.getName());
        etUnit.setText(receive.getUnit());
        etAmount.setText(String.valueOf(receive.getQuantity()));

        Picasso.with(this).load(receive.getImage()).into(ivImage);

        btnSave.setText(getString(R.string.update));
    }

    private int getPosition(Supplier supplier){
        for (Supplier x: supplierList){
            if(x.get_id().equals(supplier.get_id())){
                return supplierList.indexOf(x);
            }
        }

        return 0;
    }

    @Override
    public void checkBeforeSave() {
        String invoice = etInvoiceNo.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();


        if(receive==null){
            receive = new Receive();
        }

        receive.setProject(project.get_id());
        receive.setDate(date);
        receive.setReceived_from((Supplier) spSupplier.getSelectedItem());
        receive.setInvoice_no(invoice);
        receive.setName(name);
        receive.setUnit(unit);

        try {
            receive.setQuantity(Double.parseDouble(amountStr));
        }catch (Exception e){
            e.printStackTrace();
        }

        boolean valid = mPresenter.validate(receive);

        if(!valid){
            return;
        }

        if(ivImage.getDrawable()==null){
            Toast.makeText(this, "Browse to Select/Take an Invoice Image", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isOnline()){
            Toast.makeText(this, "Turn on Your Internet Connection to Perform this Operations", Toast.LENGTH_SHORT).show();
        }else{
            if(receive.get_id()==null){
                mPresenter.saveReceive(receive,bytes);
            }else{
                mPresenter.updateReceive(receive,bytes);
            }
        }
    }

    @Override
    public void openCalendar() {
        MyDatePickerFragment myDateDialog = new MyDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE,getString(R.string.select_material_received_date));
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
        tiInvoiceNo.setErrorEnabled(false);
        tiName.setErrorEnabled(false);
        tiUnit.setErrorEnabled(false);
        tiAmount.setErrorEnabled(false);

    }

    @Override
    public void showError(String message, int fieldId) {
        switch (fieldId){
            case 1:
                tiInvoiceNo.setError(message);
                etInvoiceNo.requestFocus();
                break;

            case 2:
                tiName.setError(message);
                etName.requestFocus();
                break;

            case 3:
                tiUnit.setError(message);
                etUnit.requestFocus();
                break;

            case 4:
                tiAmount.setError(message);
                etAmount.requestFocus();
                break;
        }
    }

    @Override
    public void complete(Receive receive) {
        Log.d("KKKKKKK","Bal Sal Call");
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.RECEIVED,receive);
        intent.putExtras(bundle);

        setResult(RESULT_OK,intent);
        finish();
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
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());

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
