package com.aiku.presentation.ui.screen.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.aiku.presentation.theme.ScreenHorizontalPadding
import com.aiku.presentation.ui.component.calendar.Calendar
import com.aiku.presentation.ui.component.card.ScheduleCard
import com.aiku.presentation.ui.screen.schedule.viewmodel.CalendarViewModel
import com.aiku.presentation.ui.screen.schedule.viewmodel.UserMonthlySchedulesUiState
import com.aiku.presentation.ui.screen.schedule.viewmodel.UserSchedulesUiState
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScheduleScreen(
    modifier: Modifier = Modifier,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    onUserScheduleClicked: () -> Unit
) {

    val currentYearMonth by calendarViewModel.currentYearMonth.collectAsState()
    val lazyUserMonthlySchedulePagingItems = calendarViewModel.userMonthlySchedules.collectAsLazyPagingItems()
    val datesWithSchedules = remember { mutableSetOf<LocalDate>() }
    val userMonthlySchedulesUiState by calendarViewModel.userMonthlySchedulesUiState.collectAsState()

    val selectedDate by calendarViewModel.selectedDate.collectAsState()
    val lazyUserSchedulePagingItems = calendarViewModel.userSchedules.collectAsLazyPagingItems()
    val userSchedulesUiState by calendarViewModel.userSchedulesUiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("내 약속") } //TODO : 수정예정
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = ScreenHorizontalPadding)
        ) {

            // 약속 있는 날짜
            when (userMonthlySchedulesUiState) {
                UserMonthlySchedulesUiState.Loading -> {}

                UserMonthlySchedulesUiState.Error -> {}

                UserMonthlySchedulesUiState.Success -> {
                    lazyUserMonthlySchedulePagingItems.itemSnapshotList.items.forEach { schedule ->
                        schedule.time.toLocalDate()?.let { datesWithSchedules.add(it) }
                    }

                    Calendar(
                        year = currentYearMonth.year,
                        month = currentYearMonth.monthValue,
                        selectedDate = selectedDate,
                        onDateSelected = { calendarViewModel.selectDate(it) },
                        onPreviousMonth = { calendarViewModel.onPreviousMonth() },
                        onNextMonth = { calendarViewModel.onNextMonth() },
                        datesWithSchedules = datesWithSchedules.toList()
                    )
                }
            }



            // 선택된 날짜의 약속 목록 표시
            when (userSchedulesUiState) {
                UserSchedulesUiState.Loading -> {}

                UserSchedulesUiState.Error -> {}

                UserSchedulesUiState.Success -> {
                    if (lazyUserSchedulePagingItems.itemCount == 0) {
                        //TODO : 약속 없을 때 캐릭터
                    } else {
                        LazyColumn(
                            modifier = Modifier.padding(top = 28.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(lazyUserSchedulePagingItems.itemCount) { index ->
                                lazyUserSchedulePagingItems[index]?.let {
                                    ScheduleCard(
                                        schedule = it,
                                        onClick = { onUserScheduleClicked() }
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun MyScheduleScreenPreview() {
    MyScheduleScreen(
        onUserScheduleClicked = {}
    )
}