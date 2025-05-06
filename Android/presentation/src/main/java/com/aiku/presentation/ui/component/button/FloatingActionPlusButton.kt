package com.aiku.presentation.ui.component.button

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.presentation.theme.CobaltBlue

private val FABSize = 54.dp
private val FABIconSize = 20.dp

@Composable
fun FloatingActionPlusButton(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier
            .size(FABSize)
            .shadow(
                elevation = 6.dp, // 그림자 높이
                shape = CircleShape, // FAB의 원형 모양에 맞춘 그림자
                clip = false, // 그림자가 요소 바깥으로 나갈 수 있게 함
                ambientColor = Color.Gray.copy(alpha = 0.8f), // 주변 그림자 색상
                spotColor = Color.Black.copy(alpha = 0.4f) // 직접적인 그림자 색상
            ),
        shape = CircleShape,
        containerColor = CobaltBlue,
        content = {
            Icon(
                modifier = Modifier.size(FABIconSize),
                painter = painterResource(id = R.drawable.fab_plus),
                contentDescription = "Add",
                tint = Color.White
            )
        },
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FloatingActionPlusButton({})
}