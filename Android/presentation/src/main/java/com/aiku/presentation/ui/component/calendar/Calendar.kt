package com.aiku.presentation.ui.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aiku.core.R
import com.aiku.core.theme.Body2_Medium
import com.aiku.core.theme.Body2_SemiBold
import com.aiku.core.theme.Subtitle3_SemiBold
import com.aiku.presentation.theme.CobaltBlue
import com.aiku.presentation.theme.Gray04
import com.aiku.presentation.theme.Green5
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun Calendar(
    year: Int,
    month: Int,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    datesWithSchedules: List<LocalDate>
) {
    val yearMonth = YearMonth.of(year, month)
    val daysInMonth = yearMonth.lengthOfMonth()  // 해당 월의 일 수
    val firstDayOfMonth = yearMonth.atDay(1).dayOfWeek  // 해당 월의 1일이 시작하는 요일

    Column {

        // 월/연도 표시와 이전/다음 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            IconButton(onClick = onPreviousMonth) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Previous Month",
                    tint = Color.Gray
                )
            }

            Text(
                text = "${yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} $year",
                style = Subtitle3_SemiBold,
                color = CobaltBlue,
            )

            IconButton(onClick = onNextMonth) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "Next Month",
                    tint = Color.Gray
                )
            }
        }


        // 요일 헤더
        val daysOfWeek = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 26.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    style = Body2_SemiBold,
                    modifier = Modifier.weight(1f), // 각 요일을 균등 분할
                    textAlign = TextAlign.Center // 텍스트 가운데 정렬
                )
            }
        }

        // 날짜 그리드
        val daysOffset = (firstDayOfMonth.value % 7)
        LazyVerticalGrid(
            columns = GridCells.Fixed(7)
        ) {

            //빈칸
            items(daysOffset) { Spacer(modifier = Modifier.aspectRatio(1f)) }

            // 날짜 칸
            items(daysInMonth) { day ->
                val date = LocalDate.of(year, month, day + 1)
                DateCell(
                    day = day + 1,
                    isSelected = date == selectedDate,
                    hasSchedule = datesWithSchedules.contains(date),
                    onClick = { onDateSelected(date) }
                )
            }
        }
    }
}

@Composable
fun DateCell(
    day: Int,
    isSelected: Boolean,
    hasSchedule: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .background(
                color = if (isSelected) Green5 else Color.Transparent,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (hasSchedule) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .background(color = if (isSelected) Color.White else CobaltBlue, shape = CircleShape),
                )
            } else {
                Spacer(modifier = Modifier.size(5.dp))
            }

            Text(
                modifier = Modifier.padding(top = 6.dp, bottom = 11.dp),
                text = day.toString(),
                color = if (isSelected) Color.White else Gray04,
                style = Body2_Medium
            )
        }
    }
}