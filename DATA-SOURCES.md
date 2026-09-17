# Miladiyyah — Data Sources

## Prinsip

Dokumen ini memisahkan **source of truth**, boundary integrasi, dan cache.
Tidak ada endpoint, credential, spreadsheet ID, Firebase collection, atau schema produksi yang boleh ditebak.

## Source of Truth Matrix

| Data | Source of Truth | Boundary / Sync | Status |
|---|---|---|---|
| Gregorian/Miladiyyah | Android Gregorian calculation | Local | FIXED |
| Hijri base | Android local calculation | Local | FIXED |
| Hijri correction | Data resmi Kemenag | Firebase distribution | CONCEPT ONLY |
| Holiday/commemoration | General calendar / official source | Firebase distribution | CONCEPT ONLY |
| Prayer times | Bimas Islam Kemenag | Firebase distribution | SOURCE FIXED |
| Wahidiyah activities | Spreadsheet Pusat | GAS → JSON | SOURCE FIXED |
| Special jamaah agenda | Spreadsheet Pusat | GAS → JSON | SOURCE FIXED |
| Announcements | Spreadsheet Pusat | GAS → JSON | SOURCE FIXED |
| Library metadata | Spreadsheet Pusat | GAS → JSON | SOURCE FIXED |
| Library files | Google Drive Pusat | Link/file reference | SOURCE FIXED |
| Media | Spreadsheet Pusat | GAS → JSON | SOURCE FIXED |
| Dana Box | Local Android app | Local scheduler | FIXED |
| Location | Android Location Service | Local | FIXED |
| Cache | Room | Local | CACHE ONLY |
| Push notification | Firebase/FCM if needed | Feature-specific | OPTIONAL |
| YouTube Live notification | Removed | None | REMOVED |

## Confirmed Workbook Example

Workbook yang sudah diperiksa:
`Dashboard Admin(1).xlsx`

Sheet:
- `Pengumuman`
- `Media`
- `Jadwal_Kegiatan_tahunan`

### Pengumuman
- judul
- tanggal
- isi_teks
- link_file

### Media
- judul
- isi_teks
- link_file

### Jadwal_Kegiatan_tahunan
- nama_acara
- tanggal_pelaksanaan
- jam
- deskripsi
- link_file

### Important
Workbook di atas adalah **contoh struktur/ekspor**, bukan bukti Spreadsheet Pusat produksi.
Implementasi produksi wajib mengikuti Spreadsheet Pusat sebenarnya.

## Provider Rules

### Spreadsheet Pusat
Android tidak membaca struktur spreadsheet secara langsung.

Flow:
Spreadsheet Pusat → GAS → JSON → Android → Room.

### Google Apps Script
GAS bertanggung jawab untuk:
- read Sheet,
- validation,
- filtering,
- transformation,
- JSON API.

Android hanya mengonsumsi kontrak JSON yang sudah didefinisikan di boundary.

### Firebase
Firebase hanya dipakai pada area yang memang membutuhkan distribusi data dinamis.

### Room
Room adalah cache, bukan source of truth.

## Assets

`dataapkmiladiyyah.rar` adalah paket aset:
- logo
- notification sounds

Paket tersebut bukan source code project dan tidak menjadi provider data.

## Blocking Gaps

Sebelum implementasi provider produksi:
- Spreadsheet Pusat asli
- GAS source
- Web App URL
- Firebase project/schema
- Prayer sync mechanism
- Hijri override schema
- struktur Sheet produksi

## Rule

**No guessing. No fake endpoint. No fake Firebase schema. No production hardcode from example data.**
