package service;

import java.time.YearMonth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Transaction;

import storage.TransactionStorage;

public class TransactionService {

    private static List<Transaction>
    transactions =
            new ArrayList<>();

    private static String
    currentUserEmail;

    public static void setCurrentUser(
            String email) {

        currentUserEmail =
                email;

        transactions =
                TransactionStorage
                        .loadTransactions(
                                email
                        );
    }

    public static void addTransaction(
            Transaction transaction) {

        transactions.add(
                transaction
        );

        save();
    }

    public static void updateTransaction(
            String id,
            String type,
            String category,
            double amount,
            String note,
            java.time.LocalDate date) {

        Transaction transaction =
                getById(id);

        if (transaction == null) {
            return;
        }

        transaction.setType(
                type
        );

        transaction.setCategory(
                category
        );

        transaction.setAmount(
                amount
        );

        transaction.setNote(
                note
        );

        transaction.setDate(
                date
        );

        save();
    }

    public static void deleteTransaction(
            String id) {

        transactions.removeIf(
                transaction ->
                        transaction
                                .getId()
                                .equals(
                                        id
                                )
        );

        save();
    }

    public static Transaction getById(
            String id) {

        for (Transaction transaction :
                transactions) {

            if (transaction
                    .getId()
                    .equals(id)) {

                return transaction;
            }
        }

        return null;
    }

    public static List<Transaction>
    getTransactions() {

        return transactions;
    }

    public static List<Transaction>
    getTransactionsForMonth(
            YearMonth month) {

        List<Transaction> result =
                new ArrayList<>();

        for (Transaction transaction :
                transactions) {

            if (YearMonth
                    .from(
                            transaction
                                    .getDate()
                    )
                    .equals(month)) {

                result.add(
                        transaction
                );
            }
        }

        return result;
    }

    public static double getTotalIncome() {

        return totalIncome(
                transactions
        );
    }

    public static double getTotalExpenses() {

        return totalExpenses(
                transactions
        );
    }

    public static double getBalance() {

        return getTotalIncome()
                - getTotalExpenses();
    }

    public static double getMonthlyIncome(
            YearMonth month) {

        return totalIncome(
                getTransactionsForMonth(
                        month
                )
        );
    }

    public static double getMonthlyExpenses(
            YearMonth month) {

        return totalExpenses(
                getTransactionsForMonth(
                        month
                )
        );
    }

    public static double getMonthlyBalance(
            YearMonth month) {

        return getMonthlyIncome(
                month
        )
                -
                getMonthlyExpenses(
                        month
                );
    }

    public static String getTopExpenseCategory(
            YearMonth month) {

        Map<String, Double> totals =
                new HashMap<>();

        for (Transaction transaction :
                getTransactionsForMonth(
                        month
                )) {

            if (transaction
                    .getType()
                    .equalsIgnoreCase(
                            "Expense"
                    )) {

                totals.merge(
                        transaction
                                .getCategory(),
                        transaction
                                .getAmount(),
                        Double::sum
                );
            }
        }

        String best =
                "None";

        double max =
                0;

        for (Map.Entry<String, Double> entry :
                totals.entrySet()) {

            if (entry.getValue()
                    > max) {

                max =
                        entry.getValue();

                best =
                        entry.getKey();
            }
        }

        return best;
    }

    private static double totalIncome(
            List<Transaction> list) {

        double total =
                0;

        for (Transaction transaction :
                list) {

            if (transaction
                    .getType()
                    .equalsIgnoreCase(
                            "Income"
                    )) {

                total +=
                        transaction
                                .getAmount();
            }
        }

        return total;
    }

    private static double totalExpenses(
            List<Transaction> list) {

        double total =
                0;

        for (Transaction transaction :
                list) {

            if (transaction
                    .getType()
                    .equalsIgnoreCase(
                            "Expense"
                    )) {

                total +=
                        transaction
                                .getAmount();
            }
        }

        return total;
    }

    private static void save() {

        if (currentUserEmail != null) {

            TransactionStorage
                    .saveTransactions(
                            currentUserEmail,
                            transactions
                    );
        }
    }

    public static void clearCurrentUser() {

        transactions.clear();

        currentUserEmail =
                null;
    }
}