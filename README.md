![bbe5eb61b43c3862612d](https://github.com/user-attachments/assets/23914b63-fd7c-4e8d-a711-1217e62d1c44)🧱 Arkanoid Game (Java)

Một phiên bản mở rộng của trò chơi Arkanoid / Breakout, được viết bằng Java Swing, với nhiều loại gạch, hiệu ứng nổ, power-up, hệ thống lưu game, và quản lý màn chơi.
Link Demo Game: https://drive.google.com/file/d/1asiq9hjAnrbcnkupkiAWYKsfKtzLHB4l/view?usp=sharing

🎮 Tính năng chính
🧩 Hệ thống gameplay

Paddle di chuyển mượt mà bằng phím ← và →.

Bóng bật theo góc tùy vị trí va chạm.

Hiệu ứng va chạm vật lý và ngăn bóng chỉ di chuyển ngang hoặc dọc.

Nhiều loại Brick:

🟦 NormalBrick: gạch cơ bản, phá 1 hit.

🟩 PowerUpBrick: rơi power-up khi bị phá.

🟥 BonusBrick: cho điểm cao hơn. Có nhiều loại Bonus Brick khác nhau

💣 ExplosionBrick: phát nổ và phá gạch xung quanh.

🟫 UnBreakBrick: không thể bị phá.

⚡ Power-ups

Khi phá PowerUpBrick, sẽ rơi ngẫu nhiên:

🧱 Expand Paddle – mở rộng paddle.

🔥 Fast Ball – tăng tốc bóng.

⚪ Multi Ball – nhân đôi bóng.

Power-up sẽ kích hoạt khi va chạm với paddle.

💾 Hệ thống lưu game

Có thể lưu game đang chơi.

Lần mở sau, nếu có dữ liệu lưu, sẽ hiển thị nút Continue Game trong MenuPanel.

Khi chọn Continue, game sẽ tải lại y nguyên trạng thái trước đó (gạch, bóng, powerup, điểm...).

🕹️ Điều khiển
Hành động	Phím
Di chuyển sang trái	←
Di chuyển sang phải	→
Bắt đầu thả bóng	SPACE
Tạm dừng / Hiện menu pause	ESC
Tiếp tục	Chọn "Continue" trong Pause Menu
🧱 Hệ thống Level

Mỗi level (Level1, Level2, Level3, …) có bố cục gạch và tỉ lệ xuất hiện power-up khác nhau.

Khi phá hết các gạch có thể phá, game sẽ tự chuyển qua level kế tiếp.

Gạch UnBreakBrick không ảnh hưởng đến điều kiện thắng.


🎨 Giao diện & hiệu ứng

Hình ảnh và âm thanh được load từ thư mục ArkanoidGame/assets/.

Font chữ pixel được load từ font.ttf.

Có hiệu ứng mờ, vụn, nổ và alpha blending.

Giao diện menu:

Start Game

Continue Game (nếu có save)

Exit

🧩 Cấu trúc dự án

![Uploading bbe5eb61b43c3862612d.jpg…]()

🔧 Cách chạy game
✅ Yêu cầu:

JDK 17 trở lên

IDE: IntelliJ / VSCode / Eclipse

🚀 Chạy:

Mở project trong IDE.

Chạy file main.java.

Chọn:

Start Game để bắt đầu mới.

Continue Game nếu đã lưu trước đó.

🧠 Ghi chú kỹ thuật

Game chạy ở 60 FPS với javax.swing.Timer.

Sử dụng double buffering để tránh giật hình.

GameManager quản lý toàn bộ vòng đời game, Renderer chỉ vẽ và xử lý phím.

Hệ thống pause hoạt động qua GlassPane trong MenuManager.

📁 Lưu game

Dữ liệu được lưu vào:

ArkanoidGame/data/savegame.dat


Nội dung gồm:

Level hiện tại

Điểm, mạng

Vị trí paddle

Các bóng (vị trí, vận tốc)

Các gạch (loại, trạng thái)

Power-up còn rơi

🧨 Hiệu ứng nổ (ExplosionEffect)

Khi ExplosionBrick bị phá:

Gây nổ (hiệu ứng vòng tròn nở dần).

Các gạch xung quanh bán kính nhất định bị phá theo.

Hiệu ứng chỉ tồn tại trong thời gian ngắn (500ms).
