package com.dw.eduspot.ui.course

data class Course(
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    val totalTests: Int   // ✅ ADD THIS
)

object CourseFakeData {

    fun getCourseById(id: String): Course {
        return when (id) {
            "UPSC" -> Course(
                id = "REET",
                title = "REET Full Course",
                description = "...",
                price = "₹999",
                totalTests = 2
            )
            "REET" -> Course(
                id = "REET",
                title = "REET Exam Mock Test",
                description = "Rajasthan REET level mock tests.",
                price = "₹499",
                totalTests = 2
            )
            else -> Course(
                id = "NEET",
                title = "NEET Medical Mock Test",
                description = "Medical entrance mock tests with ranking.",
                price = "₹1299",
                totalTests = 2
            )
        }
    }
}