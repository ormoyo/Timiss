public class AppInfo {
    // ... your existing fields (packageName, name, icon)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppInfo appInfo = (AppInfo) o;
        return Objects.equals(packageName, appInfo.packageName) &&
                Objects.equals(name, appInfo.name) &&
                Objects.equals(icon, appInfo.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageName, name, icon);
    }

    // ... your other methods (constructors, getters, etc.)
}
