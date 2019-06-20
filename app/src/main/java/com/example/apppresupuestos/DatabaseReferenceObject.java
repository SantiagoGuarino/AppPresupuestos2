package com.example.apppresupuestos;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class DatabaseReferenceObject implements Serializable {
    DatabaseReference databaseReference;

    public DatabaseReferenceObject(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
