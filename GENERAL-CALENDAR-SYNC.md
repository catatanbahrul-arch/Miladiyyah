# General Calendar Sync Boundary — Fase 17

## Tujuan

Fase 17 menambahkan adapter fitur kalender umum di atas generic Firebase boundary.

Alur sekarang:

```text
FirebaseDocument
      ↓
GeneralCalendarFirebaseDocumentMapper
      ↓
GeneralCalendarRemoteItem
      ↓
GeneralCalendarPayloadMapper
      ↓
CalendarEvent
```

Dengan pola ini, domain `CalendarEvent` tidak mengetahui bentuk envelope Firebase.

## Field mapping

Adapter membaca field generik:

| FirebaseDocument field | GeneralCalendarRemoteItem |
|---|---|
| `id` | `id` |
| `dateIso` | `dateIso` |
| `title` | `title` |
| `description` | `description` |
| `category` | `category` |
| `isHoliday` | `isHoliday` |
| `sourceUrl` | `sourceUrl` |

`FirebaseDocument.id` digunakan sebagai fallback ketika field `id` kosong.

Nilai boolean menerima bentuk:

```text
true / false
1 / 0
yes / no
y / n
```

Nilai lain dianggap null dan kemudian mengikuti aturan `GeneralCalendarPayloadMapper`.

## Validasi

Validasi tanggal dan title tetap dilakukan oleh `GeneralCalendarPayloadMapper`.

Dengan demikian:

- tanggal ISO invalid ditolak;
- title kosong ditolak;
- field teks dinormalisasi;
- `isHoliday` null menjadi false di mapper domain.

## Kondisi Firebase

Fase 17 belum mengaktifkan Firebase SDK atau network.

Belum ditentukan:

- Firebase project produksi;
- collection produksi;
- Firebase Rules;
- credentials;
- sinkronisasi server;
- jadwal sync.

Adapter hanya memetakan envelope generic ke model domain.

## Source flow yang tetap dikunci

```text
Sumber kalender umum / sumber resmi
                    ↓
              Sinkronisasi
                    ↓
                 Firebase
                    ↓
          FirebaseDocument
                    ↓
      GeneralCalendarFirebaseDocumentMapper
                    ↓
          GeneralCalendarPayloadMapper
                    ↓
              CalendarEvent
```

## Guard

Fase 17 tidak:

- menambah Firebase SDK;
- menambah network client;
- menambah API key;
- menambah credential;
- menentukan collection production;
- mengubah FirebaseDynamicDataSource;
- mengubah FirebaseDocument;
- mengubah CalendarEvent;
- mengubah repository contract;
- mengubah Gregorian/Hijri engine;
- mengubah CalendarScreen;
- mengubah MainActivity;
- menghardcode daftar tanggal merah.
