package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import model.User;

public class DataStorage {

    private static final String USER_FILE =
            "users.txt";

    public static void saveUsers(
            List<User> users) {

        try (PrintWriter writer =
                     new PrintWriter(
                             new FileWriter(
                                     USER_FILE
                             )
                     )) {

            for (User user :
                    users) {

                writer.println(
                        safe(user.getName())
                        + "|"
                        + safe(user.getEmail())
                        + "|"
                        + safe(
                                user.getPasswordHash()
                        )
                        + "|"
                        + safe(
                                user.getSalt()
                        )
                        + "|"
                        + safe(
                                user.getCurrency()
                        )
                );
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static List<User> loadUsers() {

        List<User> users =
                new ArrayList<>();

        File file =
                new File(
                        USER_FILE
                );

        if (!file.exists()) {
            return users;
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

                String[] parts =
                        line.split(
                                "\\|",
                                -1
                        );

                if (parts.length >= 5) {

                    users.add(
                            new User(
                                    parts[0],
                                    parts[1],
                                    parts[2],
                                    parts[3],
                                    parts[4]
                            )
                    );
                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        return users;
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