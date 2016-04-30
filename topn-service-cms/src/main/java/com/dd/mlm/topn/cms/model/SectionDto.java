/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.mlm.topn.cms.model;

/**
 *
 * @author lordoftheflies
 */
public class SectionDto {

    public SectionDto() {
    }

    public SectionDto(String type) {
        this.type = type;
    }

    public SectionDto(String name, String title, String type, String data) {
        this.name = name;
        this.title = title;
        this.type = type;
        this.data = data;
    }
    
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
