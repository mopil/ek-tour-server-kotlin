package com.ektour.utils

import com.amazonaws.services.s3.AmazonS3Client
import org.apache.poi.ss.formula.functions.T
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class CustomAwsS3Client(
    private val s3Client: AmazonS3Client
) {
    companion object {
        const val bucketName = "ektour"
        const val excelPathKey = "static/estimate.xlsx"
    }

    fun <T> useS3Object(key: String, block: (InputStream) -> T): T {
        s3Client.getObject(bucketName, key).objectContent.use { inputStream ->
            return block(inputStream)
        }
    }
}
