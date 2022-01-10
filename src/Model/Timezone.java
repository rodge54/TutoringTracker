package Model;

public class Timezone {
    private int timezoneId;
    private String zone;

    public Timezone(int timeZoneId, String zone) {
        this.timezoneId = timeZoneId;
        this.zone = zone;
    }

    public int getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(int timezoneId) {
        this.timezoneId = timezoneId;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return getZone();
    }
}
