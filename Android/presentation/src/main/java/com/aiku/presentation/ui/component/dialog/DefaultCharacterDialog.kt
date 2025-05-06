package com.aiku.presentation.ui.component.dialog

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.core.theme.Body1
import com.aiku.presentation.DefaultCharacter
import com.aiku.presentation.DefaultThemeColor
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.ui.component.background.CircularBackground
import com.aiku.presentation.ui.component.button.FullWidthButton
import com.aiku.presentation.ui.component.chip.CharacterSelectChip

@Composable
fun DefaultCharacterDialog(
    onDismissRequest: () -> Unit,
    @DrawableRes currentCharacterRes: Int,
    currentBackgroundColor: Color,
    onComplete: (Int, Color) -> Unit
) {

    var selectedCharacterRes by remember { mutableIntStateOf(currentCharacterRes) }
    var selectedBackgroundColor by remember { mutableStateOf(currentBackgroundColor) }

    MinimalDialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.select_character), style = Body1)
            CircularBackground(
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 14.dp),
                color = selectedBackgroundColor
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = selectedCharacterRes), contentDescription = stringResource(
                    id = R.string.default_character_content_description
                ))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DefaultCharacter.entries.forEach {
                    val character = it
                    CharacterSelectChip(
                        modifier = Modifier.clickable {
                            selectedCharacterRes = character.characterRes
                        },
                        isSelected = selectedCharacterRes == character.characterRes,
                        characterRes = character.characterRes,
                        contentDescription = stringResource(id = character.characterName),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 22.dp, start = 25.dp, end = 25.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DefaultThemeColor.entries.forEach {
                    val themeColor = it
                    CircularBackground(
                        modifier = Modifier.size(40.dp),
                        color = themeColor.color,
                        onClick = {
                            selectedBackgroundColor = themeColor.color
                        }
                    )
                }
            }

            FullWidthButton(
                modifier = Modifier.padding(top = 22.dp),
                background = ButtonDefaults.buttonColors(Green5),
                onClick = {
                    onComplete(selectedCharacterRes, selectedBackgroundColor)
                },
                content = {
                    Text(text = stringResource(id = R.string.ok))
                }
            )
        }

    }
}