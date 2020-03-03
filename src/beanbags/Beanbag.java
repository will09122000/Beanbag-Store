package beanbags;
import java.io.Serializable;

public class Beanbag implements Serializable
{
    private int num;
    private String manufacturer;
    private String name;
    private String id;
    private short year;
    private byte month;
    private String information;
    private int price;
    private int reserved;

    public void setPrice(int price){
        this.price = price;
    }

    public void setNum(int num){
        this.num = num;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setReserved(int reserved){
        this.reserved = reserved;
    }

    public int getNum(){
        return num;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public String getInformation(){
        return information;
    }

    public int getPrice(){
        return price;
    }

    public int getReserved(){
        return reserved;
    }

    public Beanbag(int num, String manufacturer, String name, String id, short year, byte month) {
        this.num = num;
        this.manufacturer = manufacturer;
        this.name = name;
        this.id = id;
        this.year = year;
        this.month = month;
     }

    public Beanbag(int num, String manufacturer, String name, String id, short year, byte month, String information) {
        this.num = num;
        this.manufacturer = manufacturer;
        this.name = name;
        this.id = id;
        this.year = year;
        this.month = month;
        this.information = information;
    }

}