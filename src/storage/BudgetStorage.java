package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import model.Budget;

public class BudgetStorage {

    private static String getFileName(
            String email) {

        return "budgets_"
                + email.replaceAll(
                        "[^a-zA-Z0-9]",
                        "_"
                )
                + ".txt";
    }

    public static void saveBudgets(
            String email,
            List<Budget> budgets) {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(
                                     getFileName(
                                             email
                                     )
                             )
                     )) {

            for (Budget b :
                    budgets) {

                writer.println(
                        b.getCategory()
                        + "|"
                        + b.getLimit()
                );
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static List<Budget>
    loadBudgets(
            String email) {

        List<Budget> list =
                new ArrayList<>();

        File file =
                new File(
                        getFileName(
                                email
                        )
                );

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(
                                     file
                             )
                     )) {

            String line;

            while ((line =
                    reader.readLine())
                    != null) {

                String[] p =
                        line.split(
                                "\\|"
                        );

                if (p.length == 2) {

                    list.add(
                            new Budget(
                                    p[0],
                                    Double.parseDouble(
                                            p[1]
                                    )
                            )
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }
}