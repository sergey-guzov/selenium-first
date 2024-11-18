package com.github.sergey_guzov.pages.pastebin.settings;

public enum ExpirationTime {
    TEN_MINUTES("10 Minutes"),
    ONE_HOUR("1 hour"),
    ONE_DAY("1 day");

    private final String expirationTime;

    ExpirationTime (String expirationTime) {

        this.expirationTime = expirationTime;

    }

    public String getExpirationTime () {

        return this.expirationTime;
    }

}
