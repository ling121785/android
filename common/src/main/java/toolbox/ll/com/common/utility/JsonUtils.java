package toolbox.ll.com.common.utility;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;

/**
 * Json转换工具
 */
public class JsonUtils {

    private static final Gson mGson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    public static <T> T  jsonToObj(String json, Class<T> classOfT) {
        T info = null;
        if(json==null)
            return null;
        try {
            info = mGson.fromJson(json, classOfT);
        } catch (Exception e) {
            Logger.i(e.getMessage()+json);
        }
        return info;
    }

    public static <T> T jsonToObj(String json, Type classOfT) {
        T info = null;
        try {
            info = mGson.fromJson(json, classOfT);
        } catch (Exception e) {

        }
        return info;
    }

    public static String objToJson(Object object) {
        if(object==null)
            return null;
        return mGson.toJson(object);
    }
}
