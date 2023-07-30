package com.ektour.utils

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import java.io.InputStream
import java.util.UUID
import org.apache.poi.ss.formula.functions.T
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class CustomAwsS3Client(
    private val s3Client: AmazonS3Client,
) {
    companion object {
        const val bucketName = "ektour"
        const val excelPathKey = "static/estimate.xlsx"
        const val STATIC_DIRECTORY = "static"
        const val DEFAULT_LOGO_FILENAME = "logo.png"
    }

    fun <T> useS3Object(key: String, block: (InputStream) -> T): T {
        s3Client.getObject(bucketName, key).objectContent.use { inputStream ->
            return block(inputStream)
        }
    }

    fun uploadObject(
        directory: String? = null,
        file: MultipartFile,
        fileName: String? = null
    ) {
        val objectName = fileName ?: file.originalFilename ?: UUID.randomUUID().toString()
        val objectMetadata = ObjectMetadata().apply {
            contentLength = file.size
            contentType = file.contentType
        }
        val bucketPath = directory ?: STATIC_DIRECTORY
        file.inputStream.use { inputStream ->
            s3Client.putObject(
                PutObjectRequest(bucketPath, objectName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            )
        }
    }

    fun deleteObject(key: String) {
        try {
            s3Client.deleteObject(bucketName, key)
        } catch (ignored: Exception) { }
    }
}
