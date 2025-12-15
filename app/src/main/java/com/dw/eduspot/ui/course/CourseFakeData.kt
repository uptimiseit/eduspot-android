package com.dw.eduspot.ui.course

data class Course(
    val id: String,
    val title: String,
    val description: String,
    val price: String
)

object CourseFakeData {

    fun getCourseById(id: String): Course {
        return when (id) {
            "UPSC" -> Course(
                id = "UPSC",
                title = "UPSC Full Mock Test",
                description = "Complete UPSC mock test series with analysis.",
                price = "₹999"
            )
            "REET" -> Course(
                id = "REET",
                title = "REET Exam Mock Test",
                description = "Rajasthan REET level mock tests.",
                price = "₹499"
            )
            else -> Course(
                id = "NEET",
                title = "NEET Medical Mock Test",
                description = "Medical entrance mock tests with ranking.",
                price = "₹1299"
            )
        }
    }
}