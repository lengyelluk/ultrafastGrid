package config;

public enum DeviceType {
    LAPTOP("Laptop", 1200),
    TABLET("Tablet", 800),
    MOBILE("Mobile", 500);

    private final int width;
    private final String deviceName;

    private DeviceType(final String deviceName, final int width) {
        this.deviceName = deviceName;
        this.width = width;
    }

    public int getDeviceWidth() {
        return width;
    }

    public String getDeviceName() { return deviceName; }
}
