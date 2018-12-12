package multibrandinfotech.developer.gmeagro.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import multibrandinfotech.developer.gmeagro.Model.DatabaseHelper;
import multibrandinfotech.developer.gmeagro.R;
import multibrandinfotech.developer.gmeagro.ViewModel.DatePickerFragment;

public class IndentForm extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private RadioGroup radioGroup;
    private EditText editTextDatePicker;
    private AutoCompleteTextView editTextDistributor, editTextPartyCode;
    private Button buttonProceed;
    private RadioButton radioButton;
    private String paymentType, orderDate, deliveryDate, distributor, partycode;
    private static final String[] distributorArray = new String[]{"Dhaka", "Chittagong", "Khulna", "Sylhet", "Barishal", "Comilla", "Noakhali", "Jessore", "Rajshahi", "Mymensingh", "Rangpur"};
    private static final String[] partyCodeArray = new String[]{"Gulshan", "Banani", "Baridhara", "Mohakhali", "Badda", "Mirpur", "Mohammadpur", "Rampura", "Khilgaon", "Tejgaon", "Farmgate"};
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent_form);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        editTextDatePicker = (EditText) findViewById(R.id.editText_DatePicker);
        editTextDistributor = (AutoCompleteTextView) findViewById(R.id.editText_Distributor);
        editTextPartyCode = (AutoCompleteTextView) findViewById(R.id.editText_Party_Code);
        buttonProceed = (Button) findViewById(R.id.button_Proceed);

        final ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, distributorArray);
        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, partyCodeArray);

        editTextDistributor.setAdapter(countryAdapter);
        editTextPartyCode.setAdapter(cityAdapter);

        databaseHelper = new DatabaseHelper(this);
        final SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        editTextDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

//        editTextDistributor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < distributorArray.length; i++) {
//                    if(editTextDistributor.getText().equals("Bangladesh")){
//                        editTextPartyCode.setText(partyCodeArray[i]);
//                        Toast.makeText(getApplicationContext(), "Working", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//        });
//
//        editTextPartyCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < partyCodeArray.length; i++) {
//                    if(editTextPartyCode.getText().equals("Dhaka")){
//                        editTextDistributor.setText("Bangladesh");
//                    }
//                }
//            }
//
//        });

        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentType = String.valueOf(radioButton.getText());
                deliveryDate = editTextDatePicker.getText().toString();
                distributor = editTextDistributor.getText().toString();
                partycode = editTextPartyCode.getText().toString();

                if (distributor.equals("") || partycode.equals("") || orderDate.equals("") || deliveryDate.equals("") || distributor.equals("") || partycode.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill-up all details", Toast.LENGTH_LONG).show();
                } else {

                    databaseHelper.insertIndentData(paymentType, orderDate, deliveryDate, distributor, partycode);
                    Intent i = new Intent(IndentForm.this, AddItem.class);
                    startActivity(i);
                }
            }
        });
    }

    public void radioCheck(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        orderDate = new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
        deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        String dDate = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());

        editTextDatePicker.setText(dDate);
    }
}
