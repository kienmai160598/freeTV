# Debloat lists

One folder per TV model. `debloat.txt` is applied with `scripts/tvctl disable` and undone with
`scripts/tvctl restore`. Notes about each model (stock launcher, setupwraith, what must stay,
quirks) are in the comment header of its `debloat.txt`; read it before applying.

## Format

- Folder `<brand>-<model>/`, one `debloat.txt`.
- First line: a comment naming the TV, Android version and build (`scripts/tvctl probe` prints them).
- One package per line, `#` comments and trailing `# notes` allowed, grouped by purpose, no duplicates.
- Only packages actually disabled and lived with; say what broke and what was re-enabled.
- Name a non-default stock launcher or Live TV package in the header.
- Add a row below. `make test` checks every list and that this table matches the folders.

| Folder | TV | OS |
|---|---|---|
| `acer-ar55ar2851udpro/` | Acer AR55AR2851UDPRO 55" (SCBC R4, Realtek rtd2851m), India | Android TV 14 |
| `aipro2k/` | AiPro2K (Expressluck, dongnae; Changhong AI PONT family) | Android TV 14 |
| `aiwa-ai-pont-sa/` | AIWA AI PONT SA (Changhong, osaka, ISDB86AW) | Google TV 14 |
| `alwtniet-hy320-mini/` | Alwtniet HY320 Mini projector (device adt3, Askey; Allwinner/HTC firmware), 1 GB, 720p | Android 11 |
| `amazon-fire-tv-aftdec012e/` | Amazon Fire TV, AFTDEC012E (ginza), Android 11 | Android 11 / Fire OS |
| `amazon-fire-tv-afteu014/` | Amazon Fire TV smart TV (AFTEU014, greta, MediaTek m7322), Germany | Fire OS 7.6.8.5 / Android 9 |
| `amazon-fire-tv-afttiff43/` | Amazon Fire TV 50" 4K smart TV (AFTTIFF43, brandenburg) | Fire OS 7.7.1.7 / Android 9 |
| `amazon-fire-tv-stick-4k-2018/` | Amazon Fire TV Stick 4K, 1st gen 2018 (AFTMM, mantis) | Fire OS 6.7.1.1 / Android 7.1 |
| `amazon-fire-tv-stick-4k-2023/` | Amazon Fire TV Stick 4K, 2nd gen 2023 (AFTKM, karat), Brazil | Fire OS 8.1.8.5 / Android 11 |
| `amazon-fire-tv-stick-4k-max-2021/` | Amazon Fire TV Stick 4K Max, 1st gen 2021 (AFTKA, kara), Colombia | Fire OS 7.7.1.3 / Android 9 |
| `amazon-fire-tv-stick-lite-2020/` | Amazon Fire TV Stick Lite, 1st gen 2020 (AFTSS, sheldon) | Fire OS 7.7.1.7 / Android 9 |
| `anker-nebula-d2140/` | Anker Nebula Cosmos projector D2140 (D2140-Cosmos, SDMC/Amlogic, 2 GB), Vietnam | Android TV 9 |
| `anker-nebula-d2426/` | Anker Nebula projector D2426 (D2426_CapsuleIIIPro, baekdu, SDMC/MediaTek m7632, 1.7 GB), Brazil | Android TV 14 |
| `anker-nebula-mars-3-air/` | Anker Nebula Mars 3 Air projector D2325 (Nebula_Mars_2_V2, taihang, MediaTek m7332), Portugal | Google TV 14 |
| `aqua-matrixtv-ee-50/` | AQUA MatrixTV EE 50" 4K (Haier, AQ9676EU, omonia, MediaTek), Indonesia | Google TV 14 |
| `artel-cvte-mt5863/` | Artel smart TV (CVTE TP.MT5510S.PB782, cvt_mt5863_eu, SMART_TV), Uzbekistan | Android 7.0 |
| `asus-m1g/` | ASUS M1G Google TV (MOKA, dahu, Realtek RTD2851F), US | Android TV 11 |
| `beko-b43b880b/` | Beko B43B880B (Arçelik, sinchon, MediaTek), Turkey | Android TV 11 |
| `benq-e55-735/` | BenQ E55-735 (Changhong AI PONT, DVB96BQ) | Google TV 14 |
| `benq-e55-750/` | BenQ E55-750 (Changhong AI PONT, DVB96BQ) | Google TV (Android 11) |
| `benq-e55-760/` | BenQ E55-760 (Changhong AI PONT, GTVU64BQ, board alimos) | Google TV 14 |
| `benq-gv32/` | BenQ GV32 portable projector (SDMC, BenQ_GV01, himalaya, MediaTek m7332) | Google TV 14 |
| `benq-qs01/` | BenQ QS01 Android TV dongle for BenQ projectors (Inspur, Amlogic S905Y2, 2 GB) | Android TV 12 |
| `bgh-smart-tv-android14/` | BGH Smart TV (reported model; UKNV.260821.001) | Android TV 14 |
| `byintek-t5-max/` | BYINTEK T5 Max projector (built-in Google TV stick: SkyworthDigital YYT, Amlogic), Brazil | Google TV 14 |
| `byintek-u4/` | BYINTEK Love U4 projector, 1080P (Skyworth RG_HS352, HiSilicon Hi3751V350, 1 GB), Brazil | Android 9 |
| `caxium-58-realtek-rtk2851p/` | Caxium 58" 147 cm UHD Smart TV (Realtek RTD285O, RTK2851P, TopTech + Zeasn), Colombia | Android TV 9 |
| `cecotec-shandao/` | Cecotec VQU10065S 65" QLED (Vestel-built, shandao, MediaTek), Spain | Android TV 14 |
| `challenger-32t22bt/` | Challenger 32T22 BT (cvt_mt5863_col, board TP.MT5510.PB781, MediaTek MT5659, 1 GB RAM), Colombia | Android 7.0 |
| `chiq-ai-pont/` | CHiQ AI PONT (DVB93CQ) | Android TV 11 |
| `chiq-gtvu65/` | CHiQ AI PONT (Changhong GTVU65, dangni, MediaTek mt5867) | Google TV 14 |
| `compac-atv-r2/` | Compac ATV R2 (SCBC R2, Realtek RTD285O) | Android TV 9 |
| `coocaa-k3-pro-90p3d-max/` | Skyworth Coocaa K3 Pro 90P3D Max (8R145_P3DM, Realtek RTD2885N), China market | Android 11 (Coocaa OS) |
| `coocaa-p9-projector/` | Coocaa P9 projector (MediaTek mt5862), China | Android 9 (Coocaa OS) |
| `dcolor-gd2/` | Dcolor GD2 (YHT) | Google TV 14 (SDK 34) |
| `dialog-viu-sei103dl/` | Dialog ViU (SEI Robotics SEI103DL, Amlogic, NES middleware), Sri Lanka | Android TV 9 |
| `dyon-2k-android-tv/` | DYON 2K Android TV (MTC, komagome, MediaTek m5621), Germany | Android TV 11 |
| `eko-2k-google-tv-tennoji/` | EKO 2K Google TV (MTC-built, tennoji, MediaTek mt5867 / T56) | Google TV 14 |
| `eko-4k-bangbae/` | EKO 4K Android TV (bangbae, MTC, MediaTek) | Android TV 11 |
| `epson-ls800/` | Epson LS800 laser projector (Askey HA90 module, sti6202d101, Amlogic) | Android TV 14 |
| `ffalcon-65s585c-pro/` | FFalcon (雷鸟) 鹤6 PRO 65S585C Pro (China, tcl_mt5879_cn, MediaTek MT9653) | Android TV 11 |
| `ffalcon-r34a0-cn/` | FFalcon (雷鸟) r34a0, China (V8-A920T01-LF1V147, Amlogic T920), 1 GB | Android 5.1.1 |
| `formovie-laser-4k-hanlla/` | Formovie Laser 4K projector (hanlla, MediaTek T31 / m7632, 1.8 GB) | Android TV 14 |
| `formovie-mprojm05/` | Formovie / Fengmi projector MiProjM05 (eva, Amlogic) | Android TV 9 |
| `freebox-player-pop/` | Freebox Player POP (Free, fbx8am, Amlogic), IPTV/DVB-T box | Android TV 10 |
| `gadnic-proj100w/` | Gadnic PROJ100W projector (built-in Google TV stick: SkyworthDigital YYT, Amlogic HP46B), Argentina | Google TV 14 |
| `google-chromecast-gtv-4k/` | Chromecast with Google TV 4K (sabrina) | Google TV 14 |
| `google-chromecast-gtv-hd/` | Chromecast with Google TV HD (boreal) | Google TV 14 |
| `google-tv-streamer-4k-kirkwood/` | Google TV Streamer 4K (kirkwood, GZRNL) | Google TV 14 |
| `grundig-50gfu7850b/` | Grundig 50 GFU 7850 B 50" (Arçelik, CO3Plus, sinchon, MediaTek), Turkey | Android TV 11 |
| `grundig-50vlx22ldlbv/` | Grundig 50VLX22LDLBV (Arçelik, CO3Plus, sinchon, MediaTek), Netherlands | Android TV 11 |
| `grundig-kunyang/` | Grundig Android UHD TV (Arçelik, GxGrNM, kunyang, MediaTek), Turkey | Android TV 14 |
| `haier-android-tv-dvb2k/` | Haier Android TV DVB2K (broadway, HaierATVT56DVB) | Android TV 11 |
| `haier-h32s80efx/` | Haier H32S80EFX (MatrixTV CE, HRT56EU, deto, MediaTek), Thailand | Google TV 14 |
| `haier-hr9216eu/` | Haier MatrixTV GE (HR9216EU, haeundae, MediaTek), EU | Google TV 14 |
| `hisense-65d62qh/` | Hisense 65D62QH (VIDAA_TV, MediaTek MT9653), China | Android TV 11 |
| `hisense-e5q-pro/` | Hisense E5Q Pro (VIDAA_TV, MediaTek MT9653), China | Android TV 11 |
| `hisense-fire-tv-55qd6qf/` | Hisense 55QD6QF Fire TV (AFTOLCBC82, ginza), Canada | Fire OS 8.1.8.0 / Android 11 |
| `hisense-fire-tv-aftcibfbd1/` | Hisense Fire TV (AFTCIBFBD1, coyote, MediaTek), US | Fire OS 8.0 / Android 11 |
| `hisense-hg9/` | Hisense HG9 (HiSmart 4K A4FFM) | Android TV 9 |
| `hisense-hismarttv-a4/` | Hisense HiSmartTV A4 (xiaoyushan, MStar m7322), India | Android TV 9 |
| `hisense-hismarttv-a4-android11/` | Hisense HiSmartTV A4 (xiaoyushan, MStar m7322), Android 11 firmware RTT2.221108.001 | Android TV 11 |
| `hisense-smarttv-4k-ffm/` | Hisense SmartTV 4K FFM (huangshan, MediaTek), Latin America; also the US Hisense U9DG | Android TV 10 |
| `hisense-smarttv-4k-ffm-android11/` | Hisense SmartTV 4K FFM (songshan, MediaTek m7632) | Android 11 (SDK 30) |
| `hisense-smarttv-4k-ffm-lushan/` | Hisense SmartTV 4K FFM (lushan, MediaTek MT9676), Mongolia | Google TV 12 |
| `hisense-u75-hengshan/` | Hisense U75 (hengshan, MediaTek), Latin America | Google TV 12 |
| `hitachi-65qledsm20/` | Hitachi 65QLEDSM20 (MTC, bangbae, MediaTek m7322), Australia | Android TV 11 |
| `hitachi-cdh-le32smart21/` | Hitachi CDH-LE32SMART21 (TCL-built C06, C06_2K_GB, MediaTek MT9221, 1 GB), Argentina | Android TV 11 |
| `homeplus-stb-6252c/` | Home+ STB-6252C (中嘉寬頻, Inspur, MT5221), DVB-C/OTT box | Android TV 11 |
| `hotack-l018/` | HOTACK L018 Smart Projector (Allwinner H713, reports ADT-3, LuminaOS) | Android 11 |
| `hotack-smart2kpjt/` | HOTACK SMART2KPJT projector, "Whale TV 95C8" (Amlogic am30b8, WhaleOS S8E, Zeasn firmware) | Android 13 |
| `hy320/` | HY320 projector (t950s_be311, Google-certified Android TV) | Android 13 |
| `impex-evoq-4k/` | Impex evoQ 4K (Changhong, GTVU64IP, board alimos, MediaTek mt5896), India | Google TV 14 |
| `inclo-google-tv-4k/` | INCLO Google TV 4K (KTC, Realtek, khardi, KTC_INCLO_4K_EU_23GV1), Taiwan | Google TV 11 |
| `insignia-ns42f201na22/` | Insignia 42" F20 Fire TV (NS-42F201NA22, AFTEAMR311, blanche) | Fire OS 6.7.1.1 / Android 7.1.2 |
| `insignia-ns55f301na25/` | Insignia 55" F30 4K Fire TV (NS-55F301NA25, AFTDCT31, duckie) | Fire OS 7 / Android 9 |
| `jmgo-n1s-holan/` | JMGO N1S projector (K80A, holan, MediaTek m7332), Australia | Google TV 14 |
| `jvc-android-tv-menlo/` | JVC Android TV (Vestel-built, BARTU_Jvc, menlo, MediaTek mt5867), Spain | Android TV 11 |
| `jvc-lt-43km348/` | JVC LT-43KM348 (KTC-built, KTC_JVC_2K_25GV1, jagalchi, MediaTek mt5867 / T56) | Google TV 14 |
| `jvc-lt55mb508/` | JVC LT55MB508 (MTC, seocho, MediaTek MSD6886), Brazil | Android TV 11 |
| `jvc-uk-4k-sadang/` | JVC LT-58CA810 (UK 4K Android TV, MTC, MTCMSD6886UK, sadang, MediaTek m7322), UK | Android TV 11 |
| `kalley-43g200/` | Kalley 43G200 (device R3_GTV, MOKA, Realtek RTD2841A), Latin America | Google TV 14 |
| `kalley-r10g/` | Kalley R10G (device R10G, product ACER_FFM, MOKA, Realtek RTD2875P), Latin America | Google TV 14 |
| `karbonn-swtv-20ae/` | Karbonn SWTV-20AE (Skyworth OEM, SW4H, MediaTek), India | Android TV 10 |
| `kogan-g7/` | Kogan G7 (Expressluck, cottongreen, Realtek), Australia | Google TV (Android 11) |
| `kogan-gtvu65kg/` | Kogan Google TV (Changhong CHiQ AI PONT, GTVU65KG, dangni, MediaTek mt5867), Australia | Google TV 14 |
| `koryo-smart-tv-hi3751v553/` | Koryo 55" 4K Smart TV (CVTE TP.HV553.PC821B, HiSilicon Hi3751V553, 1 GB) | Android 7.0 |
| `ktc-4k-smart-tv-hongkong/` | KTC 4K Smart TV (device hongkong, MediaTek), Taiwan | Google TV 14 |
| `liberton-ltv-24h01at/` | Liberton LTV 24H01AT (Liberton, built by Changhong, device redwood), Ukraine | Android TV 11 |
| `lumio-vision-7/` | Lumio Vision 7 55" (KTC ODM, Realtek, khardi, KTC_LUMIO_4K_EU_23GV1), India | Google TV 14 |
| `magcubic-hy300-pro/` | Magcubic HY300 Pro projector (rk3566_rgo, device adt3, Askey) | Android 11 |
| `magcubic-hy300-pro-plus/` | Magcubic HY300 Pro+ projector (rk3566_rgo) | Android 14 |
| `master-g-mga3200/` | Master-G MGA3200 (SCBC R1, Realtek; Android reports model `ATV R1`), Chile | Android TV 9 |
| `mecool-km2-plus/` | MECOOL KM2 Plus | Android TV 14 |
| `motorola-mt4310/` | Motorola MT4310 (Newsan; Hisense board, hayward, MediaTek), Argentina | Android TV 11 |
| `motorola-swtv-24ae-4k/` | Motorola SWTV-24AE-4K (Skyworth OEM, eleonas, MediaTek), India | Google TV 14 |
| `motorola-swtv-24ae-fhd/` | Motorola SWTV-24AE-FHD (Skyworth OEM, beomil, MediaTek), India/Global | Google TV 14 |
| `mtc-uk-2k-android-tv/` | MTC UK 2K Android TV (tabata, MediaTek m5621; JVC-family firmware, `android.autoinstalls.config.JVC` present), UK | Android TV 11 |
| `next-start-pro-4k/` | Next Start Pro 4K TV Box (SDT HPR314-Next, Realtek RTD1325), Turkey, streaming box | Google TV 14 |
| `noblex-shinjuku/` | Noblex NoblexTV (Skyworth-built, shinjuku, MediaTek m5621), Argentina | Android TV 11 |
| `nokia-streaming-box-8000/` | Nokia Streaming Box 8000 (Sagemcom/NES, SEI600NK4, HND, Amlogic) | Android TV 14 |
| `nokia-streaming-box-8010/` | Nokia Streaming Box 8010 (Sagemcom/NES, SEI804NK, YYJ, Amlogic) | Android TV 14 |
| `nvidia-shield-tv-2017/` | NVIDIA SHIELD TV 2017 (darcy) | Android TV 11 |
| `nvidia-shield-tv-2019/` | NVIDIA SHIELD TV 2019 tube (P3430, sif) | Android TV 11 |
| `nvidia-shield-tv-pro-2019/` | NVIDIA SHIELD TV Pro 2019 (P2897, mdarcy) | Android TV 11 |
| `ok-ai-pont-ikebukuro/` | OK. AI PONT (Changhong, ikebukuro, MediaTek), Italy | Android TV 11 |
| `oneplus-tv-cebu/` | OnePlus TV (C00, Cebu, MediaTek m7632), India | Android TV 10 |
| `oneplus-tv-dosa/` | OnePlus TV (Dosa, Oneplus_Dosa_IN) | Android TV 11 |
| `oneplus-tv-dubai/` | OnePlus TV (D00, Dubai) | Google TV 14 |
| `oneplus-tv-y1/` | OnePlus Y1 43" (Y Series, shibuya) | Android TV 11 |
| `onn-4k-plus-2025/` | onn. 4K Plus Streaming (2025, Walmart; coffey, Amlogic S905X5M), US | Google TV 14 |
| `onn-4k-streaming-box-2023/` | onn 4K Streaming Box (2023, Walmart; YOC, Amlogic), US | Google TV 14 |
| `oppo-k9-55-oppocnm632/` | OPPO K9 55 (OPPOCNM632) | Android 9 (ColorOS TV) |
| `panasonic-4ktv-0su/` | Panasonic 4KTV-0SU (Skyworth-built SW4H, MediaTek m7332), Gulf region | Android TV 10 |
| `panasonic-4ktv-3mup/` | Panasonic 4KTV-3MUP (MOKA R4_GTV, Realtek rtd2851m), Taiwan | Google TV 14 |
| `panasonic-4ktv-3su/` | Panasonic 4KTV-3SU (Skyworth-built SW6H, MediaTek, same board as `thomson-sppl-4k-sw6h/`), ~1.9 GB | Android TV 14 (Google TV home) |
| `panasonic-4ktv-jup/` | Panasonic 4KTV-JUP (Jupiter_PA, MediaTek) | Android TV 11 |
| `panasonic-kowloon/` | Panasonic Google TV LCD (OZUM_Panasonic, kowloon, MediaTek) | Android 14 |
| `panasonic-th-40hs550k/` | Panasonic TH-40HS550K (Skyworth-built, GS655 shibuya, MediaTek) | Android TV 11 |
| `panavox-google-tv/` | PANAVOX Google TV (dongnae) | Google TV 14 |
| `peaq-ptv43aqu-5023c/` | PEAQ PTV 43AQU-5023C (Changhong, reports OK. AI PONT, dundas, MediaTek m7332) | Android TV 11 |
| `philips-50pud7406/` | Philips 50PUD7406/43 (TPV, PH0M_EA_T32, MStar/MediaTek), Chile | Android TV 11 |
| `philips-50puf7099-t3/` | Philips 50PUF7099/T3 (TPV EBONY_V560, Hisilicon), China | Android TV 9 |
| `philips-50pus8517/` | Philips 50PUS8517/12 (TP Vision, PH1M_EA_9970A, MediaTek MT5895), Europe | Android TV 11 |
| `philips-55oled707/` | Philips 55OLED707/12 (TP Vision, PH1M_EA_9970A, MediaTek MT5895), UK | Android TV 11 |
| `philips-55oled708/` | Philips 55OLED708/12 (TPV, PH1M_WW_9972, MediaTek), Poland | Google TV 12 |
| `philips-55oled820/` | Philips 55OLED820/12 (TPV, PH1M_WW_9972, MediaTek), Czech Republic | Google TV 14 |
| `philips-55pos9002/` | Philips 55POS9002/12 OLED (TP Vision TPM171E, PH7M_EU_5596, MediaTek MT5891), Europe | Android TV 8.0 |
| `philips-55pus7503/` | Philips 55PUS7503/12 (TP Vision TPM171E, PH7M_EU_5596, MediaTek MT5891), Europe | Android TV 8.0 |
| `philips-65oled706/` | Philips 65OLED706/12 (TP Vision, PH1M_EA_9970A, MediaTek MT5895), France; 55OLED706/12, Poland | Android TV 11 |
| `philips-65oled907/` | Philips 65OLED907/12 and 55OLED907/12 (TP Vision, PH1M_EA_9970A, MediaTek MT5895), UK and France | Android TV 11 |
| `philips-65pml9506/` | Philips 65PML9506/12 (TP Vision, PH1M_EA_9970A, MediaTek), Spain | Android TV 11 |
| `philips-65pus8102/` | Philips 65PUS8102/12 (TP Vision TPM171E, PH7M_EU_5596, MediaTek MT5891), Europe | Android TV 8.0 |
| `philips-65pus9435/` | Philips 65PUS9435/12 (TP Vision TPM191E, PH9M_EA_5599, MediaTek MT5887), Germany | Android TV 12 |
| `philips-75pus8505/` | Philips 75PUS8505/12 (TP Vision TPM191E, PH9M_EA_5599, MediaTek MT5887), Europe | Android TV 12 |
| `philips-google-tv-ta1/` | Philips 65PUS8729/60 (TPV, PH1M_WW_9972, MediaTek), Russia | Google TV 14 |
| `philips-google-tv-ta7/` | Philips Google TV TA7 (TPV, PH3M_AL_T32, MediaTek), Brazil | Google TV 14 |
| `philips-r8g-google-tv/` | Philips R8G Google TV (MOKA, whitefield, Realtek RTD2851F), US | Google TV 14 |
| `philips-smart-4k-google-tv/` | Philips Smart 4K TV (Skyworth, eleonas, MediaTek), India | Google TV 14 |
| `philips-tpm191e/` | Philips TPM191E (TP Vision, PH9M_EA_5599, MediaTek mt5887), Europe | Android TV 12 |
| `polytron-2k-hayward/` | Polytron 2K (WLTV POLYTRON2K, hayward; Hisense/CVTE board, MediaTek mt5867), Indonesia | Android TV 11 |
| `portronics-beem-470/` | Portronics Beem 470 Smart LED Projector (Allwinner H713, 1 GB), India | Android 11 |
| `pptv-p34a4p/` | PPTV p34a4p (Suning PPTV, Amlogic board), China | Android 5.1.1 |
| `prismplus-q65-ultra-r10g/` | PRISM+ Q65 Ultra 2026 QLED 4K AI Google TV (R10G, PRISMPLUS_FFM, MOKA, Realtek RTD2875 SoC, RTD6748 platform) | Google TV 14 |
| `prosonic-bodrum-finch/` | Prosonic Android TV (Vestel-built, BODRUM, finch, MediaTek m7332), Denmark | Android TV 14 |
| `rca-2k-android-tv-lasalle/` | RCA 2K Android TV (KONKA, device lasalle, Realtek RTD2841A, ISDB) | Android TV 11 |
| `rca-ai-pont/` | RCA AI PONT SA (Changhong, redwood, ISDB92RC; also sold as realme 40-inch and AIWA AW32B4SMG) | Android 14 |
| `rca-rc50a21s-4ksm/` | RCA RC50A21S-4KSM, Zeasn home (Toptech firmware RTK2851P, Realtek RTD2851P, board mac6p) | Android TV 9 |
| `redmi-mitv-antl0/` | Redmi TV (MiTV-ANTL0, starwars) | Android 5.1.1 (not Android TV) |
| `redmi-mitv-mftp0/` | Redmi TV (MiTV-MFTP0, alita), China firmware 23.2.10.6129 | Android TV 9 (CN build) |
| `redmi-mitv-mooq3/` | Redmi TV 43" (MiTV-MOOQ3, tarzan) | Android TV 11 |
| `rocktek-gb1/` | RockTek GB1 | Google TV 14 |
| `rocktek-gx1/` | RockTek GX1 (HPR3A1325_4K) | Google TV 14 |
| `sankey-smart-tv-hi3751v350/` | Sankey SMART_TV (CVTE, HiSilicon Hi3751V350, 1 GB) | Android 12 |
| `sankey-smart2ktvse/` | Sankey SMART2KTVSE (Whale OS, SiliconPlayer am30bm) | Android TV 13 |
| `sanyo-atv-r2/` | SANYO ATV R2 (SCBC, Realtek RTD285O) | Android TV 9 |
| `sber-salute-tv-at30a6/` | Sber Salute TV 43" (CVTE TP.T962D4.PC756, Amlogic T962D4, at30a6, 2 GB), Russia | StarOS 1.101 / Android 13 |
| `sber-salute-tv-pb766/` | Sber Salute TV 32" (CVTE TPD.MT9216.PB766, MediaTek MT9256, 1 GB), Russia | StarOS 1.103 / Android 11 |
| `seleco-tv39020hda9smart/` | Seleco TV39020HDA9SMART 39" (AT_SmartScreen, Toptech + Zeasn, Realtek RTK2842P), Italy | Android TV 9 |
| `sharp-2k-eu/` | SHARP 2K EU Android TV (Expressluck martin, MediaTek mt5867), EU | Android TV 14 |
| `sharp-2t-c32gh3000i/` | SHARP 2T-C32GH3000I (SHARP 2K Smart TV-A; Skyworth-built, beomil, MediaTek mt5867), Indonesia | Google TV 14 |
| `sharp-4t-c43dl1/` | SHARP AQUOS 4T-C43DL1 (AQUOS-TVJ21-2, tcve2uj, MediaTek), Japan | Android TV 11 |
| `sharp-55gp6260e/` | SHARP 55GP6260E (Sharp Google TV, kaitak, MediaTek MTK9612), EU | Google TV 11 |
| `sharp-aquos-tve21f/` | SHARP AQUOS TVE21F (AQUOS-TVE21F, banqiao, CVTE/MediaTek), UK | Android TV 14 |
| `sharp-aquos-tve23a/` | SHARP AQUOS TVE23A (AQUOS-TVE23A, sandiego, CVTE/MediaTek mt5889), Denmark | Google TV 14 |
| `sharp-aquos-tvx19b/` | SHARP AQUOS-TVX19B (AQUOS-TVX19B, gangnam, MediaTek m7322), Taiwan | Android TV 11 |
| `siragon-4k-smart-tv-elliniko/` | Síragon 4K Smart TV (KTC, MediaTek MT9676, elliniko, KTC_SIRAGON_4K_24GV1), Venezuela | Google TV 14 |
| `skyworth-43e6a/` | Skyworth 43E6A (open_fhd, MediaTek MT5886) | Android TV 8.0 |
| `skyworth-65a3f/` | Skyworth 65A3F (7T641_A3F, MediaTek MT9633), China | Android 13 (CCOS 10.01.250328) |
| `skyworth-7s60-a5/` | Skyworth 7S60_A5 (sky8S34, 1.9 GB), China market | Coocaa OS / Android 8 |
| `skyworth-7t861-a23/` | Skyworth 7T861_A23 (MediaTek MT9638), China market | Coocaa OS / Android 10 |
| `skyworth-8r40-k5c/` | Skyworth / Coocaa 8R40_K5C (Realtek rtd285x, 1 GB, 32-bit), China domestic firmware | Android 8.0 (not Google TV) |
| `skyworth-sw-22hk-nf/` | Skyworth SW-22HK NF (SW6H, MediaTek m7332) | Android TV 11 (Google TV home) |
| `skyworth-sw-22sa/` | Skyworth SW-22SA (SW6H, MediaTek), ~1.9 GB | Android TV 14 (Google TV home) |
| `skyworth-swtv-21ae/` | Skyworth SWTV-21AE (sunnyvale, MediaTek) | Android TV 14 |
| `skyworth-swtv-22ae-fhd/` | Skyworth SWTV-22AE-FHD (umeda, MediaTek), Vietnam/Global | Google TV 14 |
| `skyworth-swtv-24ae-4k-nf/` | Skyworth SWTV-24AE-4K NF (eleonas, MediaTek mt5896), South Africa/Global | Google TV 14 |
| `skyworth-swtv-24na-4k-nf/` | Skyworth SWTV-24NA-4K NF (eleonas, MediaTek mt5896), US | Google TV 14 |
| `skyworth-xiaopai-p3/` | Skyworth Xiaopai P3 (Q5001, cupid-p2), streaming box | Android 10 |
| `sony-bravia-4k-gb/` | Sony BRAVIA 4K GB (BRAVIA_ATV2_EU, BRAVIA_ATV2) | Android TV 9 |
| `sony-bravia-4k-gb-us/` | Sony BRAVIA 4K GB (BRAVIA_ATV2_UC, MediaTek mt5891) | Android TV 9 |
| `sony-bravia-4k-ur2/` | Sony BRAVIA 4K UR2 (BRAVIA_UR2_4K, MediaTek) | Android TV 10 |
| `sony-bravia-4k-ur3/` | Sony BRAVIA 4K UR3 (BRAVIA_UR3, MediaTek mt5893), EU | Android TV 10 |
| `sony-bravia-4k-vh1/` | Sony BRAVIA 4K VH1 (KD-55XH9096, BRAVIA_VH1, MediaTek MT5895), EU | Google TV 12 |
| `sony-bravia-4k-vh2/` | Sony BRAVIA 4K VH2 (KD-55X80J, BRAVIA_VH2, MediaTek), EU and North America | Google TV 12 |
| `sony-bravia-4k-vh21/` | Sony BRAVIA 4K VH21 (KD-65X85K, 2022, BRAVIA_VH21, MediaTek MT5895), Australia | Google TV 12 |
| `sony-bravia-4k-vh21-eu/` | Sony BRAVIA 4K VH21 (XR-55A80K, 2022, BRAVIA_VH21, MediaTek mt5895), EU | Google TV 12 |
| `sony-bravia-4k-vh21-us/` | Sony BRAVIA 4K VH21 (XR-65A95K, 2022, BRAVIA_VH21, MediaTek), US | Google TV 12 |
| `sony-bravia-9-ii/` | Sony BRAVIA 9 II (K-65XR90M2, BRAVIA_AE3, MediaTek MT5838) | Google TV 14 |
| `sony-bravia-ae1/` | Sony BRAVIA 4K AE1 (BRAVIA_AE_M6L, MediaTek MT5897), EU | Google TV 12 |
| `sony-bravia-ae2/` | Sony BRAVIA 4K AE2 (BRAVIA_AE2, MediaTek MT5897), North America | Google TV 14 |
| `sony-bravia-bf1/` | Sony BRAVIA BF1 (BRAVIA 2 II class, BRAVIA_BF1_4K_A, MediaTek) | Google TV 12 |
| `sony-bravia-tl2/` | Sony BRAVIA TL2 (OD0M_EA_T32, TPV-built, MediaTek), Europe | Android TV 11 |
| `sony-bravia-vu1/` | Sony BRAVIA VU1 (KD-43X7500H, BRAVIA_VU1_UC, Realtek) | Android TV 11 |
| `sony-bravia-vu3/` | Sony BRAVIA KD-55X75K (BRAVIA_VU3_4K, Realtek rtd2885m) | Google TV 14 |
| `sony-bravia-vu31/` | Sony BRAVIA VU31 4K (BRAVIA_VU31_4K, Realtek), India | Google TV 14 |
| `sony-kd-43x8000g/` | Sony BRAVIA KD-43X8000G (2019, BRAVIA_ATV3_4K, MediaTek), APAC | Android TV 9 |
| `sony-kd-43x80l/` | Sony BRAVIA KD-43X80L (2023, BRAVIA_VH22, MediaTek MT5895), EU | Google TV 12 |
| `sony-kd-49x8000g-hk/` | Sony BRAVIA KD-49X8000G (2019, BRAVIA_ATV3_4K, MediaTek), Hong Kong | Android TV 9 |
| `sony-kd-49x8307c/` | Sony BRAVIA KD-49X8307C (2015, "BRAVIA 4K 2015", SVP-DTV15, MediaTek), EU firmware | Android TV 7.0 |
| `sony-kd-55x7500-cn/` | Sony KD-55X7500 (BRAVIA 4K GB ATV3, BRAVIA_ATV3_4K_CN), China | Android TV 9 |
| `sony-kd-55x8000c/` | Sony BRAVIA KD-55X8000C (2015, "BRAVIA 4K 2015", SVP-DTV15, MediaTek), China firmware | Android TV 7.0 |
| `sony-kd-55x8509c/` | Sony BRAVIA KD-55X8509C (2015, "BRAVIA 4K 2015", SVP-DTV15, MediaTek), EU firmware | Android TV 7.0 |
| `sony-kd-55x85j/` | Sony BRAVIA KD-55X85J (2021, BRAVIA_VH2, MediaTek MT5895), South America | Google TV 12 |
| `sony-kd-55x9500g-cn/` | Sony BRAVIA KD-55X9500G (BRAVIA_UR2_4K_CN), China | Android TV 10 |
| `sony-kd-55xe8596/` | Sony BRAVIA KD-55XE8596 (2017, "BRAVIA 4K GB", BRAVIA_ATV2_EU, MediaTek MT5891), UK | Android TV 9 |
| `sony-kd-55xf7596/` | Sony BRAVIA KD-55XF7596 (2018, "BRAVIA 4K GB ATV3", BRAVIA_ATV3_4K_EU, MediaTek MT5891), EU | Android TV 9 |
| `sony-kd-55xf8096/` | Sony BRAVIA KD-55XF8096 (2018, "BRAVIA 4K GB ATV3", BRAVIA_ATV3_4K_EU, MediaTek MT5891), Spain | Android TV 9 |
| `sony-kd-65x80j/` | Sony BRAVIA KD-65X80J (2021, BRAVIA_VH2, MediaTek), India | Google TV 10 |
| `sony-kd-65x82l/` | Sony BRAVIA KD-65X82L (2023, BRAVIA_VH22, MediaTek), India | Google TV 12 |
| `sony-kd-65x9300d/` | Sony BRAVIA KD-65X9300D (2016, SVP-DTV15, MediaTek), Asia-Pacific | Android TV 8.0 |
| `sony-kdl-43w950d/` | Sony BRAVIA KDL-43W950D (2015/2016, SVP-DTV15, MediaTek), India ("PA") firmware | Android TV 8.0.0 |
| `sony-kdl-55w800c/` | Sony BRAVIA KDL-55W800C (2015, "BRAVIA 2015", SVP-DTV15, MediaTek), Taiwan firmware | Android TV 7.0 |
| `sony-x9000h-cn/` | Sony BRAVIA X9000H (2020, BRAVIA_VH1_CN, MediaTek MT5895), China | Android TV 12 |
| `sony-xbr-55x850c/` | Sony BRAVIA XBR-55X850C (2015, "BRAVIA 4K 2015", SVP-DTV15, MediaTek), US firmware | Android TV 8.0 |
| `sony-xbr-55x850d/` | Sony BRAVIA XBR-55X850D (Android model "BRAVIA 4K 2015"), firmware 6.827 | Android TV 8.0.0 |
| `sony-xbr-65x900f/` | Sony BRAVIA XBR-65X900F (2018, BRAVIA_ATV3_4K, MediaTek MT5891), US/Canada | Android TV 9 |
| `sony-xbr-x900e/` | Sony BRAVIA XBR-65X900E (2017, BRAVIA_ATV2) | Android TV 9 |
| `sony-xr-65a80j/` | Sony XR-65A80J (65-inch OLED, 2021, BRAVIA_VH2), EU | Google TV 12 |
| `sony-xr-65x90j-cn/` | Sony XR-65X90J (2021, BRAVIA_VH2, MediaTek MT5895), China | Android TV 10 |
| `stellamore-t950s/` | Stellamore T950S projector (Droidlogic be30a3, Amlogic txhd2) | Android 13 |
| `strong-leap-s3/` | Strong LEAP-S3 box (SkyworthDigital YDA, Amlogic S905Y4), Italy | Google TV 14 |
| `super-general-cvte-t972/` | Super General 55" 4K (CVTE TP.SK708D.PC821, Amlogic T972, x30a4, 1 GB), Saudi Arabia | Android 9 |
| `tcl-32es570f/` | TCL 32ES570F (C06, C06_2K_GB, MStar mt5867, 939 MB), Italy | Android TV 11 |
| `tcl-32l5a/` | TCL 32L5A (C06, model "Smart TV", 1 GB) | Android TV 11 |
| `tcl-32s334/` | TCL 32S334 (BeyondTV5, North America, Realtek RTD2851) | Android TV 9 |
| `tcl-32s5400a/` | TCL 32S5400A (C06, C06_2K_GB, MediaTek MT9221, 1 GB), 2023 | Android TV 11 |
| `tcl-32s5k/` | TCL 32S5K (C06, C06_2K_GB, MediaTek MT9221, 918 MB), EU | Android TV 11 |
| `tcl-32sf560-fire-tv/` | TCL 32SF560 (TCL Fire TV, AFTSHN02, shine) | Fire OS 7.7.1.7 / Android 9 |
| `tcl-40s5400a/` | TCL 40S5400A (C06, C06_2K_GB, MediaTek MT9221, 1 GB) | Android TV 11 |
| `tcl-43c645/` | TCL 43C645 (Smart TV Pro, Realtek RTD2851 / R51MT05, V8-R51MT05-LF1V652); also TCL 60C635 (G03_4K_GB, Realtek rtd288o), Spain | Google TV launcher on Android TV 11 |
| `tcl-43ep660/` | TCL 43EP660 (Europe, BeyondTV, Realtek rtd285o, V8-R851T02-LF1V378) | Android TV 9 |
| `tcl-43p615/` | TCL 43P615 (Europe, BeyondTV, Realtek RTD2851, AR06) | Android TV 11 |
| `tcl-43p725/` | TCL 43P725 (Smart TV Pro, BeyondTV4, Realtek rtd288o), Italy | Android TV 11 |
| `tcl-43s450f-ca/` | TCL 43S450F-CA (S4 Fire TV Edition, AFTDEC012E, ginza, MediaTek), Canada | Fire OS 8.1.8.5 / Android 11 |
| `tcl-43s5400a/` | TCL 43S5400A (C06, C06_2K_GB, MediaTek MT9221, 1 GB) | Android TV 11 |
| `tcl-43s5k/` | TCL 43S5K (G13, G13_2K_GB, MediaTek MT9221, 1 GB), India | Google TV 14 |
| `tcl-50c725k/` | TCL 50C725K (Smart TV Pro, BeyondTV4/BeyondTV6, RTD2851M, Realtek rtd288o, 2 GB), AR10 build | Android TV 11 |
| `tcl-50dp660/` | TCL 50DP660 (Europe, "Percee TV", MediaTek MT5658 / board mt5891, V8-T658T01-LF1V602) | Android TV 8.0 |
| `tcl-50ep680/` | TCL 50EP680 (Europe, BeyondTV, Realtek rtd285o) | Android TV 11 |
| `tcl-50j7000a/` | TCL 50J7000A (Thailand, BeyondTV, Realtek RT2851 / rtd285o, AR04, V8-R851T02-LF1V674) | Android TV 11 |
| `tcl-50p615/` | TCL 50P615 (Greece, BeyondTV, Realtek RTD2851 / rtd285o, AR05) | Android TV 11 |
| `tcl-50p735/` | TCL 50P735 (BeyondTV4, Smart TV Pro) | Google TV 11 |
| `tcl-55a300w/` | TCL 55A300W picture-frame TV (2023, G08, G08_4K_US_NF, MediaTek MT9653), US | Google TV 12 |
| `tcl-55c715/` | TCL 55C715 QLED (Europe, BeyondTV2, Realtek RTD2851, V8-R851T02-LF1V606) | Android TV 9 |
| `tcl-55c725/` | TCL 55C725 (Smart TV Pro, BeyondTV4, G03_4K_GB, Realtek R51MT05, V8-R51MT05-LF1V620), Italy; also TCL 65C655 Pro (same build) | Google TV launcher on Android TV 11 |
| `tcl-55c745/` | TCL 55C745, 2023 (G08, G08_4K_GB, MediaTek tcl9618) | Google TV 14 |
| `tcl-55c7k-uk/` | TCL 55C7K QD-Mini LED, 2025, UK (G08, MediaTek tcl9618) | Google TV 14 |
| `tcl-55c815/` | TCL 55C815 (2020, Europe/Denmark, BeyondTV2, Realtek rtd285o) | Android TV 11 |
| `tcl-55ep640/` | TCL 55EP640 (Greece, BeyondTV, Realtek RTD2851 / rtd285o, AR04) | Android TV 11 |
| `tcl-55p638/` | TCL 55P638 (Smart TV, BeyondTV4) | Android TV 11 |
| `tcl-55p7k/` | TCL 55P7K, Middle East (G10, G10_4K_GB, Realtek RTD2875P) | Google TV 12 |
| `tcl-55p8ls/` | TCL 55P8LS QD-Mini LED, Vietnam (G10, G10_4K_GB_32BIT, Realtek rtd6748) | Google TV 12 |
| `tcl-55q6cx1/` | TCL 55Q6CX1 (Smart TV Pro, G08, G08_4K_GB, MediaTek) | Google TV 12 |
| `tcl-55s470g/` | TCL 55S470G (G10, G10_4K_US_NF, Realtek), US | Google TV 12 |
| `tcl-55s546-us/` | TCL 55S546 (5-Series, G03_4K_US, BeyondTV4, GTV93), US | Google TV 11 |
| `tcl-58s571f-fire-tv/` | TCL 58S571F (TCL Fire TV, AFTDEC012E, ginza, MediaTek mt5896), US | Fire OS 8 / Android 11 |
| `tcl-65c6k-google-tv/` | TCL 65C6K (Smart TV Pro, G08, G08_4K_GB, MediaTek tcl9618 / mt5896, SoC MT9689), Latin America | Google TV 14 |
| `tcl-65c725/` | TCL 65C725 (Smart TV Pro, BeyondTV4) | Android TV 11 |
| `tcl-65c735/` | TCL 65C735 (Smart TV Pro, G05, MediaTek), France; also TCL 55C728, Turkey, and TCL 65C935, France | Google TV 11 |
| `tcl-65c7k-google-tv/` | TCL 65C7K (Smart TV Pro, G08, G08_4K_GB, MediaTek mt5896), Brazil | Google TV 14 |
| `tcl-65c7l-google-tv/` | TCL 65C7L (Smart TV Pro, G08, G08_4K_GB, MediaTek tcl9618), 2025, Australia | Google TV 14 |
| `tcl-65c8l-google-tv/` | TCL 65C8L (Smart TV Pro, G17, G17_4K_GB, MediaTek tcl9655), 2025, Australia | Google TV 14 |
| `tcl-65ep640/` | TCL 65EP640 (Finland, BeyondTV, Realtek RTD2851 / rtd285o, AP06) | Android TV 9 |
| `tcl-65ep68/` | TCL 65EP68 (Europe, BeyondTV, Realtek RTD2851 / rtd285o, V8-R851T02-LF1V722) | Android TV 11 |
| `tcl-65p615/` | TCL 65P615 (Europe, BeyondTV, Realtek RTD2851, AP06, V8-R851T02-LF1V637) | Android TV 9 |
| `tcl-65q550g/` | TCL 65Q550G (TCL Smart TV, Google TV 11, V8-R51MT08-LF1V095.021344) | Google TV |
| `tcl-65q650g/` | TCL 65Q650G (US Q6 2023, BeyondTV4, Realtek RTD2851 / R51MT08, V8-R51MT08-LF1V095, 2 GB) | Google TV launcher on Android TV 11 |
| `tcl-65qm8l-google-tv/` | TCL 65QM8L (Smart TV Pro, G17, G17_4K_US, MediaTek tcl9655), 2025, US | Google TV 14 |
| `tcl-65x10/` | TCL 65X10 QLED (France, BeyondTV2, Realtek RTD2851, V8-R851T02-LF1V363) | Android TV 9 |
| `tcl-75p635/` | TCL 75P635 (Smart TV, BeyondTV4, G07), AR07 build | Android TV 11 |
| `tcl-75p8k/` | TCL 75P8K, Europe / Nordics (G10, G10_4K_GB / C6KS, Realtek RTD2875P / rtd6748) | Google TV 12 |
| `tcl-85rm7l-google-tv/` | TCL 85RM7L (G08, G08_4K_GB, MediaTek tcl9618) | Google TV 14 |
| `tcl-ak30a5/` | TCL ak30a5 (China, Amlogic) | Android TV 9 |
| `tcl-beyondtv/` | TCL BeyondTV (India) | Android TV 9 |
| `tcl-beyondtv-pl/` | TCL BeyondTV, Poland (Realtek RTD2851, AP03, 2 GB) | Android TV 9 |
| `tcl-beyondtv-rt2851/` | TCL BeyondTV, EU/Nordic (Realtek RTD2851, V8-R851T02-LF1V606) | Android TV 9 |
| `tcl-beyondtv2-android-11/` | TCL BeyondTV2, Android 11 build (AR05, Realtek rtd285o) | Android TV 11 |
| `tcl-beyondtv2-latin-america/` | TCL BeyondTV2 (Latin America / Brazil, Realtek RTD2851) | Android TV 9 |
| `tcl-beyondtv4-uk/` | TCL Smart TV Pro, UK Freeview Play (BeyondTV4 / BeyondTV6, Realtek rtd288o, 2 GB, AR05) | Android TV 11 |
| `tcl-c635/` | TCL C635 (Smart TV Pro, BeyondTV4, Realtek rtd288o, Android 11/SDK 30), Brazil | Android TV 11 |
| `tcl-c755-google-tv/` | TCL C755 QD-Mini LED, 2024, Australia (G08, MediaTek tcl9618) | Google TV 14 |
| `tcl-c845x2-google-tv/` | TCL 75C845X2, UK/Europe (G08_4K_GB, MediaTek tcl9618) | Google TV 14 |
| `tcl-c89b-google-tv/` | TCL C89B 85", 2024, a local variant of the C855 (G08, G08_4K_GB, MediaTek tcl9618) | Google TV 14 |
| `tcl-g03-google-tv/` | TCL Smart TV Pro (G03, G03_4K_GB, BeyondTV4, Realtek R51MT05, V8-R51MT05-LF1V620), Middle East firmware | Google TV 11 |
| `tcl-g05-google-tv/` | TCL Smart TV Pro (G05, G05_4K_US, MediaTek MT5889); also TCL 55Q750G | Google TV 11 |
| `tcl-g06-google-tv-indonesia/` | TCL 40" Smart TV (G06, MediaTek MT9221 / T221, AR2101, V8-T221T02-LF1V058), Indonesia | Google TV 11 |
| `tcl-g07-google-tv/` | TCL Smart TV (G07, G07_4K_GB_NF, Realtek RTD2851A, 2 GB) | Google TV 11 |
| `tcl-g08-au-android12/` | TCL Smart TV Pro (G08, G08_4K_GB, MediaTek), Australian firmware | Google TV 12 |
| `tcl-g08-eu-android12/` | TCL Smart TV Pro (G08, G08_4K_GB, MediaTek), Europe/UK firmware | Android TV 12 |
| `tcl-g08-us-google-tv/` | TCL 75T7 (Smart TV Pro, G08, G08_4K_US, MediaTek tcl9618), US firmware | Google TV 14 |
| `tcl-g09-google-tv/` | TCL Smart TV Pro (G09, G09_4K_GB, Amlogic T5M, 2 GB), UK firmware, Brazil | Google TV 12 |
| `tcl-g10-google-tv/` | TCL 55" 4K Google TV, 2023 (G10, G10_4K_US_NF); also TCL 55P6K, Spain and TCL 43P69K, Netherlands (G10_4K_GB_NF_32BIT) | Google TV 12 |
| `tcl-l40s66e/` | TCL L40S66E (C06, C06_2K_GB, MediaTek MT9221, 1 GB), Argentina | Android TV 11 |
| `tcl-mt9655-cn-android14/` | TCL / FFalcon China (tcl9655_cn, MediaTek MT9655 "merak", UTT2.250416.001 AU01) | Android 14 |
| `tcl-q10g-pro/` | TCL 55Q10G Pro (China, tcl_mt5879_cn, MediaTek MT9952) | Android TV 11 |
| `tcl-q7c-google-tv/` | TCL Q7C (Smart TV Pro, G08, G08_4K_GB, MediaTek tcl9618), European firmware | Google TV 14 |
| `tcl-qm6k-google-tv/` | TCL QM6K Pro, 2025 (G08, MediaTek tcl9618) | Google TV 14 |
| `tcl-tc8000-ay30a2/` | TCL tc8000_ay30a2 (China, Amlogic ohm, TC8000, 3.8 GB) | Android 11 |
| `tcl-uniontv-latin-america/` | TCL UnionTV, Latin America (TCL-built like Thomson 32HE5800, Realtek RT2841) | Android TV 9 |
| `telesystem-ont2hd/` | Tele System TS ON T2/HD (ONT2HD, SDMC, Amlogic), Italy | Android TV 11 |
| `thomson-32he5800/` | Thomson 32HE5800 (TCL-built UnionTV, Realtek RT2841), Spain | Android TV 9 |
| `thomson-43rt1055/` | Thomson 43RT1055 FA Series (SPPL-built, sauve, Realtek RTD2841A), India | Android TV 14 |
| `thomson-sppl-4k-sw6h/` | Thomson SPPL 4K Google TV (Skyworth SW6H, MediaTek, same board as `skyworth-sw-22sa/`), India, ~1.9 GB | Android TV 14 (Google TV home) |
| `thomson-t32rtl6000/` | Thomson T32RTL6000 (SCBC, Realtek; Android reports model `ATV R1`) | Android TV 9 |
| `tivo-stream-4k/` | TiVo Stream 4K (SEI Robotics IPA1104HDW, SEI400TV, Amlogic S905Y2, NES) | Android TV 12 |
| `tornado-65ua1400e/` | Tornado 65UA1400E (El Araby, Skyworth-built SW4H, MediaTek m7332), Egypt | Android TV 11 |
| `toshiba-65ua2363dg/` | Toshiba 65UA2363DG (Vestel-built, BODRUM_Toshiba, finch, MediaTek m7332), France | Android TV 14 |
| `toshiba-cosmos-android-tv/` | Toshiba (Vestel Cosmos170, yeoksam, MediaTek), Spain | Android TV 11 |
| `toshiba-fire-tv-aftha004/` | Toshiba Fire TV (AFTHA004, hazel) | Fire OS 7 / Android 9 |
| `turkcell-tv-plus-4k-box-dv6069y/` | Turkcell TV+ 4K Box (SDMC DV6069Y, Amlogic), Turkey, operator streaming box | Android TV 10 |
| `tuvio-td43ufbcv5/` | Tuvio TD43UFBCV5 (Changhong AI PONT, DVB96, MediaTek m7632), Russia | Google TV 14 |
| `verizon-stream-tv/` | Verizon Stream TV, 1st gen (WNC JS8V, Amlogic), US | Android 12 |
| `vidda-c1s-mt9669/` | Vidda C1S 4K laser projector (VIDAA_TV, device MT9669, board m7642), China JuUI | Android 9 |
| `vidda-mt9653/` | Vidda VIDAA_TV (Hisense sub-brand; device MT9653, board mt5879), China JuUI | Android TV 11 |
| `vision-2024vision43stv/` | Vision 2024VISION43STV (CVTE TP.T950D4T.PB751, Amlogic T950D4, 1 GB), Europe | Android 13 |
| `vu-4k-lushan/` | Vu 4K (HVCL, lushan, MediaTek), India | Google TV 12 |
| `vu-premium-fhd-43-hamamatsucho/` | Vu Premium FHD 43-inch (Smart 2K ATV4, hamamatsucho), India | Android TV 9 |
| `vu-vibe-tv/` | Vu VIBE TV (KONKA, bandra, Realtek), India | Google TV 14 |
| `wanbo-x5-pro/` | Wanbo X5 Pro projector (built-in Google TV stick: SkyworthDigital YYT, Amlogic), Australia | Google TV 14 |
| `xgimi-horizon-s-pro/` | XGIMI Horizon S Pro projector (XK03H, xgimigalileo, MediaTek) | Android TV 11 |
| `xgimi-mogo-2/` | XGIMI MoGo 2 projector (MoGo, xgimiapollo, pokemon) | Android TV 11 |
| `xgimi-mogo-3-pro/` | XGIMI MoGo 3 Pro projector (G0035, altai, MediaTek) | Google TV 14 |
| `xgimi-mogo-4/` | XGIMI MoGo 4 projector (WK08K, altai, MediaTek) | Google TV 14 |
| `xgimi-mogo-4-laser/` | XGIMI MoGo 4 Laser projector (WK03K, altai, MediaTek) | Google TV 14 |
| `xiaomi-f2-fire-tv-aftma2857f/` | Xiaomi F2 Fire TV (AFTMA2857F, shine) | Fire OS 7.7.1.7 / Android 9 |
| `xiaomi-mi-box-3/` | Xiaomi Mi Box 3 (MIBOX3, once), streaming box | Android TV 9 |
| `xiaomi-mi-box-s/` | Xiaomi Mi Box S (MIBOX4, oneday), streaming box | Android TV 9 |
| `xiaomi-mi-box-s-2nd-gen/` | Xiaomi Mi Box S 2nd gen (MiTV-AFKR0, jaws), streaming box | Google TV 11 |
| `xiaomi-mi-box-s-android12/` | Xiaomi Mi Box S (MIBOX4, oneday), streaming box, Android 12 update | Android TV 12 |
| `xiaomi-mi-tv-4a/` | Xiaomi Mi TV 4A (MiTV-MOOQ1) | Android TV 10 |
| `xiaomi-mitv-aesp0/` | Xiaomi MiTV-AESP0 stick (aquaman, 1 GB) | Android TV 9 |
| `xiaomi-mitv-afmu0/` | Xiaomi TV Box S 3rd gen (MiTV-AFMU0, twilight, Amlogic), streaming box | Google TV 14 |
| `xiaomi-mitv-afmu1/` | Xiaomi TV Stick 4K 2nd gen (MiTV-AFMU1, twilight, adastra, Amlogic), streaming stick | Google TV 14 |
| `xiaomi-mitv-ansp0/` | Xiaomi MiTV-ANSP0 (小米电视5 Pro 65", jobs, Amlogic), China firmware 23.6.5.3226, 4 GB RAM | Android TV 9 (CN build) |
| `xiaomi-mitv-astp0/` | Xiaomi MiTV-ASTP0 (mulan, L55M7-EA), China firmware 22.12.2.1630, 1.5 GB RAM, 1080p | Android TV 9 (CN build) |
| `xiaomi-mitv-astp1/` | Xiaomi MiTV-ASTP1 (maverick), China firmware 2.40.641.0514.cn, 1 GB RAM, 720p | Android TV 9 (CN build) |
| `xiaomi-mitv-astr0/` | Xiaomi Redmi Smart TV A Pro 55" 2025 Energy-saving (MiTV-ASTR0, blueplanet, Amlogic), China firmware OS3.0.106.0.USTAATV, 3 GB RAM | Android 14 (HyperOS 3, CN build) |
| `xiaomi-mitv-axfr0/` | Xiaomi MiTV-AXFR0 (rango, Amlogic, 1 GB), India | Android TV 11 |
| `xiaomi-mitv-axfr1/` | Xiaomi MiTV-AXFR1 (rango, Amlogic, 1 GB), India | Android TV 11 |
| `xiaomi-mitv-axso0/` | Xiaomi MiTV-AXSO0 (dangal; Mi TV 4C Pro 2019 / 4A Pro) | Android TV 9 |
| `xiaomi-mitv-axso2/` | Xiaomi MiTV-AXSO2 (dangalUHD, India, 2 GB) | Android TV 9 |
| `xiaomi-mitv-ayfr0/` | Xiaomi TV Stick 4K (MiTV-AYFR0, soul, Amlogic), streaming stick | Android TV 14 |
| `xiaomi-mitv-mffu0/` | Xiaomi TV S Pro 100 Mini LED 2025 (小米电视 S Pro 100, L100MB-SP, MiTV-MFFU0, finch, MediaTek), China firmware OS3.0.115.0.UFFMATV, 4 GB RAM | Android 14 (HyperOS 3, CN build) |
| `xiaomi-mitv-mftr0/` | Xiaomi MiTV-MFTR0 (freeguy, MediaTek mt9653), China firmware OS2.0.23.0.RFTMATV (HyperOS TV 2) | Android 11 (HyperOS TV 2, CN build) |
| `xiaomi-mitv-moeu0/` | Xiaomi TV S Mini LED 75 2025 (MiTV-MOEU0, ladybird, MediaTek) | Google TV 14 |
| `xiaomi-mitv-mooq0/` | Xiaomi Mi TV (MiTV-MOOQ0, croods) | Android TV 10 |
| `xiaomi-mitv-mooq2/` | Xiaomi Mi TV P1 43" (MiTV-MOOQ2, croods) | Android TV 11 |
| `xiaomi-mitv-moor0/` | Xiaomi MiTV-MOOR0 (venom, MediaTek) | Google TV 11 |
| `xiaomi-mitv-moor2/` | Xiaomi MiTV-MOOR2 / MOOR4 (venom, MediaTek) | Google TV 11 |
| `xiaomi-mitv-mosr4/` | Xiaomi MiTV-MOSR4 (irobot, MediaTek) | Google TV 11 |
| `xiaomi-mitv-mssp0/` | Xiaomi MiTV-MSSP0 / Xiaomi Mi TV 4A 32 (amelie), firmware PI.5926 | Android TV 9 |
| `xiaomi-mitv-mssp1/` | Xiaomi MiTV-MSSP1 (nino), firmware PI.5925 | Android TV 9 |
| `xiaomi-mitv-mssp2/` | Xiaomi MiTV-MSSP2 (volver, MediaTek), firmware PI.4621, low-RAM | Android TV 9 |
| `xiaomi-mitv-mssp3/` | Xiaomi MiTV-MSSP3 (machuca, MediaTek) | Android TV 11 |
| `xiaomi-mitv-mssp3-android9/` | Xiaomi MiTV-MSSP3 (machuca, MediaTek), Android 9 firmware PI.7524 | Android TV 9 |
| `xiaomi-mitv-mteq0/` | Xiaomi TV EA Pro 55" (MiTV-MTEQ0, baymax, MediaTek), China firmware 23.6.26.4028 | Android TV 10 (CN build) |
| `xiaomi-tv-a-pro-2026/` | Xiaomi TV A Pro 75" 2026 (MiTV-MZTU0, river, MediaTek) | Google TV 14 |
| `xiaomi-tv-a43-2025/` | Xiaomi TV A43 2025 (MiTV-MOOQ1) | Google TV 11 |

Models without a tested list: [`docs/devices/`](../../docs/devices/).
