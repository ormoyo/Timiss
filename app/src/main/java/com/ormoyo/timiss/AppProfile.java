package com.ormoyo.timiss;

import java.io.Serializable;
import java.util.Objects;

public class AppProfile implements Serializable {
    private final String packageName;

    private final int time;
    private final int timeLeft;

    public AppProfile(String packageName, int time, int timeLeft) {
        this.packageName = packageName;
        this.time = time;
        this.timeLeft = timeLeft;
    }

    public String getPackageName() {
        return packageName;
    }

    public int getTime() {
        return time;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o instanceof AppInfo) return Objects.equals(packageName, ((AppInfo)o).getName());
        if(!(o instanceof AppProfile profile)) return false;
        return Objects.equals(packageName, profile.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName);
    }

    @Override
    public String toString() {
        return "AppProfile{" +
                "packageName='" + packageName + '\'' +
                '}';
    }
}
