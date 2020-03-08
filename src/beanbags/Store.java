package beanbags;
import java.io.IOException;

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

    public void setSold(ObjectArrayList sold){
        this.sold = sold;
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
        // Checks the ID contains characters A-F and 0-0 only and checks the length is equal to 8.
        if (id.matches("^[0-9a-fA-F]+$") && id.length() == 8) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInvalidMonthException(byte month) {
        // Checks the month is between 1 and 12.
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

        assert year > 0 : "Year must be greater than zero.";
        // If no exceptions thrown, add beanbag object to stock array.
        int stockSize = stock.size();
        stock.add(beanbag);
        assert stockSize + 1 == stock.size() : "Stock size has not increased by one";
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

        assert year > 0 : "Year must be greater than zero.";
        // If no exceptions thrown, add beanbag object to stock array.
        int stockSize = stock.size();
        stock.add(beanbag);
        assert stockSize + 1 == stock.size() : "Stock size has not increased by one.";
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
        if (stock.size() > 0) {
            for (int i = 0; i < stock.size(); i++) {
                Beanbag stockItem = ((Beanbag) stock.get(i));
                if (stockItem.getId().equals(id)) {
                    stockItem.setPrice(priceInPence);
                    for (int j = 0; j < reservations.size(); j++) {
                        Reserve reservation = ((Reserve) reservations.get(j));
                        if (reservation.getId().equals(id)) {
                            if (reservation.getPrice() > priceInPence)
                                reservation.setPrice(priceInPence);
                        }
                    }
                    break;
                }
                // If the id was not found, raise an exception.
                else if (i + 1 == stock.size()) {
                    throw new BeanBagIDNotRecognisedException();
                }
            }
        }
        // If no stock present, raise an exception.
        else {
            throw new BeanBagIDNotRecognisedException();
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
        if (stock.size() > 0) {
            for (int i = 0; i < stock.size(); i++) {
                Beanbag stockItem = ((Beanbag) stock.get(i));
                if (stockItem.getId().equals(id)) {
                    // If the stock is empty, raise an exception.
                    if (stockItem.getNum() == 0) {
                        throw new BeanBagNotInStockException();
                    }
                    // If there is enough stock available, update the sold array and update the quantity of stock that is
                    // left.
                    else if (stockItem.getNum() - stockItem.getReserved() - num >= 0) {
                        if (stockItem.getPrice() != 0) {
                            if (sold.size() > 0) {
                                for (int j = 0; j < sold.size(); j++) {
                                    Sold soldItem = ((Sold) sold.get(j));
                                    if (soldItem.getId().equals(stockItem.getId())) {
                                        soldItem.setNum(soldItem.getNum() + num);
                                    }
                                    else if (i + 1 == sold.size()) {
                                        int soldSize = sold.size();
                                        Sold newSold = new Sold(num, stockItem.getManufacturer(), stockItem.getName(), id, stockItem.getYear(), stockItem.getMonth(), stockItem.getInformation());
                                        newSold.setPrice(stockItem.getPrice());
                                        sold.add(newSold);
                                        assert soldSize + 1 == sold.size() : "Sold size has not increased by one.";
                                    }
                                }
                            } else {
                                int soldSize = sold.size();
                                Sold newSold = new Sold(num, stockItem.getManufacturer(), stockItem.getName(), id, stockItem.getYear(), stockItem.getMonth(), stockItem.getInformation());
                                newSold.setPrice(stockItem.getPrice());
                                sold.add(newSold);
                                assert soldSize + 1 == sold.size() : "Sold size has not increased by one.";
                            }
                            stockItem.setNum(stockItem.getNum() - num);
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
                    break;
                }
                // If the id was not found, raise an exception.
                else if (i + 1 == stock.size()) {
                    throw new BeanBagIDNotRecognisedException();
                }
            }
        }
        // If no stock present, raise an exception.
        else {
            throw new BeanBagIDNotRecognisedException();
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
        if (stock.size() > 0) {
            // Iterate through stock until the beanbag object with a matching id is found.
            for (int i = 0; i < stock.size(); i++) {
                Beanbag stockItem = ((Beanbag) stock.get(i));
                if (stockItem.getId().equals(id)) {
                    // If the stock is empty, raise an exception.
                    if (stockItem.getNum() == 0) {
                        throw new BeanBagNotInStockException();
                    }
                    // If there is sufficient unreserved stock and the price has been set, reserve the
                    // beanbags and create the reservation number to be returned.
                    else if (stockItem.getNum() - stockItem.getReserved() >= num) {
                        if (stockItem.getPrice() != 0) {
                            stockItem.setReserved(stockItem.getReserved() + num);
                            reservationNum = reservations.size() + 1;
                            int price = stockItem.getPrice();
                            Reserve newReservation = new Reserve(reservationNum, id, num, price);

                            int reservationsSize = reservations.size();
                            reservations.add(newReservation);
                            assert reservationsSize + 1 == reservations.size() : "Reservations size has not increased by one.";
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
                    break;
                }
                // If the id was not found, raise an exception.
                else if (i + 1 == stock.size()) {
                    throw new BeanBagIDNotRecognisedException();
                }
            }
        }
        // If no stock present, raise an exception.
        else {
            throw new BeanBagIDNotRecognisedException();
        }
        return reservationNum;
    }

    public void unreserveBeanBags(int reservationNumber)
            throws ReservationNumberNotRecognisedException
    {
        // Iterate through reservations until the reservation object with a matching reservation number is found.
        if (reservations.size() > 0) {
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

                            int reservationsSize = reservations.size();
                            reservations.remove(i);
                            assert reservationsSize - 1 == reservations.size() : "Reservations size has not decreased by one.";
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
        // If reservation number not found, raise an exception.
        else {
            throw new ReservationNumberNotRecognisedException();
        }
    }

    public void sellBeanBags(int reservationNumber)
            throws ReservationNumberNotRecognisedException
    {
        // Iterate through reservations until the reservation object with a matching reservation number is found.
        if (reservations.size() > 0) {
            for (int i = 0; i < reservations.size(); i++) {
                Reserve reservation = ((Reserve) reservations.get(i));
                // If the reservation is found, loop through the stock to find the stock with the matching id to the
                // reservation.
                if (reservation.getReservationNum() == reservationNumber) {
                    for (int j = 0; j < stock.size(); j++) {
                        Beanbag stockItem = ((Beanbag) stock.get(j));
                        // If found, update the sold array, correct the number of beanbags reserved and remove the
                        // reservation from the array of reservations. Finally, break from the find reservation loop.
                        if (stockItem.getId().equals(reservation.getId())) {
                            if (sold.size() > 0) {
                                for (int k = 0; k < sold.size(); k++) {
                                    Sold soldItem = ((Sold) sold.get(k));
                                    if (soldItem.getId().equals(stockItem.getId())) {
                                        soldItem.setNum(soldItem.getNum() + reservation.getNum());
                                    }
                                    else if (i + 1 == sold.size()) {
                                        int soldSize = sold.size();
                                        Sold newSold = new Sold(reservation.getNum(), stockItem.getManufacturer(), stockItem.getName(), stockItem.getId(), stockItem.getYear(), stockItem.getMonth(), stockItem.getInformation());
                                        newSold.setPrice(stockItem.getPrice());
                                        sold.add(newSold);
                                        assert soldSize + 1 == sold.size() : "Sold size has not increased by one.";
                                    }
                                }
                            } else {
                                int soldSize = sold.size();
                                Sold newSold = new Sold(reservation.getNum(), stockItem.getManufacturer(), stockItem.getName(), stockItem.getId(), stockItem.getYear(), stockItem.getMonth(), stockItem.getInformation());
                                newSold.setPrice(stockItem.getPrice());
                                sold.add(newSold);
                                assert soldSize + 1 == sold.size() : "Sold size has not increased by one.";
                            }
                            stockItem.setReserved(stockItem.getReserved() - reservation.getNum());
                            stockItem.setNum(stockItem.getNum() - reservation.getNum());
                            reservations.remove(i);
                            break;
                        }
                    }
                    break;
                }
                // If the reservation number was not found, raise an exception.
                else if (i + 1 == reservations.size()) {
                    throw new ReservationNumberNotRecognisedException();
                }
            }
        }
        // If reservation number not found, raise an exception.
        else {
            throw new ReservationNumberNotRecognisedException();
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
        assert totalStock >= 0 : "Total stock is less than zero.";
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
        assert totalReservedStock >= 0 : "Total reserved stock is less than zero." ;
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
        if (stock.size() > 0) {
            // Iterate through stock until the beanbag object with a matching id is found.
            for (int i = 0; i < stock.size(); i++) {
                Beanbag stockItem = ((Beanbag) stock.get(i));
                // If found, add the total number of beanbags that stock contains to 'totalStockMatchingID'.
                if (stockItem.getId().equals(id)) {
                    totalStockMatchingID += stockItem.getNum();
                    break;
                }
                // If the id was not found, raise an exception.
                else if (i + 1 == stock.size()) {
                    throw new BeanBagIDNotRecognisedException();
                }
            }
        }
        // If the id was not found, raise an exception.
        else {
            throw new BeanBagIDNotRecognisedException();
        }
        assert totalStockMatchingID >= 0 : "Total stock with matching ID is less than zero.";
        return totalStockMatchingID;
    }

    public void saveStoreContents(String filename)
            throws IOException
    {
        String fileName = filename + ".ser";
        // Create the object output stream.
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            // Write the three ObjectArrayList instances to the file.
            out.writeObject(stock);
            out.writeObject(reservations);
            out.writeObject(sold);
        }
        catch (IOException e) {
            throw new IOException();
        }
    }

    public void loadStoreContents(String filename)
            throws IOException, ClassNotFoundException
    {
        String fileName = filename + ".ser";
        ObjectArrayList stock=null, reservations=null, sold=null;

        // Create the object input stream.
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            // Read in an object three time, if it's an instance of ObjectArrayList, assign the object to the empty
            // ObjectArrayList variables.
            Object obj = in.readObject();
            if (obj instanceof ObjectArrayList)
                stock = (ObjectArrayList) obj;
            else
                throw new ClassNotFoundException();
            obj = in.readObject();
            if (obj instanceof ObjectArrayList)
                reservations = (ObjectArrayList) obj;
            else
                throw new ClassNotFoundException();
            obj = in.readObject();
            if (obj instanceof ObjectArrayList)
                sold = (ObjectArrayList) obj;
            else
                throw new ClassNotFoundException();

            // Set the ObjectArrayList using the setters defined in their classes.
            setStock(stock);
            setReservations(reservations);
            setSold(sold);
        }
        catch(IOException e) {
            throw new IOException();
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
        assert totalDiffStock >= 0 : "Total number of different types of beanbags is less than zero.";
        return totalDiffStock;
    }

    public int getNumberOfSoldBeanBags()
    {
        int totalSold = 0;
        // Iterate through the entire sold array adding the number of beanbags in each sold object to 'totalSold'.
        for (int i = 0; i < sold.size(); i++) {
            Sold stockItem = ((Sold) sold.get(i));
            totalSold += stockItem.getNum();
        }
        assert totalSold >= 0 : "Total number of sold beanbags is less than zero.";
        return totalSold;
    }

    public int getNumberOfSoldBeanBags(String id)
            throws BeanBagIDNotRecognisedException, IllegalIDException
    {
        // Check exception.
        if (checkIllegalIDException(id)) {
            throw new IllegalIDException();
        }

        int totalSold = 0;
        if (sold.size() > 0) {
            // Iterate through the entire sold array adding the number of beanbags if the ID matches to 'totalSold'.
            for (int i = 0; i < sold.size(); i++) {
                Sold stockItem = ((Sold) sold.get(i));
                if (stockItem.getId().equals(id)) {
                    totalSold += stockItem.getNum();
                    break;
                }
                // If the id was not found, raise an exception.
                else if (i + 1 == sold.size()) {
                    throw new BeanBagIDNotRecognisedException();
                }
            }
        }
        // If the id was not found, raise an exception.
        else {
            throw new BeanBagIDNotRecognisedException();
        }
        assert totalSold >= 0 : "Total number of sold beanbags with matching ID is less than zero.";
        return totalSold;
    }

    public int getTotalPriceOfSoldBeanBags()
    {
        int totalPrice = 0;
        // Iterate through the entire sold array adding the number of beanbags multiplied by the price of that beanbag
        // in each sold object to 'totalSold'.
        for (int i = 0; i < sold.size(); i++) {
            Sold soldItem = ((Sold) sold.get(i));
            //System.out.println(soldItem.getNum());
            //System.out.println(soldItem.getPrice());
            totalPrice += soldItem.getNum() * soldItem.getPrice();
        }
        assert totalPrice >= 0 : "Total price of sold beanbags is less than zero.";
        return totalPrice;
    }

    public int getTotalPriceOfSoldBeanBags(String id)
            throws BeanBagIDNotRecognisedException, IllegalIDException
    {
        // Check exception.
        if (checkIllegalIDException(id)) {
            throw new IllegalIDException();
        }

        int totalPrice = 0;
        // Iterate through the entire sold array adding the number of beanbags multiplied by the price of that beanbag
        // if the ID matches to 'totalSold'.
        if (sold.size() > 0) {
            for (int i = 0; i < sold.size(); i++) {
                Sold soldItem = ((Sold) sold.get(i));
                if (soldItem.getId().equals(id)) {
                    totalPrice += soldItem.getNum() * soldItem.getPrice();
                    break;
                }
                // If the id was not found, raise an exception.
                else if (i + 1 == sold.size()) {
                    throw new BeanBagIDNotRecognisedException();
                }
            }
        }
        // If the id was not found, raise an exception.
        else {
            throw new BeanBagIDNotRecognisedException();
        }
        assert totalPrice >= 0 : "Total price of sold beanbags with matching ID is less than zero.";
        return totalPrice;
    }

    public int getTotalPriceOfReservedBeanBags()
    {
        int totalPrice = 0;
        // Iterate through the entire stock array adding the number of beanbags that are reserved multiplied by the
        // price of that beanbag in each sold object to 'totalSold'.
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            totalPrice += stockItem.getReserved() * stockItem.getPrice();
        }
        assert totalPrice >= 0 : "Total price of reserved beanbags is less than zero.";
        return totalPrice;
    }

    public String getBeanBagDetails(String id)
            throws BeanBagIDNotRecognisedException, IllegalIDException
    {
        // Check exception.
        if (checkIllegalIDException(id)) {
            throw new IllegalIDException();
        }

        String BeanBagDetails = "";
        // Iterate through the entire stock array assinging the free text detail of the beanbag object with a matching
        // ID.
        if (stock.size() > 0) {
            for (int i = 0; i < stock.size(); i++) {
                Beanbag stockItem = ((Beanbag) stock.get(i));
                if (stockItem.getId().equals(id)) {
                    BeanBagDetails = stockItem.getInformation();
                    break;
                }
                // If the id was not found, raise an exception.
                else if (i + 1 == stock.size()) {
                    throw new BeanBagIDNotRecognisedException();
                }
            }
        }
        // If the id was not found, raise an exception.
        else {
            throw new BeanBagIDNotRecognisedException();
        }
        return BeanBagDetails;
    }

    public void empty()
    {
        setStock(new ObjectArrayList());
        setReservations(new ObjectArrayList());
        setSold(new ObjectArrayList());
    }

    public void resetSaleAndCostTracking()
    {
        setSold(new ObjectArrayList());
    }

    public void replace(String oldId, String replacementId)
            throws BeanBagIDNotRecognisedException, IllegalIDException
    {
        // Check exception for both input ID's.
        if (checkIllegalIDException(oldId) || checkIllegalIDException(replacementId)) {
            throw new IllegalIDException();
        }

        // Iterate through the entire stock array to find the beanbag object with id 'oldId', use the set method to
        // change the id of the beanbag object to the new id and replace that beanbag object in the stock array with
        // the updated object.
        for (int i = 0; i < stock.size(); i++) {
            Beanbag stockItem = ((Beanbag) stock.get(i));
            if (stockItem.getId().equals(oldId)) {
                stockItem.setId(replacementId);
                break;
            }
            // If the id was not found, raise an exception.
            else if (i + 1 == stock.size()) {
                throw new BeanBagIDNotRecognisedException();
            }
        }

        // Same as above but for reservations.
        for (int i = 0; i < reservations.size(); i++) {
            Reserve stockItem = ((Reserve) reservations.get(i));
            if (stockItem.getId().equals(oldId)) {
                stockItem.setId(replacementId);
            }
        }
    }

}
