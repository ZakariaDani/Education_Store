package ma.ensa.educationstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initUIActions();
    }

    private void initUIActions() {
        Button button_signup = (Button) findViewById(R.id.button_signup);
        button_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        System.out.println(view.getId());
        if(view.getId() == R.id.button_signup){
            Intent myIntent = new Intent(LoginActivity.this, SignUpActivity.class);
            LoginActivity.this.startActivity(myIntent);
        }
    }
}
