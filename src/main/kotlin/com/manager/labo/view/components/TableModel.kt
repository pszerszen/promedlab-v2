package com.manager.labo.view.components

enum class TableModel(val columns: Array<String>) {

    EXAMINATIONS_SET(arrayOf("Kod badania", "Nazwa Badania", "Badający", "Wynik")),
    REQUESTS(arrayOf("Data Zlecenia", "Kod badania", "PESEL", "Nazwisko", "Imię", "Adres", "Telefon")),
    PATIENTS(arrayOf("PESEL", "Nazwisko", "Imię", "Adres", "Telefon"));

}
