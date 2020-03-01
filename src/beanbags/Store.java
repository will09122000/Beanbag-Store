package beanbags;
import java.io.IOException;
import java.lang.Class;

public class Store implements BeanBagStore {

    private ObjectArrayList stock = new ObjectArrayList();
    private ObjectArrayList reservations = new ObjectArrayList();


    public void addBeanBags(int num, String manufacturer, String name,
    String id, short year, byte month)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
    IllegalIDException, InvalidMonthException {
        Beanbag beanbag = new Beanbag(num, manufacturer, name, id, year, month);
        stock.add(beanbag);
    }

    public void addBeanBags(int num, String manufacturer, String name, 
    String id, short year, byte month, String information)
    {
        Beanbag beanbag = new Beanbag(num, manufacturer, name, id, year, month, information);
        stock.add(beanbag);
     }

    public void setBeanBagPrice(String id, int priceInPence)
    {
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            if (stockItem.getId() == id) {
                stockItem.setPrice(priceInPence);
            }
            stock.replace(stockItem, i);
        }
    }

    public void sellBeanBags(int num, String id)
    {
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            if (stockItem.getId() == id) {
                if (stockItem.getNum() - num == 0) {
                    stock.remove(i);
                } else {
                    stockItem.setNum(stockItem.getNum() - num);
                    stock.replace(stockItem, i);
                }
            }
        }
    }

    public int reserveBeanBags(int num, String id) {
        int reservationNum = 0;
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            if (stockItem.getId() == id) {
                if (stockItem.getNum() - stockItem.getReserved() >= num) {
                    stockItem.setReserved(stockItem.getReserved() + num);
                    reservationNum = reservations.size() + 1;
                    Reserve newReservation = new Reserve(reservationNum, id, num);
                    reservations.add(newReservation);
                }
            }
        }
        return reservationNum;
    }

    public void unreserveBeanBags(int reservationNumber)
    {

    }

    public void sellBeanBags(int reservationNumber)
    {

    }

    public int beanBagsInStock() {
        return stock.size();
    }

    public int reservedBeanBagsInStock() {
        return 0;
    }

    public int beanBagsInStock(String id) {
        return 0;
    }

    public void saveStoreContents(String filename) {

    }

    public void loadStoreContents(String filename) {

    }

    public int getNumberOfDifferentBeanBagsInStock() {
        return 0;
    }

    public int getNumberOfSoldBeanBags() {
        return 0;
    }

    public int getNumberOfSoldBeanBags(String id) {
        return 0;
    }

    public int getTotalPriceOfSoldBeanBags() {
        return 0;
    }

    public int getTotalPriceOfSoldBeanBags(String id) {
        return 0;
    }

    public int getTotalPriceOfReservedBeanBags() {
        return 0;
    }

    public String getBeanBagDetails(String id) {
        return "";
    }

    public void empty() {

    }
     
    public void resetSaleAndCostTracking() {

    }
     
    public void replace(String oldId, String replacementId) {

    }

}
