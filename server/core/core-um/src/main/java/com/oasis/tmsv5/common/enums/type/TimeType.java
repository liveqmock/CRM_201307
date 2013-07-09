package com.oasis.tmsv5.common.enums.type;

import java.util.ArrayList;
import java.util.List;

public enum TimeType {
    H4, H8, H12, D1, D2, D4, WEEK,

    DISPLAY_LIST, INOUTWARD_LIST, TOI_INPUTAMOUNT_LIST;

    public static List<TimeType> getStatus4List(TimeType listType) {
        List<TimeType> list = new ArrayList<TimeType>();
        switch (listType) {
        case DISPLAY_LIST:
            list.add(TimeType.H4);
            list.add(TimeType.H8);
            list.add(TimeType.H12);
            list.add(TimeType.D1);
            list.add(TimeType.D2);
            break;
        case INOUTWARD_LIST:
            list.add(TimeType.H4);
            list.add(TimeType.H8);
            list.add(TimeType.H12);
            list.add(TimeType.D1);
            list.add(TimeType.D2);
            break;
        case TOI_INPUTAMOUNT_LIST:
            list.add(TimeType.H12);
            list.add(TimeType.D1);
            list.add(TimeType.D2);
            list.add(TimeType.D4);
            list.add(TimeType.WEEK);
            break;
        }
        return list;
    }
}
