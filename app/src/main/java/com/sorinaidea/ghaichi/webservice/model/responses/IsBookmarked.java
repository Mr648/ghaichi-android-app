package com.sorinaidea.ghaichi.webservice.model.responses;

public class IsBookmarked extends Response {
    private boolean isBookmarked;

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}
