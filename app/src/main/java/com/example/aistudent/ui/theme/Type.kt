package com.example.aistudent.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.aistudent.R


val helvetica = FontFamily(
    listOf(
        Font(R.font.helvetica, FontWeight.Normal),
        Font(R.font.helvetica_bold, FontWeight.Bold),
        Font(R.font.helvetica_boldoblique, FontWeight.SemiBold),
        Font(R.font.helvetica_compressed, FontWeight.ExtraBold),
        Font(R.font.helvetica_light, FontWeight.Light),
        Font(R.font.helvetica_oblique, FontWeight.Medium),
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        color = TextWhite,
        fontFamily = helvetica,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    headlineLarge = TextStyle(
        color = TextWhite,
        fontFamily = helvetica,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    headlineSmall = TextStyle(
        color = TextWhite,
        fontFamily = helvetica,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)