package com.forbitbd.storemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.storeapp.ui.store.StoreActivity;
import com.forbitbd.storeapp.utils.Constant;

public class MainActivity extends AppCompatActivity {

    private Project project;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        project = new Project();
        project.set_id("5ee22225f127667cc70e13ee");
        project.setName("My Project");

        final SharedProject sharedProject = new SharedProject(project);
        sharedProject.getStore().setWrite(false);
        sharedProject.getStore().setUpdate(false);
        sharedProject.getStore().setDelete(false);

        Button btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.PROJECT,sharedProject);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
    }
}
