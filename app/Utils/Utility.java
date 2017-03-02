package Utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static play.mvc.Controller.request;

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

    public void deleteOldImage(String path) {
        Logger.debug("Delete old image");
        File f_d;
        f_d = new File(path);
        if (f_d.isDirectory()) {
            for (String x : f_d.list()) {
                new File(f_d, x).delete();

            }
        }
    }

    public ArrayList<String> uploadImage(String path, int numberOfImages) {
        //numberOfImages count starts from 1
        Http.MultipartFormData body = request().body().asMultipartFormData();
        ArrayList<String> imageNames = new ArrayList<String>();
        for (int i = 1; i < numberOfImages + 1; i++) {
            Http.MultipartFormData.FilePart uploadFile = body.getFile("image" + i);
            imageNames.add(saveImageToDisk(uploadFile, path));
        }
        return imageNames;
    }

    public String saveImageToDisk(Http.MultipartFormData.FilePart uploadFile, String path) {
        String file_name = uploadFile.getFilename();

        File uploadF = (File) uploadFile.getFile();
        String newFileName = System.currentTimeMillis() + "-" + file_name;
        File openFile = new File(path + "/" + newFileName);
        String[] x = new String[]{newFileName, uploadFile.getContentType()};
        InputStream isFile1 = null;

        try {
            isFile1 = new FileInputStream(uploadF);
            byte[] byteFile1 = IOUtils.toByteArray(isFile1);

            FileUtils.writeByteArrayToFile(openFile, byteFile1);
            isFile1.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.debug(String.valueOf(Json.toJson(x)));
        return newFileName.replace(' ', '-');
    }
}
