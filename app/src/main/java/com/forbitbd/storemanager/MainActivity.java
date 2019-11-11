package com.forbitbd.storemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.forbitbd.storeapp.models.Project;
import com.forbitbd.storeapp.ui.store.StoreActivity;
import com.forbitbd.storeapp.utils.Constant;

public class MainActivity extends AppCompatActivity {

    private Project project;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        project = new Project();
        project.set_id("5dbe73b388262501774c4efa");
        project.setName("My Project");

        Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.PROJECT,project);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}
