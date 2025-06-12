# Simple Calc. Android Training App

#### Brief
<sup><i>What is this system? Who tasked you? And why? Don't forget to tag/link</i></sup>
<br>
This is an application from these 6 suggested ones from #ai/chatgpt in [this conversation](https://chatgpt.com/share/68468aac-e7a8-8005-94be-56f9155702dc) (actually this is the first one). It aims initially to #android-dev in [[Kotlin]] - #self-paced -


###### Freelinting
- That's important because I need to study hard in short time for preparing for my #pearson Unit-7 after almost 2 weeks from [Mon 09.06.25]
- That's empathetic because this is the first time to realize that I have finished an application of both UI and logic; my idea about full-stacking was about how it's hard to handle, and I really found it hard 😁  

<br>

---

## **R**equirements 

<sup><i>Just copy every single sequence from the client</i></sup>

<br>

#### [Functional] Goals

**🔢 Input Handling:**
- Read values from both input fields.
- Validate that both are numbers.
- Handle empty or invalid input gracefully.

**➗ Operation Handling:**
For each button:
- **Add**: Add the two numbers.
- **Subtract**: Subtract second number from the first.
- **Multiply**: Multiply the two numbers.
- **Divide**: Divide first by second.
    - Special case: Show error message if second number is 0.

**🧾 Output:**
- Show the result in the result TextView.
- Show relevant error messages for:
    - Invalid input.
    - Division by zero.

#### [Non-Functional] Goals

**📱 Required UI Components:**
- 2 input fields:
    - Hint: "Enter first number"
    - Hint: "Enter second number"
- 4 operation buttons:
    - Add (`+`)
    - Subtract (`-`)
    - Multiply (`×`)
    - Divide (`÷`)
- 1 text display field:
    - Purpose: Show the result or error message.

**🎯 UI Design Goals:**
- Clean and vertical layout.
- Equal spacing between inputs and buttons.
- Responsive to screen resizing.


#### Optional Features
Not required, but great if you want to improve:
- Clear button to reset everything.
- History list showing past calculations.
- Dark mode toggle.
- Integer mode vs decimal mode toggle.
- Vibration on invalid input.

<br>

## **A**rchitecture

<sup><i>Embed an Excalidraw note describing arch.</i></sup>

<br>

<img src="Simple Calculator Android Project architecture.excalidraw" width="700" alt="Simple Calculator Architecture Diagram">
<img src="Simple Calculator Android Project architecture .excalidraw" width="700" alt="Simple Calculator Architecture Diagram">

<br>

<br>

## Dev. **N**otes

<sup><i>Include any code snippet or newly learnt concept that might be linky</i></sup>

<br>


| File/Module                     | Short Purpose                           | Further Notes                                                  |
| ------------------------------- | --------------------------------------- | -------------------------------------------------------------- |
| `{kotlin icon}MainActivity`     | Class of `{xml icon}activity_main`      | Links with the UI file for running, using `{kotlin}OnCreate()` |
|                                 |                                         |                                                                |
| `{xml icon}activity_main`       | UI page for `{kotlin icon}MainActivity` | Calls `{xml}androidx` elements for designing                   |
|                                 |                                         |                                                                |
| `{gradle icon}build.gradle.kts` | For [[Meta Programming]]                | Controls project attributes, as a manifest                     |


##### Made-with-𖹭 Cheatsheet!

- I have used this advice: [[Difference Between Traditional & Modern View Calling In Android]]

```kotlin title="build.gradle.kts (:app)"
// ...
// for generating `ViewBinding` class
buildFeatures { 	// in camelCase
    viewBinding = true  
}
// ...
```

> - Here's the rest of the idea: [[ViewBinding Class]]
> - I'm welling to use it from now on. 

<br>

- How to make vibration per certain touches (was used on operations buttons):

```kotlin 
import android.Manifest
import android.os.Vibrator  
import android.os.VibrationEffect  
// import android.content.Context  
import android.os.Build  
import androidx.annotation.RequiresPermission


/* IN `OnCreate` METHOD */
@RequiresPermission(Manifest.permission.VIBRATE)  
private fun vibrate() {  
    val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator  
  
    // For API 26+ use VibrationEffect  
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  
        val effect = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)  
        vibrator.vibrate(effect)  
    } else {  
        // Deprecated in API 26  
        vibrator.vibrate(50)  
    }  
}
```

> - This code is for a function called `vibrate()`, just call it in your logic inside lambdas to use (NOTE: it has one intensity)
> - If you had any Manifest related issues, auto-solve it with Android Studio 

<br>

- `inputsChecker()`: Self-made function; for extracting the logic of parsing & validating inputs for multiple use for each operation:

```kotlin
// IN `OnCreate()` METHOD
@RequiresPermission(allOf = [Manifest.permission.VIBRATE, Manifest.permission.VIBRATE])  
fun inputsChecker(): Pair<Double, Double> {  
    val in1 = binding.input1.text.toString().toDoubleOrNull()  
    val in2 = binding.input2.text.toString().toDoubleOrNull()  
    vibrate()

    if (in1 == null || in2 == null || in1.isNaN() || in2.isNaN()) {  
        return Pair(0.0, 0.0)  
    }  
  
    return Pair(in1, in2)  
}
```

> - `Pair<Type, Type>` is a builtin class acts like [Tuples] in [Rust] or [Python], because there's no primitive datatype for Tuples functionality, but classes like `Pair` and `Triple` are enough for most uses 


<br>

- `binding.divBtn.setOnClickListener {  }`: the trickiest button's [lambda] in this app as an example:

```kotlin
binding.divBtn.setOnClickListener {  
    val (num1, num2) = inputsChecker()  
  
    if (num2.toInt() == 0) {  
        Toast.makeText(this, "Math Error: Denomerator Can't Be 0", Toast.LENGTH_SHORT).show()  
    } else {  
        result = num1 / num2  
        binding.resultView.text = result.toString()  
    }
```

> - NOTE: Don't forget the `.text` method for the `TextView` object, while `.toString()` method for the [String] object holding data (as here it's `result`)

<br>

- **activity_main.xml**: the whole UI *(I will indulge the whole UI just because its details are limitless)*

```xml title=main_activity.xml
<?xml version="1.0" encoding="utf-8"?>  
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"  
    xmlns:app="http://schemas.android.com/apk/res-auto"  
    xmlns:tools="http://schemas.android.com/tools"  
    android:id="@+id/main"  
    android:layout_width="match_parent"  
    android:layout_height="match_parent"  
    android:orientation="vertical"  
    android:layout_margin="0dp"  
    android:paddingBottom="110dp"  
    android:gravity="center"  
    android:layout_gravity="top"  
    tools:context=".MainActivity">  
  
    <LinearLayout  
        android:layout_width="match_parent"  
        android:layout_height="wrap_content"  
        android:orientation="horizontal"  
        android:layout_marginTop="-220dp"  
        android:layout_marginBottom="130dp"  
        android:gravity="center">  
  
        <ImageView  
            android:layout_width="wrap_content"  
            android:layout_height="wrap_content"  
            android:src="@drawable/drafting_compass"  
            android:scaleX="0.6"  
            android:scaleY="0.6"/>  
  
        <TextView  
            android:layout_width="wrap_content"  
            android:layout_height="wrap_content"  
            android:text="@string/title"  
            android:textSize="24sp"  
            android:textColor="@color/baby_blue"  
            android:fontFamily="@font/satisfy"/>  
  
        <TextView  
            android:layout_width="wrap_content"  
            android:layout_height="wrap_content"  
            android:text="@string/trademark_symbol"  
            android:textSize="24sp"  
            android:textColor="@color/baby_blue"/>  
  
    </LinearLayout>  
  
    <LinearLayout  
        android:layout_width="match_parent"  
        android:layout_height="wrap_content"  
        android:layout_marginBottom="40dp"  
        android:gravity="center">  
  
        <ImageView  
            android:layout_width="wrap_content"  
            android:layout_height="wrap_content"  
            android:src="@drawable/calculator"/>  
  
        <TextView  
            android:id="@+id/result_view"  
            android:layout_width="wrap_content"  
            android:layout_height="wrap_content"  
            android:text="null"  
            android:fontFamily="@font/sfmono"  
            android:textSize="24sp"  
            />  
  
    </LinearLayout>  
  
  
    <EditText  
        android:id="@+id/input1"  
        android:layout_width="350dp"  
        android:layout_height="48dp"  
        android:layout_marginBottom="20dp"  
        android:hint="First Number"  
        android:fontFamily="@font/sfmono"  
        android:inputType="numberDecimal"  
        android:textColorHint="@color/light_grey"/>  
  
    <EditText  
        android:id="@+id/input2"  
        android:layout_width="350dp"  
        android:layout_height="48dp"  
        android:layout_marginBottom="20dp"  
        android:hint="Second Number"  
        android:fontFamily="@font/sfmono"  
        android:inputType="numberDecimal"  
        android:textColorHint="@color/light_grey"/>  
  
    <LinearLayout  
        android:layout_width="match_parent"  
        android:layout_height="wrap_content"  
        android:orientation="horizontal"  
        android:gravity="center"  
        android:layout_marginTop="20dp">  
  
        <ImageButton  
            android:id="@+id/add_btn"  
            android:layout_width="wrap_content"  
            android:layout_height="wrap_content"  
            android:textColor="@color/baby_blue"  
            android:src="@drawable/circle_plus"  
            android:background="@drawable/button_ripple"  
            android:hapticFeedbackEnabled="true"  
            android:layout_marginHorizontal="15dp"  
            />  
  
        <ImageButton  
            android:id="@+id/sub_btn"  
            android:layout_width="wrap_content"  
            android:layout_height="wrap_content"  
            android:textColor="@color/baby_blue"  
            android:src="@drawable/circle_minus"  
            android:background="@drawable/button_ripple"  
            android:hapticFeedbackEnabled="true"  
            android:layout_marginHorizontal="15dp"  
            />  
  
        <ImageButton  
            android:id="@+id/mul_btn"  
            android:layout_width="wrap_content"  
            android:layout_height="wrap_content"  
            android:textColor="@color/baby_blue"  
            android:src="@drawable/circle_x"  
            android:background="@drawable/button_ripple"  
            android:hapticFeedbackEnabled="true"  
            android:layout_marginHorizontal="15dp"  
            />  
  
        <ImageButton  
            android:id="@+id/div_btn"  
            android:layout_width="wrap_content"  
            android:layout_height="wrap_content"  
            android:textColor="@color/baby_blue"  
            android:src="@drawable/circle_divide"  
		    android:background="@drawable/button_ripple"  
            android:hapticFeedbackEnabled="true"  
            android:layout_marginHorizontal="15dp"  
            />  
  
    </LinearLayout>  
  
</LinearLayout>

```

<br>

- How to make a ripple (the visual watery waves beneath touches) to pass as an `android:background`:

```xml title="button_ripple"

<?xml version="1.0" encoding="utf-8"?>  
<ripple xmlns:android="http://schemas.android.com/apk/res/android"  
    android:color="?android:attr/colorControlHighlight">  
    <item android:id="@+id/masked">  
        <shape android:shape="ring">  
<!--            <solid android:color="#444"/>-->  
<!--            <corners android:radius="30dp"/>-->  
        </shape>  
    </item>  
</ripple>
```

<br>

- Example for font configuration file:

```xml title="res/font/sfmono.xml"
<?xml version="1.0" encoding="utf-8"?>  
<font-family xmlns:android="http://schemas.android.com/apk/res/android">  
    <font  
        android:fontStyle="normal"  
        android:fontWeight="600"  
        android:font="@font/sf_mono_regular"/>  
</font-family>
```
<br>

---

## **T**o-dos

<sup><i>A comprehensive list of tasks for following up during work</i></sup>

<br>

- [/] Because I have finished the project before beginning documenting, this will be empty. ✅ 2025-06-09

<br>

---

## Final **O**utput

<sup><i>Here, just celebrate finishing piece of software, with evidences!</i></sup>

<br>


<video src="SimpleCalculatorAndroidKotlinOutput.mp4" autoplay loop muted width="600"></video>




- [GitHub Repository](https://github.com/douddjs/1SimpleCalcAndroidTraining.git)

<br>

---

> By me, Adham Ahmad Hosny
> [Github](https://github.com/douddjs) &emsp; [Linkedin](https://linkedin.com/in/douddev)
