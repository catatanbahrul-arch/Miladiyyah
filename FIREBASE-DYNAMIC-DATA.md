# Firebase Dynamic Data Foundation — Fase 16

## Tujuan

Fase 16 menyiapkan **boundary** untuk data dinamis yang pada arsitektur final akan didistribusikan melalui Firebase.

Fase ini **belum memasang Firebase SDK** dan belum membuat konfigurasi project Firebase.

## Boundary

Struktur:

```text
FirebaseDynamicDataSource
          ↓
FirebaseDocument
          ↓
Mapper / Repository spesifik fitur
          ↓
Domain model
```

`FirebaseDocument` adalah envelope generik:

- `id`
- `fields`

Core boundary ini sengaja tidak mengetahui schema kalender, prayer schedule, koreksi Hijri, atau data lain.

## Implementasi default

`DefaultFirebaseDynamicDataSource` saat ini mengembalikan:

```text
emptyList()
```

Implementasi kosong ini menjaga agar aplikasi belum melakukan network access sebelum:

- Firebase project ditetapkan;
- schema koleksi ditetapkan;
- payload production disepakati;
- rules/security ditetapkan;
- sync strategy ditetapkan;
- validasi data ditetapkan.

## Rencana penggunaan sesuai source mapping

Firebase hanya dipakai untuk dynamic sync yang memang sudah ditetapkan pada arsitektur project, termasuk:

- koreksi Hijri Kemenag;
- kalender Kemenag;
- jadwal shalat yang telah disinkronkan;
- dynamic data lain yang memang ditetapkan menggunakan Firebase.

Google Sheets tetap menjadi CMS utama untuk data resmi Wahidiyah dan Google Apps Script menjadi gateway JSON untuk data tersebut.

Room tetap menjadi cache lokal, bukan source of truth.

## Guard

Fase 16 tidak:

- menambah Firebase SDK;
- menambah `google-services.json`;
- menyimpan credential;
- menambah endpoint;
- membuka network;
- menentukan schema koleksi General Calendar;
- menentukan schema Prayer;
- menentukan schema Hijri;
- mengubah GregorianCalendarEngine;
- mengubah HijriCalendarEngine;
- mengubah CalendarScreen;
- mengubah MainActivity;
- mengubah repository/domain contract yang sudah ada.

Fase berikutnya dapat menentukan schema Firebase spesifik untuk fitur tertentu tanpa mengotori boundary generic ini.
