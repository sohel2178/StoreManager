package com.forbitbd.storeapp.ui.store.addSupplier;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Supplier;
import com.forbitbd.storeapp.utils.Constant;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddSupplierActivity extends PrebaseActivity implements AddSupplierContract.View, View.OnClickListener {


    private TextInputLayout tiName,tiContact,tiEmail,tiMaterialSupply;
    private EditText etName,etContact,etEmail,etMaterialSupply;
    private Button btnBrowse,btnSave;
    private ImageView ivImage;


    private Project project;
    private Supplier supplier;

    private byte[] bytes;

    private AddSupplierPresenter mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier);
        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        this.supplier = (Supplier) getIntent().getSerializableExtra(Constant.SUPPLIER);

        mPresenter = new AddSupplierPresenter(this);

        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);

        if(supplier !=null){
            getSupportActionBar().setTitle("Update Supplier");
        }else{
            getSupportActionBar().setTitle("Create New Supplier");
        }

        setupBannerAd(R.id.adView);

        tiName = findViewById(R.id.ti_name);
        tiContact = findViewById(R.id.ti_contact);
        tiEmail = findViewById(R.id.ti_email);
        tiMaterialSupply = findViewById(R.id.ti_material_supply);

        etName = findViewById(R.id.name);
        etContact = findViewById(R.id.contact);
        etEmail = findViewById(R.id.email);
        etMaterialSupply = findViewById(R.id.material_supply);

        ivImage = findViewById(R.id.image);

        btnBrowse = findViewById(R.id.browse);
        btnSave = findViewById(R.id.save);

        btnBrowse.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        if(supplier!=null){
            mPresenter.bind();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppPreference.getInstance(this).getCounter()> com.forbitbd.androidutils.utils.Constant.COUNTER){
            showInterAd();
        }
    }

    @Override
    public void onClick(View view) {

        if(view==btnBrowse){
            mPresenter.openCamera();
        }else if(view==btnSave){
            String name = etName.getText().toString().trim();
            String contact = etContact.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String materialSupply = etMaterialSupply.getText().toString().trim();

            if(supplier==null){
                supplier = new Supplier();
            }

            supplier.setName(name);
            supplier.setProject(project.get_id());
            supplier.setContact(contact);
            supplier.setEmail(email);
            supplier.setMaterial_supply(materialSupply);

            boolean valid = mPresenter.validate(supplier);

            if(!valid){
                return;
            }

            if(ivImage.getDrawable()==null){
                Toast.makeText(this, "Browse to Select/Take an Supplier Image", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!isOnline()){
                Toast.makeText(this, "Turn on Your Internet Connection to Perform this Operations", Toast.LENGTH_SHORT).show();
            }else{
                if(supplier.get_id()==null){
                    mPresenter.saveSupplier(supplier,bytes);
                }else{
                    mPresenter.updateSupplier(supplier,bytes);
                }
            }


        }


    }

    @Override
    public void openCamera() {
        openCropImageActivity(true);
    }

    @Override
    public void clearPreError() {
        tiName.setErrorEnabled(false);
        tiContact.setErrorEnabled(false);
        tiEmail.setErrorEnabled(false);
        tiMaterialSupply.setErrorEnabled(false);
    }

    @Override
    public void showValidationError(String message, int fieldId) {
        switch (fieldId){
            case 1:
                tiName.setError(message);
                etName.requestFocus();
                break;

            case 2:
                tiContact.setError(message);
                etContact.requestFocus();
                break;

            case 3:
                tiEmail.setError(message);
                etEmail.requestFocus();
                break;

            case 4:
                tiMaterialSupply.setError(message);
                etMaterialSupply.requestFocus();
                break;
        }
    }

    @Override
    public void addSupplierComplete(Supplier supplier) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.SUPPLIER,supplier);
        intent.putExtras(bundle);

        setResult(RESULT_OK,intent);

        finish();

    }

    @Override
    public void updateSupplierComplete(Supplier supplier) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.SUPPLIER,supplier);
        intent.putExtras(bundle);

        setResult(RESULT_OK,intent);

        finish();
    }

    @Override
    public void bind() {
        etName.setText(supplier.getName());
        etContact.setText(supplier.getContact());
        etEmail.setText(supplier.getEmail());
        etMaterialSupply.setText(supplier.getMaterial_supply());

        Picasso.with(getApplicationContext())
                .load(supplier.getImage())
                .into(ivImage);

        btnSave.setText(getString(R.string.update));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                    Bitmap scaledBitMap = MyUtil.getScaledBitmap(bitmap,200,200);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    scaledBitMap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos);
                    bytes = bos.toByteArray();
                    ivImage.setImageBitmap(scaledBitMap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
