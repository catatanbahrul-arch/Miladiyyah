package id.miladiyyah.app.ui.screens.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import id.miladiyyah.app.domain.calendar.GregorianCalendarDay
import id.miladiyyah.app.domain.calendar.GregorianCalendarEngine
import id.miladiyyah.app.domain.calendar.HijriCalendarEngine
import id.miladiyyah.app.domain.calendar.HijriDate
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.runtime.MutableState

private val monthNames = listOf(
    "Januari",
    "Februari",
    "Maret",
    "April",
    "Mei",
    "Juni",
    "Juli",
    "Agustus",
    "September",
    "Oktober",
    "November",
    "Desember",
)

private val weekdayNames = listOf(
    "Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min"
)

private val weekdayLongNames = mapOf(
    DayOfWeek.MONDAY to "Senin",
    DayOfWeek.TUESDAY to "Selasa",
    DayOfWeek.WEDNESDAY to "Rabu",
    DayOfWeek.THURSDAY to "Kamis",
    DayOfWeek.FRIDAY to "Jumat",
    DayOfWeek.SATURDAY to "Sabtu",
    DayOfWeek.SUNDAY to "Minggu",
)

private val gregorianDateFormatter =
    DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale("id", "ID"))



private val yearMonthSaver: Saver<MutableState<YearMonth>, String> = Saver(
    save = { it.value.toString() },
    restore = { mutableStateOf(YearMonth.parse(it)) }
)

private val localDateSaver: Saver<MutableState<LocalDate>, Long> = Saver(
    save = { it.value.toEpochDay() },
    restore = { mutableStateOf(LocalDate.ofEpochDay(it)) }
)

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
) {
    val today = GregorianCalendarEngine.today()
    var displayedMonth by rememberSaveable(saver = yearMonthSaver) {
        mutableStateOf(YearMonth.of(today.year, today.monthValue))
    }
    var selectedDate by rememberSaveable(saver = localDateSaver) {
        mutableStateOf(today)
    }

    val calendar = GregorianCalendarEngine.monthGrid(
        displayedMonth.year,
        displayedMonth.monthValue,
    )
    val selectedHijri = HijriCalendarEngine.fromGregorian(selectedDate)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(
            text = "Kalender Miladiyyah",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(
                onClick = {
                    val previous = GregorianCalendarEngine.previousMonth(displayedMonth)
                    val preservedDay = selectedDate.dayOfMonth.coerceAtMost(
                        previous.lengthOfMonth()
                    )
                    displayedMonth = previous
                    selectedDate = previous.atDay(preservedDay)
                },
                modifier = Modifier.size(width = 52.dp, height = 44.dp),
                contentPadding = PaddingValues(0.dp),
            ) {
                Text("‹", style = MaterialTheme.typography.titleLarge)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = monthNames[displayedMonth.monthValue - 1],
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = displayedMonth.year.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Button(
                onClick = {
                    val next = GregorianCalendarEngine.nextMonth(displayedMonth)
                    val preservedDay = selectedDate.dayOfMonth.coerceAtMost(
                        next.lengthOfMonth()
                    )
                    displayedMonth = next
                    selectedDate = next.atDay(preservedDay)
                },
                modifier = Modifier.size(width = 52.dp, height = 44.dp),
                contentPadding = PaddingValues(0.dp),
            ) {
                Text("›", style = MaterialTheme.typography.titleLarge)
            }
        }

        TextButton(
            onClick = {
                displayedMonth = YearMonth.of(today.year, today.monthValue)
                selectedDate = today
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text("Hari Ini")
        }

        SelectedDateCard(
            gregorianDate = selectedDate,
            hijriDate = selectedHijri,
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
            ),
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                WeekdayHeader()
                CalendarGrid(
                    days = calendar.days,
                    today = today,
                    selectedDate = selectedDate,
                    onDateSelected = { date ->
                        selectedDate = date
                        if (date.year != displayedMonth.year ||
                            date.monthValue != displayedMonth.monthValue) {
                            displayedMonth = YearMonth.of(date.year, date.monthValue)
                        }
                    },
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Bulan dimulai pada hari ${
                weekdayLongNames[calendar.firstDayOfWeek] ?: "tidak diketahui"
            }",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )

        Text(
            text = "Hijriah dasar lokal • belum dikoreksi Kemenag",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}



@Composable
private fun SelectedDateCard(
    gregorianDate: LocalDate,
    hijriDate: HijriDate,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = gregorianDate.format(gregorianDateFormatter),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                text = HijriCalendarEngine.format(hijriDate),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )

            Text(
                text = "Base Hijriah lokal",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}



@Composable
private fun WeekdayHeader() {
    Row(modifier = Modifier.fillMaxWidth()) {
        weekdayNames.forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}



@Composable
private fun CalendarGrid(
    days: List<GregorianCalendarDay>,
    today: LocalDate,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        days.chunked(7).forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                week.forEach { day ->
                    CalendarDayCell(
                        day = day,
                        isToday = GregorianCalendarEngine.isToday(day.date, today),
                        isSelected = day.date == selectedDate,
                        onClick = { onDateSelected(day.date) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}



@Composable
private fun CalendarDayCell(
    day: GregorianCalendarDay,
    isToday: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textColor = when {
        !day.isInCurrentMonth -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.40f)
        isToday -> MaterialTheme.colorScheme.onPrimary
        else -> MaterialTheme.colorScheme.onSurface
    }

    val hijri = HijriCalendarEngine.fromGregorian(day.date)

    val backgroundColor = when {
        isToday -> MaterialTheme.colorScheme.primary
        isSelected -> MaterialTheme.colorScheme.secondaryContainer
        else -> Color.Transparent
    }

    val borderColor = when {
        isToday -> MaterialTheme.colorScheme.primary
        isSelected -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
    }

    Box(
        modifier = modifier
            .aspectRatio(0.82f)
            .clip(MaterialTheme.shapes.small)
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = MaterialTheme.shapes.small,
            )
            .clickable(onClick = onClick)
            .padding(vertical = 5.dp, horizontal = 2.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = textColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = when {
                    isToday || isSelected -> FontWeight.Bold
                    else -> FontWeight.Normal
                },
                textAlign = TextAlign.Center,
            )

            Text(
                text = hijri.hijriDay.toString(),
                color = if (day.isInCurrentMonth) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.30f)
                },
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
            )
        }
    }
}
