package cn.ac.lz233.tarnhelm.ui.compose.page

import android.content.ComponentName
import android.content.pm.PackageManager
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cn.ac.lz233.tarnhelm.App
import cn.ac.lz233.tarnhelm.R
import cn.ac.lz233.tarnhelm.ui.compose.MainViewModel
import cn.ac.lz233.tarnhelm.ui.process.ProcessEditTextActivity
import cn.ac.lz233.tarnhelm.ui.process.ProcessShareActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(viewModel: MainViewModel) {
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec, rememberTopAppBarScrollState())
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(vertical = 20.dp),
        topBar = {
            MediumTopAppBar(
                title = { Text(text = stringResource(id = R.string.settingsTitle)) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "back icon"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(horizontal = 24.dp),
                content = {
                    items(Settings.settingsItems) {
                        when(it) {
                            is Settings.SettingsTitle -> {
                                Text(
                                    text = it.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            is Settings.SettingsSwitch -> {
                                val checkedState = remember { mutableStateOf(App.sp.getBoolean(it.key, false)) }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = it.title,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Switch(
                                        checked = checkedState.value,
                                        onCheckedChange = { bool ->
                                            checkedState.value = bool
                                            App.editor.putBoolean(it.key, bool).apply()
                                            it.callback(bool)
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(15.dp))
                    }
                },
                contentPadding = innerPadding
            )
        }
    )
}

object Settings {
    abstract class SettingsItem
    data class SettingsTitle(val title: String) : SettingsItem()
    data class SettingsSwitch(val title: String, val key: String, val callback: (Boolean) -> Unit = {}): SettingsItem()

    val settingsItems = listOf(
        SettingsTitle(App.context.getString(R.string.settingsWorkModeTitle)),
        SettingsSwitch(App.context.getString(R.string.settingsWorkModeEditTextMenu), "workModeEditTextMenu", ::onEditTextMenuSwitchChanged),
        SettingsSwitch(App.context.getString(R.string.settingsWorkModeShare), "workModeShare", ::onShareSwitchChanged),
    )

    private fun onEditTextMenuSwitchChanged(boolean: Boolean) {
        if (boolean) {
            App.context.packageManager.setComponentEnabledSetting(
                ComponentName(App.context, ProcessEditTextActivity::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } else {
            App.context.packageManager.setComponentEnabledSetting(
                ComponentName(App.context, ProcessEditTextActivity::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }

    private fun onShareSwitchChanged(boolean: Boolean) {
        if (boolean) {
            App.context.packageManager.setComponentEnabledSetting(
                ComponentName(App.context, ProcessShareActivity::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } else {
            App.context.packageManager.setComponentEnabledSetting(
                ComponentName(App.context, ProcessShareActivity::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }

}