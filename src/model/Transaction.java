package model;

import java.time.LocalDate;
import java.util.UUID;

public class Transaction {

    private final String id;

    private String type;
    private String category;
    private double amount;
    private String note;
    private LocalDate date;

    public Transaction(
            String type,
            String category,
            double amount,
            String note,
            LocalDate date) {

        this(
                UUID.randomUUID().toString(),
                type,
                category,
                amount,
                note,
                date
        );
    }

    public Transaction(
            String id,
            String type,
            String category,
            double amount,
            String note,
            LocalDate date) {

        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;

        this.note =
                note == null
                ? ""
                : note;

        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setType(
            String type) {

        this.type = type;
    }

    public void setCategory(
            String category) {

        this.category = category;
    }

    public void setAmount(
            double amount) {

        this.amount = amount;
    }

    public void setNote(
            String note) {

        this.note =
                note == null
                ? ""
                : note;
    }

    public void setDate(
            LocalDate date) {

        this.date = date;
    }
}