package br.edu.utfpr.kaue.sgoc.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Item {

//    public static Comparator<Item> ascSort = new Comparator<Item>() {
//        @Override
//        public int compare(Item o1, Item o2) {
//            return o1.getName().compareToIgnoreCase(o2.getName());
//        }
//    };
//
//    public static Comparator<Item> descSort = new Comparator<Item>() {
//        @Override
//        public int compare(Item o1, Item o2) {
//            return -1 * o1.getName().compareToIgnoreCase(o2.getName());
//        }
//    };

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(index = true)
    private String name;

    private QuantityType quantityType;

    public Item(String name, QuantityType quantityType) {
        this.name = name;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return name.equals(item.name) && quantityType == item.quantityType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantityType);
    }

    @Override
    public String toString() {
        return name + '\n' +
               quantityType.toString() + '\n';
    }
}
