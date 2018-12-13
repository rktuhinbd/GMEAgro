package multibrandinfotech.developer.gmeagro.ViewModel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import multibrandinfotech.developer.gmeagro.Model.ItemList;
import multibrandinfotech.developer.gmeagro.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<ItemList> itemLists;
    private OnItemClickListener rListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        rListener = listener;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewSerial;
        public TextView textViewItem;
        public TextView textViewQuantity;
        public TextView textViewPrice;
        public TextView textViewDiscount;
        public TextView textViewAmount;

        public ItemViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            textViewSerial = itemView.findViewById(R.id.textView_serial);
            textViewItem = itemView.findViewById(R.id.textView_item);
            textViewQuantity = itemView.findViewById(R.id.textView_quantity);
            textViewPrice = itemView.findViewById(R.id.textView_price);
            textViewDiscount = itemView.findViewById(R.id.textView_discount);
            textViewAmount = itemView.findViewById(R.id.textView_amount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public ItemAdapter(ArrayList<ItemList> itemLists) {
        this.itemLists = itemLists;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view, rListener);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        ItemList currentItem = itemLists.get(i);

        itemViewHolder.textViewSerial.setText(i + 1 + "");
        itemViewHolder.textViewItem.setText(currentItem.getItem() + "");
        itemViewHolder.textViewQuantity.setText((int) currentItem.getQuantity() + "");
        itemViewHolder.textViewPrice.setText((int) currentItem.getPrice() + "");
        itemViewHolder.textViewDiscount.setText(currentItem.getDiscount() + "");
        itemViewHolder.textViewAmount.setText((int) currentItem.getAmount() + "");
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

}
