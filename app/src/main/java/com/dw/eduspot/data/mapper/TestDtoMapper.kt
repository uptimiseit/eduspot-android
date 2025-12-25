package com.dw.eduspot.data.mapper

import com.dw.eduspot.data.local.db.entity.TestEntity
import com.dw.eduspot.data.remote.dto.TestDto

fun TestDto.toTestEntity(courseId: String): TestEntity = TestEntity(
    testId = this.id,
    testTitle = this.title,
    totalQuestions = this.totalQuestions,
    durationMinutes = this.durationMinutes,
    courseId = courseId
)