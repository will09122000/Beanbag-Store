package beanbags;
import java.io.IOException;

import java.io.Serializable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Store implements BeanBagStore {

    private ObjectArrayList stock = new ObjectArrayList();
    private ObjectArrayList reservations = new ObjectArrayList();
    private ObjectArrayList sold = new ObjectArrayList();


    public void setStock(ObjectArrayList stock){
        this.stock = stock;
    }

    public void setReservations(ObjectArrayList reservations){
        this.reservations = reservations;
    }


    public boolean checkValidQuantityException(int number) {
        // Used for multiple exception checks as they all check the same thing just the meaning of the
        // integer is different.
        // If the number of beanbags is less than one, throw an exception.
        if (number < 1) {
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
        if (checkValidQuantityException(num)) {
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
        // Create a new beanbag object with the given parameters.
        Beanbag beanbag = new Beanbag(num, manufacturer, name, id, year, month, information);

        // Check exceptions.
        if (checkValidQuantityException(num)) {
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


    public void setBeanBagPrice(String id, int priceInPence)
            throws InvalidPriceException, BeanBagIDNotRecognisedException, IllegalIDException
    {
        // Check exceptions.
        if (checkIllegalIDException(id)) {
            throw new IllegalIDException();
        }
        if (checkValidQuantityException(priceInPence)) {
            throw new InvalidPriceException();
        }

        // Iterate through stock until the beanbag object with a matching id is found.
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            if (stockItem.getId() == id) {
                stockItem.setPrice(priceInPence);
            }
            // If the id was not found, raise an exception.
            else if (i + 1 == stock.size()) {
                throw new BeanBagIDNotRecognisedException();
            }
            // Replace beanbag object with the update version including the price attribute.
            stock.replace(stockItem, i);
        }
    }


    public void sellBeanBags(int num, String id)
            throws BeanBagNotInStockException, InsufficientStockException,
            IllegalNumberOfBeanBagsSoldException, PriceNotSetException,
            BeanBagIDNotRecognisedException, IllegalIDException
    {
        // Check exceptions.
        if (checkIllegalIDException(id)) {
            throw new IllegalIDException();
        }
        if (checkValidQuantityException(num)) {
            throw new IllegalNumberOfBeanBagsSoldException();
        }

        // Iterate through stock until the beanbag object with a matching id is found.
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            if (stockItem.getId() == id) {
                // If the stock is empty, raise an exception.
                if (stockItem.getNum() == 0) {
                    throw new BeanBagNotInStockException();
                }
                // If there is enough stock available, update the sold array and update the quantity of stock that is
                // left.
                else if (stockItem.getNum() - stockItem.getReserved() - num > 0) {
                    if (stockItem.getPrice() != 0) {
                        for (int j = 0; j < sold.size(); j++) {
                            Beanbag soldItem = ((Beanbag) sold.get(i));
                            if (soldItem.getId() == stockItem.getId()) {
                                soldItem.setNum(soldItem.getNum() + stockItem.getNum());
                                sold.replace(soldItem, j);
                            }
                            else {
                                sold.add(soldItem);
                            }
                        }
                        stockItem.setNum(stockItem.getNum() - num);
                        stock.replace(stockItem, i);
                    }
                    // If the price has not been set yet, raise an exception.
                    else {
                        throw new PriceNotSetException();
                    }
                }
                // If stock is greater than zero but not enough to sell, raise an exception.
                else {
                    throw new InsufficientStockException();
                }
            }
            // If the id was not found, raise an exception.
            else if (i + 1 == stock.size()) {
                throw new BeanBagIDNotRecognisedException();
            }
        }
    }


    public int reserveBeanBags(int num, String id)
            throws BeanBagNotInStockException, InsufficientStockException,
            IllegalNumberOfBeanBagsReservedException, PriceNotSetException,
            BeanBagIDNotRecognisedException, IllegalIDException
    {
        // Check exceptions.
        if (checkIllegalIDException(id)) {
            throw new IllegalIDException();
        }
        if (checkValidQuantityException(num)) {
            throw new IllegalNumberOfBeanBagsReservedException();
        }

        int reservationNum = 0;
        // Iterate through stock until the beanbag object with a matching id is found.
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            if (stockItem.getId() == id) {
                // If the stock is empty, raise an exception.
                if (stockItem.getNum() == 0) {
                    throw new BeanBagNotInStockException();
                }
                // If there is sufficient unreserved stock and the price has bee set, reserve the
                // beanbags and create the reservation number to be returned.
                else if (stockItem.getNum() - stockItem.getReserved() >= num) {
                    if (stockItem.getPrice() != 0) {
                        stockItem.setReserved(stockItem.getReserved() + num);
                        reservationNum = reservations.size() + 1;
                        Reserve newReservation = new Reserve(reservationNum, id, num);
                        reservations.add(newReservation);
                    }
                    // If the price has not been set yet, raise an exception.
                    else {
                        throw new PriceNotSetException();
                    }
                }
                // If stock is greater than zero but not enough to reserve, raise an exception.
                else {
                    throw new InsufficientStockException();
                }
            }
            // If the id was not found, raise an exception.
            else if (i + 1 == stock.size()) {
                throw new BeanBagIDNotRecognisedException();
            }
        }
        return reservationNum;
    }


    public void unreserveBeanBags(int reservationNumber)
            throws ReservationNumberNotRecognisedException
    {
        // Iterate through reservations until the reservation object with a matching reservation number is found.
        for (int i = 0; i < reservations.size(); i++) {
            Reserve reservation = ((Reserve) reservations.get(i));
            // If the reservation is found, loop through the stock to find the stock with the matching id to the
            // reservation. If found, correct the number of beanbags reserved and remove the reservation from the
            // array of reservations. Finally, break from the find reservation loop.
            if (reservation.getReservationNum() == reservationNumber) {
                for (int j = 0; j < stock.size(); j++) {
                    Beanbag stockItem = ((Beanbag) stock.get(j));
                    if (stockItem.getId() == reservation.getId()) {
                        stockItem.setReserved(stockItem.getReserved() - reservation.getNum());
                        reservations.remove(i);
                        break;
                    }
                }
            }
            // If the reservation number was not found, raise an exception.
            else if (i + 1 == reservations.size()) {
                throw new ReservationNumberNotRecognisedException();
            }
        }
    }


    public void sellBeanBags(int reservationNumber)
            throws ReservationNumberNotRecognisedException
    {
        // Iterate through reservations until the reservation object with a matching reservation number is found.
        for (int i = 0; i < reservations.size(); i++) {
            Reserve reservation = ((Reserve) reservations.get(i));
            // If the reservation is found, loop through the stock to find the stock with the matching id to the
            // reservation.
            if (reservation.getReservationNum() == reservationNumber) {
                for (int j = 0; j < stock.size(); j++) {
                    Beanbag stockItem = ((Beanbag) stock.get(j));
                    // If found, update the sold array, correct the number of beanbags reserved and remove the
                    // reservation from the array of reservations. Finally, break from the find reservation loop.
                    if (stockItem.getId() == reservation.getId()) {
                        for (int k = 0; k < sold.size(); k++) {
                            Beanbag soldItem = ((Beanbag) sold.get(i));
                            if (soldItem.getId() == stockItem.getId()) {
                                soldItem.setNum(soldItem.getNum() + stockItem.getNum());
                                sold.replace(soldItem, k);
                            }
                            else {
                                sold.add(soldItem);
                            }
                        }
                        stockItem.setReserved(stockItem.getReserved() - reservation.getNum());
                        reservations.remove(i);
                        sold.add(stockItem);
                        break;
                    }
                }
            }
            // If the reservation number was not found, raise an exception.
            else if (i + 1 == reservations.size()) {
                throw new ReservationNumberNotRecognisedException();
            }
        }
    }


    public int beanBagsInStock()
    {
        int totalStock = 0;
        // Iterate through the entire stock adding the number of beanbags in each stock item to 'totalStock'.
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            totalStock += stockItem.getNum();
        }
        return totalStock;
    }


    public int reservedBeanBagsInStock()
    {
        int totalReservedStock = 0;
        // Iterate through the entire stock adding the number of reserved beanbags in each stock item to
        // 'totalReservedStock'.
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            totalReservedStock += stockItem.getReserved();
        }
        return totalReservedStock;
    }


    public int beanBagsInStock(String id)
            throws BeanBagIDNotRecognisedException, IllegalIDException
    {
        // Check exception.
        if (checkIllegalIDException(id)) {
            throw new IllegalIDException();
        }

        int totalStockMatchingID = 0;
        // Iterate through stock until the beanbag object with a matching id is found.
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            // If found, add the total number of beanbags that stock contains to 'totalStockMatchingID'.
            if (stockItem.getId() == id) {
                totalStockMatchingID += stockItem.getNum();
            }
            // If the id was not found, raise an exception.
            else if (i + 1 == stock.size()) {
                throw new BeanBagIDNotRecognisedException();
            }
        }
        return totalStockMatchingID;
    }


    public void saveStoreContents(String filename)
            throws IOException
    {
        String fileName = filename + ".txt";
        FileOutputStream textFile = new FileOutputStream(new File(fileName));
        ObjectOutputStream object = new ObjectOutputStream(textFile);

        //stock and reservations are both objects created from ObjectArrayList.
        object.writeObject(stock);
        object.writeObject(reservations);
        object.close();
        textFile.close();

    }


    public void loadStoreContents(String filename)
            throws IOException, ClassNotFoundException
    {
        String fileName = filename + ".txt";
        try {
            FileInputStream textFile = new FileInputStream(new File(fileName));
            ObjectInputStream oi = new ObjectInputStream(textFile);

            ObjectArrayList stock = (ObjectArrayList) oi.readObject();
            ObjectArrayList reservations = (ObjectArrayList) oi.readObject();
            setStock(stock);
            setReservations(reservations);
        }
        catch(IOException e) {
            throw new IOException();
        }
        catch(ClassNotFoundException e) {
            throw new ClassNotFoundException();
        }
    }


    public int getNumberOfDifferentBeanBagsInStock()
    {
        int totalDiffStock = 0;
        // Iterate through the entire stock incrementing 'totalDiffStock' by one if the stock item contains a level of
        // stock greater than zero.
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            if (stockItem.getNum() > 0)
                totalDiffStock += 1;
        }
        return totalDiffStock;
    }


    public int getNumberOfSoldBeanBags()
    {
        int totalSold = 0;
        // Iterate through the entire sold array adding the number of beanbags in each sold object to 'totalSold'.
        for (int i = 0; i < sold.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            totalSold += stockItem.getNum();
        }
        return totalSold;
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
