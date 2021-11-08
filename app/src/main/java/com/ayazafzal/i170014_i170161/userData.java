package com.ayazafzal.i170014_i170161;

import android.util.Log;

public class userData {
    String Id,Email,Pass,FirstName,LastName,Gender,Bio,Status,Phone;
    String Image;

    public String getPhone() {
        return Phone;
    }
    public void DisplayUser(){
        Log.d("User",this.Email);
        Log.d("User",this.Pass);
        Log.d("User",this.FirstName);
        Log.d("User",this.LastName);
        Log.d("User",this.Phone);
        com.ayazafzal.i170014_i170161.ImageUtility img=new com.ayazafzal.i170014_i170161.ImageUtility();
        Log.d("User",this.Image);

    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getId() {
        return Id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setId(String id) {
        Id = id;
    }

    public userData(String id, String email, String pass, String firstName, String lastName, String gender, String bio, String status, String phone, String image) {
        Id=id;
        Email = email;
        Pass = pass;
        FirstName = firstName;
        LastName = lastName;
        Gender = gender;
        Bio = bio;
        Status = status;
        Phone=phone;
        Image = image;
    }
    public userData(String name,String phone) {
        FirstName = name;
        LastName = name;
        Phone=phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
