package model;

public class SavingsGoal {

    private final String name;

    private double targetAmount;
    private double savedAmount;

    private boolean completed;

    public SavingsGoal(
            String name,
            double targetAmount,
            double savedAmount) {

        this(
                name,
                targetAmount,
                savedAmount,
                false
        );
    }

    public SavingsGoal(
            String name,
            double targetAmount,
            double savedAmount,
            boolean completed) {

        this.name = name;
        this.targetAmount = targetAmount;
        this.savedAmount = savedAmount;
        this.completed = completed;
    }

    public String getName() {
        return name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public double getSavedAmount() {
        return savedAmount;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTargetAmount(
            double targetAmount) {

        this.targetAmount =
                targetAmount;
    }

    public void setSavedAmount(
            double savedAmount) {

        this.savedAmount =
                savedAmount;
    }

    public void setCompleted(
            boolean completed) {

        this.completed =
                completed;
    }

    public double getRemainingAmount() {

        return Math.max(
                0,
                targetAmount
                - savedAmount
        );
    }

    public double getProgress() {

        if (targetAmount <= 0) {
            return 0;
        }

        return Math.min(
                1.0,
                savedAmount
                / targetAmount
        );
    }

    public double getProgressPercentage() {

        return getProgress()
                * 100;
    }
}