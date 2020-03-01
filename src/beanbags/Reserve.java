package beanbags;

public class Reserve {
    private int reservationNum;
    private String id;
    private int num;


    public int getReservationNum(){
        return reservationNum;
    }

    public String getId(){
        return id;
    }

    public int getNum(){
        return num;
    }


    public Reserve(int reservationNum, String id, int num) {
        this.reservationNum = reservationNum;
        this.id = id;
        this.num = num;
    }

}