package com.sinasamaki.loadinganimation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.sinasamaki.loadinganimation.R

@OptIn(ExperimentalTextApi::class)
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

@ExperimentalTextApi
val HeadingFontFamily = FontFamily(
    Font(googleFont = GoogleFont("Lora"), fontProvider = provider, weight = FontWeight.Bold)
)

@ExperimentalTextApi
val DefaultFontFamily = FontFamily(
    Font(googleFont = GoogleFont("Catamaran"), fontProvider = provider)
)

@ExperimentalTextApi
val DefaultBoldFontFamily = FontFamily(
    Font(googleFont = GoogleFont("Catamaran"), fontProvider = provider, weight = FontWeight.ExtraBold)
)


@ExperimentalTextApi
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = HeadingFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 23.sp,
        lineHeight = 23.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    caption = TextStyle(
        fontFamily = DefaultBoldFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 12.sp,
        lineHeight = 8.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    body1 = TextStyle(
        fontFamily = DefaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 14.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
)