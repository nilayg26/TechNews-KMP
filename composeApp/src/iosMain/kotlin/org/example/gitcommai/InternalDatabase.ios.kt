package org.example.gitcommai
import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIApplication

class IOSAnimationJson(override var data: String):AnimationJson{
    companion object{
    fun saveData(type: String, json: String){
        NSUserDefaults.standardUserDefaults.setObject(json, forKey = type)
    }
    fun getData(type: String):IOSAnimationJson{
        val json = NSUserDefaults.standardUserDefaults.stringForKey(type)?:""
        return IOSAnimationJson(json)
    }
    fun clearData(){
        val defaults = NSUserDefaults.standardUserDefaults
        val dict = defaults.dictionaryRepresentation()
        dict.keys.forEach {
            defaults.removeObjectForKey(it as String)
        }
    }
    }
}
actual fun getJsonData(type: String): AnimationJson {
    return IOSAnimationJson.getData(type)
}

actual fun saveJsonData(type: String, json: String) {
    IOSAnimationJson.saveData(type, json)
}