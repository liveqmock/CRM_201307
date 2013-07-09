package com.oasis.tmsv5.common.enums.status;

import java.util.ArrayList;
import java.util.List;

public enum CommonStatus {
    ENABLED, DISABLED, ACTIVE, FROZEN, DESTROYED, RETIRED, COPY, DRAFT, EXTERNAL, TEMPORARY,RESOLVED,UNRESOLVED,PROCESSING, // constants
    MAINTAINLIST, POPLIST,REPORTLIST,FORMLIST;// list

  

    public static List<CommonStatus> getStatus4List(CommonStatus listType) {
        List<CommonStatus> list = new ArrayList<CommonStatus>();
        switch (listType) {
        case MAINTAINLIST:
            list.add(CommonStatus.DRAFT);
            list.add(CommonStatus.ENABLED);
            list.add(CommonStatus.ACTIVE);
            list.add(CommonStatus.FROZEN);
            list.add(CommonStatus.EXTERNAL);
            list.add(CommonStatus.TEMPORARY);
            break;
        case POPLIST:
            list.add(CommonStatus.ACTIVE);
            break;
        case REPORTLIST:
        	 list.add(CommonStatus.ACTIVE);
        	 list.add(CommonStatus.FROZEN);
             break;
        case FORMLIST:
             list.add(CommonStatus.DRAFT);
             list.add(CommonStatus.RESOLVED);
             list.add(CommonStatus.UNRESOLVED);
//             list.add(CommonStatus.PROCESSING);
             break;
        default:
            break;
        }
        return list;
    }

}