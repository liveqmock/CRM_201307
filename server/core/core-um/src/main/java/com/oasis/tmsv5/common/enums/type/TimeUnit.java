package com.oasis.tmsv5.common.enums.type;

import java.util.ArrayList;
import java.util.List;

public enum TimeUnit {
    MINUTE, HOUR, DAY,

    DISPLAY_LIST;

    public static List<TimeUnit> getTimeUnit4List(TimeUnit listType) {
        List<TimeUnit> list = new ArrayList<TimeUnit>();
        switch (listType) {
        case DISPLAY_LIST:
            list.add(TimeUnit.HOUR);
            list.add(TimeUnit.DAY);
            list.add(TimeUnit.MINUTE);
            break;
        }
        return list;
    }
}
