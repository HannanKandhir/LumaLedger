package util;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import model.Transaction;

public class CSVExporter {

    public static void exportTransactions(
            File file,
            List<Transaction> transactions)
            throws Exception {

        try (PrintWriter out =
                     new PrintWriter(file)) {

            out.println(
                    "Date,Type,Category,Amount,Note"
            );

            for (Transaction t :
                    transactions) {

                out.printf(
                        "%s,%s,%s,%.2f,%s%n",
                        csv(
                                t.getDate()
                                        .toString()
                        ),
                        csv(
                                t.getType()
                        ),
                        csv(
                                t.getCategory()
                        ),
                        t.getAmount(),
                        csv(
                                t.getNote()
                        )
                );
            }
        }
    }

    private static String csv(
            String value) {

        if (value == null) {
            return "";
        }

        String escaped =
                value.replace(
                        "\"",
                        "\"\""
                );

        return "\""
                + escaped
                + "\"";
    }
}