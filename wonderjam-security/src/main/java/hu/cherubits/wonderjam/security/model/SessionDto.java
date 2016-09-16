/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.security.model;

import hu.cherubits.wonderjam.persistence.entities.NetworkNodeType;
import java.io.Serializable;

/**
 *
 * @author lordoftheflies
 */
public class SessionDto implements Serializable {

    public SessionDto() {
    }

    public SessionDto(NetworkNodeType nodeType, int unused, String token, String userName, String preferredLanguage) {
        this.powerUser = NetworkNodeType.ADMIN == nodeType;
        this.role = nodeType.name();
        this.unused = unused;
        this.token = token;
        this.userName = userName;
        this.preferredLanguage = preferredLanguage;
    }

    private String preferredLanguage;

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private boolean powerUser;

    public boolean isPowerUser() {
        return powerUser;
    }

    public void setPowerUser(boolean powerUser) {
        this.powerUser = powerUser;
    }

    private int unused;

    public int getUnused() {
        return unused;
    }

    public void setUnused(int unused) {
        this.unused = unused;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
