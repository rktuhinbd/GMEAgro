package multibrandinfotech.developer.gmeagro.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import multibrandinfotech.developer.gmeagro.Model.DatabaseHelper;
import multibrandinfotech.developer.gmeagro.Model.ItemList;
import multibrandinfotech.developer.gmeagro.R;
import multibrandinfotech.developer.gmeagro.ViewModel.ItemAdapter;
import multibrandinfotech.developer.gmeagro.ViewModel.PlaceOrderDialog;

public class AddItem extends AppCompatActivity {

    private EditText editTextQuantity;
    private AutoCompleteTextView editTextItemName, editTextItemCode;
    private TextView textVIewTotalAmount;
    private Button buttonAddItem, buttonPlaceOrder;

    private String itemName, itemCode, quantityText, disc;
    private int quantity = 0, price = 1000, percent = 0, grandTotal, Position;
    private double discount = 1, amount, totalAmount = 0, subtractTotal;

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private static DatabaseHelper databaseHelper;

    ArrayList<ItemList> items = new ArrayList<>();
    ArrayAdapter<String> itemNameAdapter, itemCodeAdapter;

    private String[] UnitPrice;
    private String[] Products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editTextItemName = (AutoCompleteTextView) findViewById(R.id.editText_ItemName);
        editTextItemCode = (AutoCompleteTextView) findViewById(R.id.editText_ItemCode);
        editTextQuantity = (EditText) findViewById(R.id.editText_Quantity);
        textVIewTotalAmount = (TextView) findViewById(R.id.textView_TotalAmount);

        buttonAddItem = (Button) findViewById(R.id.button_AddItem);
        buttonPlaceOrder = (Button) findViewById(R.id.button_PlaceOrder);

        databaseHelper = new DatabaseHelper(this);
        final SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        final String Flag = getIntent().getStringExtra("flag");

        Products = getResources().getStringArray(R.array.productName);
        itemNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Products);
        itemCodeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Products);

        editTextItemName.setAdapter(itemNameAdapter);
        editTextItemCode.setAdapter(itemCodeAdapter);

        editTextItemName.addTextChangedListener(watcher);
        //editTextItemCode.addTextChangedListener(watcher);

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityText = editTextQuantity.getText().toString();
                itemName = editTextItemName.getText().toString();
                itemCode = editTextItemCode.getText().toString();

                if (Flag.equals("1")) {
                    UnitPrice = getResources().getStringArray(R.array.unitPriceCash);
                } else {
                    UnitPrice = getResources().getStringArray(R.array.unitPriceDue);
                }

                addItem();
            }
        });

        buttonPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlaceOrderDialog();
            }
        });
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            for (int i = 0; i < Products.length; i++) {
                if (s.toString().equals(Products[i])) {
                    //Set Text in itemCode - AutoComplete TextView
                    editTextItemCode.setText(s);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void getRecyclerViewItemPosition(int position) {
        ItemList itemPosition = items.get(position);
        adapter.notifyItemChanged(position);
        Position = position;
    }

    public void populateRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new LinearLayoutManager(this);
        adapter = null;
        adapter = new ItemAdapter(items);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getRecyclerViewItemPosition(position);
                openRecyclerViewItemDeleteDialog();
            }
        });
    }

    public void addItem() {
        if (itemName.equals("") || itemCode.equals("") || quantityText.equals("")) {
            Toast.makeText(getApplicationContext(), "Do not leave any input blank", Toast.LENGTH_SHORT).show();
        } else {

            for (int i = 0; i < Products.length; i++) {
                if (itemName.equals(Products[i])) {
                    price = Integer.parseInt(UnitPrice[i]);
                }
            }

            quantity = Integer.parseInt(quantityText);
            discount = (price * percent) / 100;
            amount = (price - discount) * quantity;
            totalAmount += amount;
            disc = percent + "%";

            items.add(new ItemList(itemName, quantity, price, disc, amount));
            databaseHelper.insertAddItemData(itemName, itemCode, quantity, price, disc, amount);

            populateRecyclerView();

            grandTotal = ((int) totalAmount);

            textVIewTotalAmount.setText(String.valueOf(grandTotal));
            editTextItemName.setText("");
            editTextItemCode.setText("");
            editTextQuantity.setText("");
        }
    }

    public void removeItem(int position) {
        subtractTotal = (int) items.get(position).getAmount();
        totalAmount -= subtractTotal;
        grandTotal = ((int) totalAmount);
        textVIewTotalAmount.setText(String.valueOf(grandTotal));

        items.remove(position);
        adapter.notifyItemRemoved(position);

        populateRecyclerView();

        Toast.makeText(AddItem.this, "Item removed", Toast.LENGTH_SHORT).show();
    }

    public void openPlaceOrderDialog() {
        PlaceOrderDialog placeOrderDialog = new PlaceOrderDialog();
        placeOrderDialog.show(getSupportFragmentManager(), "Place Order Dialog");
    }

    public void openRecyclerViewItemDeleteDialog() {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Remove Item");
        adb.setMessage("Do you want to remove this item?");
        adb.setIcon(R.drawable.ic_remove_item);

        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                removeItem(Position);
            }
        });

        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        adb.show();
    }
}