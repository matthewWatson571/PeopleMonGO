package com.example.matthewwatson.peoplemongo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.davidstemmer.flow.plugin.screenplay.ScreenplayDispatcher;
import com.example.matthewwatson.peoplemongo.Model.ImageLoadedEvent;
import com.example.matthewwatson.peoplemongo.Network.UserStore;
import com.example.matthewwatson.peoplemongo.Stages.EditProfileStage;
import com.example.matthewwatson.peoplemongo.Stages.LoginStage;
import com.example.matthewwatson.peoplemongo.Stages.MapStage;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;

public class MainActivity extends AppCompatActivity {
    private Flow flow;
    private ScreenplayDispatcher dispatcher;
    public Bundle savedInstanceState;

    private static int RESULT_LOAD_IMAGE = 1;

    @Bind(R.id.container)
    RelativeLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.savedInstanceState = savedInstanceState;

        ButterKnife.bind(this);

        flow = PeoplemonApplication.getMainFlow();
        dispatcher = new ScreenplayDispatcher(this, container); //controls UI, container is the views
        dispatcher.setUp(flow);//sets up based on what's in flow

//        UserStore.getInstance().setToken(null);

        if (UserStore.getInstance().getToken() == null || UserStore.getInstance().getTokenExpiration() == null) {
            History newHistory = History.single(new LoginStage());
            flow.setHistory(newHistory, Flow.Direction.REPLACE); //no visual transition
        }

        if (Build.VERSION.SDK_INT >= 23) {//good stuff here for permissions.
            if (!(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            if (!(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }
    }

    @Override
    public void onBackPressed() { //overrides back button
        if (!flow.goBack()) {
            flow.removeDispatcher(dispatcher);
            flow.setHistory(History.single(new MapStage()), Flow.Direction.BACKWARD);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_profile:
                Flow flow = PeoplemonApplication.getMainFlow();
                History newHistory = flow.getHistory().buildUpon()
                        .push(new EditProfileStage())
                        .build();
                flow.setHistory(newHistory, Flow.Direction.FORWARD);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imageString = cursor.getString(columnIndex);
                cursor.close();
                EventBus.getDefault().post(new ImageLoadedEvent(imageString));//DrawingCanvas.java

            } else {
                Toast.makeText(this, R.string.error_retrieving_image, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.error_retrieving_image, Toast.LENGTH_SHORT).show();
        }
    }



}
