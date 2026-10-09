package service;

import java.util.ArrayList;
import java.util.List;

import model.SavingsGoal;

import storage.SavingsGoalStorage;

public class SavingsGoalService {

    private static List<SavingsGoal> goals =
            new ArrayList<>();

    private static String
    currentUserEmail;

    public static void setCurrentUser(
            String email) {

        currentUserEmail =
                email;

        goals =
                SavingsGoalStorage
                        .loadGoals(
                                email
                        );
    }

    public static boolean addGoal(
            String name,
            double targetAmount) {

        if (getGoalByName(
                name
        ) != null) {

            return false;
        }

        goals.add(
                new SavingsGoal(
                        name,
                        targetAmount,
                        0
                )
        );

        save();

        return true;
    }

    public static List<SavingsGoal>
    getGoals() {

        return goals;
    }

    public static SavingsGoal getGoalByName(
            String name) {

        for (SavingsGoal goal :
                goals) {

            if (goal
                    .getName()
                    .equalsIgnoreCase(
                            name
                    )) {

                return goal;
            }
        }

        return null;
    }

    public static boolean addMoneyToGoal(
            String name,
            double amount) {

        SavingsGoal goal =
                getGoalByName(
                        name
                );

        if (goal == null
                || amount <= 0) {

            return false;
        }

        goal.setSavedAmount(
                Math.min(
                        goal.getTargetAmount(),
                        goal.getSavedAmount()
                        + amount
                )
        );

        if (goal.getSavedAmount()
                >= goal.getTargetAmount()) {

            goal.setCompleted(
                    true
            );
        }

        save();

        return true;
    }

    public static boolean updateTarget(
            String name,
            double newTarget) {

        SavingsGoal goal =
                getGoalByName(
                        name
                );

        if (goal == null
                || newTarget <= 0) {

            return false;
        }

        goal.setTargetAmount(
                newTarget
        );

        if (goal.getSavedAmount()
                > newTarget) {

            goal.setSavedAmount(
                    newTarget
            );
        }

        goal.setCompleted(
                goal.getSavedAmount()
                >= newTarget
        );

        save();

        return true;
    }

    public static void deleteGoal(
            String name) {

        goals.removeIf(
                goal ->
                        goal
                                .getName()
                                .equalsIgnoreCase(
                                        name
                                )
        );

        save();
    }

    private static void save() {

        if (currentUserEmail != null) {

            SavingsGoalStorage
                    .saveGoals(
                            currentUserEmail,
                            goals
                    );
        }
    }

    public static void clearCurrentUser() {

        goals.clear();

        currentUserEmail =
                null;
    }
}