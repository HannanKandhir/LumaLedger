package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import model.RecurringBill;

public class RecurringBillStorage {

    private static String getFileName(
            String email) {

        return "bills_"
                + email.replaceAll(
                        "[^a-zA-Z0-9]",
                        "_"
                )
                + ".txt";
    }

    public static void save(
            String email,
            List<RecurringBill> bills) {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(
                                     getFileName(
                                             email
                                     )
                             )
                     )) {

            for (RecurringBill b :
                    bills) {

                writer.println(
                        safe(
                                b.getName()
                        )
                        + "|"
                        + b.getAmount()
                        + "|"
                        + b.getDueDay()
                        + "|"
                        + safe(
                                b.getCategory()
                        )
                );
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static List<RecurringBill>
    load(
            String email) {

        List<RecurringBill> list =
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

                if (p.length == 4) {

                    list.add(
                            new RecurringBill(
                                    p[0],
                                    Double.parseDouble(
                                            p[1]
                                    ),
                                    Integer.parseInt(
                                            p[2]
                                    ),
                                    p[3]
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