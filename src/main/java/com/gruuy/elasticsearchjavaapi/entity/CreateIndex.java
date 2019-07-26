package com.gruuy.elasticsearchjavaapi.entity;

import java.util.Date;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 16:27 2019/7/25
 */
public class CreateIndex {
    private String title;
    private String message;
    private Date create_date;

    /**
     * @param title
     * @param message
     * @param create_date
     */
    public CreateIndex(String title, String message, Date create_date) {
        this.title = title;
        this.message = message;
        this.create_date = create_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
