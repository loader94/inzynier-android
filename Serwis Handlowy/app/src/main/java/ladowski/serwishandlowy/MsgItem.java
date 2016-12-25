package ladowski.serwishandlowy;

/**
 * Created by Paweu on 2016-12-18.
 */
public class MsgItem {
    private String from;
    private String to;
    private String date;
    private String topic;

    public MsgItem(String f, String t, String d, String top){
        this.from = f;
        this.to = t;
        this.date = d;
        this.topic = top;
    }
    public String getFrom(){return from;}
    public String getTo() {return to;}
    public String getDate() {return date;}
    public String getTopic() {return topic;}
    public void setFrom(String from){this.from=from;}
    public void setTo(String to) {this.to=to;}
    public void setDate(String date){this.date=date;}
    public void setTopic(String topic){this.topic=topic;}
}
