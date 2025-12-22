import com.dw.eduspot.ui.dashboard.model.ExamCourse

object DashboardFakeData {

    val popularExams = listOf(
        ExamCourse(
            id = "ssc_cgl",
            title = "SSC CGL Full-Length Test Series",
            subtitle = "Complete Tier I + II Preparation",
            category = "SSC",
            totalTests = 30,
            language = "Hindi",
            mrp = 1299,
            offerPrice = 499,
            isBestSeller = true
        ),
        ExamCourse(
            id = "reet_lvl1",
            title = "REET Level 1 Test Series",
            subtitle = "Latest Pattern Mock Tests",
            category = "REET",
            totalTests = 25,
            language = "Hindi",
            mrp = 999,
            offerPrice = 399
        )
    )

    val newExams = listOf(
        ExamCourse(
            id = "bank_po",
            title = "Bank PO Full-Length Tests",
            subtitle = "Pre + Mains Mock Series",
            category = "Banking",
            totalTests = 20,
            language = "Hindi",
            mrp = 1199,
            offerPrice = 599,
            isNew = true
        )
    )
}