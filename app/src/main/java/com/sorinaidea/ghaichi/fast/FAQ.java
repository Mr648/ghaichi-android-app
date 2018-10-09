package com.sorinaidea.ghaichi.fast;

import com.google.gson.annotations.SerializedName;

public class FAQ {

    @SerializedName("faq_title")
    private String t;
    @SerializedName("faq_answer")
    private String a;
    @SerializedName("faq_question")
    private String q;

    public String getTitle() {
        return t;
    }

    public String getAnswer() {
        return a;
    }

    public void setAnswer(String answer) {
        this.a = answer;
    }

    public String getQuestion() {
        return q;
    }

    public void setQuestion(String question) {
        this.q = question;
    }

    public void setTitle(String t) {
        this.t = t;
    }

    private boolean hide = true;


    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }
}
