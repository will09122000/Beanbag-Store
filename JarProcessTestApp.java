import beanbags.*;
import java.io.IOException;

/**
 * Please follow instructions in the ECM1410_CA_jar_walkthrough
 * document in conjunction with this small application to
 * check you have built your jar file correctly
 * <p>
 * You should of course expand this and instantiate a Store
 * instance when checking the performance of the package as
 * your pair develops it
 *
 * @author Jonathan Fieldsend 
 * @version 1.0
 */
public class JarProcessTestApp {

    public static void main(String[] args)
            throws BeanBagIDNotRecognisedException, BeanBagMismatchException, BeanBagNotInStockException,
            IllegalIDException, IllegalNumberOfBeanBagsAddedException, IllegalNumberOfBeanBagsReservedException,
            IllegalNumberOfBeanBagsSoldException, InsufficientStockException, InvalidMonthException,
            InvalidPriceException, PriceNotSetException, ReservationNumberNotRecognisedException, IOException,
            ClassNotFoundException

    {
        Store store = new Store();

        short year = 2020;
        byte month = 01;
        store.addBeanBags(50, "Will's Beanbag maker", "Comfy beanbag", "12345678", year, month, "INFORAMTTION");
        store.addBeanBags(60, "Will's Beanbag maker", "Comfy beanbag", "A2345678", year, month);

        System.out.println("Store instance successfully made, with "
                + store.beanBagsInStock()
                + " beanbags in stock.");

        store.setBeanBagPrice("12345678", 1);
        store.setBeanBagPrice("A2345678", 1);
        /*
        store.sellBeanBags(5, "12345678");
        store.sellBeanBags(10, "A2345678");
        System.out.println(store.reserveBeanBags(15, "12345678"));
        System.out.println(store.reserveBeanBags(20, "A2345678"));
        System.out.println(store.reserveBeanBags(15, "12345678"));
        store.unreserveBeanBags(3);
        System.out.println(store.reserveBeanBags(15, "12345678"));
        store.sellBeanBags(1);
        store.sellBeanBags(2);
        System.out.println(store.beanBagsInStock());
        System.out.println(store.reservedBeanBagsInStock());
        System.out.println(store.beanBagsInStock("12345678"));
        System.out.println(store.getNumberOfDifferentBeanBagsInStock());
        System.out.println(store.getNumberOfSoldBeanBags());
        System.out.println(store.getNumberOfSoldBeanBags("12345678"));
        System.out.println(store.getTotalPriceOfSoldBeanBags());
        System.out.println(store.getTotalPriceOfSoldBeanBags("12345678"));
        System.out.println(store.getTotalPriceOfReservedBeanBags());
        System.out.println(store.getBeanBagDetails("12345678"));
        store.replace("12345678", "87654321");
        System.out.println(store.getBeanBagDetails("87654321"));
         */
    }
}