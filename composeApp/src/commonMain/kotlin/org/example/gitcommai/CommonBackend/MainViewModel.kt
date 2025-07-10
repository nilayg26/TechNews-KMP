package org.example.gitcommai.CommonBackend
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import org.example.gitcommai.BuildKonfig
import org.example.gitcommai.NewsClasses.MainNews
import org.example.gitcommai.getJsonData
import org.example.gitcommai.saveJsonData

abstract class MainViewModel : ViewModel(){
    abstract suspend fun get(type:String=""):String
    abstract val status:MutableState<String>
}
class NewsViewModel : MainViewModel() {
    private val client = HttpClient()
    private val apiKey= BuildKonfig.NEWS_API_KEY
    override var status = mutableStateOf("")
        private set
    var mainNews: MutableState<MainNews?> = mutableStateOf(null)
        private set

    override suspend fun get(type:String) :String{
        status.value= NewsStatus.Loading
        println("api key is: $apiKey")
        try {
            val json = client.get("https://newsapi.org/v2/everything?q=technology&apiKey=$apiKey").body<String>()
            mainNews.value= Json.decodeFromString(json)
            println(json)
            status.value= NewsStatus.Initalised
        }
        catch (e:Exception){
            println("Error: ${e.message.toString()}")
            status.value= NewsStatus.Error
        }
        finally {
           println("Finally Executed")
            return  ""
        }
    }
}
class AnimationModel: MainViewModel(){
    override var status = mutableStateOf("")
        private set
    private val client = HttpClient()
    override suspend fun get(type:String) :String{
        status.value= AnimationStatus.Loading
        val animationJson= getJsonData(type = type)
        if (animationJson.data.isNotBlank()){
            status.value=AnimationStatus.Initalised
        }
        return animationJson.data.ifBlank {
            loadAnimation(type)
        }
    }
    private suspend fun loadAnimation(type: String):String{
        val map= mapOf(("loading_animation" to "https://nilayg26.github.io/Animation/loading_gitcommai.json"))
        var json=""
        try {
            json = map[type]?.let {client.get(it).body<String>()}?:""
            println(json)
            saveJsonData(type = type, json = json)
            status.value= AnimationStatus.Initalised
        }
        catch (e:Exception){
            println("Error: ${e.message.toString()}")
            status.value= AnimationStatus.Error
        }
        finally {
            println("Finally Executed")
            return json
        }
    }
}
interface Status{
    var Loading:String
    var Initalised:String
    var Error:String
}
object NewsStatus:Status{
    override var Loading: String="NewsLoading"
    override var Initalised: String="NewsInitialised"
    override var Error: String="NewsError"
}
object AnimationStatus:Status{
    override var Loading: String="AnimationLoading"
    override var Initalised: String="AnimationInitialised"
    override var Error: String="AnimationError"
}