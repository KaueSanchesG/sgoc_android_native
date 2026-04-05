package br.edu.utfpr.kaue.sgoc.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.utfpr.kaue.sgoc.R;
import br.edu.utfpr.kaue.sgoc.model.Client;

public class ClientAdapter extends BaseAdapter {

    private Context context;
    private List<Client> clientList;

    private static class ClientHolder {
        public TextView textViewClientNameValue;
        public TextView textViewClientAddressValue;
        public TextView textViewClientContactValue;
    }

    public ClientAdapter(Context context, List<Client> clientList) {
        this.context = context;
        this.clientList = clientList;
    }

    @Override
    public int getCount() {
        return clientList.size();
    }

    @Override
    public Object getItem(int position) {
        return clientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClientHolder holder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.client_list_item, parent, false);

            holder = new ClientHolder();

            holder.textViewClientNameValue = convertView.findViewById(R.id.textViewClientNameValue);
            holder.textViewClientAddressValue = convertView.findViewById(R.id.textViewClientAddressValue);
            holder.textViewClientContactValue = convertView.findViewById(R.id.textViewClientContactValue);

            convertView.setTag(holder);
        } else {
            holder = (ClientHolder) convertView.getTag();
        }

        Client client = clientList.get(position);

        holder.textViewClientNameValue.setText(String.valueOf(client.getName()));
        holder.textViewClientAddressValue.setText(String.valueOf(client.getAddress()));
        holder.textViewClientContactValue.setText(String.valueOf(client.getContact()));

        return convertView;
    }
}