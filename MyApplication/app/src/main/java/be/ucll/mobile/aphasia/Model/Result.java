package be.ucll.mobile.aphasia.Model;

import java.util.Date;

/**
 * Created by croewens on 04/01/2017.
 */

public class Result {
    //oefening
    //private ImageItem imageItem;
    private String imagePath;
    //answer to voice translated to text
    private String answer;

    //path voice stuff
    private String path;
    private Date date;

    //length van de audio file
    private String duration = "00:00";

    public Result(ImageItem item, String answer, String path, int duration) {
        this(item, answer, path, duration, new Date());
    }

    public Result(ImageItem item, String answer, String path, int duration, Date date) {
        setImagePath(item.getPath());
        setAnswer(answer);
        setDuration(duration);
        setPath(path);
        setDate(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        //System.out.println(duration + " IERSE");
        int m = (duration / 1000) / 60;
        int s = (duration / 1000) % 60;
        String minutes = "";
        String seconds = "";

        if (m < 10) {
            minutes = "0";
        }

        if (s < 10) {
            seconds = "0";
        }
        this.duration = minutes + m + ":" +  seconds + s;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
