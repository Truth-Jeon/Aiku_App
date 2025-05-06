package com.aiku.presentation.ui.screen.my.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aiku.core.R
import com.aiku.core.theme.Body2
import com.aiku.core.theme.Subtitle3_Bold
import com.aiku.core.theme.Subtitle3_Medium
import com.aiku.core.theme.Subtitle3_SemiBold
import com.aiku.core.theme.Subtitle_2G
import com.aiku.presentation.theme.CobaltBlue
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.theme.Gray03
import com.aiku.presentation.theme.Green2
import com.aiku.presentation.ui.component.button.FullWidthButton
import com.aiku.presentation.ui.component.chip.FilledChip
import com.aiku.presentation.ui.component.image.ProfileIcon
import com.aiku.presentation.ui.screen.my.viewmodel.MyPageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    onListItemClicked: (MyPageListType) -> Unit = {},
    viewModel: MyPageViewModel = hiltViewModel()
) {

    val user by viewModel.user.collectAsStateWithLifecycle()
    val backgroundSpacingPx = with(LocalDensity.current) { 14.dp.toPx() }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawRoundRect(
                        color = Green2,
                        size = Size(size.width, size.height - backgroundSpacingPx),
                        cornerRadius = CornerRadius(100f, 100f)
                    )
                }
        ) {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(text = stringResource(R.string.my_page), style = Subtitle3_Bold)
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(IntrinsicSize.Min).padding(top = 30.dp).padding(horizontal = 35.dp)
            ) {
                Column {
                    Text(text = user.nickname, style = Subtitle_2G)
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = user.id.toString(),  // TODO EMAIL
                        style = Body2
                    )
                    FilledChip(
                        modifier = Modifier.padding(top = 16.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.edit_profile),
                            style = Body2,
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                ProfileIcon(
                    modifier = Modifier.aspectRatio(1f).fillMaxHeight(),
                    profile = user.profile
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(top = 28.dp)
                    .padding(horizontal = 20.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 14.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = user.badge.name, style = Subtitle3_Bold)
                    Row(
                        modifier = Modifier.padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.badge),
                            style = Body2,
                            color = Gray03
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = stringResource(R.string.badge),
                            tint = Gray03
                        )
                    }
                }
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = Gray02,
                    thickness = 1.dp,
                )
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.padding(start = 6.dp),
                            painter = painterResource(R.drawable.ic_aku),
                            contentDescription = stringResource(R.string.my_aku)
                        )
                        Text(text = user.point.toString(), style = Subtitle3_SemiBold, modifier = Modifier.padding(start = 6.dp))
                    }
                    Text(
                        modifier = Modifier.padding(top = 14.dp),
                        text = stringResource(R.string.my_aku),
                        style = Body2,
                        color = Gray03
                    )
                }
            }

            FullWidthButton(
                modifier = Modifier.padding(top = 20.dp).padding(horizontal = 20.dp),
                background = ButtonDefaults.buttonColors(
                    containerColor = CobaltBlue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = stringResource(R.string.go_to_store),
                    style = Subtitle3_Medium
                )
            }

        }

        MyPageList(
            modifier = Modifier
                .padding(top = 20.dp)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(10.dp))
            ,
            onItemClicked = onListItemClicked
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    MyPageScreen()
}