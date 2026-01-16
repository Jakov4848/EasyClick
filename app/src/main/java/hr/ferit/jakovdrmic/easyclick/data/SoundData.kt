package hr.ferit.jakovdrmic.easyclick.data

import hr.ferit.jakovdrmic.easyclick.R
import hr.ferit.jakovdrmic.easyclick.data.model.Sound
import hr.ferit.jakovdrmic.easyclick.data.model.SoundCategory


object SoundData {

    val sounds = listOf(
        Sound("1", "Classic Click", resId = R.raw.click, SoundCategory.CLASSIC),
        Sound("2", "Wood Click", R.raw.click2, SoundCategory.WOOD),
        Sound("3", "Digital Click", R.raw.click3, SoundCategory.DIGITAL),
        Sound("4", "Soft Click", R.raw.click4, SoundCategory.CLASSIC),
        Sound("5", "Hard Click", R.raw.click5, SoundCategory.CLASSIC)
    )
}

