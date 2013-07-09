package com.oasis.tmsv5.common.enums.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Day {
    SUN(64, "Sunday"), // 1 << 6 : 1000000
    MON(32, "Monday"), // 1 << 5 : 0100000
    TUE(16, "Tuesday"), // 1 << 4 : 0010000
    WED(8, "Wednesday"), // 1 << 3 : 0001000
    THU(4, "Thursday"), // 1 << 2 : 0000100
    FRI(2, "Friday"), // 1 << 1 : 0000010
    SAT(1, "Saturday"); // 1 << 0 : 0000001

    int value;

    String fullName;

    static List<Day> allDays = Arrays.asList(Day.values());

    static Map<Integer, Day> valueMap = new HashMap<Integer, Day>();

    static {
        for (Day day : allDays) {
            valueMap.put(day.value, day);
        }
    }

    Day(int value, String fullName) {
        this.value = value;
        this.fullName = fullName;
    }

    public int getValue() {
        return value;
    }

    public String getFullName() {
        return fullName;
    }

    public static Day parseDayByValue(int value) {
        return valueMap.get(value);
    }

    public static Day parseDayByOrdinal(int ordinal) {
        return allDays.get(ordinal);
    }

    public static String encode(List<Day> days) {
        int value = 0; // Integer.valueOf("1111111", 2);
        for (Day day : days) {
            value = value | day.value;
        }
        StringBuffer buf = new StringBuffer(Integer.toBinaryString(value));
        while (buf.length() < 7) {
            buf.insert(0, '0');
        }
        return buf.toString();
    }

    public static List<Day> decode(String str) {
        assert str.length() == 7;
        int value = Integer.parseInt(str, 2);
        assert value >= 0;
        assert value <= 127;

        List<Day> result = new ArrayList<Day>();
        for (Day day : allDays) {
            if ((value & day.value) == day.value) {
                result.add(day);
            }
        }
        return result;
    }

    public static Day getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_WEEK);
        switch (day) {
        case 1:
            return SUN;
        case 2:
            return MON;
        case 3:
            return TUE;
        case 4:
            return WED;
        case 5:
            return THU;
        case 6:
            return FRI;
        case 7:
            return SAT;
        default:
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(Day.parseDayByValue(32));
        System.out.println(Day.parseDayByOrdinal(0));

        List<Day> days = new ArrayList<Day>();
        days.add(Day.THU);
        days.add(Day.SAT);
        String code = Day.encode(days);
        System.out.println(code);

        System.out.println(Day.decode(code));

        System.out.println(Day.MON.getValue());
        System.out.println(Day.MON.equals(Day.MON));
    }

}
