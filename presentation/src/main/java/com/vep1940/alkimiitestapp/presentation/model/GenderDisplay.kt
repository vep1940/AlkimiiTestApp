package com.vep1940.alkimiitestapp.presentation.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.vep1940.alkimiitestapp.domain.model.Gender
import com.vep1940.alkimiitestapp.presentation.R

internal enum class GenderDisplay {
    FEMALE,
    MALE,
    GENDERLESS,
    UNKNOWN,
}

internal fun Gender.toPresentation() = when(this) {
    Gender.FEMALE -> GenderDisplay.FEMALE
    Gender.MALE -> GenderDisplay.MALE
    Gender.GENDERLESS -> GenderDisplay.GENDERLESS
    Gender.UNKNOWN -> GenderDisplay.UNKNOWN
}

@Composable
internal fun GenderDisplay.getText() = when(this){
    GenderDisplay.FEMALE -> stringResource(R.string.female_gender)
    GenderDisplay.MALE -> stringResource(R.string.male_gender)
    GenderDisplay.GENDERLESS -> stringResource(R.string.genderless_gender)
    GenderDisplay.UNKNOWN -> stringResource(R.string.unknown_gender)
}