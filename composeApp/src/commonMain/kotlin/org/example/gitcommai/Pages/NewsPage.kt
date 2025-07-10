package org.example.gitcommai.Pages
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.coroutines.launch
import org.example.gitcommai.CommonBackend.AnimationModel
import org.example.gitcommai.CommonBackend.NewsStatus
import org.example.gitcommai.CommonBackend.NewsViewModel
import org.example.gitcommai.NewsClasses.Article
data class AlertDialogInfo(
    val imageVector: ImageVector=Icons.AutoMirrored.Filled.ExitToApp,
    val body: String="Want to read full Article? You will be directed to Browser",
    val dismissText: String="Not Now",
    val confirmText: String="Go",
    val onConfirmationRequest: ()->Unit
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  NewsPage(n: NewsViewModel, a: AnimationModel){
    var showNews by rememberSaveable{
        mutableStateOf(false)
    }
    var json by rememberSaveable{
        mutableStateOf("")
    }
    var alertDialogInfo = remember {
        AlertDialogInfo(onConfirmationRequest = {})
    }
    var enableAlertDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit){
        launch {
            n.get()
        }
        launch {
           json= a.get(type = "loading_animation")
        }
    }
    LaunchedEffect(n.status.value){
        when(n.status.value) {
            NewsStatus.Initalised -> showNews = true
        }
    }
    val scrollBehavior= TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection), topBar = { GitCommAITopAppBar("Daily Tech News",scrollBehavior) }) {
            paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            if(!showNews && json.isNotBlank()){
                AnimationLottie(json)
            }
            AnimatedVisibility(showNews) {
                ArticleList(articles = n.mainNews.value!!.articles, onArticleClick = {})
            }
        }
    }
}

@Composable
fun ArticleList(articles: List<Article>, onArticleClick: (Article) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(articles) { article ->
            ArticleCard(article = article, onClick = { onArticleClick(article) })
        }
    }
}

@Composable
fun ArticleCard(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            article.urlToImage?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = article.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = article.title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            article.description?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = article.publishedAt.split("T")[0],
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
@Composable
fun AnimationLottie(json: String) {
    val composition by rememberLottieComposition{
        LottieCompositionSpec.JsonString(json)
    }
    Image(painter = rememberLottiePainter(composition = composition, iterations =  Compottie.IterateForever), contentDescription = "loading animation")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitCommAITopAppBar(text: String, scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(canScroll = {true}), backButton: Boolean = false, onBackButton:()->Unit={}){
    Row {
        TopAppBar(modifier = Modifier.animateContentSize(),scrollBehavior = scrollBehavior,
            title = {
                Text(text, fontWeight = FontWeight.Bold, fontSize = 32.sp, modifier = Modifier.statusBarsPadding())
            },
            navigationIcon = {
                if (backButton) {
                    IconButton(
                        onClick = onBackButton,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        )
    }
}
@Composable
fun GitCommAIAlertDialogue(imageVector: ImageVector =Icons.Filled.Warning, body:String="", dismissText:String="Not Now", confirmText:String="Confirm", onDismissRequest: (Boolean) -> Unit, onConfirm: () -> Unit){
    AlertDialog(onDismissRequest = { onDismissRequest(false) },
        confirmButton = {
            Button(onClick = {
                onConfirm();onDismissRequest(false)
            }) { Text(confirmText) }
        },
        dismissButton = {
            Button(
                onClick = { onDismissRequest(false) }
            )
            {
                Text(dismissText)
            }
        },
        title = {
            Row(horizontalArrangement = Arrangement.Center) {
                Text("")
                Icon(
                    imageVector = imageVector,
                    contentDescription = ""
                )
            }
        },
        text = { Text(body) }
    )
}

