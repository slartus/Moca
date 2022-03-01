package com.therabody.plus.core_lib.ui.compose.themes

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val primary: Color,
    val primaryText: Color,
    val actionBarContentColor: Color,
    val primaryBackground: Color,
    val secondaryBackground: Color,
    val primarySurface: Color,
    val drawerColor: Color
)

val lightPalette = AppColors(
    primaryText = Palette.black,
    primaryBackground = Palette.white,
    actionBarContentColor = Palette.white,
    secondaryBackground = Color(0xFFCCCCCC),
    primarySurface = Color(0xFF0F9D58),
    drawerColor = Palette.white,
    primary = Color.Green
)

val darkPalette = AppColors(
    actionBarContentColor = Palette.lightGray,
    primaryText = Palette.lightGray,
    primaryBackground = Palette.nero15,
    secondaryBackground = Palette.nero29,
    primarySurface = Palette.nero21,
    drawerColor = Palette.nero1B,
    primary = Palette.bermuda
)

val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No colors provided")
}

object Palette {
    // naming from https://www.htmlcsscolor.com/hex/
    val white: Color = Color.White
    val black: Color = Color.Black
    val nero15: Color = Color(0xFF151515)
    val nero1B: Color = Color(0xFF1B1B1B)
    val nero21: Color = Color(0xFF212121)
    val nero29: Color = Color(0xFF292929)
    val lightGray: Color = Color(0xFFD0D0D0)
    val christmasOrange: Color = Color(0xFFd36728)
    val bermuda: Color = Color(0xFF80CBC4)
}