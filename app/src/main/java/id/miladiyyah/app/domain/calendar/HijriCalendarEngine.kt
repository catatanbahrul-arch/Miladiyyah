package id.miladiyyah.app.domain.calendar

import java.time.LocalDate
import java.time.chrono.HijrahDate

/**
 * Mesin Hijriah dasar lokal.
 *
 * Penting:
 * - Ini hanya BASE HIJRI hasil chronology lokal Android.
 * - Tidak mengklaim sebagai keputusan rukyat/itsbat Kemenag.
 * - Tidak mempunyai Firebase, API, GAS, Spreadsheet, atau network.
 * - Koreksi resmi nantinya diterapkan di boundary terpisah.
 */
object HijriCalendarEngine {

    fun fromGregorian(date: LocalDate): HijriDate {
        val hijrahDate = HijrahDate.from(date)

        return HijriDate(
            gregorianDate = date,
            hijriYear = hijrahDate.get(java.time.temporal.ChronoField.YEAR),
            hijriMonth = hijrahDate.get(java.time.temporal.ChronoField.MONTH_OF_YEAR),
            hijriDay = hijrahDate.get(java.time.temporal.ChronoField.DAY_OF_MONTH),
        )
    }

    fun today(): HijriDate =
        fromGregorian(LocalDate.now())

    fun monthName(month: Int): String =
        when (month) {
            1 -> "Muharram"
            2 -> "Safar"
            3 -> "Rabiul Awal"
            4 -> "Rabiul Akhir"
            5 -> "Jumadil Awal"
            6 -> "Jumadil Akhir"
            7 -> "Rajab"
            8 -> "Sya'ban"
            9 -> "Ramadan"
            10 -> "Syawal"
            11 -> "Dzulqa'dah"
            12 -> "Dzulhijjah"
            else -> throw IllegalArgumentException("Bulan Hijriah harus 1..12")
        }

    fun format(date: HijriDate): String =
        "${date.hijriDay} ${monthName(date.hijriMonth)} ${date.hijriYear} H"
}
