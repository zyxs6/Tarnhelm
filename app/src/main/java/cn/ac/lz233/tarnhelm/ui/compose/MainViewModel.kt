package cn.ac.lz233.tarnhelm.ui.compose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import cn.ac.lz233.tarnhelm.App
import cn.ac.lz233.tarnhelm.R
import cn.ac.lz233.tarnhelm.util.ktx.getString

class MainViewModel: ViewModel() {

    lateinit var navController: NavController

    var modeEditTextMenu = mutableStateOf(false)
    var modeSharingDialog = mutableStateOf(false)
    var modeXposed = mutableStateOf(false)
    val anyModeEnabled get() = modeEditTextMenu.value || modeSharingDialog.value || modeXposed.value
    val workModeList get() = mutableListOf<String>().apply {
        if (App.isEditTextMenuActive()) add(R.string.mainStatusWorkModeEditTextMenu.getString())
        if (App.isShareActive()) add(R.string.mainStatusWorkModeShare.getString())
        if (App.isXposedActive()) add(R.string.mainStatusWorkModeXposed.getString())
    }

    fun getActiveModeText(): String {
        var flag = 0
        return StringBuilder().apply {
            var shouldAddComma = false
            workModeList.forEach {
                if (shouldAddComma) append(", ")
                append(it)
                if (length != flag) shouldAddComma = true
                flag = length
            }
        }.toString()
    }

}