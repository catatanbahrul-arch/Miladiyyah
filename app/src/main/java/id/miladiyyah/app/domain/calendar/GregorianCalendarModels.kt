package id.miladiyyah.app.domain.calendar

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

/**
 * Satu tanggal yang akan digunakan oleh UI.
 *
 * isInCurrentMonth membedakan tanggal padding dari bulan sebelumnya /
 * berikutnya ketika month grid ditampilkan.
 */
data class GregorianCalendarDay(
    val date: LocalDate,
    val isInCurrentMonth: Boolean,
)

/**
 * Hasil pembentukan satu bulan kalender.
 *
 * firstDayOfWeek menggunakan ISO DayOfWeek.
 * grid menggunakan urutan Monday -> Sunday dan selalu 6 minggu (42 sel)
 * agar UI dapat mempunyai tinggi kalender yang konsisten.
 */
data class GregorianCalendarMonth(
    val yearMonth: YearMonth,
    val firstDayOfMonth: LocalDate,
    val daysInMonth: Int,
    val firstDayOfWeek: DayOfWeek,
    val days: List<GregorianCalendarDay>,
)
