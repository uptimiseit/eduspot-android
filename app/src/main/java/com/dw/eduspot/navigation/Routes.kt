package com.dw.eduspot.navigation

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val MAIN = "main" // Shell entry

    const val HOME = "home"
    const val MY_COURSES = "my_courses"
    const val RESULT = "result"
    const val PROFILE = "profile"

    const val COURSE_DETAIL = "course_detail/{courseId}"
    const val TEST_LIST = "test_list/{courseId}/{attemptId}"
    const val TEST_GUIDELINES = "test_guidelines/{attemptId}/{testId}"
    const val TEST_PREPARING = "test_preparing/{attemptId}/{testId}"
    const val TEST_ENGINE = "test_engine/{attemptId}/{testId}"
    const val RESULT_DETAIL = "result_detail/{attemptId}/{testId}"
}