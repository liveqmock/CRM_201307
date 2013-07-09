package com.oasis.tmsv5.common.enums.type;

public enum CarrierLevel {
    DIAMOND(1000000), GOLDEN(100000), SILVER(10000), COPPER(2000), IRON(1000);

    private int star;

    private CarrierLevel(int star) {
        this.star = star;
    }

    public int getStar() {
        return this.star;
    }
}
