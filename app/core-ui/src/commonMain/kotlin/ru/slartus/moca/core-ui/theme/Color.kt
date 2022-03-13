package ru.slartus.moca.`core-ui`.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val secondaryVariant: Color,
    val background: Color,
    val surface: Color,
    val error: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onError: Color,
    val highLight: Color,
    val isLight: Boolean,
) {
    fun toMaterialColors(): Colors = Colors(
        primary,
        primaryVariant,
        secondary,
        secondaryVariant,
        background,
        surface,
        error,
        onPrimary,
        onSecondary,
        onBackground,
        onSurface,
        onError,
        isLight
    )
}

val lightPalette = AppColors(
    secondaryVariant = Palette.black,
    background = Palette.white,
    primary = Palette.white,
    primaryVariant = Palette.white,
    secondary = Color(0xFFCCCCCC),
    surface = Palette.white,
    error = Color(0xFFB00020),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,
    highLight = Color.Green,
    isLight = true
)

val darkPalette = AppColors(
    primaryVariant = Palette.lightGray,
    primary = Palette.nero21,
    secondaryVariant = Palette.lightGray,
    background = Palette.nero15,
    secondary = Palette.nero29,
    surface = Palette.nero1B,
    error = Color(0xFFCF6679),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.Black,
    onError = Color.Black,
    highLight = Palette.gossamer,
    isLight = false
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
    val gossamer: Color = Color(0xFF379488)

    val silver: Color = Color(0xFFBDBDBD)
}