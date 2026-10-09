package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import model.SavingsGoal;

public class SavingsGoalStorage {

    private static String getFileName(
            String email) {

        return "goals_"
                + email.replaceAll(
                        "[^a-zA-Z0-9]",
                        "_"
                )
                + ".txt";
    }

    public static void saveGoals(
            String email,
            List<SavingsGoal> goals) {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(
                                     getFileName(
                                             email
                                     )
                             )
                     )) {

            for (SavingsGoal g :
                    goals) {

                writer.println(
                        safe(
                                g.getName()
                        )
                        + "|"
                        + g.getTargetAmount()
                        + "|"
                        + g.getSavedAmount()
                        + "|"
                        + g.isCompleted()
                );
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static List<SavingsGoal>
    loadGoals(
            String email) {

        List<SavingsGoal> list =
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
                                "\\|",
                                -1
                        );

                if (p.length >= 3) {

                    boolean complete =
                            p.length >= 4
                            && Boolean.parseBoolean(
                                    p[3]
                            );

                    list.add(
                            new SavingsGoal(
                                    p[0],
                                    Double.parseDouble(
                                            p[1]
                                    ),
                                    Double.parseDouble(
                                            p[2]
                                    ),
                                    complete
                            )
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }

    private static String safe(
            String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace(
                        "|",
                        "/"
                )
                .replace(
                        "\n",
                        " "
                );
    }
}