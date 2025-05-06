package com.aiku.presentation.ui.group

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.core.theme.Body2
import com.aiku.core.theme.Subtitle_2G
import com.aiku.presentation.state.user.MemberState
import com.aiku.presentation.theme.Gray02
import com.aiku.presentation.ui.component.background.CircularBackground
import com.aiku.presentation.ui.component.chip.OutlinedChip
import com.aiku.presentation.ui.component.image.ProfileIcon

private const val MEMBERS_PER_ROW = 3

@Composable
fun GroupMembersView(
    modifier: Modifier = Modifier,
    onInviteClicked: () -> Unit = {},
    scrollState: LazyListState,
    members: List<MemberState>
) {
    if (members.isEmpty()) {
        EmptyMembersView(
            modifier = modifier,
            onInviteClicked
        )
    } else {
        LazyColumn(
            modifier = modifier,
            state = scrollState
        ) {
            item {
                Row(
                    modifier = Modifier.padding(top = 30.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularBackground(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            showClickRipple = false,
                            onClick = onInviteClicked,
                            color = Gray02
                        ) {
                            Image(
                                modifier = Modifier.padding(12.dp),
                                painter = painterResource(id = R.drawable.char_head_unknown),
                                contentDescription = stringResource(
                                    id = R.string.invite
                                )
                            )
                        }
                        Text(
                            modifier = Modifier.padding(top = 5.dp),
                            text = stringResource(id = R.string.invite),
                            style = Body2.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                    MembersIconRow(
                        modifier = Modifier.weight(2f),
                        members = members.take(MEMBERS_PER_ROW - 1)
                    )
                }
            }

            items(count = members.size / MEMBERS_PER_ROW) {
                MembersIconRow(
                    modifier = Modifier.padding(top = 30.dp),
                    members = members.subList(
                        (it + 1) * MEMBERS_PER_ROW - 1,
                        ((it + 2) * MEMBERS_PER_ROW - 1).coerceAtMost(members.size)
                    ),
                    adjustWeight = true
                )
            }
        }
    }
}

@Composable
fun MembersIconRow(
    modifier: Modifier = Modifier,
    members: List<MemberState>,
    adjustWeight: Boolean = false
) {
    Row(
        modifier = modifier
    ) {
        members.forEach { member ->
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileIcon(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    showClickRipple = false,
                    profile = member.profile
                )
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = member.nickname,
                    style = Body2.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        if (adjustWeight) {
            repeat(MEMBERS_PER_ROW - members.size) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun EmptyMembersView(
    modifier: Modifier = Modifier,
    onInviteClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.suggest_invite_member), style = Subtitle_2G)
        Image(
            modifier = Modifier
                .size(300.dp)
                .padding(top = 24.dp),
            painter = painterResource(id = R.drawable.char_running_boy),
            contentDescription = stringResource(
                id = R.string.group_no_member_content_description
            )
        )
        OutlinedChip(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = R.string.invite_member),
            onClick = onInviteClicked
        )
    }
}