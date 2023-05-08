
package com.alican.mvvm_starter.util.utils

import android.content.Context


fun Context.loadJSONFromAssets(fileName: String): String {
    return applicationContext.assets.open(fileName).bufferedReader().use { reader ->
        reader.readText()
    }
}


