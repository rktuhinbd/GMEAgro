package multibrandinfotech.developer.gmeagro.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
    private static final String[] distributorArray = new String[]{"A K Khan & Company", "Aarong", "Adamjee Jute Mills", "Advanced Chemical Industries", "Agamee Prakashani", "Akij", "A J Group", "Bangladesh Machine Tools Factory", "Bangladesh Petroleum Corporation", "Bashundhara Group", "Beximco Pharma", "Square Pharma"};
    private static final String[] partyCodeArray = new String[]{"Conglomerates", "Consumer goods", "Consumer services", "Basic materials", "Industrials", "Oil & gas", "Health care", "Medicine", "Garments"};
    private static DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent_form);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        editTextDatePicker = (EditText) findViewById(R.id.editText_DatePicker);
        editTextDistributor = (AutoCompleteTextView) findViewById(R.id.editText_Distributor);
        editTextPartyCode = (AutoCompleteTextView) findViewById(R.id.editText_Party_Code);
        buttonProceed = (Button) findViewById(R.id.button_Proceed);

        editTextDistributor.addTextChangedListener(watcher);
        editTextPartyCode.addTextChangedListener(watcher);

        databaseHelper = new DatabaseHelper(this);
        final SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        editTextDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Cursor cursor = databaseHelper.fetchIndentData();
        final String[] distributorValues = new String[cursor.getCount()];
        final String[] partyCodeValues = new String[cursor.getCount()];

        //Fetch Database Values
//        int i = 0;
//        if (cursor.getCount() == 0) {
//            Toast.makeText(getApplication(), "Database is empty", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        while (cursor.moveToNext()) {
//            distributor = cursor.getString(3);
//            partycode = cursor.getString(4);
//
//            distributorValues[i] = distributor;
//            partyCodeValues[i] = partycode;
//            i++;
//        }
        // <<<====O====>>> //

        //Setting Autocomplete TextField Array
        final ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, distributorArray);
        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, partyCodeArray);

        editTextDistributor.setAdapter(countryAdapter);
        editTextPartyCode.setAdapter(cityAdapter);
        // <<<====O====>>> //

        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "Please fill-up all details", Toast.LENGTH_LONG).show();
                }
                else{
                    paymentType = (String) radioButton.getText();
                    deliveryDate = editTextDatePicker.getText().toString();
                    distributor = editTextDistributor.getText().toString();
                    partycode = editTextPartyCode.getText().toString();

                    if (paymentType.equals(null) || deliveryDate.equals("") || distributor.equals("") || partycode.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please fill-up all details", Toast.LENGTH_LONG).show();
                    } else {

                        databaseHelper.insertIndentData(paymentType, orderDate, deliveryDate, distributor, partycode);
                        Intent i = new Intent(IndentForm.this, AddItem.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
            }
        });
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().equals("A K Khan & Company")) {
                editTextPartyCode.setText("Conglomerates");
            } else if (s.toString().equals("Aarong")) {
                editTextPartyCode.setText("Consumer goods");
            } else if (s.toString().equals("Advanced Chemical Industries")) {
                editTextPartyCode.setText("Basic materials");
            } else if (s.toString().equals("Akij")) {
                editTextPartyCode.setText("Consumer goods");
            } else if (s.toString().equals("Bangladesh Petroleum Corporation")) {
                editTextPartyCode.setText("Oil & gas");
            } else if (s.toString().equals("Bangladesh Machine Tools Factory")) {
                editTextPartyCode.setText("Industrials");
            } else if (s.toString().equals("Bashundhara Group")) {
                editTextPartyCode.setText("Consumer goods");
            } else if (s.toString().equals("Adamjee Jute Mills")) {
                editTextPartyCode.setText("Basic materials");
            } else if (s.toString().equals("Agamee Prakashani")) {
                editTextPartyCode.setText("Consumer services");
            } else if (s.toString().equals("A J Group")) {
                editTextPartyCode.setText("Garments");
            } else if (s.toString().equals("Beximco")) {
                editTextPartyCode.setText("Health care");
            } else if (s.toString().equals("Square")) {
                editTextPartyCode.setText("Health care");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}