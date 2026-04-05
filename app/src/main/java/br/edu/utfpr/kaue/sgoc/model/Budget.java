package br.edu.utfpr.kaue.sgoc.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Budget {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String description;

    private long clientId;

    private boolean isComplete;

    private Date date;

    public Budget(String description, long clientid) {
        this.description = description;
        this.clientId = clientid;
        this.isComplete = false;
        this.date = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}