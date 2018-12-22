package multibrandinfotech.developer.gmeagro.View;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import multibrandinfotech.developer.gmeagro.Model.DatabaseHelper;
import multibrandinfotech.developer.gmeagro.R;

public class Login extends AppCompatActivity {

    private long backPressTime;
    private Toast backToast;
    private TextInputLayout editTextUserName, editTextPassword;
    private Button buttonLogin;
    private String userName, Password, userNameData, passwrodData;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName = findViewById(R.id.editText_UserName);
        editTextPassword = findViewById(R.id.editText_Password);

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

                validateUserName();
                validatePassword();

                if (validateUserName() == true && validatePassword() == true) {
                    Intent i = new Intent(Login.this, Home.class);
                    startActivity(i);
                }
            }
        });
    }

    private boolean validateUserName() {
        userName = editTextUserName.getEditText().getText().toString();
        if (userName.isEmpty()) {
            editTextUserName.setError("User Name must be entered");
            return false;
        } else if (!userName.equals(userNameData)) {
            editTextUserName.setError("Wrong User Name");
            return false;
        } else {
            editTextUserName.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        Password = editTextPassword.getEditText().getText().toString();
        if (Password.isEmpty()) {
            editTextPassword.setError("Password must be entered");
            return false;
        } else if (!Password.equals(passwrodData)) {
            editTextPassword.setError("Wrong Password");
            return false;
        } else {
            editTextUserName.setError(null);
            return true;
        }
    }

    @Override
    public void onBackPressed() {

        if (backPressTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } else {
            backToast = Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressTime = System.currentTimeMillis();
    }

    public Drawable resizeImage(int imageResource) {// R.drawable.large_image
        // Get device dimensions
        Display display = getWindowManager().getDefaultDisplay();
        double deviceWidth = display.getWidth();

        BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(
                imageResource);
        double imageHeight = bd.getBitmap().getHeight();
        double imageWidth = bd.getBitmap().getWidth();

        double ratio = deviceWidth / imageWidth;
        int newImageHeight = (int) (imageHeight * ratio);

        Bitmap bMap = BitmapFactory.decodeResource(getResources(), imageResource);
        Drawable drawable = new BitmapDrawable(this.getResources(),
                getResizedBitmap(bMap, newImageHeight, (int) deviceWidth));

        return drawable;
    }

    /************************ Resize Bitmap *********************************/
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;
    }
}