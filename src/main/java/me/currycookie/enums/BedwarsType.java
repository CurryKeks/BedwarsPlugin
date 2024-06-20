package me.currycookie.enums;

public enum BedwarsType {
    CLASSIC8x1(8, 1), CLASSIC4x2(8, 2), CLASIC4x4(16, 4);

    private int playersNeeded, maxPlayersPerTeam;
    private TeamSetting[] teamSettings;
    private

    BedwarsType(int playersNeeded, int maxPlayersPerTeam, TeamSetting... teamSettings) {
        this.playersNeeded = playersNeeded;
        this.teamSettings = new TeamSetting[teamSettings.length];
        for(int i = 0; i < teamSettings.length; i++)
            this.teamSettings[i] = teamSettings[i];
    }

    public int getPlayersNeeded() {
        return this.playersNeeded;
    }

    public TeamSetting[] getTeamSettings() {
        return this.teamSettings;
    }

    public int getMaxPlayersPerTeam() {
        return this.maxPlayersPerTeam;
    }
}
