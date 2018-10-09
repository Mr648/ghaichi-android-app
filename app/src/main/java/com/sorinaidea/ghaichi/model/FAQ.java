package com.sorinaidea.ghaichi.model;

/**
 * Created by mr-code on 3/10/2018.
 */

public class FAQ {
    private String question;
    private String answer;
    private String title;
    private boolean hide;


    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTitle() {
        return title;
    }

    public FAQ(String answer, String question){
        this.title = "عنوان";
        this.setHide(true);
        this.answer = answer;
        this.question = question;
    }
}
