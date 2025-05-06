package com.aiku.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.aiku.domain.usecase.LoginUseCase
import com.aiku.presentation.navigation.route.BtmNav
import com.aiku.presentation.navigation.route.Routes
import com.aiku.presentation.state.group.GroupState
import com.aiku.presentation.state.schedule.GroupScheduleOverviewState
import com.aiku.presentation.state.user.MemberState
import com.aiku.presentation.state.user.TermViewState
import com.aiku.presentation.theme.Gray01
import com.aiku.presentation.ui.component.navigation.BottomNavigation
import com.aiku.presentation.ui.group.GroupScreen
import com.aiku.presentation.ui.group.schedule.waiting.composable.BettingScreen
import com.aiku.presentation.ui.group.schedule.waiting.composable.WaitingScheduleScreen
import com.aiku.presentation.ui.screen.home.composable.HomeScreen
import com.aiku.presentation.ui.screen.login.composable.LoginScreen
import com.aiku.presentation.ui.screen.my.composable.InquiryScreen
import com.aiku.presentation.ui.screen.my.composable.MyPageListType
import com.aiku.presentation.ui.screen.my.composable.MyPageScreen
import com.aiku.presentation.ui.screen.my.composable.NotificationSettingScreen
import com.aiku.presentation.ui.screen.my.composable.SeeTermDetailScreen
import com.aiku.presentation.ui.screen.my.composable.SeeTermsScreen
import com.aiku.presentation.ui.screen.notification.composable.NotificationScreen
import com.aiku.presentation.ui.screen.schedule.MyScheduleScreen
import com.aiku.presentation.ui.screen.signup.composable.ProfileEditScreen
import com.aiku.presentation.ui.screen.signup.composable.TermsAgreementScreen
import com.aiku.presentation.ui.screen.signup.composable.TermsContentScreen
import com.aiku.presentation.ui.screen.splash.composable.SplashScreen
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.reflect.typeOf

@Composable
fun AikuNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    loginUseCase: LoginUseCase
) {

    var selectedBottomItem by remember { mutableStateOf(BtmNav.Home) }
    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (backStackEntry.shouldShowBottomNav()) {
                BottomNavigation(
                    bottomItems = BtmNav.entries,
                    selectedBottomItem = selectedBottomItem,
                    onTabSelected = {
                        selectedBottomItem = it
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Splash,
            modifier = Modifier,
            enterTransition = {
                EnterTransition.None
            }, exitTransition = {
                ExitTransition.None
            }
        ) {
            composable<Routes.Splash> {
                SplashScreen(
                    loginUseCase = loginUseCase,
                    onComplete = { isSuccessful ->
                        navController.navigate(if (isSuccessful) Routes.Main.Graph else Routes.Auth.Graph) {
                            popUpTo<Routes.Splash> {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            navigation<Routes.Auth.Graph>(
                startDestination = Routes.Auth.Login,
            ) {
                composable<Routes.Auth.Login> {
                    LoginScreen(loginUseCase = loginUseCase, onComplete = { isSucessful, idToken, email->
                        navController.navigate(
                            (if(isSucessful) Routes.Main.Graph
                            else idToken?.let { it1 -> Routes.Auth.TermAgreement(it1, email) }) as Any
                        ) {
                            popUpTo<Routes.Auth.Login> {
                                inclusive = true
                            }
                        }
                    })
                }
                composable<Routes.Auth.TermAgreement> { backStackEntry ->
                    val kakaoInfo = backStackEntry.toRoute<Routes.Auth.TermAgreement>()
                    val idToken = kakaoInfo.idToken
                    val email = kakaoInfo.email
                    TermsAgreementScreen(
                        idToken,
                        email,
                        onNavigateToProfileEditScreen = { idToken, email ->
                            navController.navigate(Routes.Auth.ProfileEdit(idToken, email))
                        },
                        onNavigateToTermContentScreen = { identifier ->
                            navController.navigate(
                                Routes.Auth.TermContent(identifier)
                            )
                        }
                    )
                }
                composable<Routes.Auth.TermContent> { backStackEntry ->
                    val termContent = backStackEntry.toRoute<Routes.Auth.TermContent>()
                    val identifier = termContent.identifier
                    TermsContentScreen(identifier)
                }
                composable<Routes.Auth.ProfileEdit> { backStackEntry ->
                    val kakaoInfo = backStackEntry.toRoute<Routes.Auth.ProfileEdit>()
                    val idToken = kakaoInfo.idToken
                    val email = kakaoInfo.email

                    ProfileEditScreen(
                        idToken,
                        email,
                        modifier = Modifier,
                        onCompleteEdit = {
                            navController.navigate(Routes.Main.Graph) {
                                popUpTo(Routes.Auth.Graph)
                            }
                        }
                    )
                }
            }

            navigation<Routes.Main.Graph>(
                startDestination = Routes.Main.Home,
            ) {
                composable<Routes.Main.Home> {
                    // 홈 화면
                    HomeScreen(
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                        onGroupClicked = { groupId, groupName ->
                            navController.navigate(
                                Routes.Main.Group(groupId, groupName)
                            )
                        },
                        onTodayScheduleClicked = {},
                        onNavigateToNotification = {
                            navController.navigate(Routes.Main.Notification)
                        }
                    )
                }
                composable<Routes.Main.MySchedule> {
                    MyScheduleScreen(
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                        onUserScheduleClicked = {} //TODO : navigate
                    )
                }
                composable<Routes.Main.MyPage> {
                    MyPageScreen(
                        modifier = Modifier.fillMaxSize().background(Gray01),
                        onListItemClicked = {
                            when(it) {
                                MyPageListType.NOTIFICATION -> {
                                    navController.navigate(Routes.Main.NotificationSetting)
                                }
                                MyPageListType.ACCOUNT -> {
                                    // 계정 화면
                                }
                                MyPageListType.SEE_TERMS -> {
                                    navController.navigate(Routes.Main.SeeTerms)
                                }
                                MyPageListType.SET_PERMISSIONS -> {
                                    // 권한 설정 화면
                                }
                                MyPageListType.INQUIRY -> {
                                    navController.navigate(Routes.Main.Inquiry)
                                }
                                MyPageListType.HELP -> {
                                    // 도움말 화면
                                }
                                MyPageListType.GIVE_RATING -> {
                                    // 평가 화면
                                }
                            }
                        }
                    )
                }

                composable<Routes.Main.Group> { backStackEntry ->
                    val groupArguments = backStackEntry.toRoute<Routes.Main.Group>()
                    val groupId = groupArguments.groupId
                    val groupName = groupArguments.groupName

                    GroupScreen(
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                        groupId = groupId,
                        groupName = groupName,
                        onNavigateToWaitingScheduleScreen = { groupScheduleOverview, group ->
                            navController.navigate(
                                Routes.ScheduleWaiting.ScheduleWaiting(
                                    group = group,
                                    groupScheduleOverview = groupScheduleOverview
                                )
                            )
                        }, onNavigateToCreateScheduleScreen = {
                            navController.navigate(Routes.Main.CreateSchedule.Graph)
                        }
                    )
                }

                composable<Routes.Main.ScheduleRunning> {
                    // 진행 중 약속 화면
                }

                navigation<Routes.Main.CreateSchedule.Graph>(
                    startDestination = Routes.Main.CreateSchedule.First,
                ) {
                    composable<Routes.Main.CreateSchedule.First> {

                    }
                    // 약속 생성 화면 그래프
                }

                composable<Routes.Main.Shop> {
                    // 상점 화면
                }
                composable<Routes.Main.Notification> {
                    NotificationScreen(
                        modifier = Modifier.fillMaxSize().background(Gray01)
                    )
                }
                composable<Routes.Main.NotificationSetting> {
                    NotificationSettingScreen(
                        modifier = Modifier.fillMaxSize().background(Color.White)
                    )
                }
            }

            navigation<Routes.ScheduleWaiting.Graph>(
                startDestination = Routes.ScheduleWaiting.ScheduleWaiting(),
            ) {
                composable<Routes.ScheduleWaiting.ScheduleWaiting>(
                    typeMap = mapOf(
                        typeOf<GroupState>() to GroupNavType,
                        typeOf<GroupScheduleOverviewState>() to GroupScheduleOverviewNavType
                    )
                ) { backStackEntry ->
                    val group = backStackEntry.toRoute<Routes.ScheduleWaiting.ScheduleWaiting>().group
                    val groupScheduleOverview =
                        backStackEntry.toRoute<Routes.ScheduleWaiting.ScheduleWaiting>().groupScheduleOverview

                    val selectedMemberId by backStackEntry.savedStateHandle.getStateFlow("selectedMemberId", 0L).collectAsStateWithLifecycle()

                    WaitingScheduleScreen(
                        modifier = Modifier,
                        group = group,
                        scheduleOverview = groupScheduleOverview,
                        selectedMemberId = selectedMemberId,
                        onMemberClicked = { member ->
                            navController.navigate(
                                Routes.ScheduleWaiting.Betting(
                                    member = member,
                                    group = group,
                                    scheduleId = groupScheduleOverview.id
                                )
                            )
                        }
                    )
                }

                composable<Routes.ScheduleWaiting.Betting>(
                    typeMap = mapOf(
                        typeOf<GroupState>() to GroupNavType,
                        typeOf<MemberState>() to MemberNavType
                    )
                ) { backStackEntry ->
                    val arguments = backStackEntry.toRoute<Routes.ScheduleWaiting.Betting>()
                    BettingScreen(
                        modifier = Modifier.fillMaxSize(),
                        onBettingComplete = {
                            navController.previousBackStackEntry?.savedStateHandle?.set("selectedMemberId", arguments.member.id)
                            navController.popBackStack()
                        }
                    )
                }

                composable<Routes.Main.SeeTerms> {
                    SeeTermsScreen(
                        modifier = Modifier.fillMaxSize().background(Color.White),
                        onTermItemClicked = {
                            navController.navigate(Routes.Main.SeeTermDetail(it))
                        }
                    )
                }

                composable<Routes.Main.SeeTermDetail>(
                    typeMap = mapOf(
                        typeOf<TermViewState>() to TermNavType
                    )
                ) {
                    val arguments = it.toRoute<Routes.Main.SeeTermDetail>()
                    SeeTermDetailScreen(
                        term = arguments.term
                    )
                }

                composable<Routes.Main.Inquiry> {
                    InquiryScreen(
                        modifier = Modifier.fillMaxSize().background(Color.White).padding(horizontal = 20.dp).padding(bottom = 36.dp),
                        onNavigateTermDetailScreen = {
                            navController.navigate(Routes.Main.SeeTermDetail(it))
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(selectedBottomItem) {
        if (backStackEntry.shouldShowBottomNav()) {
            navController.navigate(
                when (selectedBottomItem) {
                    BtmNav.Home -> Routes.Main.Home
                    BtmNav.MySchedule -> Routes.Main.MySchedule
                    BtmNav.My -> Routes.Main.MyPage
                }
            ) {
                popUpTo<Routes.Main.Home> {
                    inclusive = false
                }
                launchSingleTop = true
            }
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
private fun NavBackStackEntry?.shouldShowBottomNav(): Boolean {
    return when (this?.destination?.route?.substringBefore("/")) {
        Routes.Main.Home.serializer().descriptor.serialName,
        Routes.Main.MySchedule.serializer().descriptor.serialName,
        Routes.Main.MyPage.serializer().descriptor.serialName,
        Routes.Main.Group.serializer().descriptor.serialName -> true

        else -> false
    }
}