package org.example.gitcommai

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

interface AndroidDa{
    fun saveData()
}
class AndroidAnimationJson(override var data: String):AnimationJson{
    companion object{
        fun saveData(type: String, json: String){
            AndroidShared.sharedPreferences.edit().putString(type,json).apply()
        }
        fun getData(type: String):AndroidAnimationJson{
            val str= AndroidShared.sharedPreferences.getString(type,"")?:""
            return AndroidAnimationJson(str)
        }
        fun clearData(){
            AndroidShared.sharedPreferences.edit().clear().apply()
        }
    }
}
actual fun getJsonData(type: String): AnimationJson {
  return AndroidAnimationJson.getData(type)
}

actual fun saveJsonData(type: String, json: String) {
    return AndroidAnimationJson.saveData(type, json)
}