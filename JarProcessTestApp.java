import beanbags.Store;
import beanbags.BeanBagStore;

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

    public static void main(String[] args) {
        Store store = new Store();

        short year = 2020;
        byte month = 02;
        store.addBeanBags(12, "Will's Beanbag maker", "Comfy beanbag", "12345678", year, month, "INformationnnn");

        System.out.println("Store instance successfully made, with "
                + store.beanBagsInStock()
                + " beanbags in stock.");

        store.setBeanBagPrice("12345678", 500);
        store.sellBeanBags(5, "12345678");
        int test = store.reserveBeanBags(4, "12345678");
    }
}