package com.zhidian.ecommerce.tenant.invoker;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.zhidianwuxian.cn</p>
 * <p>Description: timestamp to java.util.Date </p>
 * <p>Author:jmzhang/张际明, 16/10/30</p>
 */
public class TimestampTypeAdapter implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if(null == jsonElement) {
            return null;
        }

        Date date = null;
        try {
            String timestamp = jsonElement.getAsString();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(timestamp));
            date = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }
}
