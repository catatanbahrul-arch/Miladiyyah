package id.miladiyyah.app.domain.calendar

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

/**
 * Mesin kalender Miladiyyah/Gregorian.
 *
 * Prinsip:
 * - Source kalender = java.time Gregorian calendar pada Android.
 * - Tidak bergantung internet.
 * - Tidak bergantung Firebase, GAS, Spreadsheet, atau provider eksternal.
 * - Tidak menyimpan kalender fisik per tahun.
 * - Tahun/bulan dihitung dinamis.
 *
 * API ini sengaja murni agar UI, Room, dan provider tidak tercampur
 * dengan aturan dasar kalender Gregorian.
 */
object GregorianCalendarEngine {

    fun today(): LocalDate = LocalDate.now()

    fun yearMonth(year: Int, month: Int): YearMonth =
        YearMonth.of(year, month)

    fun yearMonth(date: LocalDate = today()): YearMonth =
        YearMonth.from(date)

    fun isLeapYear(year: Int): Boolean =
        YearMonth.of(year, 1).isLeapYear

    fun daysInMonth(year: Int, month: Int): Int =
        YearMonth.of(year, month).lengthOfMonth()

    fun firstDayOfMonth(year: Int, month: Int): LocalDate =
        YearMonth.of(year, month).atDay(1)

    fun firstDayOfMonth(yearMonth: YearMonth): LocalDate =
        yearMonth.atDay(1)

    fun nextMonth(yearMonth: YearMonth): YearMonth =
        yearMonth.plusMonths(1)

    fun previousMonth(yearMonth: YearMonth): YearMonth =
        yearMonth.minusMonths(1)

    fun nextYear(year: Int): Int =
        year + 1

    fun previousYear(year: Int): Int =
        year - 1

    /**
     * Posisi tanggal dalam urutan ISO Monday -> Sunday.
     *
     * Senin = 0 ... Minggu = 6
     */
    fun weekdayIndexMondayFirst(date: LocalDate): Int =
        date.dayOfWeek.value - 1

    /**
     * Posisi sel tanggal pada grid kalender Monday -> Sunday.
     * Sel pertama bulan memiliki index 0..6 sesuai hari awal bulan.
     *
     * Contoh:
     * - bulan dimulai Senin  -> index 0
     * - bulan dimulai Minggu -> index 6
     */
    fun gridIndexMondayFirst(date: LocalDate): Int {
        val first = firstDayOfMonth(date.year, date.monthValue)
        return weekdayIndexMondayFirst(first) + date.dayOfMonth - 1
    }

    /**
     * Membentuk grid 42 sel (6 minggu), Monday -> Sunday.
     *
     * Tanggal di luar bulan berjalan tetap dipertahankan sehingga UI
     * dapat menampilkan kalender penuh tanpa logika tanggal tambahan.
     */
    fun monthGrid(year: Int, month: Int): GregorianCalendarMonth {
        val yearMonth = YearMonth.of(year, month)
        val first = yearMonth.atDay(1)
        val length = yearMonth.lengthOfMonth()

        val leadingDays = weekdayIndexMondayFirst(first)
        val totalCells = 42
        val firstGridDate = first.minusDays(leadingDays.toLong())

        val days = (0 until totalCells).map { index ->
            val date = firstGridDate.plusDays(index.toLong())

            GregorianCalendarDay(
                date = date,
                isInCurrentMonth = date.month == yearMonth.month &&
                    date.year == yearMonth.year,
            )
        }

        return GregorianCalendarMonth(
            yearMonth = yearMonth,
            firstDayOfMonth = first,
            daysInMonth = length,
            firstDayOfWeek = first.dayOfWeek,
            days = days,
        )
    }

    /**
     * Menguji apakah tanggal yang diberikan adalah hari ini.
     */
    fun isToday(date: LocalDate, today: LocalDate = today()): Boolean =
        date == today
}
