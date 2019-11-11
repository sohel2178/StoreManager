package com.forbitbd.storeapp.utils;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.forbitbd.storeapp.BuildConfig;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.ui.zoomImage.ZoomImageActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by sohel on 29-01-18.
 */

public class PrebaseActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private FragmentManager manager;


    public ProgressDialog mProgressDialog;

    private TextView tvTitle;
    private Toolbar toolbar;


    //private UserLocalStore userLocalStore;








    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    public void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id==android.R.id.home){
            onBackPressed();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this,R.style.MyTheme);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large_Inverse);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }





    public void openCropImageActivity(boolean square){

        if(square){
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }else{
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(4,3)
                    .start(this);
        }


    }

    public void openCropImageActivity(int width,int height){

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(width,height)
                .start(this);



    }

    public void startZoomImageActivity(String url){
        Intent intent = new Intent(this, ZoomImageActivity.class);
        intent.putExtra(Constant.PATH,url);
        startActivity(intent);
    }

    /*public void openImagePicker(){

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.OFF)
                .start(this);

    }*/



    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }



    public int getScreenWidth(){

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        //int height = metrics.heightPixels;
        return width;
    }

    public int getScreenHeight(){

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int height = metrics.heightPixels;
        //int height = metrics.heightPixels;

        return height;
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }


    public void hideKey(View view){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }


    public void openFile(String path) {
        File file = new File(path);

        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Uri uri = FileProvider.getUriForFile(this,
                BuildConfig.APPLICATION_ID+".fileprovider",file);

        target.setDataAndType(uri,"application/vnd.ms-excel");

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            PackageManager pm = getPackageManager();
            if (intent.resolveActivity(pm) != null) {
                startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
            showToast("Please Download a Excel Reader");
        }

    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public String saveFile(String directory, String filename, ResponseBody responseBody){

        File dir = new File(directory);

        if(!dir.exists()){
            dir.mkdirs();
        }

        File myFile = new File(directory, filename);

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            byte[] fileReader = new byte[4096];

            long fileSize = responseBody.contentLength();


            inputStream = responseBody.byteStream();
            outputStream = new FileOutputStream(myFile);

            while (true) {
                int read = inputStream.read(fileReader);

                if (read == -1) {
                    break;
                }

                outputStream.write(fileReader, 0, read);

            }

            outputStream.flush();

            return myFile.getPath();
        } catch (IOException e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public String saveTaskFile(String projectName, String directory, ResponseBody responseBody){
        String file = Environment.getExternalStorageDirectory().getPath()
                + File.separator+getString(R.string.app_name)
                + File.separator+projectName
                + File.separator+directory;

        String fileName = "Tasks.xlsx";

        return saveFile(file,fileName,responseBody);
    }


    public String saveMonthlyAttendanceFile(String projectName, String directoryName, ResponseBody responseBody, int year, int month) {
        String file = Environment.getExternalStorageDirectory().getPath()
                + File.separator+getString(R.string.app_name)
                + File.separator+projectName
                + File.separator+directoryName;
        File dir = new File(file);

        if(!dir.exists()){
            dir.mkdirs();
        }
        String monthStr = getResources().getStringArray(R.array.month_array)[month];
        String fileName = monthStr+" - "+year+ ".xlsx";

        File myFile = new File(file, fileName);


        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            byte[] fileReader = new byte[4096];

            long fileSize = responseBody.contentLength();


            inputStream = responseBody.byteStream();
            outputStream = new FileOutputStream(myFile);

            while (true) {
                int read = inputStream.read(fileReader);

                if (read == -1) {
                    break;
                }

                outputStream.write(fileReader, 0, read);

            }

            outputStream.flush();

            return myFile.getPath();
        } catch (IOException e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }









}
