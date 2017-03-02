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

    public static final String PROFILE_IMAGE = "profile_image";
    public static final String PRODUCT_IMAGE = "product_image";
    public static final String ADVERT_IMAGE = "advert_image";
}
