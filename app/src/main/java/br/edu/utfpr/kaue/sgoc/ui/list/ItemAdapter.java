package br.edu.utfpr.kaue.sgoc.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.utfpr.kaue.sgoc.R;
import br.edu.utfpr.kaue.sgoc.model.Item;
import br.edu.utfpr.kaue.sgoc.ui.util.QuantityTypeFormatter;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private List<Item> itemList;

    private String[] quantityTypes;

    private static class ItemHolder {
        public TextView textViewItemNameValue;
        public TextView textViewItemQuantityTypeValue;
    }

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;

        quantityTypes = context.getResources().getStringArray(R.array.tipo_quantitativo);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =  inflater.inflate(R.layout.item_list_item, parent, false);

            holder = new ItemHolder();

            holder.textViewItemNameValue = convertView.findViewById(R.id.textViewItemNameValue);
            holder.textViewItemQuantityTypeValue = convertView.findViewById(R.id.textViewItemQuantityTypeValue);

            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }

        Item item = itemList.get(position);

        holder.textViewItemNameValue.setText(String.valueOf(item.getName()));


//        holder.textViewItemQuantityTypeValue.setText(quantityTypes[item.getQuantityType().ordinal()]);
        holder.textViewItemQuantityTypeValue.setText(
                QuantityTypeFormatter.format(context, item.getQuantityType())
        );


        return convertView;
    }
}
