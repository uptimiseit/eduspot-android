package com.dw.eduspot.ui.dashboard

import com.dw.eduspot.ui.dashboard.model.ExamCategory

object DashboardCategories {

    val categories = listOf(
        ExamCategory("all", "All"),
        ExamCategory("upsc", "UPSC"),
        ExamCategory("neet", "NEET"),
        ExamCategory("reet", "REET"),
        ExamCategory("ssc", "SSC"),
        ExamCategory("bank", "Banking")
    )
}