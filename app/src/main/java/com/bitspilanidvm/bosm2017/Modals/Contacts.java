package com.bitspilanidvm.bosm2017.Modals;

public class Contacts {

    String name;
    String number;
    String dept_info;

    public Contacts() {
    }

    public Contacts(String name, String number, String dept_info) {
        this.name = name;
        this.number = number;
        this.dept_info = dept_info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDept_info() {
        return dept_info;
    }

    public void setDept_info(String dept_info) {
        this.dept_info = dept_info;
    }
}
