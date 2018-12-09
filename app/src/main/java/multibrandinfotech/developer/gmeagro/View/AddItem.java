package multibrandinfotech.developer.gmeagro.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import multibrandinfotech.developer.gmeagro.Model.ItemList;
import multibrandinfotech.developer.gmeagro.R;
import multibrandinfotech.developer.gmeagro.ViewModel.ItemAdapter;
import multibrandinfotech.developer.gmeagro.ViewModel.PlaceOrderDialog;

import java.util.ArrayList;

public class AddItem extends AppCompatActivity {

    private EditText editTextItemName, editTextItemCode, editTextQuantity;
    private TextView textVIewTotalAmount;
    private Button buttonAddItem, buttonPlaceOrder;

    private String itemName, itemCode, quantityText;
    private int quantity = 0, price = 1000, percent = 5;
    private double discount = 1, amount, totalAmount = 0;
    public int pos;

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<ItemList> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editTextItemName = (EditText) findViewById(R.id.editText_ItemName);
        editTextItemCode = (EditText) findViewById(R.id.editText_ItemCode);
        editTextQuantity = (EditText) findViewById(R.id.editText_Quantity);
        textVIewTotalAmount = (TextView) findViewById(R.id.textView_TotalAmount);

        buttonAddItem = (Button) findViewById(R.id.button_AddItem);
        buttonPlaceOrder = (Button) findViewById(R.id.button_PlaceOrder);

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityText = editTextQuantity.getText().toString();
                itemName = editTextItemName.getText().toString();
                itemCode = editTextItemCode.getText().toString();
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

    public void getRecyclerViewItemPosition(int position) {
        ItemList itemPosition = items.get(position);
        adapter.notifyItemChanged(position);
        pos = position;
    }

    public void populateRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new LinearLayoutManager(this);
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
            quantity = Integer.parseInt(quantityText);
            discount = (price * percent) / 100;
            amount = (price - discount) * quantity;
            totalAmount += amount;

            items.add(new ItemList(pos + 1, itemName, quantity, price, percent, amount));
            pos++;

            Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT).show();

            populateRecyclerView();

            int grandTotal = ((int) totalAmount);

            textVIewTotalAmount.setText(String.valueOf(grandTotal));
            editTextItemName.setText("");
            editTextItemCode.setText("");
            editTextQuantity.setText("");
        }
    }

    public void removeItem(int position) {
        items.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void openPlaceOrderDialog() {
        PlaceOrderDialog placeOrderDialog = new PlaceOrderDialog();
        placeOrderDialog.show(getSupportFragmentManager(), "Place Order Dialog");
    }

    public void openRecyclerViewItemDeleteDialog() {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Do you want to remove this item?");
        //adb.setIcon(android.R.drawable.ic_delete);

        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                removeItem(pos);
                Toast.makeText(AddItem.this, "Item removed", Toast.LENGTH_SHORT).show();
            }
        });

        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        adb.show();
    }
}