package ca.sfu.Navy.walkinggroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapActivityStart();
        logInActivityStart();
    }

    private void logInActivityStart() {
        Button button = (Button) findViewById(R.id.login_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void MapActivityStart() {
        Button button = (Button) findViewById(R.id.MapButtonID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MapsActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

}
