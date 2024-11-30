package com.teampatch.core.domain.fake

import com.teampatch.core.domain.model.Daily

class FakeDaily : FakeModel<List<Daily>>() {

    override fun build(): List<Daily> = listOf(
        Daily("q001", 1, "What is your favorite color?"),
        Daily("q002", 2, "What is your dream job?"),
        Daily("q003", 3, "Where would you like to travel?"),
        Daily("q004", 4, "What is your favorite book?"),
        Daily("q005", 5, "Who is your role model?"),
        Daily("q006", 6, "What is your favorite food?"),
        Daily("q007", 7, "What are your hobbies?"),
        Daily("q008", 8, "What is your favorite movie?"),
        Daily("q009", 9, "What is your biggest fear?"),
        Daily("q010", 10, "What is your proudest achievement?")
    )
}