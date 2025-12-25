//package com.dw.eduspot.data.remote.dto
//
//import com.dw.eduspot.data.local.db.entity.CourseEntity
//import com.dw.eduspot.ui.course.Course
//
//data class CourseDto(
//    val id: String,
//    val title: String,
//    val subtitle: String,
//    val category: String,
//    val totalTests: Int,
//    val language: String,
//    val mrp: Int,
//    val offerPrice: Int,
//    val isBestSeller: Boolean,
//    val isNew: Boolean,
//    val updatedAt: Long = System.currentTimeMillis()
//) {
//    fun toEntity() = CourseEntity(
//        id = id,
//        title = title,
//        subtitle = subtitle,
//        category = category,
//        language = language,
//        totalTests = totalTests,
//        mrp = mrp,
//        offerPrice = offerPrice,
//        isBestSeller = isBestSeller,
//        isNew = isNew,
//        updatedAt = updatedAt
//    )
//
//    fun toDomain() = Course(
//        id = id,
//        title = title,
//        category = category,
//        totalTests = totalTests,
//        language = language,
//        mrp = mrp,
//        offerPrice = offerPrice,
//        isBestSeller = isBestSeller,
//        isNew = isNew
//    )
//}