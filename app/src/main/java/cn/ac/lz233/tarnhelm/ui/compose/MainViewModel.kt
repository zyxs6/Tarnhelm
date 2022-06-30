package cn.ac.lz233.tarnhelm.ui.compose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import cn.ac.lz233.tarnhelm.App
import cn.ac.lz233.tarnhelm.R
import cn.ac.lz233.tarnhelm.util.ktx.getString

class MainViewModel: ViewModel() {

    lateinit var navController: NavController

    private val modeEditTextMenu get() = App.isEditTextMenuActive()
    private val modeSharingDialog get() = App.isShareActive()
    private val modeXposed get() = App.isXposedActive()
    val anyModeEnabled get() = modeEditTextMenu or modeSharingDialog or modeXposed
    private val workModeList get() = mutableListOf<String>().apply {
        if (modeEditTextMenu) add(R.string.mainStatusWorkModeEditTextMenu.getString())
        if (modeSharingDialog) add(R.string.mainStatusWorkModeShare.getString())
        if (modeXposed) add(R.string.mainStatusWorkModeXposed.getString())
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