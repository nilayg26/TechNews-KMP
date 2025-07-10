package org.example.gitcommai
import androidx.compose.runtime.Composable
import org.example.gitcommai.CommonBackend.AnimationModel
import org.example.gitcommai.CommonBackend.NewsViewModel
import org.example.gitcommai.Pages.AIPage
import org.example.gitcommai.Pages.NewsPage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App() {
    val n= NewsViewModel()
    val a= AnimationModel()
    GitCommAITheme{
        NewsPage(n,a)
    }

}
@Composable
@Preview
fun AppPreview(){
    App()
}
