package com.example.di

import com.example.repo.CharacterRepo
import com.example.repo.CharacterRepoImpl
import org.koin.dsl.module

val koinModule = module {
    single<CharacterRepo> {
        CharacterRepoImpl()
    }
}