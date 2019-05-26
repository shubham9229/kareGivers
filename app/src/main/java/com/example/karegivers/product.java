package com.example.karegivers;

public class product {

    private String name,address,description,mobile;

    public product()
    {

    }

    public product(String name, String address, String description, String mobile) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getMobile() {
        return mobile;
    }
}
