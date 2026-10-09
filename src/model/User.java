package model;

public class User {

    private String name;
    private final String email;

    private String passwordHash;
    private String salt;

    private String currency;

    public User(
            String name,
            String email,
            String passwordHash,
            String salt,
            String currency) {

        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.salt = salt;

        if (currency == null || currency.isBlank()) {
            this.currency = "USD";
        } else {
            this.currency = currency;
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswordHash(
            String passwordHash) {

        this.passwordHash =
                passwordHash;
    }

    public void setSalt(
            String salt) {

        this.salt = salt;
    }

    public void setCurrency(
            String currency) {

        this.currency = currency;
    }
}