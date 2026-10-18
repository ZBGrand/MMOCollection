LimbusSkills - Tanglecleaver (Meursault, Limbus Company) cho MMOItems
Viết cho MythicLib 1.7.1 (jar đã để sẵn trong libs/MythicLib.jar)
====================================================================

BUILD RA .JAR
1. Cài JDK 21+ và Maven (Minecraft 1.21.11 chạy trên Java 21).
2. pom.xml đã đặt sẵn spigot.version = 1.21.11-R0.1-SNAPSHOT.
3. Chạy trong thư mục này:   mvn clean package
4. Lấy target/LimbusSkills-1.0.jar bỏ vào plugins/, restart server.

DÙNG TRONG GAME
1. /mmoitems edit <TYPE> <ID>  ->  Ability  ->  chọn TANGLECLEAVER
2. Chọn trigger (vd chuột phải) và chỉnh modifier:
     damage-per-coin = 5
     range           = 6
     tremor-damage   = 1.5
     burn-damage     = 1
     reload          = 8
     star-duration   = 10
   Cooldown gợi ý: 8-10 giây.

LƯU Ý
- plugin.yml có loadbefore: [MMOItems] để skill được đăng ký TRƯỚC khi MMOItems tải item.
