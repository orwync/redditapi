package com.example.orwyn_carvalho_reddit;

public class Posts {
    private String title;
    private String pic;
    private String points;
    private String selftext;
    private String comment;

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public Posts(String title, String pic, String points, String selftext, String comment) {
        this.title = title;
        this.pic = pic;
        this.points = points;
        this.selftext = selftext;
        this.comment = comment;
    }

    public void setSelftext(String selftext) {
        this.selftext = selftext;
    }

    public String getSelftext() {
        return selftext;
    }


    public void setPoints(String points) {
        this.points = points;
    }

    public String getPoints() {
        try{
        if(Integer.parseInt(points)>999){
            points = points.substring(0,points.length()-3)+"k";
        }}
        catch (Exception ex){
            ex.printStackTrace();
        }
        return points;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPic(String pic) {

        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }
}
