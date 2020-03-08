package beanbags;
import java.io.Serializable;

public class Reserve implements Serializable
{
    private int reservationNum;
    private String id;
    private int num;
    private int price;

    // Setters

    public void setId(String id){
        assert id.matches("^[0-9a-fA-F]+$") && id.length() == 8 : "ID must be an 8 digit hexadecimal number.";
        this.id = id;
    }

    public void setNum(int num){
        assert num >= 0 : "Number of beanbags must be greater than or equal to zero.";
        this.num = num;
    }

    public void setPrice(int price){
        assert price >= 0 : "Price must be greater than or equal to zero.";
        this.price = price;
    }

    // Getters

    public int getReservationNum(){
        return reservationNum;
    }

    public String getId(){
        return id;
    }

    public int getNum(){
        return num;
    }

    public int getPrice(){
        return price;
    }

    // Constructors

    public Reserve(int reservationNum, String id, int num, int price) {
        assert reservationNum >= 0 : "Reservation number must be greater than or equal to zero.";
        assert id.matches("^[0-9a-fA-F]+$") && id.length() == 8 : "ID must be an 8 digit hexadecimal number.";
        assert num >= 0 : "Number of beanbags must be greater than or equal to zero.";
        assert price >= 0 : "Price must be greater than or equal to zero.";

        this.reservationNum = reservationNum;
        this.id = id;
        this.num = num;
        this.price = price;
    }

}