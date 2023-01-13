package com.ektour.service

import com.ektour.repository.EstimateRepository
import com.ektour.common.ExcelException
import com.ektour.common.getExelPath
import com.ektour.common.logger
import com.ektour.entity.Estimate
import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletResponse

@Service
class ExcelService(private val repository: EstimateRepository) {

    val log = logger()

    // 특정 셀에 특정 값 넣기
    private fun setValue(sheet: Sheet, position: String, value: String) {
        val ref = CellReference(position)
        val row = sheet.getRow(ref.row)
        val cell = row.getCell(ref.col.toInt())
        cell.setCellValue(value)
    }

    // 엑셀 파일 불러와서 값 수정
    fun createExcel(estimateId: Long, response: HttpServletResponse) {
        try  {
            // 견적 가져오기
            val estimate: Estimate = repository.findById(estimateId).orElseThrow()

            // 엑셀 파일 불러오기
            val opcPackage: OPCPackage = OPCPackage.open(File(getExelPath()))
            val workbook = XSSFWorkbook(opcPackage)
            val sheetName = workbook.getSheetName(0)
            val sheet: Sheet = workbook.getSheet(sheetName)

            // 데이터 세팅
            setValue(sheet, "C4", estimate.name) // 수신
            setValue(sheet, "C5", estimate.email) // 이메일
            setValue(sheet, "C6", convertPhone(estimate.phone)) // 연락처
            setValue(sheet, "C8", convertDateWithYear(estimate.createdDate)) // 견적일
            setValue(sheet, "C9", convertDateWithYear(estimate.validDate)) // 유효일

            // 차량-내용
            val date = "${convertDate(estimate.departDate)} ~ ${convertDate(estimate.arrivalDate)}"
            setValue(sheet, "C14", date)
            val content = "${estimate.departPlace} ~ ${estimate.arrivalPlace}"
            setValue(sheet, "F14", content)
            setValue(sheet, "L14", estimate.vehicleType.substring(0, 4)) // 규격
            setValue(sheet, "N14", estimate.vehicleNumber.toString()) // 댓수
            setValue(sheet, "O14", "대")

            // 다운로드
            val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
            val fileName = "견적서_${estimate.name}님_${today}"
            // 엑셀 다운로드시 한글 깨짐 처리
            val outputFileName = String(fileName.toByteArray(charset("KSC5601")), Charsets.ISO_8859_1)

            response.contentType = "ms-vnd/excel"
            response.setHeader("Content-Disposition", "attachment;filename=$outputFileName.xlsx")
            response.status = 200

            workbook.write(response.outputStream)
            workbook.close()
        } catch (e: Exception) {
            log.warn("엑셀 에러 = {}", e.message)
            throw ExcelException("엑셀 변환/다운로드 관련 오류 발생")
        }
    }

    // 06-29 형식
    private fun convertDate(date: String): String = date.substring(5, 10)

    // 2022-06-29 형식
    private fun convertDateWithYear(date: String): String = date.substring(0, 10)

    // 010-1234-1234 형식으로 변환
    private fun convertPhone(phone: String): String {
        if (phone.contains("-")) return phone
        val top = phone.substring(0, 3)
        val mid = phone.substring(3, 7)
        val bottom = phone.substring(7, 11)
        return "$top-$mid-$bottom"
    }
}