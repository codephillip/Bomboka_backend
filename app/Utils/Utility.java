package Utils;

/**
 * Created by codephillip on 01/12/16.
 */
public class Utility {

    public static boolean isEmpty(String text) {
        return (text == null);
    }

    // means it contains text. True if contains . False if it is null
    public static boolean isNotEmpty(String text) {
        return (text != null);
    }

    public static final String PROFILE_IMAGE = "profileImage";
    public static final String PRODUCT_IMAGE = "productImage";
    public static final String ADVERT_IMAGE = "advertImage";
}
