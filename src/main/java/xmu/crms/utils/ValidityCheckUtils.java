package xmu.crms.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * validity check tool
 * @author Jackey
 *
 */
public class ValidityCheckUtils {
    /**
     * Validaity check of phone number
     * @param phone
     * @return  only if validity
     */
    public static boolean isPhoneFitFormat(String phone) {
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";  
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
        Matcher m = p.matcher(phone);  
        return m.matches();  
    }
    

}
