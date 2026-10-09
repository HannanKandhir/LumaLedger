package service;

import java.time.YearMonth;

import java.util.ArrayList;
import java.util.List;

import model.Budget;
import model.Transaction;

import storage.BudgetStorage;

public class BudgetService {

    private static List<Budget> budgets =
            new ArrayList<>();

    private static String
    currentUserEmail;

    public static void setCurrentUser(
            String email) {

        currentUserEmail =
                email;

        budgets =
                BudgetStorage
                        .loadBudgets(
                                email
                        );
    }

    public static void setBudget(
            String category,
            double limit) {

        for (Budget budget :
                budgets) {

            if (budget
                    .getCategory()
                    .equalsIgnoreCase(
                            category
                    )) {

                budget.setLimit(
                        limit
                );

                save();

                return;
            }
        }

        budgets.add(
                new Budget(
                        category,
                        limit
                )
        );

        save();
    }

    public static void deleteBudget(
            String category) {

        budgets.removeIf(
                budget ->
                        budget
                                .getCategory()
                                .equalsIgnoreCase(
                                        category
                                )
        );

        save();
    }

    public static List<Budget>
    getBudgets() {

        return budgets;
    }

    public static double getSpentForCategory(
            String category) {

        return getSpentForCategory(
                category,
                YearMonth.now()
        );
    }

    public static double getSpentForCategory(
            String category,
            YearMonth month) {

        double total =
                0;

        for (Transaction transaction :
                TransactionService
                        .getTransactionsForMonth(
                                month
                        )) {

            if (transaction
                    .getType()
                    .equalsIgnoreCase(
                            "Expense"
                    )
                    &&
                    transaction
                            .getCategory()
                            .equalsIgnoreCase(
                                    category
                            )) {

                total +=
                        transaction
                                .getAmount();
            }
        }

        return total;
    }

    public static double getRemainingBudget(
            String category) {

        Budget budget =
                getBudgetByCategory(
                        category
                );

        if (budget == null) {
            return 0;
        }

        return budget.getLimit()
                -
                getSpentForCategory(
                        category
                );
    }

    public static double getTotalRemainingBudget() {

        double total =
                0;

        for (Budget budget :
                budgets) {

            total +=
                    getRemainingBudget(
                            budget
                                    .getCategory()
                    );
        }

        return total;
    }

    public static Budget getBudgetByCategory(
            String category) {

        for (Budget budget :
                budgets) {

            if (budget
                    .getCategory()
                    .equalsIgnoreCase(
                            category
                    )) {

                return budget;
            }
        }

        return null;
    }

    private static void save() {

        if (currentUserEmail != null) {

            BudgetStorage.saveBudgets(
                    currentUserEmail,
                    budgets
            );
        }
    }

    public static void clearCurrentUser() {

        budgets.clear();

        currentUserEmail =
                null;
    }
}