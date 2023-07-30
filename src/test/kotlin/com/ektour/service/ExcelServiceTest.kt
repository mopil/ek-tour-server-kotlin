package com.ektour.service
import com.ektour.common.client.CustomAwsS3Client
import com.ektour.common.client.CustomAwsS3Client.Companion.excelPathKey
import com.ektour.common.exception.ExcelException
import com.ektour.model.TravelType
import com.ektour.model.VehicleType
import com.ektour.model.domain.Estimate
import com.ektour.model.domain.EstimateRepository
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import io.mockk.every
import io.mockk.mockk
import org.apache.poi.ss.usermodel.Workbook
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Optional
import javax.servlet.http.HttpServletResponse

@DisplayName("엑셀 서비스 테스트")
internal class ExcelServiceTest {
    val s3Client: CustomAwsS3Client = mockk()
    val estimateRepository: EstimateRepository = mockk()
    val excelService = ExcelService(estimateRepository, s3Client)

    val mockEstimate = Estimate(
        id = 1L,
        name = "홍길동",
        email = "",
        phone = "",
        password = "",
        travelType = TravelType.일반여행,
        vehicleType = VehicleType.SMALL,
        vehicleNumber = 1,
        memberCount = 1,
        departDate = "",
        arrivalDate = "",
        departPlace = "",
        arrivalPlace = "",
        memo = "",
        ip = ""
    )

    @Test
    fun `견적서 엑셀 다운로드에 성공한다`() {
        every { estimateRepository.findById(any()) }.returns(Optional.of(mockEstimate))
        every { s3Client.useS3Object<Workbook>(excelPathKey, any()) }.throws(mockk())

        val response: HttpServletResponse = mockk()
        excelService.createExcel(1L, response).run {
            response.status shouldBe 200
        }
    }

    @Test
    fun `기본 엑셀 파일을 읽어오는데 실패하면 예외를 던진다`() {
        every { estimateRepository.findById(any()) }.returns(Optional.of(mockEstimate))
        every { s3Client.useS3Object<Workbook>(excelPathKey, any()) }.throws(Exception("S3 오류"))

        shouldThrowExactly<ExcelException> {
            excelService.createExcel(1L, mockk())
        }.run { message shouldStartWith "기본 견적서 엑셀 파일을 읽어오는데 실패했습니다." }
    }
}
