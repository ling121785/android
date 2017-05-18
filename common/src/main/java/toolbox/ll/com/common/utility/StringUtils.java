package toolbox.ll.com.common.utility;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串转换工具
 */
public class StringUtils {

    public static final String DEFAULT_SEP = ",";

    public static String list2String(List<String> list) {
        return list2String(list, DEFAULT_SEP);
    }

    public static String list2String(List<String> list, String sep) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : list) {
            if (flag) {
                result.append(sep);
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    public static ArrayList<String> string2List(String str) {
        return string2List(str,DEFAULT_SEP);
    }

    public static ArrayList<String> string2List(String str, String sep) {
        ArrayList<String> list = new ArrayList<String>();
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] text = str.split(sep);
        for (String s : text) {
            list.add(s);
        }
        return list;
    }

    public static ArrayList<String> arrayToList(String[] array){
        if(array==null)
            return null;
        ArrayList<String> list = new ArrayList<String>();
        for(String item:array){
            list.add(item);
        }
        return list;
    }
    public static boolean isEmpty(String str){
        if(str==null)
            return true;
        return str.trim().isEmpty();
    }

    public static String getString(String... strs){
        if(strs==null||strs.length==0)
            return null;
        for(String item:strs){
            if(!StringUtils.isEmpty(item))
                return item;
        }
        return null;
    }
}
