package multibrandinfotech.developer.gmeagro.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import multibrandinfotech.developer.gmeagro.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUserName, editTextPassword;
    private Button buttonLogin;
    private String userName, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName = (EditText) findViewById(R.id.editText_UserName);
        editTextPassword = (EditText) findViewById(R.id.editText_Password);

        buttonLogin = (Button) findViewById(R.id.button_Login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = editTextUserName.getText().toString();
                Password = editTextPassword.getText().toString();

                if (userName.equals("superadmin")  && Password.equals("superadmin")) {
                    Intent i = new Intent(LoginActivity.this, IndentForm.class);
                    startActivity(i);

                }
                else if(userName.equals("") || Password.equals("")){
                    Toast.makeText(getApplicationContext(),"Username or Password can not be blank", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
