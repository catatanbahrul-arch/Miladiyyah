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
