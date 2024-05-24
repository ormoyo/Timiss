package com.ormoyo.timiss;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Objects;

public class AppInfo {
    private final String packageName;
    private final String name;

    private final Drawable drawable;

    public AppInfo(String packageName, String name, Drawable drawable)
    {
        this.packageName = packageName;
        this.name = name;

        this.drawable = drawable;
    }

    public String getName()
    {
        return name;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppInfo info)) return false;
        return Objects.equals(packageName, info.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName, name, drawable);
    }
}
