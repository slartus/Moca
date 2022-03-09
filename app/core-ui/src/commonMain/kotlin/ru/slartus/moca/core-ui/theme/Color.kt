package ru.slartus.moca.`core-ui`.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val darkTheme: Boolean,
    val highLight: Color,
    val primaryText: Color,
    val primaryBackground: Color,
    val actionBarColor: Color,
    val actionBarContentColor: Color,
    val statusBarColor: Color,
    val navigationBarColor: Color,
    val secondaryBackground: Color,
    val primarySurface: Color,
    val drawerColor: Color,
    val strongText: Color,
    val secondaryText: Color,
)

val lightPalette = AppColors(
    darkTheme = false,
    primaryText = Palette.black,
    primaryBackground = Palette.white,
    actionBarColor = Palette.white,
    actionBarContentColor = Palette.white,
    secondaryBackground = Color(0xFFCCCCCC),
    primarySurface = Color(0xFF0F9D58),
    drawerColor = Palette.white,
    highLight = Color.Green,
    statusBarColor = Palette.white,
    navigationBarColor = Palette.white,
    strongText = Palette.black,
    secondaryText = Color(0xFFCCCCCC)
)

val darkPalette = AppColors(
    darkTheme = true,
    actionBarContentColor = Palette.lightGray,
    actionBarColor = Palette.nero21,
    primaryText = Palette.lightGray,
    primaryBackground = Palette.nero15,
    secondaryBackground = Palette.nero29,
    primarySurface = Palette.nero21,
    drawerColor = Palette.nero1B,
    highLight = Palette.bermuda,
    statusBarColor = Palette.nero21,
    navigationBarColor = Color.Black,
    strongText = Palette.white,
    secondaryText = Palette.silver
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
    val silver: Color = Color(0xFFBDBDBD)
}