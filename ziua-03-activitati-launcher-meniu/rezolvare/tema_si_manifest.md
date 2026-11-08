# Tema și Manifestul

## `res/values/themes.xml`: necesar pentru meniu

```xml
<!-- ÎNAINTE: parent="Theme.Material3.DayNight.NoActionBar"  → meniul NU apare -->
<style name="Base.Theme.CartiApp" parent="Theme.Material3.DayNight">
</style>
```
Dacă există și `res/values-night/themes.xml`, schimbi și acolo.

## `AndroidManifest.xml`: generat automat dacă ai creat activitățile cu New → Activity → Empty Views Activity

```xml
<activity
    android:name=".MainActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
<activity
    android:name=".AddCarteActivity"
    android:exported="false" />
<activity
    android:name=".AboutActivity"
    android:exported="false" />
```
Dacă ai creat activitatea ca simplă clasă Java, trebuie s-o adaugi manual aici. Altfel: `ActivityNotFoundException`.
