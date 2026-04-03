package br.edu.utfpr.kaue.sgoc.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Client {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private String address;

    private String contact;

    public Client(String name, String address, String contact) {
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return name.equals(client.name) && address.equals(client.address) && contact.equals(client.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, contact);
    }
}
