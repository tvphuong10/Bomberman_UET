# Bài tập lớn làm game Bomberman

Game được mô phỏng theo trò trơi bomberman

<img src="src\main\resources\Animations\Menu1.png" alt="drawing" width="400"/>

**Màn hình** Có 2 chế độ **người chơi** hoặc **Bomber tự chơi**

<img src="src\main\resources\Img\Demo.png" alt="drawing" width="400"/>

luật chơi:
- Game sẽ đc chia thành nhiều tầng (level)
- Mỗi tầng đc tạo từ nhiều phòng xuất hiện ngẫu nhiên bao gồm:
    một phòng khởi đầu
    một cửa hàng (nơi trao đổi hàng hóa)
    các phòng chưa quái vật được lấy ngẫu nhiên
    một phòng chứa cổng lên level tiếp theo
- Người chơi bắt đầu từ phòng khởi đầu tìm đến phòng chứa cổng để đến level tiếp theo với độ khó cao hơn.
- Vũ khí có thẻ sử dụng là bomb nổ theo chiều dọc sức mạnh và số lượng của quả bomb có thể tăng thêm bằng việc thu thập các Item
- Giết quái vật để thu thập tiền sử dụng tiền để trao đổi trong cửa hàng
- Mỗi khi va chạm với quái vật (trừ quái băng) nhân vật sẽ bị mất 1 mạng nếu số mạng về 0 ...bạn thua.

Các loại quái:
- ![](https://github.com/tvphuong10/Bomberman_UET/blob/main/src/main/resources/Animations/Bat1.png) **dơi** di chuyển ngẫu nhiên  
- ![](https://github.com/tvphuong10/Bomberman_UET/blob/main/src/main/resources/Animations/Ghost1.png) **ma** đuổi theo người chơi 
- ![](https://github.com/tvphuong10/Bomberman_UET/blob/main/src/main/resources/Animations/Iceboss1.png) **băng** di chuyển xuyên vật thể. Chỉ đóng băng người chơi khi va chạm. không thể bị tiêu diệt.
- ![](https://github.com/tvphuong10/Bomberman_UET/blob/main/src/main/resources/Animations/Slime1.png) **Chất dẻo** di chuyển ngẫu nhiên. Mỗi khi bị tiêu diệt sẽ tách làm đôi (tối đa 2 lần) đồng thời tăng tốc độ.

Cây kế thừa chính:
<img src="src\main\resources\Img\Demo2.png" alt="drawing" width="900"/>
trong đó:
- Character.java là lớp trừu tượng cho những vật thể có thể chuyển động: Quái, người chơi, AI.
- Room.java có công việc quản lý những vật thể bất động: lửa, bomb, tường, thùng, ....
  Room hiển thị theo 4 lớp (layer): Nền; Các vật thể, quái vật và người chơi; Hiệu ứng chói sáng; Giao diện Shop, Pause, Menu,...
- Level quản lý các và hiển thị các Room, khả năng chuyển phòng của nhân vật,...

