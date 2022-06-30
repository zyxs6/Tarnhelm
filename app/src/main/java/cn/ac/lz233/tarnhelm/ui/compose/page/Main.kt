package cn.ac.lz233.tarnhelm.ui.compose.page

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cn.ac.lz233.tarnhelm.App
import cn.ac.lz233.tarnhelm.BuildConfig
import cn.ac.lz233.tarnhelm.R
import cn.ac.lz233.tarnhelm.ui.compose.CardView
import cn.ac.lz233.tarnhelm.ui.compose.MainViewModel

@Composable
fun Main(viewModel: MainViewModel) {
    Column {
        MediumTopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        })
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Main.StatusCardView(viewModel)
            Main.RulesCardView(viewModel)
            Main.SettingItem(viewModel)
            Main.AboutItem()
        }
    }
}

object Main {

    @Composable
    fun StatusCardView(viewModel: MainViewModel) {
        val anyModeActive = viewModel.anyModeEnabled
        val containerColor = if (anyModeActive) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.errorContainer
        }
        val onContainerColor = if (anyModeActive) {
            MaterialTheme.colorScheme.onSecondaryContainer
        } else {
            MaterialTheme.colorScheme.onErrorContainer
        }
        CardView(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = containerColor, contentColor = onContainerColor)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val modeText = viewModel.getActiveModeText()
                Icon(
                    painter =
                    if (!anyModeActive) painterResource(id = R.drawable.ic_error)
                    else painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = "working mode status",
                    modifier = Modifier.size(38.dp)
                )
                Spacer(modifier = Modifier.size(15.dp))
                Column {
                    Text(
                        text = stringResource(id = R.string.mainStatusTitle),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(id = R.string.mainStatusPassSummary, modeText),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

    @Composable
    fun RulesCardView(viewModel: MainViewModel) {
        CardView(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                }
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_rule_folder),
                    contentDescription = "rules icon"
                )
                Spacer(modifier = Modifier.size(15.dp))
                Column {
                    Text(
                        text = stringResource(id = R.string.mainRulesTitle),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(id = R.string.mainRulesSummary, App.ruleDao.getCount().toString()),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

    @Composable
    fun SettingItem(viewModel: MainViewModel) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                },
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_settings), contentDescription = "settings icon")
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = stringResource(id = R.string.mainSettingsTitle),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }

    @Composable
    fun AboutItem() {
        val openDialog = remember { mutableStateOf(false) }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    openDialog.value = true
                },
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_info), contentDescription = "info icon")
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = stringResource(id = R.string.mainAboutTitle),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        if (openDialog.value) {
            AboutDialog(isOpen = openDialog)
        }
    }

    @Composable
    fun AboutDialog(isOpen: MutableState<Boolean>) {
        if (isOpen.value) {
            AlertDialog(
                onDismissRequest = {
                    isOpen.value = false
                                   },
                confirmButton = {},
                text = {
                    Row(
                        modifier = Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_error),
                            contentDescription = "app icon",
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Column {
                            Text(
                                text = stringResource(id = R.string.app_name),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                                style = MaterialTheme.typography.bodySmall
                            )
                            val annotatedText = buildAnnotatedString {
                                withStyle(SpanStyle(MaterialTheme.colorScheme.onPrimaryContainer)) {
                                    append(stringResource(id = R.string.aboutViewSourceCodeText) + " ")
                                }
                                pushStringAnnotation(
                                    tag = "openSourceCode",
                                    annotation = "openSourceCode"
                                )
                                withStyle(SpanStyle(Color(0xFF0E9FF2))) {
                                    append("Github")
                                }
                                pop()
                            }
                            ClickableText(
                                text = annotatedText,
                                onClick = { offset ->
                                    annotatedText.getStringAnnotations(
                                        tag = "openSourceCode",
                                        start = offset,
                                        end = offset
                                    ).firstOrNull()?.let {
                                        runCatching {
                                            App.context.startActivity(
                                                Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/lz233/Tarnhelm"))
                                                    .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
                                            )
                                        }
                                    }
                                },
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                },
                shape = MaterialTheme.shapes.large
            )
        }

    }

}
