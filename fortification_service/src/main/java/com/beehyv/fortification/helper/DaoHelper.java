package com.beehyv.fortification.helper;

import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;

public class DaoHelper {
    public static void setQueryDateRange(String prefix, Object value, TypedQuery<?> query) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {
            String exp = (String) value;
            String[] dateList = exp.split("-");
            try {
                query.setParameter(prefix + "Start", formatter.parse(dateList[0]));
            } catch (Exception e) {
                query.setParameter(prefix + "Start", null);
            }
            try {
                query.setParameter(prefix + "End", formatter.parse(dateList[1]));
            } catch (Exception e) {
                query.setParameter(prefix + "End", null);
            }
        } catch (Exception e) {
            query.setParameter(prefix + "Start", null);
            query.setParameter(prefix + "End", null);
        }
    }
}
