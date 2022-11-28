package com.example.demo_movies.Model;

public class ReviewFModel {
    private String Rcast;
    private String Rcover;
    private String Rdes;
    private String Rlink;
    private String Rthumb;
    private String Rtitle;
    private String Rtlink;

    public ReviewFModel() {
    }

    public ReviewFModel(String rcast, String rcover, String rdes, String rlink, String rthumb, String rtitle, String rtlink) {
        Rcast = rcast;
        Rcover = rcover;
        Rdes = rdes;
        Rlink = rlink;
        Rthumb = rthumb;
        Rtitle = rtitle;
        Rtlink = rtlink;
    }

    public String getRcast() {
        return Rcast;
    }

    public void setRcast(String rcast) {
        Rcast = rcast;
    }

    public String getRcover() {
        return Rcover;
    }

    public void setRcover(String rcover) {
        Rcover = rcover;
    }

    public String getRdes() {
        return Rdes;
    }

    public void setRdes(String rdes) {
        Rdes = rdes;
    }

    public String getRlink() {
        return Rlink;
    }

    public void setRlink(String rlink) {
        Rlink = rlink;
    }

    public String getRthumb() {
        return Rthumb;
    }

    public void setRthumb(String rthumb) {
        Rthumb = rthumb;
    }

    public String getRtitle() {
        return Rtitle;
    }

    public void setRtitle(String rtitle) {
        Rtitle = rtitle;
    }

    public String getRtlink() {
        return Rtlink;
    }

    public void setRtlink(String rtlink) {
        Rtlink = rtlink;
    }
}
