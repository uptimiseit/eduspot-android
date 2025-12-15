package com.dw.eduspot.ui.testlist

object TestFakeData {

    fun getTestsForCourse(courseId: String): List<TestItem> {
        return listOf(
            TestItem(
                id = "$courseId-T1",
                title = "Mock Test 1",
                totalQuestions = 50,
                durationMinutes = 60
            ),
            TestItem(
                id = "$courseId-T2",
                title = "Mock Test 2",
                totalQuestions = 100,
                durationMinutes = 120
            )
        )
    }
}