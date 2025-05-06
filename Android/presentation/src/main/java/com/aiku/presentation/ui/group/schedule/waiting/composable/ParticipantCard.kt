package com.aiku.presentation.ui.group.schedule.waiting.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.theme.Body2
import com.aiku.core.theme.Subtitle_4G
import com.aiku.domain.model.group.type.ProfileBackground
import com.aiku.domain.model.group.type.ProfileCharacter
import com.aiku.domain.model.group.type.ProfileType
import com.aiku.presentation.state.user.MemberState
import com.aiku.presentation.state.user.ProfileState
import com.aiku.presentation.theme.CobaltBlue
import com.aiku.presentation.theme.Green5
import com.aiku.presentation.ui.component.image.ProfileImage
import com.aiku.presentation.util.noRippleClickable
import com.aiku.core.R
import com.aiku.core.theme.Caption1

@Composable
fun ParticipantCard(
    modifier: Modifier = Modifier,
    member: MemberState,
    color: Color = Color.White,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    rank: Int,
    border: BorderStroke? = null,
    onParticipantClicked: (member: MemberState) -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color
        ), shape = shape,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 1.dp
        ), border = border
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .noRippleClickable(onClick = { onParticipantClicked(member) })
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(bottomEnd = 8.dp))
                        .background(Green5)
                ) {
                    Text(
                        text = rank.toString(),
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White,
                        style = Subtitle_4G
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                if (border == null) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(horizontal = 6.dp),
                        text = "꼴찌로 선택",
                        color = CobaltBlue,
                        style = Body2,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(6.dp)
                            .padding(horizontal = 6.dp).clip(
                            RoundedCornerShape(50)
                        ).background(CobaltBlue).padding(horizontal = 4.dp, vertical = 2.dp), text = "선택 완료",
                        color = Color.White,
                        style = Body2,
                    )
                }
            }
            Spacer(modifier = Modifier.size(25.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(60.dp),
                    profile = member.profile
                )
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = member.nickname,
                        style = Body2,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_aku),
                            contentDescription = stringResource(R.string.participation_fee)
                        )
                        Text(
                            modifier = Modifier.padding(start = 4.dp),
                            text = member.point.toString(),
                            style = Caption1
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}

@Composable
@Preview
private fun ParticipantCardPreview() {
    ParticipantCard(
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(8.dp)),
        member = MemberState(
            1, "김철수", ProfileState(
                ProfileType.CHAR, "", ProfileCharacter.C01,
                ProfileBackground.GREEN
            ), 200
        ),
        rank = 1,
        onParticipantClicked = {}
    )
}