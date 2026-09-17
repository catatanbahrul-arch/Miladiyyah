# Production Data Provider Readiness

## Purpose

Fase 35 membuat boundary readiness sebelum source produksi benar-benar
diaktifkan.

Readiness bukan provider implementation dan bukan network operation.

## States

### NOT_AVAILABLE

Provider/source produksi belum tersedia.

Contoh kondisi:

- Spreadsheet Pusat asli belum tersedia.
- GAS source belum tersedia.
- Web App GAS belum tersedia.
- Firebase project/schema belum tersedia.

### CONFIGURATION_PENDING

Provider/source tersedia secara konsep atau implementation boundary,
tetapi konfigurasi, schema, security rule, endpoint, atau validasi
produksi belum lengkap.

### READY_FOR_EXPLICIT_ACTIVATION

Source, schema, configuration, dan validation sudah tersedia serta
caller boleh melakukan activation secara eksplisit.

Status ini tidak otomatis berarti aplikasi sudah mengaktifkan provider.

## Production activation gates

Sebelum provider nyata diaktifkan, minimal harus ada:

```text
source nyata
    ↓
schema nyata
    ↓
configuration tervalidasi
    ↓
security/access policy
    ↓
mapping validation
    ↓
explicit activation
```

## Current project rule

Selama bahan produksi belum tersedia, aplikasi tidak boleh:

- menebak endpoint;
- menebak spreadsheet ID;
- membuat collection produksi fiktif;
- memasukkan credential ke source;
- memasukkan dataset contoh sebagai dataset resmi;
- mengaktifkan network pada startup.

## Existing architecture

Fase 35 hanya menambahkan readiness boundary.

Boundary yang sudah ada tetap menjadi tempat integrasi:

- General Calendar source selector
- Firebase runtime/dynamic source
- repository composition
- Room cache
- NotificationService

Tidak ada perubahan terhadap kontrak `CalendarEventRepository`.

## Important distinction

`ProductionDataProviderReadiness` adalah **status/contract**.

Ia bukan:

- Firebase client;
- GAS client;
- HTTP client;
- scheduler;
- worker;
- credentials store;
- production provider.

## Future activation

Provider nyata baru boleh ditambahkan setelah source artifacts
produksi benar-benar tersedia dan diverifikasi.
