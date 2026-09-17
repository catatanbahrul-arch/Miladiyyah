# Miladiyyah — Source Evidence Lock

Dokumen ini mengunci **hanya fakta sumber data yang sudah tersedia atau sudah ditetapkan dari bahan proyek**.
Dokumen ini sengaja membedakan antara:

- **CONFIRMED / TERBUKTI**: ada bahan atau keputusan sumber yang sudah jelas.
- **SCHEMA EXAMPLE / CONTOH STRUKTUR**: struktur pernah diberikan/diperiksa, tetapi belum dibuktikan sebagai Spreadsheet Pusat produksi.
- **GAP / BELUM ADA**: bahan nyata belum tersedia.
- **CONCEPT ONLY / KONSEP**: arsitektur sudah ditetapkan, tetapi implementasi/source contract nyata belum tersedia.

## 1. Kalender Miladiyyah / Gregorian

Status: **CONFIRMED**

Sumber:
- Perhitungan kalender Gregorian langsung di Android.

Cakupan:
- Tahun
- Bulan
- Tanggal
- Hari
- Panjang bulan
- Leap year
- Hari pertama bulan
- Posisi tanggal
- Navigasi bulan/tahun
- Tombol kembali ke hari ini

Aturan:
- Tidak memakai kalender fisik tahunan yang di-hardcode.
- Tidak memakai Google Sheets, Firebase, atau API sebagai sumber kalender Gregorian.

## 2. Hijriah

Status: **CONCEPT ONLY**

Alur yang dikunci:
Gregorian → mesin hitung Hijriah lokal → data koreksi resmi Kemenag → Firebase → hasil final Android.

Yang sudah tersedia:
- Prinsip mesin lokal.
- Prinsip adanya koreksi resmi.

Yang belum tersedia:
- Project Firebase nyata.
- Collection/schema nyata.
- Format override konkret.
- Aturan precedence/detail penerapan koreksi.

Karena itu tidak boleh dibuat provider produksi atau data override fiktif pada fase ini.

## 3. Kalender Umum / Hari Libur / Peringatan

Status: **CONCEPT ONLY**

Rencana sumber:
- Google general calendar / sumber resmi yang sesuai.

Arsitektur rencana:
Calendar General → sync → Firebase → Android.

Yang belum dikunci:
- Endpoint/provider konkret.
- Dataset produksi.
- Schema Firebase.
- Mekanisme sinkronisasi nyata.

## 4. Jadwal Shalat

Status: **SOURCE FIXED, IMPLEMENTATION GAP**

Sumber utama yang sudah ditentukan:
- Bimas Islam Kemenag.

Lokasi pengguna:
- Android Location Service.
- Tidak dikunci ke kota tertentu.

Data yang direncanakan:
- Imsak
- Subuh
- Dzuhur
- Ashar
- Maghrib
- Isya

Arsitektur yang direncanakan:
Bimas Islam Kemenag → Firebase → Android → Room cache → jadwal shalat.

Yang belum tersedia:
- Mekanisme ingestion/sync nyata.
- Format dataset.
- Schema Firebase.
- Strategi refresh/cache yang final.

## 5. Data Resmi Wahidiyah / Spreadsheet Pusat

Status: **SOURCE FIXED**

Prinsip:
- Data resmi Wahidiyah berasal dari Spreadsheet Pusat.
- Android tidak boleh bergantung langsung pada struktur internal spreadsheet.
- GAS menjadi API bridge.
- JSON dari GAS menjadi kontrak sisi aplikasi.
- Room menjadi cache, bukan source of truth.

Alur:
Spreadsheet Pusat → Google Apps Script → JSON API → Android → Room → UI.

## 6. Workbook yang Sudah Diperiksa

Status: **SCHEMA EXAMPLE**

Workbook:
`Dashboard Admin(1).xlsx`

Sheet yang terdeteksi:
1. `Pengumuman`
2. `Media`
3. `Jadwal_Kegiatan_tahunan`

### Pengumuman
Header yang diperiksa:
- `judul`
- `tanggal`
- `isi_teks`
- `link_file`

### Media
Header yang diperiksa:
- `judul`
- `isi_teks`
- `link_file`

### Jadwal_Kegiatan_tahunan
Header yang diperiksa:
- `nama_acara`
- `tanggal_pelaksanaan`
- `jam`
- `deskripsi`
- `link_file`

Catatan penting:
- Workbook tersebut diperlakukan sebagai **contoh/ekspor struktur**, bukan bukti bahwa itu adalah Spreadsheet Pusat produksi.
- Nilai contoh yang memakai penanda "Contoh isi" tidak boleh dijadikan data produksi.
- Nama kolom sumber tidak boleh diubah hanya untuk menyesuaikan aplikasi.
- Jika Spreadsheet Pusat asli mempunyai nama kolom berbeda, implementasi harus mengikuti data nyata dan dibuatkan mapping internal di boundary GAS/domain.

## 7. Pengumuman

Status: **SCHEMA EXAMPLE + SOURCE FIXED**

Source of truth:
- Spreadsheet Pusat.

Boundary:
- Sheet → GAS → JSON → Android.

Schema produksi:
- BELUM DIKUNCI sampai Spreadsheet Pusat asli diperiksa.

## 8. Kegiatan / Agenda

Status: **SCHEMA EXAMPLE**

Workbook contoh yang tersedia menunjukkan:
`Jadwal_Kegiatan_tahunan`

Header:
- `nama_acara`
- `tanggal_pelaksanaan`
- `jam`
- `deskripsi`
- `link_file`

Belum terbukti:
- Apakah ini satu-satunya Sheet kegiatan di Pusat.
- Apakah ada Sheet agenda khusus.
- Apakah ada agenda jamaah khusus.
- Apakah ada status/publish/ID unik.

Karena itu struktur final tidak boleh dibuat dari asumsi workbook contoh.

## 9. Pustaka

Status: **GAP**

Rencana:
- Metadata buku/PDF dari Spreadsheet Pusat.
- File fisik/material dari Google Drive Pusat.
- Android menerima metadata/link lalu membuka/mengambil file sesuai kebutuhan.

Yang belum tersedia:
- Sheet Pustaka asli.
- Header nyata.
- ID unik.
- Format link Drive.
- Aturan status/publish.

Tidak boleh membuat schema final berdasarkan tebakan.

## 10. Media

Status: **SCHEMA EXAMPLE + SOURCE FIXED**

Workbook contoh memiliki Sheet `Media`:
- `judul`
- `isi_teks`
- `link_file`

Source of truth produksi:
- Spreadsheet Pusat.

Schema final:
- Menunggu Sheet Pusat asli.

## 11. Informasi Resmi Lainnya

Status: **GAP**

Yang direncanakan:
- Semua informasi resmi Pusat melalui pintu Spreadsheet Pusat → GAS → JSON.

Yang belum diketahui:
- Sheet apa saja.
- Nama kolom.
- Status data.
- ID unik.
- Link/file/media relation.

Tidak boleh membuat tabel final tanpa bukti dari sumber asli.

## 12. Dana Box / Kotak Dana

Status: **CONFIRMED LOCAL**

Sumber:
- Lokal di aplikasi.

Tidak memakai:
- Spreadsheet
- GAS
- Firebase
- API
- Internet

Perilaku:
- Jadwal pagi + sore.
- Notification.
- Sound lokal di `res/raw`.
- Offline tetap berjalan.
- Perubahan jadwal permanen memerlukan perubahan konfigurasi/aplikasi jika tidak ada redesign.

## 13. YouTube Live Notification

Status: **REMOVED FROM SCOPE**

Tidak dibuat:
- Deteksi live YouTube.
- Polling live.
- FCM live notification khusus.
- Notification live.

Jika suatu saat Live diperlakukan sebagai media biasa, itu merupakan scope terpisah.

## 14. Room

Status: **ARCHITECTURE FIXED**

Room:
- Cache/local data.
- Cache jadwal shalat.
- Notification state.
- Preferences/state lokal.

Room bukan source of truth.

Alur:
Online source → sync → validation → Room → app.
Offline → Room → app.

## 15. Firebase

Status: **LIMITED ROLE**

Direncanakan hanya untuk kebutuhan dinamis tertentu:
- Koreksi Hijriah Kemenag.
- Data kalender Kemenag.
- Distribusi data jadwal shalat bila arsitektur final tetap memakai Firebase.
- FCM jika nanti memang diperlukan.

Firebase bukan database utama data resmi Wahidiyah.

## 16. Google Apps Script

Status: **GAP**

Fungsi yang direncanakan:
- Membaca Spreadsheet Pusat.
- Validasi.
- Filter.
- Transformasi.
- JSON API.
- Menjadi boundary agar Android tidak tergantung struktur internal spreadsheet.

Yang belum tersedia:
- Source code GAS nyata.
- Web App deployment.
- URL Web App.
- Kontrak JSON produksi.

## 17. Google Drive

Status: **CONCEPT FIXED, DATA GAP**

Peran:
- Penyimpanan file PDF/dokumen/material resmi.
- Link/file reference disimpan atau dipublikasikan melalui data Sheet Pusat.

Yang belum tersedia:
- Folder/struktur produksi.
- Aturan naming.
- Access policy.
- Link nyata.

## 18. Asset Aplikasi

Status: **ASSET PACKAGE RECEIVED**

File sumber yang dikirim:
`dataapkmiladiyyah.rar`

Keterangan:
- Paket aset aplikasi.
- Digunakan untuk **suara notifikasi dan logo**.
- Bukan dianggap sebagai source code project.
- Belum dimodifikasi atau dipasang oleh fase ini.

Aturan:
- Logo diperlakukan sebagai aset resmi yang diberikan pengguna.
- Suara notifikasi dipasang pada fase resource/notification yang sesuai.
- Jangan mengubah identitas logo tanpa instruksi khusus.

## 19. CRITICAL GAPS BEFORE FULL PROVIDER IMPLEMENTATION

Masih belum tersedia:
1. Spreadsheet Pusat asli.
2. Struktur Sheet produksi sebenarnya.
3. GAS source code.
4. URL Web App GAS.
5. Struktur Sheet Pustaka.
6. Struktur informasi resmi lainnya.
7. Firebase project + schema/data pipeline.
8. Mekanisme sync jadwal shalat.
9. Format aturan override Hijriah.

## 20. NON-GOALS FASE INI

Fase 2E tidak:
- membuat API GAS produksi,
- membuat Firebase collection produksi,
- menebak endpoint,
- menebak spreadsheet ID,
- menanam credential,
- membuat dataset palsu,
- menghardcode kalender 2026,
- memasukkan contoh workbook sebagai data produksi,
- commit,
- push ke GitHub.

## 21. NEXT SAFE IMPLEMENTATION

Setelah Fase 2E PASS:
- bangun mesin Gregorian/Miladiyyah,
- lalu mesin Hijriah lokal,
- kemudian Room/cache,
- kemudian scheduler Dana Box,
- setelah source backend nyata tersedia, sambungkan provider sebenarnya.

---
