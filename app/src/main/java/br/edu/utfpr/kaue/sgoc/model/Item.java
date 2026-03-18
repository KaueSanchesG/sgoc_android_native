package br.edu.utfpr.kaue.sgoc.model;

import java.util.Comparator;

public class Item {

    public static Comparator<Item> ascSort = new Comparator<Item>() {
        @Override
        public int compare(Item o1, Item o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    };

    public static Comparator<Item> descSort = new Comparator<Item>() {
        @Override
        public int compare(Item o1, Item o2) {
            return -1 * o1.getName().compareToIgnoreCase(o2.getName());
        }
    };

    private String name;
    private QuantityType quantityType;

    public Item(String nome, QuantityType quantityType) {
        this.name = nome;
        this.quantityType = quantityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QuantityType getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(QuantityType quantityType) {
        this.quantityType = quantityType;
    }


    @Override
    public String toString() {
        return name + '\n' +
               quantityType.toString() + '\n';
    }
}
