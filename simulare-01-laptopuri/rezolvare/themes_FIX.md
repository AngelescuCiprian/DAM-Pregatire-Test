# FIX critic: tema de noapte

Ai scos `NoActionBar` doar din `res/values/themes.xml`. În **`res/values-night/themes.xml`** a rămas:
```xml
<style name="Base.Theme.SimulareTestDAM_1" parent="Theme.Material3.DayNight.NoActionBar">
```
Dacă telefonul sau emulatorul e în **dark mode**, aplicația folosește tema de noapte și **meniul dispare**.

Fix: schimbi și acolo în `parent="Theme.Material3.DayNight"`.
