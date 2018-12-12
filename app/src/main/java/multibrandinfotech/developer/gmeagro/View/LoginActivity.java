package multibrandinfotech.developer.gmeagro.View;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import multibrandinfotech.developer.gmeagro.Model.DatabaseHelper;
import multibrandinfotech.developer.gmeagro.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUserName, editTextPassword;
    private Button buttonLogin;
    private String userName, Password, userNameData, passwrodData;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName = (EditText) findViewById(R.id.editText_UserName);
        editTextPassword = (EditText) findViewById(R.id.editText_Password);

        buttonLogin = (Button) findViewById(R.id.button_Login);

        databaseHelper = new DatabaseHelper(this);
        final SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = databaseHelper.fetchLoginData();

                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplication(), "Database is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                while (cursor.moveToNext()) {
                    userNameData = cursor.getString(0);
                    passwrodData = cursor.getString(1);
                }

                userName = editTextUserName.getText().toString();
                Password = editTextPassword.getText().toString();

                if (userName.equals(userNameData) && Password.equals(passwrodData)) {
                    Intent i = new Intent(LoginActivity.this, IndentForm.class);
                    startActivity(i);
                } else if (userName.equals("") || Password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Username or Password can not be blank", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
