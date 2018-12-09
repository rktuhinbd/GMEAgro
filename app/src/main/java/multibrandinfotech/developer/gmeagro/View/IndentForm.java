package multibrandinfotech.developer.gmeagro.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import multibrandinfotech.developer.gmeagro.R;
import multibrandinfotech.developer.gmeagro.ViewModel.DatePickerFragment;

public class IndentForm extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private RadioGroup radioGroup;
    private EditText editTextDatePicker, editTextDistributor, editTextPartyCode;
    private Button buttonProceed;
    private RadioButton radioButton;
    private String currentDate, deliveryDate, distributor, partycode, date;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent_form);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        editTextDatePicker = (EditText) findViewById(R.id.editText_DatePicker);
        editTextDistributor = (EditText) findViewById(R.id.editText_Distributor);
        editTextPartyCode = (EditText) findViewById(R.id.editText_Party_Code);
        buttonProceed = (Button) findViewById(R.id.button_Proceed);

        editTextDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distributor = editTextDistributor.getText().toString();
                partycode = editTextPartyCode.getText().toString();
                date = editTextDatePicker.getText().toString();

                if (distributor.equals("") || partycode.equals("") || date.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fullup all details", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(IndentForm.this, AddItem.class);
                    startActivity(i);
                }
            }
        });
    }

    public void radioCheck(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        Toast.makeText(getApplicationContext(), "" + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        Calendar calendar = Calendar.getInstance();
//
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month);
//        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        String date = year + "/" + (month + 1) + "/" + dayOfMonth;
        editTextDatePicker.setText(date);
    }
}
