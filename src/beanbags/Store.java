package beanbags;
import java.io.IOException;
import java.lang.Class;

public class Store implements BeanBagStore {

    private ObjectArrayList stock = new ObjectArrayList();
    private ObjectArrayList reservations = new ObjectArrayList();

    public boolean checkIllegalNumberOfBeanBagsAddedException(int num) {
        // If the number of beanbags is less than one, throw an exception.
        if (num < 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkBeanBagMismatchException(int num, String manufacturer, String name, String id) {
        // Iterate through existing stock to check if the id already exists.
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            if (stockItem.getId() == id) {
                // If an exisitng id has no stock but the manufacturer and name match those of the beanbag
                // being added, thrown an exception.
                if (stockItem.getNum() == 0) {
                    if ((stockItem.getManufacturer() == manufacturer) && (stockItem.getName() == name)) {
                        return true;
                    }
                    // If an exisitng id currently has stock, throw an exception.
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIllegalIDException(String id) {
        if (id.matches("^[0-9a-fA-F]+$") && id.length() == 8) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInvalidMonthException(byte month) {
        int monthCheck = month;
        if (monthCheck >= 1 && monthCheck <= 12) {
            return false;
        } else {
            return true;
        }
    }

    public void addBeanBags(int num, String manufacturer, String name,
    String id, short year, byte month)
            throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
            IllegalIDException, InvalidMonthException
    {
        // Create a new beanbag object with the given parameters.
        Beanbag beanbag = new Beanbag(num, manufacturer, name, id, year, month);

        // Check exceptions.
        if (checkIllegalNumberOfBeanBagsAddedException(num)) {
            throw new IllegalNumberOfBeanBagsAddedException();
        }
        if (checkBeanBagMismatchException(num, manufacturer, name, id)) {
            throw new BeanBagMismatchException();
        }
        if (checkIllegalIDException(id)) {
            throw new IllegalIDException();
        }
        if (checkInvalidMonthException(month)) {
            throw new InvalidMonthException();
        }

        // If no exceptions thrown, add beanbag object to stock array.
        stock.add(beanbag);
    }


    public void addBeanBags(int num, String manufacturer, String name, 
    String id, short year, byte month, String information)
            throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
            IllegalIDException, InvalidMonthException
    {
        Beanbag beanbag = new Beanbag(num, manufacturer, name, id, year, month, information);
        stock.add(beanbag);
     }


    public void setBeanBagPrice(String id, int priceInPence)
            throws InvalidPriceException, BeanBagIDNotRecognisedException, IllegalIDException
    {
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            if (stockItem.getId() == id) {
                stockItem.setPrice(priceInPence);
            } else if (i + 1 == stock.size()) {
                throw new BeanBagIDNotRecognisedException();
            }
            stock.replace(stockItem, i);
        }
    }


    public void sellBeanBags(int num, String id)
            throws BeanBagNotInStockException, InsufficientStockException,
            IllegalNumberOfBeanBagsSoldException, PriceNotSetException,
            BeanBagIDNotRecognisedException, IllegalIDException
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


    public int reserveBeanBags(int num, String id)
            throws BeanBagNotInStockException, InsufficientStockException,
            IllegalNumberOfBeanBagsReservedException, PriceNotSetException,
            BeanBagIDNotRecognisedException, IllegalIDException
    {
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
            throws ReservationNumberNotRecognisedException
    {
        for (int i = 0; i < reservations.size(); i++) {
            Reserve reservation = ((Reserve) reservations.get(i));
            if (reservation.getReservationNum() == reservationNumber) {
                for (int j = 0; j < stock.size(); j++) {
                    Beanbag stockItem = ((Beanbag) stock.get(j));
                    if (stockItem.getId() == reservation.getId()) {
                        stockItem.setNum(stockItem.getNum() + reservation.getNum());
                        stockItem.setReserved(stockItem.getReserved() - reservation.getNum());
                        reservations.remove(i);
                        break;
                    }
                }
                break;
            }
        }
    }


    public void sellBeanBags(int reservationNumber)
            throws ReservationNumberNotRecognisedException
    {

    }


    public int beanBagsInStock()

    {
        return stock.size();
    }


    public int reservedBeanBagsInStock()
    {
        return 0;
    }


    public int beanBagsInStock(String id)
            throws BeanBagIDNotRecognisedException, IllegalIDException
    {
        return 0;
    }


    public void saveStoreContents(String filename)
            throws IOException
    {

    }


    public void loadStoreContents(String filename)
            throws IOException, ClassNotFoundException
    {

    }


    public int getNumberOfDifferentBeanBagsInStock()
    {
        return 0;
    }


    public int getNumberOfSoldBeanBags()
    {
        return 0;
    }


    public int getNumberOfSoldBeanBags(String id)
            throws BeanBagIDNotRecognisedException, IllegalIDException
    {
        return 0;
    }


    public int getTotalPriceOfSoldBeanBags()
    {
        return 0;
    }


    public int getTotalPriceOfSoldBeanBags(String id)
            throws BeanBagIDNotRecognisedException, IllegalIDException
    {
        return 0;
    }


    public int getTotalPriceOfReservedBeanBags()
    {
        return 0;
    }


    public String getBeanBagDetails(String id)
            throws BeanBagIDNotRecognisedException, IllegalIDException
    {
        return "";
    }


    public void empty()
    {

    }


    public void resetSaleAndCostTracking()
    {

    }


    public void replace(String oldId, String replacementId)
            throws BeanBagIDNotRecognisedException, IllegalIDException
    {

    }

}
