# 📱 AndroidPrefixSuffixEditText

A customizable `EditText` view for Android that allows you to easily add prefix and suffix texts inside input fields — perfect for currency, units, and other formatted values.

---

## 🚀 Features

- Add **prefix** and **suffix** text easily
- Customize padding, text size, and colors
- Seamless integration with your existing Android views
- Lightweight, dependency-free

---

## 🛠️ Installation

### Step 1: Add MavenCentral repository

```groovy
implementation("io.github.kuvandikov:prefix-suffix-edittext:0.1.3")
```

## 💡 Usage
In XML
```xml
<com.kuvandikov.android.prefixsuffix.PrefixSuffixEditText
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Enter amount"
    app:prefixText="$"
    app:suffixText="USD" />
```
In Kotlin
```kotlin
val input = findViewById<PrefixSuffixEditText>(R.id.inputField)
input.prefixText = "$"
input.suffixText = "USD"
```

