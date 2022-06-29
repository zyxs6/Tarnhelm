package cn.ac.lz233.tarnhelm.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cn.ac.lz233.tarnhelm.App
import cn.ac.lz233.tarnhelm.ui.compose.page.Main
import cn.ac.lz233.tarnhelm.ui.compose.theme.AppTheme
import cn.ac.lz233.tarnhelm.ui.compose.theme.isLight
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity: ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        initWorkingMode()
        setContent {
            AppTheme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colorScheme.isLight()
                val navController = rememberNavController()
                viewModel.navController = navController

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons
                    )
                }
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") { Main(viewModel) }
                    }
                }
            }
        }
    }

    private fun initWorkingMode() {
        viewModel.modeEditTextMenu.value = App.isEditTextMenuActive()
        viewModel.modeSharingDialog.value = App.isShareActive()
        viewModel.modeXposed.value = App.isXposedActive()
    }

}