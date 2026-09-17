# Miladiyyah — Room Cache Foundation — Fase 23

## Scope

Fase 23 menambahkan fondasi cache lokal menggunakan Room.

Room pada project ini diperlakukan sebagai:

```text
Online source
    ↓
validation / mapping
    ↓
Room cache
    ↓
Android UI
```

Room **bukan source of truth**.

## Current cache table

Table:

```text
calendar_events
```

Fields:

- `id`
- `dateIso`
- `title`
- `description`
- `category`
- `isHoliday`
- `sourceUrl`

## DAO contract

`CalendarEventDao` menyediakan:

- baca event dalam rentang tanggal,
- upsert batch,
- hapus event dalam rentang tanggal,
- clear seluruh cache.

## Database

Database:

```text
MiladiyyahDatabase
version = 1
```

Filename:

```text
miladiyyah_cache.db
```

## Boundary

Fase 23 **belum**:

- menghubungkan Room ke `CalendarEventRepository`;
- menghubungkan Room ke `FirebaseGeneralCalendarRemoteDataSource`;
- mengaktifkan sync saat startup;
- mengubah `MainActivity`;
- mengubah `MiladiyyahApp`;
- mengubah `CalendarScreen`;
- mengubah Gregorian engine;
- mengubah Hijri engine;
- menentukan data production;
- menentukan Firebase collection;
- membuat migration production;
- menghapus data cache otomatis.

## Dependency

Foundation menggunakan:

- Room Runtime `2.8.5`
- Room KTX `2.8.5`
- Room Compiler `2.8.5`
- KSP `2.3.12`

Catatan:
Versi dipilih dari rilis stabil yang tersedia pada saat Fase 23 dikerjakan.

## Future wiring

Fase selanjutnya dapat menambahkan:

```text
Firebase/remote source
        ↓
mapping/validation
        ↓
Room
        ↓
repository
        ↓
UI
```

tanpa mengubah kontrak domain.

## Safety rules

Tidak ada credential, endpoint, spreadsheet ID, Firebase collection, atau data kalender production yang disimpan di Room foundation ini.

## Mapping boundary

`CalendarEventRoomMapper` menjadi boundary eksplisit antara domain dan Room:

```text
CalendarEvent (domain)
        ↕
CalendarEventRoomMapper
        ↕
CalendarEventEntity (Room)
```

Aturan mapping:

- Domain -> Entity memakai `toEntityOrNull()`.
- `id`, `dateIso`, atau `title` null/kosong tidak dimasukkan ke cache.
- String dipangkas (`trim`) pada boundary data.
- Blank optional string dinormalisasi menjadi `null`.
- Entity -> Domain mempertahankan field domain yang tersedia.
- Batch mapping tersedia untuk list.
- Mapper tidak melakukan I/O, database access, network access, atau startup wiring.

## Room local data source boundary

Fase 25 menyediakan boundary data lokal:

```text
CalendarEventLocalDataSource
          ↑
RoomCalendarEventLocalDataSource
          ↓
CalendarEventDao
          ↕
CalendarEventRoomMapper
          ↕
CalendarEventEntity
```

`CalendarEventLocalDataSource` hanya mengenal model domain `CalendarEvent` dan `LocalDate`.

Implementasi Room:

- membaca cache berdasarkan rentang tanggal;
- menulis batch event ke cache;
- menghapus event berdasarkan rentang tanggal;
- membersihkan seluruh cache;
- menolak rentang tanggal terbalik dengan hasil/no-op aman;
- menggunakan `CalendarEventRoomMapper` untuk konversi domain/entity.

Fase 25 belum menghubungkan local data source ke `CalendarEventRepository`, Firebase, startup, atau UI.

## Repository cache activation boundary

Fase 26 menyediakan decorator repository eksplisit:

```text
CalendarEventRepository
        ↑
CachedCalendarEventRepository
        ├── sourceRepository
        └── CalendarEventLocalDataSource
                  ↓
             Room cache
```

Aturan:

- `sourceRepository` tetap menjadi source of truth.
- Saat source berhasil, hasil remote dikembalikan ke caller.
- Hasil remote non-empty di-upsert ke cache.
- Hasil remote empty membersihkan cache pada rentang yang sama agar cache tidak mempertahankan event lama untuk range tersebut.
- Saat source melempar `Exception`, repository mencoba membaca local cache.
- Rentang tanggal terbalik menghasilkan `emptyList()`.
- Fase 26 belum mengaktifkan decorator ini pada startup/UI.
- Fase 26 belum mengubah `DefaultCalendarEventRepository`.
- Fase 26 belum mengubah Firebase source.
