-- 1️⃣ Tạo database (nếu chưa có)
CREATE DATABASE TicketSystem;
GO

-- 2️⃣ Sử dụng database đó
USE TicketSystem;
GO

-- 3️⃣ Tạo bảng User (vì "User" là từ khóa hệ thống, ta nên đặt trong ngoặc vuông)
CREATE TABLE [User] (
    userID INT IDENTITY(1,1) PRIMARY KEY,       -- Tự động tăng
    userName NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) UNIQUE NOT NULL,
    password NVARCHAR(100) NOT NULL,
    role NVARCHAR(50) NULL                      -- Lưu vai trò (Admin, Staff, Customer,...)
);
GO

-- 4️⃣ (Tuỳ chọn) Thêm vài dữ liệu mẫu
INSERT INTO [User] (userName, email, password, role)
VALUES 
(N'Nguyễn Thành Long', N'long@gmail.com', N'23723141', N'QUANLY'),
(N'test', N'test@gmail.com', N'11111111', N'NHANVIEN'),
(N'Trần Thế Hào', N'hao@gmail.com', N'23651371', N'QUANLY'),
(N'Nguyễn Nam Trung Nguyên', N'nguyen@gmail.com', N'23640731', N'QUANLY'),
(N'Trần QUốc Anh', N'anh@gmail.com', N'23710221', N'QUANLY');
GO


select * from [User]