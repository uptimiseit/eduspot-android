package com.dw.eduspot.ui.dashboard

data class ExamItem(
    val id: String,
    val title: String,
    val description: String
)

object DashboardFakeData {

    val popularExams = listOf(
        ExamItem("1", "UPSC", "Civil Services Examination"),
        ExamItem("2", "REET", "Rajasthan Eligibility Exam"),
        ExamItem("3", "NEET", "Medical Entrance Exam")
    )

    val newExams = listOf(
        ExamItem("4", "SSC CGL", "Staff Selection Commission"),
        ExamItem("5", "Bank PO", "Probationary Officer Exams")
    )
}