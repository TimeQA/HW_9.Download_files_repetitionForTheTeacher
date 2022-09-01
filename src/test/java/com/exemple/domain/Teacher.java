package com.exemple.domain;

public class Teacher {

    private String name;
    private Boolean isGoodTeacher;
    private Integer age;
    private Passport passport;
    private Number number;

    public String getName() {
        return name;
    }

    public Boolean isGoodTeacher() {
        return isGoodTeacher;
    }

    public Integer getAge() {
        return age;
    }

    public Passport getPassport() {
        return passport;
    }

    public static class Passport {
        private Integer number;

        public Integer getNumber() {
            return number;
        }
    }
}
