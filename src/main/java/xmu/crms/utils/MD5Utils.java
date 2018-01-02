package xmu.crms.utils;

import java.security.MessageDigest;

/**
 * @author Internet
 * @modified LiuXuezhang
 */
public class MD5Utils {
   //生成MD5  
    public static String getMD5(String message) {  
        
        String md5 = "";  
        String md5Temp = "";  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象  
            byte[] messageByte = message.getBytes("UTF-8");  
            byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位  
            md5Temp = bytesToHex(md5Byte);           // 转换为16进制字符串  
            md5=md5Temp.substring(8, 24);
//            System.out.println("md5Temp:"+md5Temp);
//            System.out.println("md5:"+md5);
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        
        return md5;  
    }  
   
     // 二进制转十六进制  
    public static String bytesToHex(byte[] bytes) {  
        StringBuffer hexStr = new StringBuffer();  
        int num;  
        for (int i = 0; i < bytes.length; i++) {  
            num = bytes[i];  
             if(num < 0) {  
                 num += 256;  
            }  
            if(num < 16){  
                hexStr.append("0");  
            }  
            hexStr.append(Integer.toHexString(num));  
        }  
        return hexStr.toString().toUpperCase();  
    }  
}
