package com.zero.zero.data;

public class DonorRegistrationInfo {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;

    public DonorRegistrationInfo(String id, String email,String input_password, String phone,  String address, String city, String zip, String contact_person, String phone_contact, String cuisine_type) {

        this.id = id;
        this.input_password = input_password;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.contact_person = contact_person;
        this.phone_contact = phone_contact;
        this.cuisine_type = cuisine_type;
    }

    public String input_password;
    public String phone;
    public String email;
    public String address;
    public String city;
    public String zip;
    public String contact_person;
    public String phone_contact;
    public String cuisine_type;

    public DonorRegistrationInfo() {

    }

    @Override
    public String toString() {
        return "DonorRegistrationInfo{" +
                "id='" + id + '\'' +
                ", input_password='" + input_password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", contact_person='" + contact_person + '\'' +
                ", phone_contact='" + phone_contact + '\'' +
                ", cuisine_type='" + cuisine_type + '\'' +
                '}';
    }





    public String getInput_password() {
        return input_password;
    }

    public void setInput_password(String input_password) {
        this.input_password = input_password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getPhone_contact() {
        return phone_contact;
    }

    public void setPhone_contact(String phone_contact) {
        this.phone_contact = phone_contact;
    }

    public String getCuisine_type() {
        return cuisine_type;
    }

    public void setCuisine_type(String cuisine_type) {
        this.cuisine_type = cuisine_type;
    }
}

