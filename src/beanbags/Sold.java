package beanbags;
import java.io.Serializable;

public class Sold implements Serializable
{
    private int num;
    private String manufacturer;
    private String name;
    private String id;
    private short year;
    private byte month;
    private String information;
    private int price;

    // Setters

    public void setPrice(int price){
        assert price >= 0 : "Price must be greater than or equal to zero.";
        this.price = price;
    }

    public void setNum(int num){
        assert num >= 0 : "Number of beanbags must be greater than or equal to zero.";
        this.num = num;
    }

    // Getters

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

    public short getYear(){
        return year;
    }

    public byte getMonth(){
        return month;
    }

    public String getInformation(){
        return information;
    }

    public int getPrice(){
        return price;
    }

    // Constructors

    public Sold(int num, String manufacturer, String name, String id, short year, byte month) {
        assert num >= 0 : "Number of beanbags must be greater than or equal to zero.";
        assert id.matches("^[0-9a-fA-F]+$") && id.length() == 8 : "ID must be an 8 digit hexadecimal number.";
        assert year > 0 : "Year must be greater than zero.";
        int monthCheck = month;
        assert monthCheck >= 1 && monthCheck <= 12 : "Month must be between 1 and 12.";

        this.num = num;
        this.manufacturer = manufacturer;
        this.name = name;
        this.id = id;
        this.year = year;
        this.month = month;
    }

    public Sold(int num, String manufacturer, String name, String id, short year, byte month, String information) {
        assert num >= 0 : "Number of beanbags must be greater than or equal to zero.";
        assert id.matches("^[0-9a-fA-F]+$") && id.length() == 8 : "ID must be an 8 digit hexadecimal number.";
        assert year > 0 : "Year must be greater than zero.";
        int monthCheck = month;
        assert monthCheck >= 1 && monthCheck <= 12 : "Month must be between 1 and 12.";

        this.num = num;
        this.manufacturer = manufacturer;
        this.name = name;
        this.id = id;
        this.year = year;
        this.month = month;
        this.information = information;
    }

}