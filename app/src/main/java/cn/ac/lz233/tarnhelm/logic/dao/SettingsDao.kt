package cn.ac.lz233.tarnhelm.logic.dao

import cn.ac.lz233.tarnhelm.App

object SettingsDao {
    val workModeEditTextMenu
        get() = App.sp.getBoolean("workModeEditTextMenu", false)

    val workModeShare
        get() = App.sp.getBoolean("workModeShare", false)
}