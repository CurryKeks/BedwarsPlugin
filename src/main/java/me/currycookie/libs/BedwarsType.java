package me.currycookie.libs;

public enum BedwarsType {
    CLASSIC8x1(8), CLASSIC4x2(8), CLASIC4x4(16);

    private int playersNeeded;

    BedwarsType(int playersNeeded) {
        this.playersNeeded = playersNeeded;
    }

    public int getPlayersNeeded() {
        return this.playersNeeded;
    }
}
