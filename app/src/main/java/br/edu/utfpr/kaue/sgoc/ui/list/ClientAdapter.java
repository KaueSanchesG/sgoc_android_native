package br.edu.utfpr.kaue.sgoc.ui.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

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
        return null;
    }
}
