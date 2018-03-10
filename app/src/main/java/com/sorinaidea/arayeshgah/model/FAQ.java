package com.sorinaidea.arayeshgah.model;

/**
 * Created by mr-code on 3/10/2018.
 */

public class FAQ {
    private String question;
    private String answer;

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

    public FAQ(String answer,String question){
        this.answer = answer;
        this.question = question;
    }
}
