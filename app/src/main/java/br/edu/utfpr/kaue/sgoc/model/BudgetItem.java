package br.edu.utfpr.kaue.sgoc.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BudgetItem {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long budgetId;
    private long itemId;

    private float quantity;

    private String description;

    private double laborCost;

    public BudgetItem(long budgetId, long itemId, float quantity, String description, double laborCost) {
        this.budgetId = budgetId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.description = description;
        this.laborCost = laborCost;
    }

    public BudgetItem(long budgetId, long itemId, float quantity, String description) {
        this.budgetId = budgetId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.description = description;
        this.laborCost = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
    }
}
