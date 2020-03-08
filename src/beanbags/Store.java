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

    // Setters.

    public void setStock(ObjectArrayList stock){
        this.stock = stock;
    }

    public void setReservations(ObjectArrayList reservations){
        this.reservations = reservations;
    }

    public void setSold(ObjectArrayList sold){
        this.sold = sold;
    }

    // Methods to check common exceptions.

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

    /**
     * Method adds bean bags to the store with the arguments as bean bag details.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param num               number of bean bags added
     * @param manufacturer      bean bag manufacturer
     * @param name              bean bag name
     * @param id                ID of bean bag
     * @param year              year of manufacture
     * @param month             month of manufacture
     * @throws IllegalNumberOfBeanBagsAddedException   if the number to be added
     *                           is less than 1
     * @throws BeanBagMismatchException if the id already exists (as a current in
     *                           stock bean bag, or one that has been previously
     *                           stocked in the store, but the other stored
     *                           elements (manufacturer, name and free text) do
     *                           not match the pre-existing version
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     * @throws InvalidMonthException    if the month is not in the range 1 to 12
     */
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

    /**
     * Method adds bean bags to the store with the arguments as bean bag details.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param num               number of bean bags added
     * @param manufacturer      bean bag manufacturer
     * @param name              bean bag name
     * @param id                ID of bean bag
     * @param year              year of manufacture
     * @param month             month of manufacture
     * @param information       free text detailing bean bag information
     * @throws IllegalNumberOfBeanBagsAddedException   if the number to be added
     *                           is less than 1
     * @throws BeanBagMismatchException if the id already exists (as a current in
     *                           stock bean bag, or one that has been previously
     *                           stocked in the store, but the other stored
     *                           elements (manufacturer, name and free text) do
     *                           not match the pre-existing version
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     * @throws InvalidMonthException    if the month is not in the range 1 to 12
     */
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

    /**
     * Method to set the price of bean bags with matching ID in stock.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param id                ID of bean bags
     * @param priceInPence      bean bag price in pence
     * @throws InvalidPriceException if the priceInPence < 1
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException if the ID is not a positive eight character
     *                           hexadecimal number
     */
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
                    // If the id was found, set the price of that beanbag object.
                    stockItem.setPrice(priceInPence);
                    // Iterate through current reservations.
                    for (int j = 0; j < reservations.size(); j++) {
                        Reserve reservation = ((Reserve) reservations.get(j));
                        // If a reservation is found with a matching id and the new price is less than the current
                        // price on the reservation, edit the reservation price to the lower price.
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

    /**
     * Method sells bean bags with the corresponding ID from the store and removes
     * the sold bean bags from the stock.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param num           number of bean bags to be sold
     * @param id            ID of bean bags to be sold
     * @throws BeanBagNotInStockException   if the bean bag has previously been in
     *                      stock, but is now out of stock
     * @throws InsufficientStockException   if the bean bag is in stock, but not
     *                      enough are available (i.e. in stock and not reserved)
     *                      to meet sale demand
     * @throws IllegalNumberOfBeanBagsSoldException if an attempt is being made to
     *                      sell fewer than 1 bean bag
     * @throws PriceNotSetException if the bag is in stock, and there is sufficient
     *                      stock to meet demand, but the price has yet to be set
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
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
                    // Check that there is enough stock left.
                    else if (stockItem.getNum() - stockItem.getReserved() - num >= 0) {
                        if (stockItem.getPrice() != 0) {
                            if (sold.size() > 0) {
                                for (int j = 0; j < sold.size(); j++) {
                                    Sold soldItem = ((Sold) sold.get(j));
                                    // If the stock has been sold before, increment the total number that have been
                                    // sold.
                                    if (soldItem.getId().equals(stockItem.getId())) {
                                        soldItem.setNum(soldItem.getNum() + num);
                                    }
                                    // If it hasn't been sold before, create a new Sold object and add it to the sold
                                    // array.
                                    else if (i + 1 == sold.size()) {
                                        int soldSize = sold.size();
                                        Sold newSold = new Sold(num, stockItem.getManufacturer(), stockItem.getName(),
                                                                id, stockItem.getYear(), stockItem.getMonth(),
                                                                stockItem.getInformation());
                                        newSold.setPrice(stockItem.getPrice());
                                        sold.add(newSold);
                                        assert soldSize + 1 == sold.size() : "Sold size has not increased by one.";
                                    }
                                }
                            }
                            // If the sold array is empty, create a new Sold object and add it to the sold array.
                            else {
                                int soldSize = sold.size();
                                Sold newSold = new Sold(num, stockItem.getManufacturer(), stockItem.getName(), id,
                                                        stockItem.getYear(), stockItem.getMonth(),
                                                        stockItem.getInformation());
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

    /**
     * Method reserves bean bags with the corresponding ID in the store and returns
     * the reservation number needed to later access the reservation
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param num           number of bean bags to be reserved
     * @param id            ID of bean bags to be reserved
     * @return              unique reservation number, i.e. one not currently live
     *                      in the system
     * @throws BeanBagNotInStockException   if the bean bag has previously been in
     *                      stock, but is now out of stock
     * @throws InsufficientStockException   if the bean bag is in stock, but not
     *                      enough are available to meet the reservation demand
     * @throws IllegalNumberOfBeanBagsReservedException if the number of bean bags
     *                      requested to reserve is fewer than 1
     * @throws PriceNotSetException if the bag is in stock, and there is sufficient
     *                      stock to meet demand, but the price has yet to be set
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
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

                            // Add the new reservation object to the reservations array.
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

    /**
     * Method removes an existing reservation from the system due to a reservation
     * cancellation (rather than sale). The stock should therefore remain unchanged.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param reservationNumber           reservation number
     * @throws ReservationNumberNotRecognisedException  if the reservation number
     *                          does not match a current reservation in the system
     */
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

    /**
     * Method sells beanbags with the corresponding reservation number from
     * the store and removes these sold beanbags from the stock.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param reservationNumber           unique reservation number used to find
     *                                    beanbag(s) to be sold
     * @throws ReservationNumberNotRecognisedException  if the reservation number
     *                          does not match a current reservation in the system
     */
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
                        // If found, loop through the sold array to find and sold objects  with the matching id to the
                        // reservation.
                        if (stockItem.getId().equals(reservation.getId())) {
                            if (sold.size() > 0) {
                                for (int k = 0; k < sold.size(); k++) {
                                    Sold soldItem = ((Sold) sold.get(k));
                                    // If a matching id is found, increment the number sold for that sold object.
                                    if (soldItem.getId().equals(stockItem.getId())) {
                                        soldItem.setNum(soldItem.getNum() + reservation.getNum());
                                    }
                                    // If a matching id is not found, create a new sold object and add it to the sold
                                    // array.
                                    else if (i + 1 == sold.size()) {
                                        int soldSize = sold.size();
                                        Sold newSold = new Sold(reservation.getNum(), stockItem.getManufacturer(),
                                                                stockItem.getName(), stockItem.getId(),
                                                                stockItem.getYear(), stockItem.getMonth(),
                                                                stockItem.getInformation());
                                        newSold.setPrice(stockItem.getPrice());
                                        sold.add(newSold);
                                        assert soldSize + 1 == sold.size() : "Sold size has not increased by one.";
                                    }
                                }
                            }
                            // If the sold array is empty, create a new sold object and add it to the sold
                            // array.
                            else {
                                int soldSize = sold.size();
                                Sold newSold = new Sold(reservation.getNum(), stockItem.getManufacturer(),
                                                        stockItem.getName(), stockItem.getId(), stockItem.getYear(),
                                                        stockItem.getMonth(), stockItem.getInformation());
                                newSold.setPrice(stockItem.getPrice());
                                sold.add(newSold);
                                assert soldSize + 1 == sold.size() : "Sold size has not increased by one.";
                            }
                            // Alter the amount of stock available and reserved in the stock object.
                            stockItem.setReserved(stockItem.getReserved() - reservation.getNum());
                            stockItem.setNum(stockItem.getNum() - reservation.getNum());
                            // Remove the reservation from the reservation array.
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

    /**
     * Access method for the number of BeanBags stocked by this BeanBagStore
     * (total of reserved and unreserved stock).
     *
     * @return                  number of bean bags in this store
     */
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

    /**
     * Access method for the number of reserved bean bags stocked by this
     * BeanBagStore.
     *
     * @return                  number of reserved bean bags in this store
     */
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

    /**
     * Method returns number of bean bags with matching ID in stock (total
     * researved and unreserved).
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param id            ID of bean bags
     * @return              number of bean bags matching ID in stock
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
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

    /**
     * Method saves this BeanBagStore's contents into a serialised file,
     * with the filename given in the argument.
     *
     * @param filename      location of the file to be saved
     * @throws IOException  if there is a problem experienced when trying to save
     *                      the store contents to the file
     */
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

    /**
     * Method should load and replace this BeanBagStore's contents with the
     * serialised contents stored in the file given in the argument.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param filename      location of the file to be loaded
     * @throws IOException  if there is a problem experienced when trying to load
     *                      the store contents from the file
     * @throws ClassNotFoundException   if required class files cannot be found when
     *                      loading
     */
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

    /**
     * Access method for the number of different bean bags currently stocked by this
     * BeanBagStore.
     *
     * @return                  number of different specific bean bags currently in
     *                          this store (i.e. how many different IDs represented
     *                          by bean bags currently in stock, including reserved)
     */
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

    /**
     * Method to return number of bean bags sold by this BeanBagStore.
     *
     * @return                  number of bean bags sold by the store
     */
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

    /**
     * Method to return number of bean bags sold by this BeanBagStore with
     * matching ID.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param id                 ID of bean bags
     * @return                   number bean bags sold by the store with matching ID
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
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

    /**
     * Method to return total price of bean bags sold by this BeanBagStore
     * (in pence), i.e. income that has been generated by these sales).
     *
     * @return                  total cost of bean bags sold (in pence)
     */
    public int getTotalPriceOfSoldBeanBags()
    {
        int totalPrice = 0;
        // Iterate through the entire sold array adding the number of beanbags multiplied by the price of that beanbag
        // in each sold object to 'totalSold'.
        for (int i = 0; i < sold.size(); i++) {
            Sold soldItem = ((Sold) sold.get(i));
            totalPrice += soldItem.getNum() * soldItem.getPrice();
        }
        assert totalPrice >= 0 : "Total price of sold beanbags is less than zero.";
        return totalPrice;
    }

    /**
     * Method to return total price of bean bags sold by this BeanBagStore
     * (in pence) with  matching ID (i.e. income that has been generated
     * by these sales).
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param id                ID of bean bags
     * @return                  total cost of bean bags sold (in pence) with
     *                          matching ID
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
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

    /**
     * Method to return the total price of reserved bean bags in this BeanBagStore
     * (i.e. income that would be generated if all the reserved stock is sold
     * to those holding the reservations).
     *
     * @return                  total price of reserved bean bags
     */
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

    /**
     * Method to return the free text details of a bean bag in stock. If there
     * are no String details for a bean bag, there will be an empty String
     * instance returned.
     * <p>
     * The state of this BeanBagStore must be be unchanged if any exceptions are
     * thrown.
     *
     * @param id                ID of bean bag
     * @return                  any free text details relating to the bean bag
     * @throws BeanBagIDNotRecognisedException  if the ID is legal, but does not
     *                          match any bag in (or previously in) stock
     * @throws IllegalIDException   if the ID is not a positive eight character
     *                           hexadecimal number
     */
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

    /**
     * Method empties this BeanBagStore of its contents and resets
     * all internal counters.
     */
    public void empty()
    {
        // Create empty object array lists setting them to stock, reservations and sold attrbutes of the store object.
        setStock(new ObjectArrayList());
        setReservations(new ObjectArrayList());
        setSold(new ObjectArrayList());
    }

    /**
     * Method resets the tracking of number and costs of all bean bags sold.
     * The stock levels of this BeanBagStore and reservations should
     * be unaffected.
     */
    public void resetSaleAndCostTracking()
    {
        // Create an empty object array list setting it to the sold attrbute of the store object.
        setSold(new ObjectArrayList());
    }

    /**
     * Method replaces the ID of current stock matching the first argument with the
     * ID held in the second argument. To be used if there was e.g. a data entry
     * error on the ID initially entered. After the method has completed all stock
     * which had the old ID should now have the replacement ID (including
     * reservations), and all trace of the old ID should be purged from the system
     * (e.g. tracking of previous sales that had the old ID should reflect the
     * replacement ID).
     * <p>
     * If the replacement ID already exists in the system, this method will return
     * an {@link IllegalIDException}.
     *
     * @param oldId             old ID of bean bags
     * @param replacementId     replacement ID of bean bags
     * @throws BeanBagIDNotRecognisedException  if the oldId does not match any
     *                          bag in (or previously in) stock
     * @throws IllegalIDException   if either argument is not a positive eight
     *                          character hexadecimal number, or if the
     *                          replacementID is already in use in the store as
     *                          an ID
     */
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

        // Same as above but for current reservations.
        for (int i = 0; i < reservations.size(); i++) {
            Reserve stockItem = ((Reserve) reservations.get(i));
            if (stockItem.getId().equals(oldId)) {
                stockItem.setId(replacementId);
            }
        }
    }

}
