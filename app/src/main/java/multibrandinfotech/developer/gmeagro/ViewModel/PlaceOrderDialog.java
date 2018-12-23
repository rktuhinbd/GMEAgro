package multibrandinfotech.developer.gmeagro.ViewModel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.Toast;

import multibrandinfotech.developer.gmeagro.R;
import multibrandinfotech.developer.gmeagro.View.IndentForm;

public class PlaceOrderDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Place Order")
                .setMessage("Do you want to place order?")
                .setIcon(R.drawable.ic_confirm)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Order Successfully Placed", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getContext(), IndentForm.class);
                        startActivity(i);
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
