package com.oasis.tmsv5.common.enums.type;

import java.util.ArrayList;
import java.util.List;

public enum ExceptionWeightnessLevel {

    NORMAL(0), HIGH(1), HIGHER(2), HIGHEST(3);

    private int level;

    private ExceptionWeightnessLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean lessThan(ExceptionWeightnessLevel owl) {
        return this.getLevel() < owl.getLevel();
    }

    public static List<ExceptionWeightnessLevel> getExceptionList(){
        List<ExceptionWeightnessLevel> expList = new ArrayList<ExceptionWeightnessLevel>();
        expList.add(HIGH);
        expList.add(HIGHER);
        expList.add(HIGHEST);
        return expList;
    }
}
