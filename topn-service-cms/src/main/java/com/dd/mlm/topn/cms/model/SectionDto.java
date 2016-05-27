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

    public SectionDto(String name, String title, String type, String data, boolean hasEmbeddedFile, String justification, int fontSize, int width, int height) {
        this.title = title;
        this.name = name;
        this.type = type;
        this.data = data;
        this.hasEmbeddedFile = hasEmbeddedFile;
        this.justification = justification;
        this.fontSize = fontSize;
        this.width = width;
        this.height = height;
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

    private boolean hasEmbeddedFile;

    public boolean isHasEmbeddedFile() {
        return hasEmbeddedFile;
    }

    public void setHasEmbeddedFile(boolean hasEmbeddedFile) {
        this.hasEmbeddedFile = hasEmbeddedFile;
    }

    private String justification;

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    private int fontSize;

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    private int width;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private int height;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
