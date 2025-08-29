package com.cret.recyclerviewpractice

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

sealed interface UiItem : Parcelable { val id: Int }

@Parcelize
data class TextItem(
    override val id: Int,
    val text: String
)  : UiItem

@Parcelize
data class ImageItem(
    override val id: Int,
    @DrawableRes val imageResId: Int
)  : UiItem

@Parcelize
data class TextImageItem(
    override val id: Int,
    val text: String,
    @DrawableRes val imageResId: Int
) : UiItem