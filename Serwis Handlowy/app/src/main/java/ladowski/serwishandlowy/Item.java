package ladowski.serwishandlowy;

/**
 * Created by Paweu on 2016-11-30.
 */
public class Item {
    private String name;
    private String price;
    private String imgpath;

    public Item(String n, String p, String i){
        this.name = n;
        this.price = p;
        this.imgpath = i;
    }
    public String getName() {
        return name;
    }
    public String getPrice() {
        return price;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getImgpath(){
        return imgpath;
    }
    public void setImgpath(String i){
        this.imgpath = i;
    }
}
