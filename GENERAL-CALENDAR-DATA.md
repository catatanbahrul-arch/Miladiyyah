# General Calendar Data — Fase 15

## Tujuan

Fase 15 menambahkan boundary untuk normalisasi payload kalender umum sebelum masuk ke domain `CalendarEvent`.

Alur sekarang:

```text
Remote payload
      ↓
GeneralCalendarRemoteItem
      ↓
GeneralCalendarPayloadMapper
      ↓
CalendarEvent
      ↓
CalendarEventRepository
```

## Provider-neutral

`GeneralCalendarRemoteItem` bukan DTO milik Google Calendar, Firebase, atau provider tertentu.

Field payload yang disiapkan:

- `id`
- `dateIso`
- `title`
- `description`
- `category`
- `isHoliday`
- `sourceUrl`

## Normalisasi

Mapper melakukan:

1. parsing `dateIso` sebagai ISO local date;
2. trim whitespace pada text field;
3. membuang `id`, `description`, `category`, dan `sourceUrl` yang kosong;
4. menggunakan `false` jika `isHoliday` null;
5. menolak item dengan tanggal tidak valid;
6. menolak item tanpa title;
7. menghasilkan `CalendarEvent` yang bersih.

`mapAll()` membuang item yang tidak valid dengan `mapNotNull`.

## Guard

Fase 15 tidak:

- mengakses internet;
- memanggil Google Calendar;
- memanggil Firebase;
- menyimpan API key/credential;
- meng-hardcode tanggal merah;
- mengubah GregorianCalendarEngine;
- mengubah HijriCalendarEngine;
- mengubah CalendarScreen;
- mengubah MainActivity;
- mengubah repository interface.

Mapper hanya menjadi batas translasi dari data remote generik ke model domain.
