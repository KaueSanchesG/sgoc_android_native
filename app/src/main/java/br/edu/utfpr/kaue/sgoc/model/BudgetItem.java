package br.edu.utfpr.kaue.sgoc.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BudgetItem {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private Item item;

    private float quantity;

    private String description;

    private double laborCost;
}
