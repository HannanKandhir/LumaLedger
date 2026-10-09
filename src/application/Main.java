package application;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import model.Budget;
import model.RecurringBill;
import model.SavingsGoal;
import model.Transaction;
import model.User;

import service.AuthService;
import service.BudgetService;
import service.RecurringBillService;
import service.SavingsGoalService;
import service.TransactionService;

import util.CSVExporter;

public class Main extends Application {

    private static final String APP_NAME = "LumaLedger";

    private static final String DEVELOPER =
            "Abdul Hannan Kandhir";

    private static final String LOGO_PATH =
            "/application/images/lumaledger_logo.png";

    private boolean darkMode = true;

    private YearMonth selectedMonth =
            YearMonth.now();

    // =====================================================
    // START
    // =====================================================

    @Override
    public void start(Stage stage) {

        try {

            InputStream stream =
                    getClass()
                            .getResourceAsStream(
                                    LOGO_PATH
                            );

            if (stream != null) {

                stage.getIcons()
                        .add(
                                new Image(
                                        stream
                                )
                        );
            }

        } catch (Exception e) {

            System.out.println(
                    "App icon could not be loaded."
            );
        }

        showLoginScreen(stage);
    }

    // =====================================================
    // LOGO
    // =====================================================

    private ImageView createLogo(
            double width) {

        ImageView imageView =
                new ImageView();

        try {

            InputStream stream =
                    getClass()
                            .getResourceAsStream(
                                    LOGO_PATH
                            );

            if (stream != null) {

                imageView.setImage(
                        new Image(stream)
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Logo could not be loaded."
            );
        }

        imageView.setFitWidth(width);

        imageView.setPreserveRatio(true);

        imageView.setSmooth(true);

        return imageView;
    }

    private VBox createSidebarBranding() {

        ImageView logo =
                createLogo(220);

        logo.getStyleClass()
                .add("sidebar-logo");

        Separator separator =
                new Separator();

        separator.getStyleClass()
                .add("sidebar-divider");

        VBox box =
                new VBox(
                        7,
                        logo,
                        separator
                );

        box.setAlignment(
                Pos.CENTER
        );

        box.getStyleClass()
                .add("sidebar-brand");

        return box;
    }

    private VBox createDeveloperCredit() {

        Label top =
                new Label(
                        "DESIGNED & DEVELOPED BY"
                );

        top.getStyleClass()
                .add("developer-small");

        Label name =
                new Label(
                        DEVELOPER
                );

        name.getStyleClass()
                .add("developer-name");

        Label role =
                new Label(
                        "LumaLedger • JavaFX"
                );

        role.getStyleClass()
                .add("developer-role");

        VBox credit =
                new VBox(
                        3,
                        top,
                        name,
                        role
                );

        credit.setAlignment(
                Pos.CENTER
        );

        credit.setMaxWidth(
                Double.MAX_VALUE
        );

        credit.getStyleClass()
                .add("developer-card");

        addCardAnimation(
                credit
        );

        return credit;
    }

    // =====================================================
    // CSS
    // =====================================================

    private void applyCss(
            Scene scene) {

        scene.getStylesheets()
                .add(
                        getClass()
                                .getResource(
                                        "application.css"
                                )
                                .toExternalForm()
                );

        if (darkMode) {

            scene.getRoot()
                    .getStyleClass()
                    .add("dark-root");
        }
    }

    // =====================================================
    // LOGIN
    // =====================================================

    private void showLoginScreen(
            Stage stage) {

        ImageView logo =
                createLogo(340);

        logo.getStyleClass()
                .add("login-logo");

        Label tagline =
                new Label(
                        "Money clarity, every day."
                );

        tagline.getStyleClass()
                .add("login-tagline");

        Label feature1 =
                new Label(
                        "✓ Track income and expenses"
                );

        Label feature2 =
                new Label(
                        "✓ Build smarter monthly budgets"
                );

        Label feature3 =
                new Label(
                        "✓ Reach your savings goals"
                );

        Label feature4 =
                new Label(
                        "✓ Understand your financial habits"
                );

        feature1.getStyleClass()
                .add("login-feature");

        feature2.getStyleClass()
                .add("login-feature");

        feature3.getStyleClass()
                .add("login-feature");

        feature4.getStyleClass()
                .add("login-feature");

        Label developer =
                new Label(
                        "Designed & Developed by "
                        + DEVELOPER
                );

        developer.getStyleClass()
                .add("login-developer");

        VBox brandPanel =
                new VBox(
                        18,
                        logo,
                        tagline,
                        feature1,
                        feature2,
                        feature3,
                        feature4,
                        developer
                );

        brandPanel.setAlignment(
                Pos.CENTER_LEFT
        );

        brandPanel.setPrefWidth(
                470
        );

        brandPanel.getStyleClass()
                .add("login-brand-panel");

        Label title =
                new Label(
                        "Welcome Back"
                );

        title.getStyleClass()
                .add("page-title");

        Label subtitle =
                new Label(
                        "Sign in to your LumaLedger account"
                );

        subtitle.getStyleClass()
                .add("page-subtitle");

        TextField emailField =
                new TextField();

        emailField.setPromptText(
                "Email address"
        );

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText(
                "Password"
        );

        Button loginButton =
                new Button(
                        "Sign In"
                );

        loginButton.setMaxWidth(
                Double.MAX_VALUE
        );

        loginButton.getStyleClass()
                .add("primary-button");

        Button registerButton =
                new Button(
                        "Create New Account"
                );

        registerButton.setMaxWidth(
                Double.MAX_VALUE
        );

        registerButton.getStyleClass()
                .add("secondary-button");

        addButtonAnimation(loginButton);

        addButtonAnimation(registerButton);

        Label message =
                new Label();

        loginButton.setOnAction(e -> {

            String email =
                    emailField
                            .getText()
                            .trim();

            String password =
                    passwordField
                            .getText();

            if (email.isBlank()
                    || password.isBlank()) {

                showError(
                        message,
                        "Enter your email and password."
                );

                return;
            }

            User user =
                    AuthService.login(
                            email,
                            password
                    );

            if (user == null) {

                showError(
                        message,
                        "Invalid email or password."
                );

                shakeNode(
                        loginButton
                );

                return;
            }

            loadUserData(user);

            showDashboard(
                    stage,
                    user
            );
        });

        registerButton.setOnAction(
                e -> showRegisterScreen(
                        stage
                )
        );

        VBox loginCard =
                new VBox(
                        17,
                        title,
                        subtitle,
                        new Separator(),
                        emailField,
                        passwordField,
                        loginButton,
                        registerButton,
                        message
                );

        loginCard.setPrefWidth(
                420
        );

        loginCard.getStyleClass()
                .add("login-card");

        HBox root =
                new HBox(
                        45,
                        brandPanel,
                        loginCard
                );

        root.setAlignment(
                Pos.CENTER
        );

        root.setPadding(
                new Insets(45)
        );

        root.getStyleClass()
                .add("login-background");

        Scene scene =
                new Scene(
                        root,
                        1180,
                        720
                );

        applyCss(scene);

        stage.setScene(scene);

        stage.setTitle(APP_NAME);

        stage.show();

        playPageEntrance(root);
    }

    // =====================================================
    // LOAD USER
    // =====================================================

    private void loadUserData(
            User user) {

        TransactionService
                .setCurrentUser(
                        user.getEmail()
                );

        BudgetService
                .setCurrentUser(
                        user.getEmail()
                );

        SavingsGoalService
                .setCurrentUser(
                        user.getEmail()
                );

        RecurringBillService
                .setCurrentUser(
                        user.getEmail()
                );
    }

    // =====================================================
    // REGISTER
    // =====================================================

    private void showRegisterScreen(
            Stage stage) {

        ImageView logo =
                createLogo(260);

        Label title =
                new Label(
                        "Create Your Account"
                );

        title.getStyleClass()
                .add("page-title");

        Label subtitle =
                new Label(
                        "Start building a clearer financial future."
                );

        subtitle.getStyleClass()
                .add("page-subtitle");

        TextField nameField =
                new TextField();

        nameField.setPromptText(
                "Full name"
        );

        TextField emailField =
                new TextField();

        emailField.setPromptText(
                "Email address"
        );

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText(
                "Password"
        );

        PasswordField confirmField =
                new PasswordField();

        confirmField.setPromptText(
                "Confirm password"
        );

        Button createButton =
                new Button(
                        "Create Account"
                );

        createButton.setMaxWidth(
                Double.MAX_VALUE
        );

        createButton.getStyleClass()
                .add("primary-button");

        Button backButton =
                new Button(
                        "Back to Sign In"
                );

        backButton.setMaxWidth(
                Double.MAX_VALUE
        );

        backButton.getStyleClass()
                .add("secondary-button");

        addButtonAnimation(createButton);

        addButtonAnimation(backButton);

        Label message =
                new Label();

        createButton.setOnAction(e -> {

            String name =
                    nameField
                            .getText()
                            .trim();

            String email =
                    emailField
                            .getText()
                            .trim();

            String password =
                    passwordField
                            .getText();

            String confirm =
                    confirmField
                            .getText();

            if (name.isBlank()
                    || email.isBlank()
                    || password.isBlank()
                    || confirm.isBlank()) {

                showError(
                        message,
                        "Complete every field."
                );

                return;
            }

            if (!email.contains("@")
                    || !email.contains(".")) {

                showError(
                        message,
                        "Enter a valid email address."
                );

                return;
            }

            if (password.length() < 6) {

                showError(
                        message,
                        "Password needs at least 6 characters."
                );

                return;
            }

            if (!password.equals(confirm)) {

                showError(
                        message,
                        "Passwords do not match."
                );

                return;
            }

            boolean created =
                    AuthService
                            .registerUser(
                                    name,
                                    email,
                                    password
                            );

            if (!created) {

                showError(
                        message,
                        "An account already exists with this email."
                );

                return;
            }

            showSuccess(
                    message,
                    "Account created successfully."
            );

            nameField.clear();

            emailField.clear();

            passwordField.clear();

            confirmField.clear();
        });

        backButton.setOnAction(
                e -> showLoginScreen(
                        stage
                )
        );

        VBox card =
                new VBox(
                        15,
                        logo,
                        title,
                        subtitle,
                        new Separator(),
                        nameField,
                        emailField,
                        passwordField,
                        confirmField,
                        createButton,
                        backButton,
                        message
                );

        card.setMaxWidth(
                500
        );

        card.getStyleClass()
                .add("login-card");

        VBox root =
                new VBox(card);

        root.setAlignment(
                Pos.CENTER
        );

        root.setPadding(
                new Insets(50)
        );

        root.getStyleClass()
                .add("login-background");

        Scene scene =
                new Scene(
                        root,
                        1000,
                        760
                );

        applyCss(scene);

        stage.setScene(scene);

        stage.setTitle(
                APP_NAME
                + " - Create Account"
        );

        playPageEntrance(root);
    }

    // =====================================================
    // MAIN LAYOUT
    // =====================================================

    private BorderPane createMainLayout(
            Stage stage,
            User user,
            String activePage) {

        VBox branding =
                createSidebarBranding();

        Button dashboard =
                createMenuButton(
                        "⌂   Dashboard",
                        "dashboard",
                        activePage
                );

        Button transactions =
                createMenuButton(
                        "▣   Transactions",
                        "transactions",
                        activePage
                );

        Button budgets =
                createMenuButton(
                        "▥   Budgets",
                        "budgets",
                        activePage
                );

        Button goals =
                createMenuButton(
                        "◎   Savings Goals",
                        "goals",
                        activePage
                );

        Button bills =
                createMenuButton(
                        "▤   Bills",
                        "bills",
                        activePage
                );

        Button reports =
                createMenuButton(
                        "▰   Reports",
                        "reports",
                        activePage
                );

        Button profile =
                createMenuButton(
                        "♙   Profile",
                        "profile",
                        activePage
                );

        dashboard.setOnAction(
                e -> showDashboard(
                        stage,
                        user
                )
        );

        transactions.setOnAction(
                e -> showTransactionsPage(
                        stage,
                        user
                )
        );

        budgets.setOnAction(
                e -> showBudgetsPage(
                        stage,
                        user
                )
        );

        goals.setOnAction(
                e -> showSavingsGoalsPage(
                        stage,
                        user
                )
        );

        bills.setOnAction(
                e -> showBillsPage(
                        stage,
                        user
                )
        );

        reports.setOnAction(
                e -> showReportsPage(
                        stage,
                        user
                )
        );

        profile.setOnAction(
                e -> showProfilePage(
                        stage,
                        user
                )
        );

        Region spacer =
                new Region();

        VBox.setVgrow(
                spacer,
                Priority.ALWAYS
        );

        Button themeButton =
                new Button(
                        darkMode
                        ?
                        "☀   Light Mode"
                        :
                        "☾   Dark Mode"
                );

        themeButton.setMaxWidth(
                Double.MAX_VALUE
        );

        themeButton.getStyleClass()
                .add("sidebar-button");

        themeButton.setOnAction(e -> {

            darkMode =
                    !darkMode;

            showDashboard(
                    stage,
                    user
            );
        });

        Button logout =
                new Button(
                        "↪   Logout"
                );

        logout.setMaxWidth(
                Double.MAX_VALUE
        );

        logout.getStyleClass()
                .add("logout-button");

        logout.setOnAction(e -> {

            TransactionService
                    .clearCurrentUser();

            BudgetService
                    .clearCurrentUser();

            SavingsGoalService
                    .clearCurrentUser();

            RecurringBillService
                    .clearCurrentUser();

            showLoginScreen(stage);
        });

        VBox credit =
                createDeveloperCredit();

        VBox sidebar =
                new VBox(
                        8,
                        branding,
                        dashboard,
                        transactions,
                        budgets,
                        goals,
                        bills,
                        reports,
                        profile,
                        spacer,
                        themeButton,
                        logout,
                        credit
                );

        sidebar.getStyleClass()
                .add("sidebar");

        sidebar.setPrefWidth(
                270
        );

        BorderPane root =
                new BorderPane();

        root.setLeft(sidebar);

        return root;
    }

    // =====================================================
    // DASHBOARD
    // =====================================================

    private void showDashboard(
            Stage stage,
            User user) {

        BorderPane root =
                createMainLayout(
                        stage,
                        user,
                        "dashboard"
                );

        Label title =
                new Label(
                        getGreeting()
                        + ", "
                        + getFirstName(
                                user.getName()
                        )
                );

        title.getStyleClass()
                .add("page-title");

        Label subtitle =
                new Label(
                        "Your financial picture for "
                        + formatMonth(
                                selectedMonth
                        )
                );

        subtitle.getStyleClass()
                .add("page-subtitle");

        VBox headingText =
                new VBox(
                        3,
                        title,
                        subtitle
                );

        ComboBox<YearMonth> monthBox =
                createMonthPicker();

        monthBox.setValue(
                selectedMonth
        );

        monthBox.setPrefWidth(
                170
        );

        monthBox.setOnAction(e -> {

            selectedMonth =
                    monthBox.getValue();

            showDashboard(
                    stage,
                    user
            );
        });

        Label avatar =
                new Label(
                        getInitials(
                                user.getName()
                        )
                );

        avatar.setMinSize(
                44,
                44
        );

        avatar.setPrefSize(
                44,
                44
        );

        avatar.getStyleClass()
                .add("avatar");

        int notificationCount =
                getNotificationCount();

        Button bell =
                new Button("🔔");

        bell.getStyleClass()
                .add("notification-button");

        Label badge =
                new Label(
                        String.valueOf(
                                notificationCount
                        )
                );

        badge.getStyleClass()
                .add("notification-badge");

        badge.setVisible(
                notificationCount > 0
        );

        StackPane notificationPane =
                new StackPane(
                        bell,
                        badge
                );

        StackPane.setAlignment(
                badge,
                Pos.TOP_RIGHT
        );

        if (notificationCount > 0) {

            pulseNode(bell);
        }

        bell.setOnAction(
                e -> showNotificationWindow(
                        stage,
                        user
                )
        );

        Region topSpacer =
                new Region();

        HBox.setHgrow(
                topSpacer,
                Priority.ALWAYS
        );

        HBox topBar =
                new HBox(
                        14,
                        headingText,
                        topSpacer,
                        monthBox,
                        notificationPane,
                        avatar
                );

        topBar.setAlignment(
                Pos.CENTER_LEFT
        );

        topBar.getStyleClass()
                .add("top-bar");

        double balance =
                TransactionService
                        .getMonthlyBalance(
                                selectedMonth
                        );

        double income =
                TransactionService
                        .getMonthlyIncome(
                                selectedMonth
                        );

        double expenses =
                TransactionService
                        .getMonthlyExpenses(
                                selectedMonth
                        );

        double budgetRemaining =
                calculateBudgetRemaining(
                        selectedMonth
                );

        Label balanceValue =
                createValueLabel(
                        "blue-value"
                );

        Label incomeValue =
                createValueLabel(
                        "green-value"
                );

        Label expenseValue =
                createValueLabel(
                        "red-value"
                );

        Label budgetValue =
                createValueLabel(
                        "purple-value"
                );

        Label balanceStatus =
                createStatusLabel(
                        "This month",
                        ""
                );

        Label incomeStatus =
                createStatusLabel(
                        "Money earned",
                        "status-good"
                );

        Label expenseStatus =
                createStatusLabel(
                        "Money spent",
                        "status-danger"
                );

        Label budgetStatus =
                new Label();

        budgetStatus.getStyleClass()
                .add("status-label");

        animateMoney(
                balanceValue,
                user,
                balance
        );

        animateMoney(
                incomeValue,
                user,
                income
        );

        animateMoney(
                expenseValue,
                user,
                expenses
        );

        if (BudgetService
                .getBudgets()
                .isEmpty()) {

            budgetValue.setText(
                    "No Budget"
            );

            budgetStatus.setText(
                    "Create a monthly budget"
            );

            budgetStatus.getStyleClass()
                    .add("status-warning");

        } else {

            double displayBudget =
                    Math.max(
                            0,
                            budgetRemaining
                    );

            animateMoney(
                    budgetValue,
                    user,
                    displayBudget
            );

            if (budgetRemaining <= 0) {

                budgetStatus.setText(
                        "Fully used"
                );

                budgetStatus.getStyleClass()
                        .add("status-danger");

                budgetValue.getStyleClass()
                        .remove("purple-value");

                budgetValue.getStyleClass()
                        .add("red-value");

            } else {

                budgetStatus.setText(
                        "Available to spend"
                );

                budgetStatus.getStyleClass()
                        .add("status-good");
            }
        }

        VBox balanceCard =
                createSummaryCard(
                        "◉ Balance",
                        balanceValue,
                        balanceStatus
                );

        VBox incomeCard =
                createSummaryCard(
                        "↗ Income",
                        incomeValue,
                        incomeStatus
                );

        VBox expenseCard =
                createSummaryCard(
                        "↘ Expenses",
                        expenseValue,
                        expenseStatus
                );

        VBox budgetCard =
                createSummaryCard(
                        "▥ Budget Left",
                        budgetValue,
                        budgetStatus
                );

        HBox cards =
                new HBox(
                        16,
                        balanceCard,
                        incomeCard,
                        expenseCard,
                        budgetCard
                );

        HBox.setHgrow(
                balanceCard,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                incomeCard,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                expenseCard,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                budgetCard,
                Priority.ALWAYS
        );

        int healthScore =
                calculateHealthScore();

        Label healthTitle =
                new Label(
                        "Financial Health"
                );

        healthTitle.getStyleClass()
                .add("section-title");

        Label healthScoreLabel =
                new Label("0/100");

        healthScoreLabel
                .getStyleClass()
                .add("health-score");

        if (healthScore >= 75) {

            healthScoreLabel
                    .getStyleClass()
                    .add("health-good");

        } else if (healthScore >= 50) {

            healthScoreLabel
                    .getStyleClass()
                    .add("health-warning");

        } else {

            healthScoreLabel
                    .getStyleClass()
                    .add("health-bad");
        }

        animateHealthScore(
                healthScoreLabel,
                healthScore
        );

        ProgressBar healthProgress =
                new ProgressBar(0);

        healthProgress.setMaxWidth(
                Double.MAX_VALUE
        );

        animateProgress(
                healthProgress,
                healthScore / 100.0
        );

        Label healthDescription =
                new Label(
                        getHealthMessage(
                                healthScore
                        )
                );

        healthDescription.setWrapText(
                true
        );

        healthDescription
                .getStyleClass()
                .add("page-subtitle");

        VBox healthCard =
                new VBox(
                        10,
                        healthTitle,
                        healthScoreLabel,
                        healthProgress,
                        healthDescription
                );

        healthCard.setPrefWidth(
                290
        );

        healthCard.getStyleClass()
                .add("health-card");

        addCardAnimation(
                healthCard
        );

        Label insightTitle =
                new Label(
                        "✦ Smart Insight"
                );

        Label insight =
                new Label(
                        buildInsight()
                );

        insight.setWrapText(true);

        insight.getStyleClass()
                .add("section-title");

        VBox insightCard =
                new VBox(
                        10,
                        insightTitle,
                        insight
                );

        insightCard.getStyleClass()
                .add("glass-card");

        HBox.setHgrow(
                insightCard,
                Priority.ALWAYS
        );

        addCardAnimation(
                insightCard
        );

        HBox insightRow =
                new HBox(
                        16,
                        healthCard,
                        insightCard
                );

        VBox savingsWidget =
                createSavingsDashboardWidget(
                        user
                );

        VBox budgetWidget =
                createBudgetDashboardWidget(
                        user
                );

        VBox billWidget =
                createUpcomingBillWidget(
                        user
                );

        HBox widgets =
                new HBox(
                        16,
                        savingsWidget,
                        budgetWidget,
                        billWidget
                );

        HBox.setHgrow(
                savingsWidget,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                budgetWidget,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                billWidget,
                Priority.ALWAYS
        );

        PieChart pieChart =
                createDashboardPieChart();

        VBox pieCard =
                new VBox(
                        pieChart
                );

        pieCard.setPrefHeight(
                330
        );

        pieCard.getStyleClass()
                .add("panel");

        BarChart<String, Number> barChart =
                createDashboardBarChart();

        VBox barCard =
                new VBox(
                        barChart
                );

        barCard.setPrefHeight(
                330
        );

        barCard.getStyleClass()
                .add("panel");

        HBox charts =
                new HBox(
                        16,
                        pieCard,
                        barCard
                );

        HBox.setHgrow(
                pieCard,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                barCard,
                Priority.ALWAYS
        );

        Button addTransaction =
                new Button(
                        "+ Add Transaction"
                );

        addTransaction
                .getStyleClass()
                .add("primary-button");

        addButtonAnimation(
                addTransaction
        );

        addTransaction.setOnAction(
                e -> showTransactionEditor(
                        stage,
                        user,
                        null
                )
        );

        Label recentTitle =
                new Label(
                        "Recent Transactions"
                );

        recentTitle.getStyleClass()
                .add("section-title");

        VBox recentList =
                new VBox(
                        9,
                        recentTitle
                );

        List<Transaction> transactions =
                TransactionService
                        .getTransactionsForMonth(
                                selectedMonth
                        );

        if (transactions.isEmpty()) {

            Label empty =
                    new Label(
                            "No transactions this month."
                    );

            empty.getStyleClass()
                    .add("page-subtitle");

            recentList.getChildren()
                    .add(empty);

        } else {

            int count = 0;

            for (int i =
                    transactions.size() - 1;
                 i >= 0 && count < 5;
                 i--) {

                recentList
                        .getChildren()
                        .add(
                                createDashboardTransactionRow(
                                        transactions.get(i),
                                        user
                                )
                        );

                count++;
            }
        }

        recentList.getStyleClass()
                .add("panel");

        VBox content =
                new VBox(
                        20,
                        topBar,
                        cards,
                        insightRow,
                        widgets,
                        charts,
                        addTransaction,
                        recentList
                );

        content.setPadding(
                new Insets(26)
        );

        ScrollPane scroll =
                new ScrollPane(
                        content
                );

        scroll.setFitToWidth(true);

        root.setCenter(scroll);

        Scene scene =
                new Scene(
                        root,
                        1400,
                        850
                );

        applyCss(scene);

        stage.setScene(scene);

        stage.setTitle(
                APP_NAME
                + " - Dashboard"
        );

        playPageEntrance(
                content
        );

        animateChart(
                pieChart
        );

        animateChart(
                barChart
        );
    }

    // =====================================================
    // SUMMARY CARD
    // =====================================================

    private VBox createSummaryCard(
            String title,
            Label value,
            Label status) {

        Label titleLabel =
                new Label(title);

        titleLabel.getStyleClass()
                .add("card-label");

        value.getStyleClass()
                .add("card-value");

        VBox card =
                new VBox(
                        8,
                        titleLabel,
                        value,
                        status
                );

        card.getStyleClass()
                .add("dashboard-card");

        card.setMaxWidth(
                Double.MAX_VALUE
        );

        addCardAnimation(card);

        return card;
    }

    private Label createStatusLabel(
            String text,
            String style) {

        Label label =
                new Label(text);

        label.getStyleClass()
                .add("status-label");

        if (style != null
                && !style.isBlank()) {

            label.getStyleClass()
                    .add(style);
        }

        return label;
    }

    // =====================================================
    // SAVINGS WIDGET
    // =====================================================

    private VBox createSavingsDashboardWidget(
            User user) {

        Label title =
                new Label(
                        "Savings Goal"
                );

        title.getStyleClass()
                .add("section-title");

        VBox card =
                new VBox(
                        9,
                        title
                );

        card.getStyleClass()
                .add("compact-card");

        SavingsGoal goal =
                getBestSavingsGoal();

        if (goal == null) {

            Label empty =
                    new Label(
                            "Create a savings goal to start tracking progress."
                    );

            empty.setWrapText(true);

            empty.getStyleClass()
                    .add("page-subtitle");

            card.getChildren()
                    .add(empty);

            return card;
        }

        Label name =
                new Label(
                        goal.getName()
                );

        name.getStyleClass()
                .add("section-title");

        Label amount =
                new Label(
                        money(
                                user,
                                goal.getSavedAmount()
                        )
                        + " / "
                        + money(
                                user,
                                goal.getTargetAmount()
                        )
                );

        ProgressBar progress =
                new ProgressBar(0);

        progress.setMaxWidth(
                Double.MAX_VALUE
        );

        progress.getStyleClass()
                .add("savings-progress");

        double actualProgress =
                Math.min(
                        1,
                        goal.getProgress()
                );

        double visualProgress =
                actualProgress > 0
                ?
                Math.max(
                        actualProgress,
                        0.06
                )
                :
                0;

        animateProgress(
                progress,
                visualProgress
        );

        Label percentage =
                new Label(
                        String.format(
                                "%.0f%% complete",
                                goal
                                        .getProgressPercentage()
                        )
                );

        card.getChildren()
                .addAll(
                        name,
                        amount,
                        progress,
                        percentage
                );

        addCardAnimation(card);

        return card;
    }

    private SavingsGoal getBestSavingsGoal() {

        SavingsGoal best = null;

        double highestProgress = -1;

        for (SavingsGoal goal :
                SavingsGoalService
                        .getGoals()) {

            if (goal.isCompleted()) {

                continue;
            }

            if (goal.getProgress()
                    > highestProgress) {

                highestProgress =
                        goal.getProgress();

                best = goal;
            }
        }

        if (best == null
                &&
                !SavingsGoalService
                        .getGoals()
                        .isEmpty()) {

            best =
                    SavingsGoalService
                            .getGoals()
                            .get(0);
        }

        return best;
    }

    // =====================================================
    // BUDGET WIDGET
    // =====================================================

    private VBox createBudgetDashboardWidget(
            User user) {

        Label title =
                new Label(
                        "Budget Status"
                );

        title.getStyleClass()
                .add("section-title");

        VBox card =
                new VBox(
                        9,
                        title
                );

        card.getStyleClass()
                .add("compact-card");

        if (BudgetService
                .getBudgets()
                .isEmpty()) {

            Label empty =
                    new Label(
                            "No budgets created yet."
                    );

            empty.getStyleClass()
                    .add("page-subtitle");

            card.getChildren()
                    .add(empty);

            return card;
        }

        Budget highestUsed = null;

        double highestPercentage = -1;

        double highestSpent = 0;

        for (Budget budget :
                BudgetService
                        .getBudgets()) {

            double spent =
                    BudgetService
                            .getSpentForCategory(
                                    budget.getCategory(),
                                    selectedMonth
                            );

            double percentage =
                    budget.getLimit() <= 0
                    ?
                    0
                    :
                    spent
                    / budget.getLimit();

            if (percentage
                    > highestPercentage) {

                highestPercentage =
                        percentage;

                highestUsed =
                        budget;

                highestSpent =
                        spent;
            }
        }

        if (highestUsed != null) {

            Label category =
                    new Label(
                            categoryIcon(
                                    highestUsed
                                            .getCategory()
                            )
                            + " "
                            + highestUsed
                                    .getCategory()
                    );

            category.getStyleClass()
                    .add("section-title");

            Label numbers =
                    new Label(
                            money(
                                    user,
                                    highestSpent
                            )
                            + " / "
                            + money(
                                    user,
                                    highestUsed
                                            .getLimit()
                            )
                    );

            ProgressBar progress =
                    new ProgressBar(0);

            progress.setMaxWidth(
                    Double.MAX_VALUE
            );

            if (highestPercentage >= 1) {

                progress.getStyleClass()
                        .add("danger-progress");

            } else if (highestPercentage >= 0.8) {

                progress.getStyleClass()
                        .add("warning-progress");

            } else {

                progress.getStyleClass()
                        .add("success-progress");
            }

            animateProgress(
                    progress,
                    Math.min(
                            1,
                            highestPercentage
                    )
            );

            Label status =
                    new Label();

            if (highestPercentage >= 1) {

                status.setText(
                        "Limit reached"
                );

                status.getStyleClass()
                        .add("error-message");

            } else {

                status.setText(
                        String.format(
                                "%.0f%% used",
                                highestPercentage
                                * 100
                        )
                );
            }

            card.getChildren()
                    .addAll(
                            category,
                            numbers,
                            progress,
                            status
                    );
        }

        addCardAnimation(card);

        return card;
    }

    // =====================================================
    // BILL WIDGET
    // =====================================================

    private VBox createUpcomingBillWidget(
            User user) {

        Label title =
                new Label(
                        "Upcoming Bill"
                );

        title.getStyleClass()
                .add("section-title");

        VBox card =
                new VBox(
                        9,
                        title
                );

        card.getStyleClass()
                .add("compact-card");

        RecurringBill closest = null;

        long smallestDays =
                Long.MAX_VALUE;

        for (RecurringBill bill :
                RecurringBillService
                        .getBills()) {

            long days =
                    ChronoUnit.DAYS
                            .between(
                                    LocalDate.now(),
                                    bill.getNextDueDate()
                            );

            if (days >= 0
                    && days < smallestDays) {

                smallestDays = days;

                closest = bill;
            }
        }

        if (closest == null) {

            Label empty =
                    new Label(
                            "No recurring bills yet."
                    );

            empty.getStyleClass()
                    .add("page-subtitle");

            card.getChildren()
                    .add(empty);

            return card;
        }

        Label name =
                new Label(
                        "▤ "
                        + closest.getName()
                );

        name.getStyleClass()
                .add("section-title");

        Label amount =
                new Label(
                        money(
                                user,
                                closest.getAmount()
                        )
                );

        Label due =
                new Label(
                        smallestDays == 0
                        ?
                        "Due today"
                        :
                        "Due in "
                        + smallestDays
                        + " day(s)"
                );

        if (smallestDays <= 2) {

            due.getStyleClass()
                    .add("error-message");

        } else if (smallestDays <= 5) {

            due.getStyleClass()
                    .add("warning-message");
        }

        card.getChildren()
                .addAll(
                        name,
                        amount,
                        due
                );

        addCardAnimation(card);

        return card;
    }

    // =====================================================
    // CHARTS
    // =====================================================

    private PieChart createDashboardPieChart() {

        PieChart chart =
                new PieChart();

        chart.setTitle(
                "Spending by Category"
        );

        Map<String, Double> totals =
                new LinkedHashMap<>();

        for (Transaction transaction :
                TransactionService
                        .getTransactionsForMonth(
                                selectedMonth
                        )) {

            if (transaction
                    .getType()
                    .equalsIgnoreCase(
                            "Expense"
                    )) {

                totals.merge(
                        transaction.getCategory(),
                        transaction.getAmount(),
                        Double::sum
                );
            }
        }

        for (Map.Entry<String, Double> entry :
                totals.entrySet()) {

            chart.getData()
                    .add(
                            new PieChart.Data(
                                    entry.getKey(),
                                    entry.getValue()
                            )
                    );
        }

        chart.setLegendVisible(true);

        chart.setLabelsVisible(true);

        chart.setAnimated(true);

        return chart;
    }

    private BarChart<String, Number>
    createDashboardBarChart() {

        CategoryAxis xAxis =
                new CategoryAxis();

        NumberAxis yAxis =
                new NumberAxis();

        BarChart<String, Number> chart =
                new BarChart<>(
                        xAxis,
                        yAxis
                );

        chart.setTitle(
                "Income vs Expenses"
        );

        chart.setLegendVisible(false);

        chart.setAnimated(true);

        XYChart.Series<String, Number> series =
                new XYChart.Series<>();

        series.getData()
                .add(
                        new XYChart.Data<>(
                                "Income",
                                TransactionService
                                        .getMonthlyIncome(
                                                selectedMonth
                                        )
                        )
                );

        series.getData()
                .add(
                        new XYChart.Data<>(
                                "Expenses",
                                TransactionService
                                        .getMonthlyExpenses(
                                                selectedMonth
                                        )
                        )
                );

        chart.getData()
                .add(series);

        return chart;
    }

    // =====================================================
    // TRANSACTIONS PAGE
    // =====================================================

    private void showTransactionsPage(
            Stage stage,
            User user) {

        BorderPane root =
                createMainLayout(
                        stage,
                        user,
                        "transactions"
                );

        Label title =
                new Label(
                        "Transactions"
                );

        title.getStyleClass()
                .add("page-title");

        Label subtitle =
                new Label(
                        "Search, edit, delete and export your financial activity."
                );

        subtitle.getStyleClass()
                .add("page-subtitle");

        TextField searchField =
                new TextField();

        searchField.setPromptText(
                "Search category or note..."
        );

        ComboBox<String> typeFilter =
                new ComboBox<>();

        typeFilter.getItems()
                .addAll(
                        "All",
                        "Income",
                        "Expense"
                );

        typeFilter.setValue("All");

        ComboBox<YearMonth> monthBox =
                createMonthPicker();

        monthBox.setValue(
                selectedMonth
        );

        Button addButton =
                new Button(
                        "+ Add Transaction"
                );

        addButton.getStyleClass()
                .add("primary-button");

        Button exportButton =
                new Button(
                        "Export CSV"
                );

        exportButton.getStyleClass()
                .add("secondary-button");

        VBox list =
                new VBox(10);

        Runnable refresh =
                () -> {

                    list.getChildren()
                            .clear();

                    selectedMonth =
                            monthBox.getValue();

                    String keyword =
                            searchField
                                    .getText()
                                    .trim()
                                    .toLowerCase();

                    for (Transaction transaction :
                            TransactionService
                                    .getTransactionsForMonth(
                                            selectedMonth
                                    )) {

                        boolean typeMatches =
                                typeFilter
                                        .getValue()
                                        .equals("All")
                                ||
                                transaction
                                        .getType()
                                        .equalsIgnoreCase(
                                                typeFilter
                                                        .getValue()
                                        );

                        boolean searchMatches =
                                keyword.isBlank()
                                ||
                                transaction
                                        .getCategory()
                                        .toLowerCase()
                                        .contains(keyword)
                                ||
                                transaction
                                        .getNote()
                                        .toLowerCase()
                                        .contains(keyword);

                        if (typeMatches
                                && searchMatches) {

                            list.getChildren()
                                    .add(
                                            createTransactionRow(
                                                    transaction,
                                                    user,
                                                    stage
                                            )
                                    );
                        }
                    }

                    if (list.getChildren()
                            .isEmpty()) {

                        list.getChildren()
                                .add(
                                        new Label(
                                                "No matching transactions."
                                        )
                                );
                    }
                };

        searchField
                .textProperty()
                .addListener(
                        (
                                obs,
                                oldValue,
                                newValue
                        ) ->
                                refresh.run()
                );

        typeFilter.setOnAction(
                e -> refresh.run()
        );

        monthBox.setOnAction(
                e -> refresh.run()
        );

        addButton.setOnAction(
                e -> showTransactionEditor(
                        stage,
                        user,
                        null
                )
        );

        exportButton.setOnAction(e -> {

            try {

                FileChooser chooser =
                        new FileChooser();

                chooser.setInitialFileName(
                        "lumaledger-"
                        + selectedMonth
                        + ".csv"
                );

                File file =
                        chooser.showSaveDialog(
                                stage
                        );

                if (file != null) {

                    CSVExporter
                            .exportTransactions(
                                    file,
                                    TransactionService
                                            .getTransactionsForMonth(
                                                    selectedMonth
                                            )
                            );

                    showInformation(
                            "Export Complete",
                            "Transactions exported successfully."
                    );
                }

            } catch (Exception ex) {

                showInformation(
                        "Export Failed",
                        ex.getMessage()
                );
            }
        });

        refresh.run();

        HBox toolbar =
                new HBox(
                        10,
                        searchField,
                        typeFilter,
                        monthBox,
                        addButton,
                        exportButton
                );

        HBox.setHgrow(
                searchField,
                Priority.ALWAYS
        );

        VBox panel =
                new VBox(
                        16,
                        toolbar,
                        list
                );

        panel.getStyleClass()
                .add("panel");

        VBox content =
                new VBox(
                        20,
                        title,
                        subtitle,
                        panel
                );

        content.setPadding(
                new Insets(28)
        );

        ScrollPane scroll =
                new ScrollPane(
                        content
                );

        scroll.setFitToWidth(true);

        root.setCenter(scroll);

        Scene scene =
                new Scene(
                        root,
                        1400,
                        850
                );

        applyCss(scene);

        stage.setScene(scene);

        stage.setTitle(
                APP_NAME
                + " - Transactions"
        );

        playPageEntrance(
                content
        );
    }

    // =====================================================
    // TRANSACTION ROWS
    // =====================================================

    private HBox createTransactionRow(
            Transaction transaction,
            User user,
            Stage stage) {

        Label icon =
                new Label(
                        categoryIcon(
                                transaction
                                        .getCategory()
                        )
                );

        Label category =
                new Label(
                        transaction
                                .getCategory()
                );

        category.getStyleClass()
                .add("transaction-title");

        Label details =
                new Label(
                        transaction.getDate()
                        + " • "
                        +
                        (
                                transaction
                                        .getNote()
                                        .isBlank()
                                ?
                                "No note"
                                :
                                transaction.getNote()
                        )
                );

        details.getStyleClass()
                .add("transaction-meta");

        VBox info =
                new VBox(
                        3,
                        category,
                        details
                );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        String sign =
                transaction
                        .getType()
                        .equalsIgnoreCase(
                                "Income"
                        )
                ?
                "+"
                :
                "-";

        Label amount =
                new Label(
                        sign
                        + money(
                                user,
                                transaction.getAmount()
                        )
                );

        amount.getStyleClass()
                .add(
                        transaction
                                .getType()
                                .equalsIgnoreCase(
                                        "Income"
                                )
                        ?
                        "income-amount"
                        :
                        "expense-amount"
                );

        Button edit =
                new Button("Edit");

        edit.getStyleClass()
                .add("secondary-button");

        Button delete =
                new Button("Delete");

        delete.getStyleClass()
                .add("danger-button");

        edit.setOnAction(
                e -> showTransactionEditor(
                        stage,
                        user,
                        transaction
                )
        );

        delete.setOnAction(e -> {

            if (confirmAction(
                    "Delete Transaction?",
                    "This transaction will be permanently removed."
            )) {

                TransactionService
                        .deleteTransaction(
                                transaction.getId()
                        );

                showTransactionsPage(
                        stage,
                        user
                );
            }
        });

        HBox row =
                new HBox(
                        12,
                        icon,
                        info,
                        spacer,
                        amount,
                        edit,
                        delete
                );

        row.setAlignment(
                Pos.CENTER_LEFT
        );

        row.getStyleClass()
                .add("transaction-row");

        addRowAnimation(row);

        return row;
    }

    private HBox createDashboardTransactionRow(
            Transaction transaction,
            User user) {

        Label icon =
                new Label(
                        categoryIcon(
                                transaction
                                        .getCategory()
                        )
                );

        Label category =
                new Label(
                        transaction
                                .getCategory()
                );

        category.getStyleClass()
                .add("transaction-title");

        Label details =
                new Label(
                        transaction.getDate()
                        + " • "
                        +
                        (
                                transaction
                                        .getNote()
                                        .isBlank()
                                ?
                                "No note"
                                :
                                transaction.getNote()
                        )
                );

        details.getStyleClass()
                .add("transaction-meta");

        VBox info =
                new VBox(
                        3,
                        category,
                        details
                );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        String sign =
                transaction
                        .getType()
                        .equalsIgnoreCase(
                                "Income"
                        )
                ?
                "+"
                :
                "-";

        Label amount =
                new Label(
                        sign
                        + money(
                                user,
                                transaction.getAmount()
                        )
                );

        amount.getStyleClass()
                .add(
                        transaction
                                .getType()
                                .equalsIgnoreCase(
                                        "Income"
                                )
                        ?
                        "income-amount"
                        :
                        "expense-amount"
                );

        HBox row =
                new HBox(
                        12,
                        icon,
                        info,
                        spacer,
                        amount
                );

        row.setAlignment(
                Pos.CENTER_LEFT
        );

        row.getStyleClass()
                .add("transaction-row");

        addRowAnimation(row);

        return row;
    }

    // =====================================================
    // TRANSACTION EDITOR
    // =====================================================

    private void showTransactionEditor(
            Stage owner,
            User user,
            Transaction existing) {

        Stage window =
                new Stage();

        window.initOwner(owner);

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        Label title =
                new Label(
                        existing == null
                        ?
                        "Add Transaction"
                        :
                        "Edit Transaction"
                );

        title.getStyleClass()
                .add("page-title");

        ComboBox<String> typeBox =
                new ComboBox<>();

        typeBox.getItems()
                .addAll(
                        "Income",
                        "Expense"
                );

        typeBox.setPromptText(
                "Transaction type"
        );

        typeBox.setMaxWidth(
                Double.MAX_VALUE
        );

        ComboBox<String> categoryBox =
                new ComboBox<>();

        categoryBox.setPromptText(
                "Category"
        );

        categoryBox.setMaxWidth(
                Double.MAX_VALUE
        );

        Runnable updateCategories =
                () -> {

                    categoryBox
                            .getItems()
                            .clear();

                    if ("Income".equals(
                            typeBox.getValue())) {

                        categoryBox
                                .getItems()
                                .addAll(
                                        "Salary",
                                        "Freelance",
                                        "Business",
                                        "Bonus",
                                        "Investment",
                                        "Other"
                                );

                    } else {

                        categoryBox
                                .getItems()
                                .addAll(
                                        "Food",
                                        "Rent",
                                        "Shopping",
                                        "Transport",
                                        "Bills",
                                        "Entertainment",
                                        "Health",
                                        "Education",
                                        "Other"
                                );
                    }
                };

        typeBox.setOnAction(
                e -> updateCategories.run()
        );

        TextField amountField =
                new TextField();

        amountField.setPromptText(
                "Amount"
        );

        TextField noteField =
                new TextField();

        noteField.setPromptText(
                "Optional note"
        );

        DatePicker datePicker =
                new DatePicker(
                        LocalDate.now()
                );

        datePicker.setMaxWidth(
                Double.MAX_VALUE
        );

        if (existing != null) {

            typeBox.setValue(
                    existing.getType()
            );

            updateCategories.run();

            categoryBox.setValue(
                    existing.getCategory()
            );

            amountField.setText(
                    String.valueOf(
                            existing.getAmount()
                    )
            );

            noteField.setText(
                    existing.getNote()
            );

            datePicker.setValue(
                    existing.getDate()
            );
        }

        Button save =
                new Button(
                        existing == null
                        ?
                        "Save Transaction"
                        :
                        "Save Changes"
                );

        save.setMaxWidth(
                Double.MAX_VALUE
        );

        save.getStyleClass()
                .add("primary-button");

        addButtonAnimation(save);

        Label message =
                new Label();

        save.setOnAction(e -> {

            if (typeBox.getValue() == null
                    || categoryBox.getValue() == null
                    || amountField
                            .getText()
                            .isBlank()
                    || datePicker.getValue() == null) {

                showError(
                        message,
                        "Complete all required fields."
                );

                return;
            }

            try {

                double amount =
                        Double.parseDouble(
                                amountField
                                        .getText()
                                        .trim()
                        );

                if (amount <= 0) {

                    throw new NumberFormatException();
                }

                if (existing == null) {

                    TransactionService
                            .addTransaction(
                                    new Transaction(
                                            typeBox.getValue(),
                                            categoryBox.getValue(),
                                            amount,
                                            noteField
                                                    .getText()
                                                    .trim(),
                                            datePicker
                                                    .getValue()
                                    )
                            );

                } else {

                    TransactionService
                            .updateTransaction(
                                    existing.getId(),
                                    typeBox.getValue(),
                                    categoryBox.getValue(),
                                    amount,
                                    noteField
                                            .getText()
                                            .trim(),
                                    datePicker
                                            .getValue()
                            );
                }

                window.close();

                showTransactionsPage(
                        owner,
                        user
                );

            } catch (NumberFormatException ex) {

                showError(
                        message,
                        "Enter a valid positive amount."
                );
            }
        });

        VBox form =
                new VBox(
                        15,
                        title,
                        typeBox,
                        categoryBox,
                        amountField,
                        noteField,
                        datePicker,
                        save,
                        message
                );

        form.setPadding(
                new Insets(30)
        );

        Scene scene =
                new Scene(
                        form,
                        500,
                        560
                );

        applyCss(scene);

        window.setScene(scene);

        window.setTitle(APP_NAME);

        window.show();

        playPageEntrance(form);
    }

    // =====================================================
    // BUDGETS
    // =====================================================

    private void showBudgetsPage(
            Stage stage,
            User user) {

        BorderPane root =
                createMainLayout(
                        stage,
                        user,
                        "budgets"
                );

        Label title =
                new Label("Budgets");

        title.getStyleClass()
                .add("page-title");

        Label subtitle =
                new Label(
                        "Build limits before spending gets out of control."
                );

        subtitle.getStyleClass()
                .add("page-subtitle");

        ComboBox<String> categoryBox =
                new ComboBox<>();

        categoryBox.getItems()
                .addAll(
                        "Food",
                        "Rent",
                        "Shopping",
                        "Transport",
                        "Bills",
                        "Entertainment",
                        "Health",
                        "Education",
                        "Other"
                );

        categoryBox.setPromptText(
                "Category"
        );

        TextField limitField =
                new TextField();

        limitField.setPromptText(
                "Monthly limit"
        );

        Button save =
                new Button(
                        "Save Budget"
                );

        save.getStyleClass()
                .add("primary-button");

        Label message =
                new Label();

        VBox list =
                new VBox(14);

        Runnable[] refresh =
                new Runnable[1];

        refresh[0] = () -> {

            list.getChildren()
                    .clear();

            for (Budget budget :
                    BudgetService
                            .getBudgets()) {

                double spent =
                        BudgetService
                                .getSpentForCategory(
                                        budget.getCategory(),
                                        selectedMonth
                                );

                double remaining =
                        budget.getLimit()
                        - spent;

                double percentage =
                        budget.getLimit() <= 0
                        ?
                        0
                        :
                        spent
                        / budget.getLimit();

                Label name =
                        new Label(
                                categoryIcon(
                                        budget.getCategory()
                                )
                                + " "
                                + budget.getCategory()
                        );

                name.getStyleClass()
                        .add("section-title");

                Label stats =
                        new Label(
                                money(
                                        user,
                                        spent
                                )
                                + " spent of "
                                + money(
                                        user,
                                        budget.getLimit()
                                )
                        );

                ProgressBar progress =
                        new ProgressBar(0);

                progress.setMaxWidth(
                        Double.MAX_VALUE
                );

                if (percentage >= 1) {

                    progress.getStyleClass()
                            .add("danger-progress");

                } else if (percentage >= 0.8) {

                    progress.getStyleClass()
                            .add("warning-progress");

                } else {

                    progress.getStyleClass()
                            .add("success-progress");
                }

                animateProgress(
                        progress,
                        Math.min(
                                1,
                                percentage
                        )
                );

                Label status =
                        new Label();

                if (percentage >= 1) {

                    double over =
                            Math.max(
                                    0,
                                    spent
                                    - budget.getLimit()
                            );

                    if (over > 0) {

                        status.setText(
                                "⚠ Over budget by "
                                + money(
                                        user,
                                        over
                                )
                        );

                    } else {

                        status.setText(
                                "⚠ Limit reached"
                        );
                    }

                    status.getStyleClass()
                            .add("error-message");

                } else if (percentage >= 0.8) {

                    status.setText(
                            "Getting close to your limit"
                    );

                    status.getStyleClass()
                            .add("warning-message");

                } else {

                    status.setText(
                            money(
                                    user,
                                    remaining
                            )
                            + " remaining"
                    );

                    status.getStyleClass()
                            .add("success-message");
                }

                Button delete =
                        new Button(
                                "Delete"
                        );

                delete.getStyleClass()
                        .add("danger-button");

                delete.setOnAction(e -> {

                    BudgetService
                            .deleteBudget(
                                    budget.getCategory()
                            );

                    refresh[0].run();
                });

                VBox card =
                        new VBox(
                                10,
                                name,
                                stats,
                                progress,
                                status,
                                delete
                        );

                card.getStyleClass()
                        .add("budget-card");

                addCardAnimation(card);

                list.getChildren()
                        .add(card);
            }

            if (list.getChildren()
                    .isEmpty()) {

                list.getChildren()
                        .add(
                                new Label(
                                        "No budgets yet."
                                )
                        );
            }
        };

        save.setOnAction(e -> {

            try {

                if (categoryBox.getValue() == null) {

                    throw new NumberFormatException();
                }

                double amount =
                        Double.parseDouble(
                                limitField
                                        .getText()
                                        .trim()
                        );

                if (amount <= 0) {

                    throw new NumberFormatException();
                }

                BudgetService
                        .setBudget(
                                categoryBox.getValue(),
                                amount
                        );

                showSuccess(
                        message,
                        "Budget saved."
                );

                categoryBox.setValue(null);

                limitField.clear();

                refresh[0].run();

            } catch (NumberFormatException ex) {

                showError(
                        message,
                        "Choose a category and enter a positive amount."
                );
            }
        });

        refresh[0].run();

        HBox form =
                new HBox(
                        10,
                        categoryBox,
                        limitField,
                        save
                );

        HBox.setHgrow(
                limitField,
                Priority.ALWAYS
        );

        VBox formPanel =
                new VBox(
                        10,
                        new Label(
                                "Create or Update Budget"
                        ),
                        form,
                        message
                );

        formPanel.getStyleClass()
                .add("panel");

        VBox content =
                new VBox(
                        20,
                        title,
                        subtitle,
                        formPanel,
                        list
                );

        content.setPadding(
                new Insets(28)
        );

        ScrollPane scroll =
                new ScrollPane(
                        content
                );

        scroll.setFitToWidth(true);

        root.setCenter(scroll);

        Scene scene =
                new Scene(
                        root,
                        1400,
                        850
                );

        applyCss(scene);

        stage.setScene(scene);

        stage.setTitle(
                APP_NAME
                + " - Budgets"
        );

        playPageEntrance(content);
    }

    // =====================================================
    // SAVINGS GOALS
    // =====================================================

    private void showSavingsGoalsPage(
            Stage stage,
            User user) {

        BorderPane root =
                createMainLayout(
                        stage,
                        user,
                        "goals"
                );

        Label title =
                new Label(
                        "Savings Goals"
                );

        title.getStyleClass()
                .add("page-title");

        Label subtitle =
                new Label(
                        "Turn future plans into visible progress."
                );

        subtitle.getStyleClass()
                .add("page-subtitle");

        TextField nameField =
                new TextField();

        nameField.setPromptText(
                "Goal name"
        );

        TextField targetField =
                new TextField();

        targetField.setPromptText(
                "Target amount"
        );

        Button create =
                new Button(
                        "Create Goal"
                );

        create.getStyleClass()
                .add("primary-button");

        Label message =
                new Label();

        VBox list =
                new VBox(14);

        Runnable[] refresh =
                new Runnable[1];

        refresh[0] = () -> {

            list.getChildren()
                    .clear();

            for (SavingsGoal goal :
                    SavingsGoalService
                            .getGoals()) {

                Label name =
                        new Label(
                                (
                                        goal.isCompleted()
                                        ?
                                        "✓ "
                                        :
                                        "◎ "
                                )
                                + goal.getName()
                        );

                name.getStyleClass()
                        .add("section-title");

                Label stats =
                        new Label(
                                money(
                                        user,
                                        goal.getSavedAmount()
                                )
                                + " of "
                                + money(
                                        user,
                                        goal.getTargetAmount()
                                )
                        );

                ProgressBar progress =
                        new ProgressBar(0);

                progress.setMaxWidth(
                        Double.MAX_VALUE
                );

                progress.getStyleClass()
                        .add("savings-progress");

                double actual =
                        Math.min(
                                1,
                                goal.getProgress()
                        );

                double visual =
                        actual > 0
                        ?
                        Math.max(
                                actual,
                                0.06
                        )
                        :
                        0;

                animateProgress(
                        progress,
                        visual
                );

                Label percentage =
                        new Label(
                                String.format(
                                        "%.0f%% complete",
                                        goal
                                                .getProgressPercentage()
                                )
                        );

                TextField amountField =
                        new TextField();

                amountField.setPromptText(
                        "Add money"
                );

                Button add =
                        new Button("Add");

                add.getStyleClass()
                        .add("secondary-button");

                Button change =
                        new Button(
                                "Change Target"
                        );

                change.getStyleClass()
                        .add("secondary-button");

                Button delete =
                        new Button(
                                "Delete"
                        );

                delete.getStyleClass()
                        .add("danger-button");

                add.setOnAction(e -> {

                    try {

                        double amount =
                                Double.parseDouble(
                                        amountField
                                                .getText()
                                                .trim()
                                );

                        if (amount <= 0) {

                            throw new NumberFormatException();
                        }

                        SavingsGoalService
                                .addMoneyToGoal(
                                        goal.getName(),
                                        amount
                                );

                        refresh[0].run();

                    } catch (NumberFormatException ex) {

                        showInformation(
                                "Invalid Amount",
                                "Enter a positive number."
                        );
                    }
                });

                change.setOnAction(e -> {

                    TextInputDialog dialog =
                            new TextInputDialog(
                                    String.valueOf(
                                            goal
                                                    .getTargetAmount()
                                    )
                            );

                    dialog.setTitle(
                            "Change Target"
                    );

                    dialog.setHeaderText(
                            "New target for "
                            + goal.getName()
                    );

                    dialog.showAndWait()
                            .ifPresent(
                                    value -> {

                                        try {

                                            double amount =
                                                    Double.parseDouble(
                                                            value
                                                    );

                                            if (amount <= 0) {

                                                throw new NumberFormatException();
                                            }

                                            SavingsGoalService
                                                    .updateTarget(
                                                            goal.getName(),
                                                            amount
                                                    );

                                            refresh[0].run();

                                        } catch (
                                                NumberFormatException ex) {

                                            showInformation(
                                                    "Invalid Amount",
                                                    "Enter a positive number."
                                            );
                                        }
                                    }
                            );
                });

                delete.setOnAction(e -> {

                    if (confirmAction(
                            "Delete Goal?",
                            goal.getName()
                    )) {

                        SavingsGoalService
                                .deleteGoal(
                                        goal.getName()
                                );

                        refresh[0].run();
                    }
                });

                HBox actions =
                        new HBox(
                                8,
                                amountField,
                                add,
                                change,
                                delete
                        );

                HBox.setHgrow(
                        amountField,
                        Priority.ALWAYS
                );

                VBox card =
                        new VBox(
                                10,
                                name,
                                stats,
                                progress,
                                percentage,
                                actions
                        );

                card.getStyleClass()
                        .add("goal-card");

                addCardAnimation(card);

                list.getChildren()
                        .add(card);
            }

            if (list.getChildren()
                    .isEmpty()) {

                list.getChildren()
                        .add(
                                new Label(
                                        "No savings goals yet."
                                )
                        );
            }
        };

        create.setOnAction(e -> {

            try {

                String name =
                        nameField
                                .getText()
                                .trim();

                double target =
                        Double.parseDouble(
                                targetField
                                        .getText()
                                        .trim()
                        );

                if (name.isBlank()
                        || target <= 0) {

                    throw new NumberFormatException();
                }

                boolean created =
                        SavingsGoalService
                                .addGoal(
                                        name,
                                        target
                                );

                if (!created) {

                    showError(
                            message,
                            "A goal with this name already exists."
                    );

                    return;
                }

                showSuccess(
                        message,
                        "Goal created."
                );

                nameField.clear();

                targetField.clear();

                refresh[0].run();

            } catch (NumberFormatException ex) {

                showError(
                        message,
                        "Enter a goal name and positive target."
                );
            }
        });

        refresh[0].run();

        HBox form =
                new HBox(
                        10,
                        nameField,
                        targetField,
                        create
                );

        HBox.setHgrow(
                nameField,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                targetField,
                Priority.ALWAYS
        );

        VBox formPanel =
                new VBox(
                        10,
                        form,
                        message
                );

        formPanel.getStyleClass()
                .add("panel");

        VBox content =
                new VBox(
                        20,
                        title,
                        subtitle,
                        formPanel,
                        list
                );

        content.setPadding(
                new Insets(28)
        );

        ScrollPane scroll =
                new ScrollPane(
                        content
                );

        scroll.setFitToWidth(true);

        root.setCenter(scroll);

        Scene scene =
                new Scene(
                        root,
                        1400,
                        850
                );

        applyCss(scene);

        stage.setScene(scene);

        stage.setTitle(
                APP_NAME
                + " - Savings Goals"
        );

        playPageEntrance(content);
    }

    // =====================================================
    // BILLS
    // =====================================================

    private void showBillsPage(
            Stage stage,
            User user) {

        BorderPane root =
                createMainLayout(
                        stage,
                        user,
                        "bills"
                );

        Label title =
                new Label(
                        "Recurring Bills"
                );

        title.getStyleClass()
                .add("page-title");

        Label subtitle =
                new Label(
                        "Stay ahead of payments that repeat every month."
                );

        subtitle.getStyleClass()
                .add("page-subtitle");

        TextField nameField =
                new TextField();

        nameField.setPromptText(
                "Bill name"
        );

        TextField amountField =
                new TextField();

        amountField.setPromptText(
                "Amount"
        );

        TextField dueField =
                new TextField();

        dueField.setPromptText(
                "Due day 1-31"
        );

        ComboBox<String> categoryBox =
                new ComboBox<>();

        categoryBox.getItems()
                .addAll(
                        "Rent",
                        "Bills",
                        "Health",
                        "Education",
                        "Entertainment",
                        "Other"
                );

        categoryBox.setPromptText(
                "Category"
        );

        Button add =
                new Button(
                        "Add Bill"
                );

        add.getStyleClass()
                .add("primary-button");

        Label message =
                new Label();

        VBox list =
                new VBox(12);

        Runnable[] refresh =
                new Runnable[1];

        refresh[0] = () -> {

            list.getChildren()
                    .clear();

            for (RecurringBill bill :
                    RecurringBillService
                            .getBills()) {

                long days =
                        ChronoUnit.DAYS
                                .between(
                                        LocalDate.now(),
                                        bill.getNextDueDate()
                                );

                Label info =
                        new Label(
                                bill.getName()
                                + " • "
                                + money(
                                        user,
                                        bill.getAmount()
                                )
                                + " • "
                                +
                                (
                                        days == 0
                                        ?
                                        "Due today"
                                        :
                                        "Due in "
                                        + days
                                        + " day(s)"
                                )
                        );

                Region spacer =
                        new Region();

                HBox.setHgrow(
                        spacer,
                        Priority.ALWAYS
                );

                Button delete =
                        new Button(
                                "Delete"
                        );

                delete.getStyleClass()
                        .add("danger-button");

                delete.setOnAction(e -> {

                    RecurringBillService
                            .deleteBill(
                                    bill.getName()
                            );

                    refresh[0].run();
                });

                HBox row =
                        new HBox(
                                12,
                                new Label("▤"),
                                info,
                                spacer,
                                delete
                        );

                row.setAlignment(
                        Pos.CENTER_LEFT
                );

                row.getStyleClass()
                        .add("transaction-row");

                addRowAnimation(row);

                list.getChildren()
                        .add(row);
            }

            if (list.getChildren()
                    .isEmpty()) {

                list.getChildren()
                        .add(
                                new Label(
                                        "No recurring bills yet."
                                )
                        );
            }
        };

        add.setOnAction(e -> {

            try {

                String name =
                        nameField
                                .getText()
                                .trim();

                double amount =
                        Double.parseDouble(
                                amountField
                                        .getText()
                                        .trim()
                        );

                int day =
                        Integer.parseInt(
                                dueField
                                        .getText()
                                        .trim()
                        );

                if (name.isBlank()
                        || amount <= 0
                        || day < 1
                        || day > 31
                        || categoryBox
                                .getValue()
                                == null) {

                    throw new NumberFormatException();
                }

                RecurringBillService
                        .addBill(
                                name,
                                amount,
                                day,
                                categoryBox.getValue()
                        );

                showSuccess(
                        message,
                        "Bill added."
                );

                nameField.clear();

                amountField.clear();

                dueField.clear();

                categoryBox.setValue(null);

                refresh[0].run();

            } catch (NumberFormatException ex) {

                showError(
                        message,
                        "Enter valid bill details and due day 1-31."
                );
            }
        });

        refresh[0].run();

        HBox form =
                new HBox(
                        8,
                        nameField,
                        amountField,
                        dueField,
                        categoryBox,
                        add
                );

        HBox.setHgrow(
                nameField,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                amountField,
                Priority.ALWAYS
        );

        VBox panel =
                new VBox(
                        10,
                        form,
                        message
                );

        panel.getStyleClass()
                .add("panel");

        VBox content =
                new VBox(
                        20,
                        title,
                        subtitle,
                        panel,
                        list
                );

        content.setPadding(
                new Insets(28)
        );

        ScrollPane scroll =
                new ScrollPane(
                        content
                );

        scroll.setFitToWidth(true);

        root.setCenter(scroll);

        Scene scene =
                new Scene(
                        root,
                        1400,
                        850
                );

        applyCss(scene);

        stage.setScene(scene);

        stage.setTitle(
                APP_NAME
                + " - Bills"
        );

        playPageEntrance(content);
    }

    // =====================================================
    // REPORTS
    // =====================================================

    private void showReportsPage(
            Stage stage,
            User user) {

        BorderPane root =
                createMainLayout(
                        stage,
                        user,
                        "reports"
                );

        Label title =
                new Label(
                        "Financial Reports"
                );

        title.getStyleClass()
                .add("page-title");

        Label subtitle =
                new Label(
                        "See the patterns behind your money."
                );

        subtitle.getStyleClass()
                .add("page-subtitle");

        ComboBox<YearMonth> monthBox =
                createMonthPicker();

        monthBox.setValue(
                selectedMonth
        );

        monthBox.setOnAction(e -> {

            selectedMonth =
                    monthBox.getValue();

            showReportsPage(
                    stage,
                    user
            );
        });

        double income =
                TransactionService
                        .getMonthlyIncome(
                                selectedMonth
                        );

        double expenses =
                TransactionService
                        .getMonthlyExpenses(
                                selectedMonth
                        );

        double savingRate =
                income <= 0
                ?
                0
                :
                (
                        income
                        - expenses
                )
                / income
                * 100;

        Label summary =
                new Label(
                        "Top expense: "
                        + TransactionService
                                .getTopExpenseCategory(
                                        selectedMonth
                                )
                        + "    •    Savings rate: "
                        + String.format(
                                "%.1f%%",
                                savingRate
                        )
                        + "    •    Health score: "
                        + calculateHealthScore()
                        + "/100"
                );

        summary.getStyleClass()
                .add("section-title");

        VBox summaryPanel =
                new VBox(
                        10,
                        new Label(
                                "Monthly Summary"
                        ),
                        summary
                );

        summaryPanel.getStyleClass()
                .add("glass-card");

        PieChart pie =
                createDashboardPieChart();

        BarChart<String, Number> bar =
                createDashboardBarChart();

        VBox piePanel =
                new VBox(pie);

        piePanel.getStyleClass()
                .add("panel");

        VBox barPanel =
                new VBox(bar);

        barPanel.getStyleClass()
                .add("panel");

        HBox charts =
                new HBox(
                        16,
                        piePanel,
                        barPanel
                );

        HBox.setHgrow(
                piePanel,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                barPanel,
                Priority.ALWAYS
        );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        HBox heading =
                new HBox(
                        20,
                        new VBox(
                                3,
                                title,
                                subtitle
                        ),
                        spacer,
                        monthBox
                );

        VBox content =
                new VBox(
                        20,
                        heading,
                        summaryPanel,
                        charts
                );

        content.setPadding(
                new Insets(28)
        );

        ScrollPane scroll =
                new ScrollPane(content);

        scroll.setFitToWidth(true);

        root.setCenter(scroll);

        Scene scene =
                new Scene(
                        root,
                        1400,
                        850
                );

        applyCss(scene);

        stage.setScene(scene);

        stage.setTitle(
                APP_NAME
                + " - Reports"
        );

        playPageEntrance(content);

        animateChart(pie);

        animateChart(bar);
    }

    // =====================================================
    // PROFILE
    // =====================================================

    private void showProfilePage(
            Stage stage,
            User user) {

        BorderPane root =
                createMainLayout(
                        stage,
                        user,
                        "profile"
                );

        Label title =
                new Label(
                        "Your Profile"
                );

        title.getStyleClass()
                .add("page-title");

        Label subtitle =
                new Label(
                        "Personalize your LumaLedger experience."
                );

        subtitle.getStyleClass()
                .add("page-subtitle");

        Label avatar =
                new Label(
                        getInitials(
                                user.getName()
                        )
                );

        avatar.setMinSize(
                82,
                82
        );

        avatar.setPrefSize(
                82,
                82
        );

        avatar.getStyleClass()
                .add("avatar");

        TextField nameField =
                new TextField(
                        user.getName()
                );

        Label email =
                new Label(
                        user.getEmail()
                );

        ComboBox<String> currency =
                new ComboBox<>();

        currency.getItems()
                .addAll(
                        "USD",
                        "PKR",
                        "EUR",
                        "GBP"
                );

        currency.setValue(
                user.getCurrency()
        );

        Button save =
                new Button(
                        "Save Profile"
                );

        save.getStyleClass()
                .add("primary-button");

        Label message =
                new Label();

        save.setOnAction(e -> {

            String name =
                    nameField
                            .getText()
                            .trim();

            if (name.isBlank()) {

                showError(
                        message,
                        "Name cannot be empty."
                );

                return;
            }

            AuthService
                    .updateProfile(
                            user,
                            name,
                            currency.getValue()
                    );

            showSuccess(
                    message,
                    "Profile updated."
            );
        });

        VBox profilePanel =
                new VBox(
                        14,
                        avatar,
                        new Label("Full Name"),
                        nameField,
                        new Label("Email"),
                        email,
                        new Label(
                                "Preferred Currency"
                        ),
                        currency,
                        save,
                        message
                );

        profilePanel.getStyleClass()
                .add("panel");

        PasswordField current =
                new PasswordField();

        current.setPromptText(
                "Current password"
        );

        PasswordField next =
                new PasswordField();

        next.setPromptText(
                "New password"
        );

        Button changePassword =
                new Button(
                        "Change Password"
                );

        changePassword.getStyleClass()
                .add("secondary-button");

        Label passwordMessage =
                new Label();

        changePassword.setOnAction(e -> {

            if (next.getText()
                    .length()
                    < 6) {

                showError(
                        passwordMessage,
                        "New password needs at least 6 characters."
                );

                return;
            }

            boolean changed =
                    AuthService
                            .changePassword(
                                    user,
                                    current.getText(),
                                    next.getText()
                            );

            if (changed) {

                showSuccess(
                        passwordMessage,
                        "Password changed successfully."
                );

                current.clear();

                next.clear();

            } else {

                showError(
                        passwordMessage,
                        "Current password is incorrect."
                );
            }
        });

        VBox securityPanel =
                new VBox(
                        14,
                        new Label(
                                "Account Security"
                        ),
                        current,
                        next,
                        changePassword,
                        passwordMessage
                );

        securityPanel.getStyleClass()
                .add("panel");

        ImageView aboutLogo =
                createLogo(270);

        Label aboutTitle =
                new Label(
                        "About LumaLedger"
                );

        aboutTitle.getStyleClass()
                .add("section-title");

        Label developer =
                new Label(
                        "Designed & Developed by "
                        + DEVELOPER
                );

        developer.setStyle(
                "-fx-font-weight: bold;"
                + "-fx-font-size: 13px;"
        );

        Label aboutText =
                new Label(
                        "A JavaFX personal finance application for tracking income, "
                        + "expenses, budgets, savings goals, recurring bills and financial reports."
                );

        aboutText.setWrapText(true);

        aboutText.getStyleClass()
                .add("page-subtitle");

        VBox aboutPanel =
                new VBox(
                        12,
                        aboutLogo,
                        aboutTitle,
                        developer,
                        aboutText
                );

        aboutPanel.getStyleClass()
                .add("panel");

        VBox content =
                new VBox(
                        20,
                        title,
                        subtitle,
                        profilePanel,
                        securityPanel,
                        aboutPanel
                );

        content.setPadding(
                new Insets(28)
        );

        ScrollPane scroll =
                new ScrollPane(content);

        scroll.setFitToWidth(true);

        root.setCenter(scroll);

        Scene scene =
                new Scene(
                        root,
                        1400,
                        850
                );

        applyCss(scene);

        stage.setScene(scene);

        stage.setTitle(
                APP_NAME
                + " - Profile"
        );

        playPageEntrance(content);
    }

    // =====================================================
    // NOTIFICATIONS
    // =====================================================

    private void showNotificationWindow(
            Stage owner,
            User user) {

        Stage window =
                new Stage();

        window.initOwner(owner);

        window.initModality(
                Modality.APPLICATION_MODAL
        );

        Label title =
                new Label(
                        "Notifications"
                );

        title.getStyleClass()
                .add("page-title");

        VBox list =
                new VBox(10);

        boolean found = false;

        for (Budget budget :
                BudgetService
                        .getBudgets()) {

            double spent =
                    BudgetService
                            .getSpentForCategory(
                                    budget.getCategory(),
                                    selectedMonth
                            );

            if (budget.getLimit() > 0
                    &&
                    spent
                    / budget.getLimit()
                    >= 0.9) {

                Label message =
                        new Label(
                                "⚠ "
                                + budget.getCategory()
                                + " budget is almost used.\n"
                                + money(
                                        user,
                                        spent
                                )
                                + " / "
                                + money(
                                        user,
                                        budget.getLimit()
                                )
                        );

                message.setMaxWidth(
                        Double.MAX_VALUE
                );

                message.getStyleClass()
                        .add("notification-row");

                list.getChildren()
                        .add(message);

                found = true;
            }
        }

        for (RecurringBill bill :
                RecurringBillService
                        .getBills()) {

            long days =
                    ChronoUnit.DAYS
                            .between(
                                    LocalDate.now(),
                                    bill.getNextDueDate()
                            );

            if (days >= 0
                    && days <= 5) {

                Label message =
                        new Label(
                                "▤ "
                                + bill.getName()
                                + "\n"
                                +
                                (
                                        days == 0
                                        ?
                                        "Due today"
                                        :
                                        "Due in "
                                        + days
                                        + " day(s)"
                                )
                                + " • "
                                + money(
                                        user,
                                        bill.getAmount()
                                )
                        );

                message.setMaxWidth(
                        Double.MAX_VALUE
                );

                message.getStyleClass()
                        .add("notification-row");

                list.getChildren()
                        .add(message);

                found = true;
            }
        }

        if (!found) {

            Label empty =
                    new Label(
                            "✓ You have no urgent financial alerts."
                    );

            empty.getStyleClass()
                    .add("success-message");

            list.getChildren()
                    .add(empty);
        }

        Button close =
                new Button("Close");

        close.getStyleClass()
                .add("primary-button");

        close.setOnAction(
                e -> window.close()
        );

        VBox root =
                new VBox(
                        16,
                        title,
                        list,
                        close
                );

        root.getStyleClass()
                .add("notification-panel");

        Scene scene =
                new Scene(
                        root,
                        470,
                        500
                );

        applyCss(scene);

        window.setScene(scene);

        window.setTitle(
                APP_NAME
                + " - Notifications"
        );

        window.show();

        playPageEntrance(root);
    }

    // =====================================================
    // MONTH PICKER
    // =====================================================

    private ComboBox<YearMonth>
    createMonthPicker() {

        ComboBox<YearMonth> box =
                new ComboBox<>();

        YearMonth now =
                YearMonth.now();

        for (int i = 0;
             i < 12;
             i++) {

            box.getItems()
                    .add(
                            now.minusMonths(i)
                    );
        }

        box.setConverter(
                new StringConverter<YearMonth>() {

                    @Override
                    public String toString(
                            YearMonth month) {

                        if (month == null) {

                            return "";
                        }

                        return formatMonth(
                                month
                        );
                    }

                    @Override
                    public YearMonth fromString(
                            String text) {

                        return null;
                    }
                }
        );

        return box;
    }

    private String formatMonth(
            YearMonth month) {

        return month.format(
                DateTimeFormatter
                        .ofPattern(
                                "MMMM yyyy"
                        )
        );
    }

    // =====================================================
    // MENU
    // =====================================================

    private Button createMenuButton(
            String text,
            String page,
            String activePage) {

        Button button =
                new Button(text);

        button.setMaxWidth(
                Double.MAX_VALUE
        );

        button.setAlignment(
                Pos.CENTER_LEFT
        );

        button.getStyleClass()
                .add("sidebar-button");

        if (page.equals(
                activePage)) {

            button.getStyleClass()
                    .add(
                            "sidebar-button-active"
                    );
        }

        addButtonAnimation(button);

        return button;
    }

    // =====================================================
    // VALUE LABEL
    // =====================================================

    private Label createValueLabel(
            String styleClass) {

        Label label =
                new Label();

        label.getStyleClass()
                .add(styleClass);

        return label;
    }

    // =====================================================
    // MONEY
    // =====================================================

    private String money(
            User user,
            double amount) {

        String prefix;

        switch (user.getCurrency()) {

            case "PKR":
                prefix = "Rs ";
                break;

            case "EUR":
                prefix = "€";
                break;

            case "GBP":
                prefix = "£";
                break;

            default:
                prefix = "$";
                break;
        }

        return prefix
                + String.format(
                        "%,.2f",
                        amount
                );
    }

    // =====================================================
    // GREETING
    // =====================================================

    private String getGreeting() {

        int hour =
                LocalTime.now()
                        .getHour();

        if (hour < 12) {

            return "Good morning";
        }

        if (hour < 17) {

            return "Good afternoon";
        }

        return "Good evening";
    }

    private String getFirstName(
            String name) {

        if (name == null
                || name.isBlank()) {

            return "there";
        }

        return name.trim()
                .split("\\s+")[0];
    }

    private String getInitials(
            String name) {

        if (name == null
                || name.isBlank()) {

            return "U";
        }

        String[] parts =
                name.trim()
                        .split("\\s+");

        if (parts.length == 1) {

            return parts[0]
                    .substring(0, 1)
                    .toUpperCase();
        }

        return (
                parts[0]
                        .substring(0, 1)
                +
                parts[
                        parts.length - 1
                ]
                        .substring(0, 1)
        )
                .toUpperCase();
    }

    // =====================================================
    // CATEGORY ICON
    // =====================================================

    private String categoryIcon(
            String category) {

        if (category == null) {

            return "•";
        }

        switch (category.toLowerCase()) {

            case "food":
                return "🍔";

            case "transport":
                return "🚗";

            case "rent":
                return "🏠";

            case "shopping":
                return "🛍";

            case "bills":
                return "🧾";

            case "entertainment":
                return "🎬";

            case "health":
                return "🏥";

            case "education":
                return "📚";

            case "salary":
                return "💼";

            case "freelance":
                return "💻";

            case "business":
                return "🏢";

            case "bonus":
                return "🎁";

            case "investment":
                return "📈";

            default:
                return "•";
        }
    }

    // =====================================================
    // SMART INSIGHT
    // =====================================================

    private String buildInsight() {

        double income =
                TransactionService
                        .getMonthlyIncome(
                                selectedMonth
                        );

        double expenses =
                TransactionService
                        .getMonthlyExpenses(
                                selectedMonth
                        );

        if (income <= 0) {

            return "Add income transactions to unlock personalized financial insights.";
        }

        double savingRate =
                (
                        income
                        - expenses
                )
                / income
                * 100;

        String topCategory =
                TransactionService
                        .getTopExpenseCategory(
                                selectedMonth
                        );

        YearMonth previous =
                selectedMonth
                        .minusMonths(1);

        double previousExpenses =
                TransactionService
                        .getMonthlyExpenses(
                                previous
                        );

        String comparison = "";

        if (previousExpenses > 0) {

            double difference =
                    (
                            expenses
                            - previousExpenses
                    )
                    / previousExpenses
                    * 100;

            comparison =
                    " Spending is "
                    + String.format(
                            "%.0f%%",
                            Math.abs(
                                    difference
                            )
                    )
                    +
                    (
                            difference > 0
                            ?
                            " higher"
                            :
                            " lower"
                    )
                    + " than last month.";
        }

        if (savingRate >= 30) {

            return "Strong month. You are saving about "
                    + String.format(
                            "%.0f%%",
                            savingRate
                    )
                    + " of your income. Your largest expense category is "
                    + topCategory
                    + "."
                    + comparison;
        }

        if (savingRate >= 10) {

            return "You are saving around "
                    + String.format(
                            "%.0f%%",
                            savingRate
                    )
                    + ". Keep an eye on "
                    + topCategory
                    + "."
                    + comparison;
        }

        return "Your savings rate is low this month. Review "
                + topCategory
                + " spending and recurring bills."
                + comparison;
    }

    // =====================================================
    // HEALTH SCORE
    // =====================================================

    private int calculateHealthScore() {

        double income =
                TransactionService
                        .getMonthlyIncome(
                                selectedMonth
                        );

        double expenses =
                TransactionService
                        .getMonthlyExpenses(
                                selectedMonth
                        );

        if (income <= 0) {

            return 35;
        }

        double savingsRate =
                (
                        income
                        - expenses
                )
                / income;

        int score = 40;

        if (savingsRate >= 0.30) {

            score += 35;

        } else if (savingsRate >= 0.20) {

            score += 28;

        } else if (savingsRate >= 0.10) {

            score += 18;

        } else if (savingsRate > 0) {

            score += 8;
        }

        boolean overBudget = false;

        for (Budget budget :
                BudgetService
                        .getBudgets()) {

            double spent =
                    BudgetService
                            .getSpentForCategory(
                                    budget.getCategory(),
                                    selectedMonth
                            );

            if (spent
                    > budget.getLimit()) {

                overBudget = true;

                break;
            }
        }

        if (!BudgetService
                .getBudgets()
                .isEmpty()
                && !overBudget) {

            score += 10;
        }

        if (!SavingsGoalService
                .getGoals()
                .isEmpty()) {

            score += 10;
        }

        if (!RecurringBillService
                .getBills()
                .isEmpty()) {

            score += 5;
        }

        return Math.min(
                100,
                score
        );
    }

    private String getHealthMessage(
            int score) {

        if (score >= 85) {

            return "Excellent financial habits this month.";
        }

        if (score >= 70) {

            return "Your finances are in a healthy position.";
        }

        if (score >= 50) {

            return "Stable overall, but there is room to improve.";
        }

        return "Focus on lowering expenses and building savings.";
    }

    // =====================================================
    // BUDGET REMAINING
    // =====================================================

    private double calculateBudgetRemaining(
            YearMonth month) {

        double total = 0;

        for (Budget budget :
                BudgetService
                        .getBudgets()) {

            double spent =
                    BudgetService
                            .getSpentForCategory(
                                    budget.getCategory(),
                                    month
                            );

            total +=
                    budget.getLimit()
                    - spent;
        }

        return total;
    }

    // =====================================================
    // NOTIFICATION COUNT
    // =====================================================

    private int getNotificationCount() {

        int count = 0;

        for (RecurringBill bill :
                RecurringBillService
                        .getBills()) {

            long days =
                    ChronoUnit.DAYS
                            .between(
                                    LocalDate.now(),
                                    bill.getNextDueDate()
                            );

            if (days >= 0
                    && days <= 5) {

                count++;
            }
        }

        for (Budget budget :
                BudgetService
                        .getBudgets()) {

            double spent =
                    BudgetService
                            .getSpentForCategory(
                                    budget.getCategory(),
                                    selectedMonth
                            );

            if (budget.getLimit() > 0
                    &&
                    spent
                    / budget.getLimit()
                    >= 0.9) {

                count++;
            }
        }

        return count;
    }

    // =====================================================
    // MONEY ANIMATION
    // =====================================================

    private void animateMoney(
            Label label,
            User user,
            double target) {

        DoubleProperty value =
                new SimpleDoubleProperty(0);

        value.addListener(
                (
                        obs,
                        oldValue,
                        newValue
                ) ->
                        label.setText(
                                money(
                                        user,
                                        newValue
                                                .doubleValue()
                                )
                        )
        );

        Timeline timeline =
                new Timeline(
                        new KeyFrame(
                                Duration.ZERO,
                                new KeyValue(
                                        value,
                                        0
                                )
                        ),
                        new KeyFrame(
                                Duration.millis(
                                        700
                                ),
                                new KeyValue(
                                        value,
                                        target
                                )
                        )
                );

        timeline.play();
    }

    // =====================================================
    // HEALTH SCORE ANIMATION
    // =====================================================

    private void animateHealthScore(
            Label label,
            int target) {

        DoubleProperty value =
                new SimpleDoubleProperty(0);

        value.addListener(
                (
                        obs,
                        oldValue,
                        newValue
                ) -> {

                    int score =
                            (int) Math.round(
                                    newValue
                                            .doubleValue()
                            );

                    label.setText(
                            score
                            + "/100"
                    );
                }
        );

        Timeline timeline =
                new Timeline(
                        new KeyFrame(
                                Duration.ZERO,
                                new KeyValue(
                                        value,
                                        0
                                )
                        ),
                        new KeyFrame(
                                Duration.millis(
                                        750
                                ),
                                new KeyValue(
                                        value,
                                        target
                                )
                        )
                );

        timeline.play();
    }

    // =====================================================
    // PROGRESS ANIMATION
    // =====================================================

    private void animateProgress(
            ProgressBar bar,
            double target) {

        Timeline timeline =
                new Timeline(
                        new KeyFrame(
                                Duration.ZERO,
                                new KeyValue(
                                        bar.progressProperty(),
                                        0
                                )
                        ),
                        new KeyFrame(
                                Duration.millis(
                                        650
                                ),
                                new KeyValue(
                                        bar.progressProperty(),
                                        target
                                )
                        )
                );

        timeline.play();
    }

    // =====================================================
    // CHART ANIMATION
    // =====================================================

    private void animateChart(
            Node chart) {

        chart.setOpacity(0);

        chart.setScaleX(0.92);

        chart.setScaleY(0.92);

        FadeTransition fade =
                new FadeTransition(
                        Duration.millis(
                                650
                        ),
                        chart
                );

        fade.setToValue(1);

        ScaleTransition scale =
                new ScaleTransition(
                        Duration.millis(
                                650
                        ),
                        chart
                );

        scale.setToX(1);

        scale.setToY(1);

        new ParallelTransition(
                fade,
                scale
        ).play();
    }

    // =====================================================
    // PAGE ANIMATION
    // =====================================================

    private void playPageEntrance(
            Node node) {

        node.setOpacity(0);

        node.setTranslateY(15);

        FadeTransition fade =
                new FadeTransition(
                        Duration.millis(
                                380
                        ),
                        node
                );

        fade.setFromValue(0);

        fade.setToValue(1);

        TranslateTransition move =
                new TranslateTransition(
                        Duration.millis(
                                380
                        ),
                        node
                );

        move.setFromY(15);

        move.setToY(0);

        new ParallelTransition(
                fade,
                move
        ).play();
    }

    // =====================================================
    // BUTTON ANIMATION
    // =====================================================

    private void addButtonAnimation(
            Node node) {

        node.setOnMouseEntered(e -> {

            ScaleTransition scale =
                    new ScaleTransition(
                            Duration.millis(
                                    120
                            ),
                            node
                    );

            scale.setToX(1.035);

            scale.setToY(1.035);

            scale.play();
        });

        node.setOnMouseExited(e -> {

            ScaleTransition scale =
                    new ScaleTransition(
                            Duration.millis(
                                    120
                            ),
                            node
                    );

            scale.setToX(1);

            scale.setToY(1);

            scale.play();
        });
    }

    // =====================================================
    // CARD ANIMATION
    // =====================================================

    private void addCardAnimation(
            Node node) {

        node.setOnMouseEntered(e -> {

            TranslateTransition move =
                    new TranslateTransition(
                            Duration.millis(
                                    150
                            ),
                            node
                    );

            move.setToY(-4);

            ScaleTransition scale =
                    new ScaleTransition(
                            Duration.millis(
                                    150
                            ),
                            node
                    );

            scale.setToX(1.012);

            scale.setToY(1.012);

            new ParallelTransition(
                    move,
                    scale
            ).play();
        });

        node.setOnMouseExited(e -> {

            TranslateTransition move =
                    new TranslateTransition(
                            Duration.millis(
                                    150
                            ),
                            node
                    );

            move.setToY(0);

            ScaleTransition scale =
                    new ScaleTransition(
                            Duration.millis(
                                    150
                            ),
                            node
                    );

            scale.setToX(1);

            scale.setToY(1);

            new ParallelTransition(
                    move,
                    scale
            ).play();
        });
    }

    // =====================================================
    // ROW ANIMATION
    // =====================================================

    private void addRowAnimation(
            Node row) {

        row.setOnMouseEntered(e -> {

            TranslateTransition move =
                    new TranslateTransition(
                            Duration.millis(
                                    120
                            ),
                            row
                    );

            move.setToX(5);

            move.play();
        });

        row.setOnMouseExited(e -> {

            TranslateTransition move =
                    new TranslateTransition(
                            Duration.millis(
                                    120
                            ),
                            row
                    );

            move.setToX(0);

            move.play();
        });
    }

    // =====================================================
    // NOTIFICATION PULSE
    // =====================================================

    private void pulseNode(
            Node node) {

        ScaleTransition pulse =
                new ScaleTransition(
                        Duration.millis(
                                650
                        ),
                        node
                );

        pulse.setFromX(1);

        pulse.setFromY(1);

        pulse.setToX(1.10);

        pulse.setToY(1.10);

        pulse.setCycleCount(
                ScaleTransition.INDEFINITE
        );

        pulse.setAutoReverse(true);

        pulse.play();
    }

    // =====================================================
    // SHAKE
    // =====================================================

    private void shakeNode(
            Node node) {

        TranslateTransition transition =
                new TranslateTransition(
                        Duration.millis(
                                65
                        ),
                        node
                );

        transition.setFromX(-5);

        transition.setToX(5);

        transition.setCycleCount(6);

        transition.setAutoReverse(true);

        transition.play();
    }

    // =====================================================
    // MESSAGES
    // =====================================================

    private void showError(
            Label label,
            String message) {

        label.setText(message);

        label.getStyleClass()
                .remove("success-message");

        if (!label
                .getStyleClass()
                .contains(
                        "error-message"
                )) {

            label.getStyleClass()
                    .add("error-message");
        }
    }

    private void showSuccess(
            Label label,
            String message) {

        label.setText(message);

        label.getStyleClass()
                .remove("error-message");

        if (!label
                .getStyleClass()
                .contains(
                        "success-message"
                )) {

            label.getStyleClass()
                    .add("success-message");
        }
    }

    // =====================================================
    // ALERTS
    // =====================================================

    private void showInformation(
            String title,
            String message) {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();
    }

    private boolean confirmAction(
            String title,
            String message) {

        Alert alert =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        return alert
                .showAndWait()
                .orElse(
                        ButtonType.CANCEL
                )
                ==
                ButtonType.OK;
    }

    // =====================================================
    // MAIN
    // =====================================================

    public static void main(
            String[] args) {

        launch(args);
    }
}