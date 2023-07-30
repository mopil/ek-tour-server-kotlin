package com.ektour.service

import com.ektour.common.exception.ExcelException
import com.ektour.model.domain.Estimate
import com.ektour.model.domain.EstimateRepository
import com.ektour.utils.CustomAwsS3Client
import com.ektour.utils.CustomAwsS3Client.Companion.excelPathKey
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletResponse

@Service
class ExcelService(
    private val repository: EstimateRepository,
    private val s3Client: CustomAwsS3Client,
) {

    private fun Sheet.setCellData(position: String, value: String) {
        try {
            val ref = CellReference(position)
            val row = getRow(ref.row)
            val cell = row.getCell(ref.col.toInt())
            cell.setCellValue(value)
        } catch (e: Exception) {
            throw ExcelException("엑셀 파일에 데이터를 쓰는데 실패했습니다. (위치:$position, 값:$value) : ${e.message}")
        }
    }

    private fun getDefaultWorkbookAndSheet(): Pair<Workbook, Sheet> {
        try {
            val workbook = s3Client.useS3Object(excelPathKey) {
                XSSFWorkbook(it)
            }
            val sheetName = workbook.getSheetName(0)
            return Pair(workbook, workbook.getSheet(sheetName))
        } catch (e: Exception) {
            throw ExcelException("기본 견적서 엑셀 파일을 읽어오는데 실패했습니다. : ${e.message}")
        }
    }

    private fun Workbook.download(estimate: Estimate, response: HttpServletResponse) {
        val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val fileName = "견적서_${estimate.name}님_$today"

        try {
            val byteArrayOfFileName = fileName.toByteArray(charset("KSC5601"))
            val outputFileName = String(byteArrayOfFileName, Charsets.ISO_8859_1)

            response.apply {
                contentType = "ms-vnd/excel"
                setHeader("Content-Disposition", "attachment;filename=$outputFileName.xlsx")
                status = 200
            }

            write(response.outputStream)
        } catch (e: Exception) {
            throw ExcelException("엑셀 파일을 다운로드하는데 실패했습니다. : ${e.message}")
        } finally {
            close()
        }
    }

    fun createExcel(estimateId: Long, response: HttpServletResponse) {
        val estimate = repository.findById(estimateId).orElseThrow()
        val (workbook, sheet) = getDefaultWorkbookAndSheet()
        sheet.setDataFrom(estimate)
        workbook.download(estimate, response)
    }

    private fun Sheet.setDataFrom(estimate: Estimate) {
        apply {
            setCellData("C4", estimate.name)
            setCellData("C5", estimate.email)
            setCellData("C6", estimate.phone.toPhoneNumberFormatWithDash())
            setCellData("C8", estimate.createdDate.toYyyyMMddFormatWithDash())
            setCellData("C9", estimate.validDate.toYyyyMMddFormatWithDash())

            val date = "${estimate.departDate.toMMddFormatWithDash()} ~ ${estimate.arrivalDate.toMMddFormatWithDash()}"
            setCellData("C14", date)
            val content = "${estimate.departPlace} ~ ${estimate.arrivalPlace}"
            setCellData("F14", content)
            setCellData("L14", estimate.vehicleType.kor.substring(0, 4))
            setCellData("N14", estimate.vehicleNumber.toString())
            setCellData("O14", "대")
        }
    }

    private fun String.toMMddFormatWithDash(): String {
        return try {
            substring(5, 10)
        } catch (e: Exception) {
            this
        }
    }

    private fun String.toYyyyMMddFormatWithDash(): String {
        return try {
            substring(0, 10)
        } catch (e: Exception) {
            this
        }
    }

    private fun String.toPhoneNumberFormatWithDash(): String {
        return try {
            val top = substring(0, 3)
            val mid = substring(3, 7)
            val bottom = substring(7, 11)
            return "$top-$mid-$bottom"
        } catch (e: Exception) {
            this
        }
    }
}
