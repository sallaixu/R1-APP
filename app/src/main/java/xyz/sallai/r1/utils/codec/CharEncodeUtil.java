package xyz.sallai.r1.utils.codec;

/**
 * Description: 字符编码转换工具类
 * <p>
 * Author:
 * Date: 2023/8/15
 */
public class CharEncodeUtil {
    /**
     * unicode转utf8
     */
    public static String unicodeToUtf8(String str) {
        if(null == str) return "";
        byte[] utf8Bytes = str.getBytes(StandardCharsets.UTF_8);
        return new String(utf8Bytes, StandardCharsets.UTF_8);
    }
    
}
