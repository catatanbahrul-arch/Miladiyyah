# Offline / Sync / Cache Policy

## Purpose

Fase 34 menambahkan kebijakan pembacaan data kalender umum sebagai
**policy boundary**.

Policy ini tidak melakukan I/O dan tidak mengaktifkan network.

## Modes

### REMOTE_FIRST_WITH_LOCAL_FALLBACK

Urutan konseptual:

```text
caller
  ↓
remote/source repository
  ↓
success → return source data
failure → local cache fallback
```

Mode ini sesuai dengan `CachedCalendarEventRepository` yang sudah
menempatkan repository sumber sebagai source of truth dan Room sebagai
cache/fallback.

### LOCAL_CACHE_ONLY

```text
caller
  ↓
local cache
```

Mode ini tidak meminta network.

Implementasi konkret pemanggilan local data source tetap menjadi
tanggung jawab repository/cache boundary yang sudah ada.

### REMOTE_ONLY

```text
caller
  ↓
source repository
```

Tidak ada fallback lokal yang dijanjikan oleh policy ini.

## Important distinction

`CalendarEventReadPolicy` adalah **policy contract**, bukan executor.

Fase ini tidak:

- mengubah `CalendarEventRepository`;
- mengubah `CachedCalendarEventRepository`;
- mengubah Room DAO;
- menambah Firebase operation;
- menambah sync scheduler;
- mengaktifkan background worker;
- memanggil network;
- mengubah CalendarScreen;
- mengubah MainActivity.

## Source-of-truth rule

Data remote/source tetap menjadi source of truth.

Room tetap cache/local fallback, bukan source of truth.

## Future sync

Sync otomatis boleh ditambahkan pada fase/provider yang memang memiliki
source production dan aturan freshness yang nyata.

Jangan menebak TTL, endpoint, collection, atau jadwal sinkronisasi.
