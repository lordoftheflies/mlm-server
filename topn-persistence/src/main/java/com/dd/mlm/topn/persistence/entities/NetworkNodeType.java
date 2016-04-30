/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.mlm.topn.persistence.entities;

/**
 *
 * @author lordoftheflies
 */
public enum NetworkNodeType {

    GROUP("group"),
    USER("user"),
    ADMIN("admin");

    private final String key;

    private NetworkNodeType(String key) {
        this.key = key;
    }

}
