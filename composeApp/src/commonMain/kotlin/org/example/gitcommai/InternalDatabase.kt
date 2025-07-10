package org.example.gitcommai

import androidx.compose.runtime.Composable

interface AnimationJson{
    var data:String
}
expect fun getJsonData (type:String):AnimationJson

expect fun saveJsonData(type:String , json:String)

