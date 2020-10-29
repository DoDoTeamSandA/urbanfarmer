package com.dodo.urbanfarmer.Pojos;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import java.util.ArrayList;

public class ProfilePojo {
    private String usernameStr, DOBStr, FullNameStr, emailStr, ComapnyNameStr, PhoneNunberStr;
    private String AadharNumberStr, PincodeStr, CityNameStr, StateNameStr, HomeAddressStr, OfficeAddressStr;
    private String ProfiePicuri,AadherPicUri;

    public String getProfiePicuri() {
        return ProfiePicuri;
    }

    public void setProfiePicuri(String profiePicuri) {
        ProfiePicuri = profiePicuri;
    }

    public String getAadherPicUri() {
        return AadherPicUri;
    }

    public void setAadherPicUri(String aadherPicUri) {
        AadherPicUri = aadherPicUri;
    }

    public String getUsernameStr() {
        return usernameStr;
    }

    public void setUsernameStr(String usernameStr) {
        this.usernameStr = usernameStr;
    }

    public String getDOBStr() {
        return DOBStr;
    }

    public void setDOBStr(String DOBStr) {
        this.DOBStr = DOBStr;
    }

    public String getFullNameStr() {
        return FullNameStr;
    }

    public void setFullNameStr(String fullNameStr) {
        FullNameStr = fullNameStr;
    }

    public String getEmailStr() {
        return emailStr;
    }

    public void setEmailStr(String emailStr) {
        this.emailStr = emailStr;
    }

    public String getComapnyNameStr() {
        return ComapnyNameStr;
    }

    public void setComapnyNameStr(String comapnyNameStr) {
        ComapnyNameStr = comapnyNameStr;
    }

    public String getPhoneNunberStr() {
        return PhoneNunberStr;
    }

    public void setPhoneNunberStr(String phoneNunberStr) {
        PhoneNunberStr = phoneNunberStr;
    }

    public String getAadharNumberStr() {
        return AadharNumberStr;
    }

    public void setAadharNumberStr(String aadharNumberStr) {
        AadharNumberStr = aadharNumberStr;
    }

    public String getPincodeStr() {
        return PincodeStr;
    }

    public void setPincodeStr(String pincodeStr) {
        PincodeStr = pincodeStr;
    }

    public String getCityNameStr() {
        return CityNameStr;
    }

    public void setCityNameStr(String cityNameStr) {
        CityNameStr = cityNameStr;
    }

    public String getStateNameStr() {
        return StateNameStr;
    }

    public void setStateNameStr(String stateNameStr) {
        StateNameStr = stateNameStr;
    }

    public String getHomeAddressStr() {
        return HomeAddressStr;
    }

    public void setHomeAddressStr(String homeAddressStr) {
        HomeAddressStr = homeAddressStr;
    }

    public String getOfficeAddressStr() {
        return OfficeAddressStr;
    }

    public void setOfficeAddressStr(String officeAddressStr) {
        OfficeAddressStr = officeAddressStr;
    }

    public Boolean isValidate(){

        return Patterns.EMAIL_ADDRESS.matcher(getEmailStr()).find() && !TextUtils.isEmpty(getEmailStr()) && getPhoneNunberStr().length()>=10
                &&  !TextUtils.isEmpty(getUsernameStr()) &&  !TextUtils.isEmpty(getComapnyNameStr()) &&  !TextUtils.isEmpty(getDOBStr())
                &&  !TextUtils.isEmpty(getFullNameStr())  &&  !TextUtils.isEmpty(getAadharNumberStr())  &&  !TextUtils.isEmpty(getPincodeStr())
                &&  !TextUtils.isEmpty(getCityNameStr())
                &&  !TextUtils.isEmpty(getStateNameStr())
                && !TextUtils.isEmpty(getHomeAddressStr())
                &&  !TextUtils.isEmpty(getOfficeAddressStr());




    }

    public ArrayList<String> getValues(){

        ArrayList<String> array=new ArrayList<>();
        array.add(getUsernameStr());
        array.add(getDOBStr());
        array.add(getFullNameStr());
        array.add(getEmailStr());
        array.add(getComapnyNameStr());
        array.add(getPhoneNunberStr());
        array.add(getAadharNumberStr());
        array.add(getPincodeStr());
        array.add(getCityNameStr());
        array.add(getStateNameStr());
        array.add(getHomeAddressStr());
        array.add(getOfficeAddressStr());
        array.add(getProfiePicuri());
        array.add(getAadherPicUri());


        return array;

    }


    public String getSucessMessage(){
        return "Welcome";
    }




}
