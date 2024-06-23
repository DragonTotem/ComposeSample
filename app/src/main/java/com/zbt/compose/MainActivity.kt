package com.zbt.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zbt.compose.ButtonDestinations.ANIMATION_ROUTE
import com.zbt.compose.ButtonDestinations.DETAIL_ROTE
import com.zbt.compose.ButtonDestinations.LOGIN_ROUTE
import com.zbt.compose.ButtonDestinations.NAVIGATION_ROUTE
import com.zbt.compose.ButtonDestinations.REFRESH_MORE_ROUTE
import com.zbt.compose.ButtonDestinations.VIEW_ROUTE
import com.zbt.compose.ui.theme.ComposeSampleTheme
import com.zbt.compose.ui.theme.LearnTheme

/**
 * splashScreen 相关详见：
 * https://juejin.cn/post/6997217571208445965?searchId=2024032819182195309173BBC4991C077A
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition {
            /*when (uiState) {
                Loading -> true
                is Success -> false
            }*/
            false
        }
        setContent {
            LearnTheme {
                MainNavHost(
                    modifier = Modifier
                        .statusBarsPadding()
                        .navigationBarsPadding()
                )
            }
        }

//        setContent {
//            ComposeSampleTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
//                ) {
////                    Greeting("Android")
//                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
////                        val imageRef = createRef()
////                        Image(
////                            imageVector = ImageVector.vectorResource(R.drawable.ic_splash_first),
////                            contentDescription = "",
////                            modifier = Modifier.constrainAs(imageRef) {
////                                top.linkTo(parent.top)
////                                start.linkTo(parent.start)
////                                end.linkTo(parent.end)
////                                bottom.linkTo(parent.bottom)
////                            }
////                                .size(400.dp)
////                        )
////                        Image(
////                            bitmap = ImageBitmap.imageResource(R.drawable.ic_splash),
////                            contentDescription = "中间背景",
////                            modifier = Modifier.constrainAs(imageRef) {
////                                top.linkTo(parent.top)
////                                start.linkTo(parent.start)
////                                end.linkTo(parent.end)
////                                bottom.linkTo(parent.bottom)
////                            }
////                        )
////                        AnimatedVectorDrawable()
//                        CurtainView()
//                    }
//                }
//            }
//        }
//        lifecycleScope.coroutineContext
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeSampleTheme {
        Greeting("Android")
    }
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun AnimatedVectorDrawable() {
    val image = AnimatedImageVector.animatedVectorResource(R.drawable.ic_splash)
    var atEnd by remember { mutableStateOf(false) }
    Image(painter = rememberAnimatedVectorPainter(image, atEnd),
        contentDescription = "Timer",
        modifier = modifier(atEnd),
        contentScale = ContentScale.Crop)
}

@Composable
private fun modifier(atEnd: Boolean): Modifier {
    var atEnd1 = atEnd
    return Modifier
        .clickable {
            atEnd1 = !atEnd1
        }
        .size(400.dp)
}

object ButtonDestinations {
    const val NAVIGATION_ROUTE = "navigation"
    const val MAIN_ROUTE = "main"
    const val LOGIN_ROUTE = "login"
    const val VIEW_ROUTE = "view"
    const val ANIMATION_ROUTE = "animation"
    const val DETAIL_ROTE = "scrollDetail"
    const val REFRESH_MORE_ROUTE = "refreshLoadMore"
}

data class Button(val text: String, val method: () -> Unit)

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val buttonListSample =
        listOf(
            Button("LoginRegisterActivity") { navController.navigate(LOGIN_ROUTE) },
            Button("NavigationActivity") { navController.navigate(NAVIGATION_ROUTE) },
            Button("ViewActivity") { navController.navigate(VIEW_ROUTE) },
            Button("ScreenJumpAnimation") { navController.navigate(ANIMATION_ROUTE) },
            Button("ScrollDetailActivity") { navController.navigate(DETAIL_ROTE) },
            Button("RefreshAndLoadMoreActivity") { navController.navigate(REFRESH_MORE_ROUTE) }
        )
}