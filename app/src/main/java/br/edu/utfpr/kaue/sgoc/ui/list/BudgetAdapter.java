package br.edu.utfpr.kaue.sgoc.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.utfpr.kaue.sgoc.R;
import br.edu.utfpr.kaue.sgoc.data.ClientDatabase;
import br.edu.utfpr.kaue.sgoc.model.Budget;
import br.edu.utfpr.kaue.sgoc.model.Client;

public class BudgetAdapter extends BaseAdapter {

    private Context context;
    private List<Budget> budgetList;
    private ClientDatabase database;

    private static class BudgetHolder {
        public TextView textViewBudgetClientNameValue;
        public TextView textViewBudgetIsComplete;
        public TextView textViewBudgetDate;
    }

    public BudgetAdapter(Context context, List<Budget> budgetList) {
        this.context = context;
        this.budgetList = budgetList;
    }

    @Override
    public int getCount() {
        return budgetList.size();
    }

    @Override
    public Object getItem(int position) {
        return budgetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BudgetHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.budget_list_item, parent, false);

            holder = new BudgetHolder();

            holder.textViewBudgetClientNameValue = convertView.findViewById(R.id.textViewBudgetClientNameValue);
            holder.textViewBudgetIsComplete = convertView.findViewById(R.id.textViewBudgetStatusValue);
            holder.textViewBudgetDate = convertView.findViewById(R.id.textViewBudgetDateValue);

            convertView.setTag(holder);
        }else {
            holder= (BudgetHolder) convertView.getTag();
        }



        Budget budget = budgetList.get(position);

        Client client = database.getClientDao().queryForId(budget.getClientId());

        holder.textViewBudgetClientNameValue.setText(client.getName());
        holder.textViewBudgetDate.setText(String.valueOf(budget.getDate()));
        holder.textViewBudgetIsComplete.setText(budget.isComplete() ? context.getString(R.string.finalizado) : context.getString(R.string.aguardando));

        return convertView;
    }
}