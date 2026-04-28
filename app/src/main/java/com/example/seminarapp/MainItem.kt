package com.example.seminarapp

sealed class MainItem {
    data class Header(val name: String) : MainItem()
    object Feature : MainItem()
    object Upcoming : MainItem()
    data class SectionTitle(val title: String) : MainItem()
    data class SeminarItem(val seminar: Seminar) : MainItem()
}
