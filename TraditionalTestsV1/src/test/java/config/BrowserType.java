package config;

public enum BrowserType {
    CHROME("Chrome"),
    FIREFOX("Firefox"),
    EDGE("Edge");

    private final String browserName;

    private BrowserType(final String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserName() {
        return browserName;
    }
}
