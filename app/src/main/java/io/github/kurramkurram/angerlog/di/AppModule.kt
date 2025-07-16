package io.github.kurramkurram.angerlog.di

import io.github.kurramkurram.angerlog.ui.component.bottomnavigationbar.BottomNavigationBarViewModel
import io.github.kurramkurram.angerlog.data.database.AngerLogDatabase
import io.github.kurramkurram.angerlog.data.repository.AgreementPolicyRepository
import io.github.kurramkurram.angerlog.data.repository.AgreementPolicyRepositoryImpl
import io.github.kurramkurram.angerlog.data.repository.AngerLogDataRepository
import io.github.kurramkurram.angerlog.data.repository.AngerLogDataRepositoryImpl
import io.github.kurramkurram.angerlog.data.repository.TipsRepository
import io.github.kurramkurram.angerlog.data.repository.TipsRepositoryImpl
import io.github.kurramkurram.angerlog.ui.screen.analysis.AnalysisDataUseCase
import io.github.kurramkurram.angerlog.ui.screen.analysis.AnalysisDataUseCaseImpl
import io.github.kurramkurram.angerlog.ui.screen.analysis.AnalysisViewModel
import io.github.kurramkurram.angerlog.ui.screen.calendar.CalendarDataUseCase
import io.github.kurramkurram.angerlog.ui.screen.calendar.CalendarDataUseCaseImpl
import io.github.kurramkurram.angerlog.ui.screen.calendar.CalendarViewModel
import io.github.kurramkurram.angerlog.ui.screen.home.HomeViewModel
import io.github.kurramkurram.angerlog.ui.screen.initial.InitialViewModel
import io.github.kurramkurram.angerlog.ui.screen.register.RegisterViewModel
import io.github.kurramkurram.angerlog.ui.screen.setting.SettingViewModel
import io.github.kurramkurram.angerlog.ui.screen.tips.TipsInfoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * koinのモジュール [https://insert-koin.io/docs/reference/koin-core/modules/#what-is-a-module].
 */
val appModule =
    module {
        single<AngerLogDatabase> { AngerLogDatabase.getDatabases(get()) }
        single<AngerLogDataRepository> { AngerLogDataRepositoryImpl(get()) }
        single<AgreementPolicyRepository> { AgreementPolicyRepositoryImpl() }
        single<TipsRepository> { TipsRepositoryImpl() }

        // ボトムナビゲーション
        viewModel { BottomNavigationBarViewModel(get()) }

        viewModel { InitialViewModel(get()) }

        viewModel { HomeViewModel(get(), get()) }

        viewModel { RegisterViewModel(get()) }

        viewModel { CalendarViewModel(get()) }
        single<CalendarDataUseCase> { CalendarDataUseCaseImpl(get()) }

        viewModel { AnalysisViewModel(get()) }
        single<AnalysisDataUseCase> { AnalysisDataUseCaseImpl(get()) }

        // 設定画面
        viewModel {SettingViewModel(get())}

        // Tips画面
        viewModel { TipsInfoViewModel(get()) }
    }
