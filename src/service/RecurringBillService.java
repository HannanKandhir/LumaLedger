package service;

import java.util.ArrayList;
import java.util.List;

import model.RecurringBill;

import storage.RecurringBillStorage;

public class RecurringBillService {

    private static List<RecurringBill> bills =
            new ArrayList<>();

    private static String
    currentUserEmail;

    public static void setCurrentUser(
            String email) {

        currentUserEmail =
                email;

        bills =
                RecurringBillStorage
                        .load(
                                email
                        );
    }

    public static List<RecurringBill>
    getBills() {

        return bills;
    }

    public static void addBill(
            String name,
            double amount,
            int dueDay,
            String category) {

        bills.add(
                new RecurringBill(
                        name,
                        amount,
                        dueDay,
                        category
                )
        );

        save();
    }

    public static void deleteBill(
            String name) {

        bills.removeIf(
                bill ->
                        bill
                                .getName()
                                .equalsIgnoreCase(
                                        name
                                )
        );

        save();
    }

    private static void save() {

        if (currentUserEmail != null) {

            RecurringBillStorage.save(
                    currentUserEmail,
                    bills
            );
        }
    }

    public static void clearCurrentUser() {

        bills.clear();

        currentUserEmail =
                null;
    }
}