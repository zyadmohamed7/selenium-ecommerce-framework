package com.seleniumframework.drivers;

public enum Browsers {
    CHROME {
        @Override
        public AbstractDriver getDriverFactory() {
            return new ChromeFactory();
        }
    },
    FIREFOX {
        @Override
        public AbstractDriver getDriverFactory() {
            return new FirefoxFactory();
        }
    };

    public abstract AbstractDriver getDriverFactory();

}

