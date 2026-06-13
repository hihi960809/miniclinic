# MiniClinic - 微型診所線上掛號系統

MiniClinic 是一個基於 Spring Boot 與 Thymeleaf 打造的輕量級線上醫療掛號與看診管理系統。區分為「病患前台」與「醫護後台」，提供直覺的線上預約掛號功能、病歷查看，以及醫生端即時的預約排程與狀態管理。

---

## 🌐 線上 Demo 網址
* **展示網址**：https://github.com/hihi960809/miniclinic

---

## 🛠️ 技術棧 (Technology Stack)

* **後端核心 (Backend)**：Spring Boot 3.x, Spring Data JPA, Java 17
* **前端網頁 (Frontend)**：Thymeleaf 模板引擎, HTML5, CSS3, JavaScript (Fetch API)
* **資料庫 (Database)**：H2 Database (本機開發) / PostgreSQL (雲端部署環境)
* **依賴管理 (Build Tool)**：Maven
* **雲端部署 (Deployment)**：Render

---

## 🔐 系統預設測試帳密

為了方便評分與功能測試，系統內建以下測試資料：

### 🧑‍⚕️ 醫生後台登入
* **登入網址**：`/login`
* **測試帳號 1**：`D001` / **密碼**：`pass1234`
* **測試帳號 2**：`D005` / **密碼**：`pass1234`

### 🩺 病患前台測試資料（掛號範例）
* **測試病歷號**：`TEST00001` (可在線上掛號頁面直接輸入進行預約測試)

---

## 💻 本機執行步驟 (Local Setup Guide)

請確保您的開發環境已安裝 **Java 17 (或更高版本)** 以及 **Maven**。

### 1. 複製專案
```bash
git clone [https://github.com/hihi960809/miniclinic.git](https://github.com/hihi960809/miniclinic.git)
cd miniclinic