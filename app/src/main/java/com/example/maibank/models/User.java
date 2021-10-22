package com.example.maibank.models;

/**
 * The current user
 */
public class User {
    /**
     * First Name of the user
     */
    private String firstName;
    /**
     * Last Name of the user
     */
    private String lastName;
    /**
     * Address of the user
     */
    private String address;
    /**
     * Phone of the user
     */
    private String phone;
    /**
     * Email of the user
     */
    private String email;
    /**
     * CNP of the user
     */
    private String CNP;
    /**
     * Id of the user
     */
    private String UID;

    /**
     * Private Constructor
     */
    private User(){}

    /**
     * Helper class  for singleton
     */
    private static class Helper{
        private static final User instance = new User();
    }

    /**
     * Get the instance of the current user
     * @return The current user
     */
    public static User getInstance(){
        return Helper.instance;
    }

    /**
     * @return The unique id of the user
     */
    public String getUID() {
        return UID;
    }

    /**
     * Sets the unique id of the user
     * @param UID The UID
     */
    public void setUID(String UID) {
        this.UID = UID;
    }

    /**
     *
     * @return The first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user
     * @param firstName The string representing the first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return The last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user
     * @param lastName The string representing the last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return The address of the user
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user
     * @param address The string representing the address of the user
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return The phone of the user
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone of the user
     * @param phone The string representing the phone of the user
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user
     * @param email The string representing the email address of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return The CNP of the user
     */
    public String getCNP() {
        return CNP;
    }

    /**
     * Sets the CNP of the user
     * @param CNP The string representing the CNP of the user
     */
    public void setCNP(String CNP) {
        this.CNP = CNP;
    }
}
