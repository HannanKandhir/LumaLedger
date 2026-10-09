package service;

import java.util.List;

import model.User;

import storage.DataStorage;

import util.PasswordUtil;

public class AuthService {

    private static List<User> users =
            DataStorage.loadUsers();

    public static boolean registerUser(
            String name,
            String email,
            String password) {

        for (User user :
                users) {

            if (user
                    .getEmail()
                    .equalsIgnoreCase(
                            email
                    )) {

                return false;
            }
        }

        String salt =
                PasswordUtil
                        .generateSalt();

        String hash =
                PasswordUtil
                        .hashPassword(
                                password,
                                salt
                        );

        users.add(
                new User(
                        name,
                        email,
                        hash,
                        salt,
                        "USD"
                )
        );

        DataStorage.saveUsers(
                users
        );

        return true;
    }

    public static User login(
            String email,
            String password) {

        for (User user :
                users) {

            if (user
                    .getEmail()
                    .equalsIgnoreCase(
                            email
                    )
                    &&
                    PasswordUtil
                            .verifyPassword(
                                    password,
                                    user.getSalt(),
                                    user.getPasswordHash()
                            )) {

                return user;
            }
        }

        return null;
    }

    public static void updateProfile(
            User user,
            String newName,
            String currency) {

        user.setName(
                newName
        );

        user.setCurrency(
                currency
        );

        DataStorage.saveUsers(
                users
        );
    }

    public static boolean changePassword(
            User user,
            String currentPassword,
            String newPassword) {

        if (!PasswordUtil
                .verifyPassword(
                        currentPassword,
                        user.getSalt(),
                        user.getPasswordHash()
                )) {

            return false;
        }

        String newSalt =
                PasswordUtil
                        .generateSalt();

        user.setSalt(
                newSalt
        );

        user.setPasswordHash(
                PasswordUtil
                        .hashPassword(
                                newPassword,
                                newSalt
                        )
        );

        DataStorage.saveUsers(
                users
        );

        return true;
    }
}