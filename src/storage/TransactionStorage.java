package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;

public class TransactionStorage {

    private static String getFileName(
            String email) {

        return "transactions_"
                + email.replaceAll(
                        "[^a-zA-Z0-9]",
                        "_"
                )
                + ".txt";
    }

    public static void saveTransactions(
            String email,
            List<Transaction> transactions) {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(
                                     getFileName(
                                             email
                                     )
                             )
                     )) {

            for (Transaction t :
                    transactions) {

                writer.println(
                        t.getId()
                        + "|"
                        + t.getType()
                        + "|"
                        + t.getCategory()
                        + "|"
                        + t.getAmount()
                        + "|"
                        + safe(
                                t.getNote()
                        )
                        + "|"
                        + t.getDate()
                );
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static List<Transaction>
    loadTransactions(
            String email) {

        List<Transaction> list =
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

                if (p.length == 6) {

                    list.add(
                            new Transaction(
                                    p[0],
                                    p[1],
                                    p[2],
                                    Double.parseDouble(
                                            p[3]
                                    ),
                                    p[4],
                                    LocalDate.parse(
                                            p[5]
                                    )
                            )
                    );

                } else if (p.length == 5) {

                    /*
                     * Compatibility with
                     * your older files.
                     */

                    list.add(
                            new Transaction(
                                    p[0],
                                    p[1],
                                    Double.parseDouble(
                                            p[2]
                                    ),
                                    p[3],
                                    LocalDate.parse(
                                            p[4]
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