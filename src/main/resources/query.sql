## 앨범 테이블 이미지 Insert
use photo_album;

SELECT * from album;
INSERT INTO photo_album.album
(`album_id`, `created_at`, `album_name`)
VALUES
(1, CURRENT_TIMESTAMP, '앨범 1'),
(2, CURRENT_TIMESTAMP, '앨범 2'),
(3, CURRENT_TIMESTAMP, '앨범 3'),
(4, CURRENT_TIMESTAMP, '앨범 4'),
(5, CURRENT_TIMESTAMP, '앨범 5');


## 사진 테이블 이미지 Insert
INSERT INTO photo_album.photo
(`photo_id`, `file_name`, `file_size`, `thumb_url`, `uploaded_at`, `original_url`, `album_id`)
VALUES
(1, 'Arrow.png', 400717, '/photos/thumb/1/Arrow.png', CURRENT_TIMESTAMP, '/photos/original/1/Arrow.png', 1),
(2, 'Baylor.png', 305686, '/photos/thumb/1/Baylor.png', CURRENT_TIMESTAMP, '/photos/original/1/Baylor.png', 1),
(3, 'Breslin.png', 314907, '/photos/thumb/1/Breslin.png', CURRENT_TIMESTAMP, '/photos/original/1/Breslin.png', 1),
(4, 'Charleston.png', 277109, '/photos/thumb/1/Charleston.png', CURRENT_TIMESTAMP, '/photos/original/1/Charleston.png', 1),
(5, 'Everest.png', 369800, '/photos/thumb/2/Everest.png', CURRENT_TIMESTAMP, '/photos/original/2/Everest.png', 2),
(6, 'Farren.png', 364446, '/photos/thumb/2/Farren.png', CURRENT_TIMESTAMP, '/photos/original/2/Farren.png', 2),
(7, 'Gianni.png', 336036, '/photos/thumb/2/Gianni.png', CURRENT_TIMESTAMP, '/photos/original/2/Gianni.png', 2),
(8, 'Jess.png', 305628, '/photos/thumb/2/Jess.png', CURRENT_TIMESTAMP, '/photos/original/2/Jess.png', 2),
(9, 'Jo.png', 378612, '/photos/thumb/3/Jo.png', CURRENT_TIMESTAMP, '/photos/original/3/Jo.png', 3),
(10, 'Kendall.png', 315378, '/photos/thumb/3/Kendall.png', CURRENT_TIMESTAMP, '/photos/original/3/Kendall.png', 3),
(11, 'Landyn.png', 365262, '/photos/thumb/3/Landyn.png', CURRENT_TIMESTAMP, '/photos/original/3/Landyn.png', 3),
(12, 'Lennon.png', 358884, '/photos/thumb/3/Lennon.png', CURRENT_TIMESTAMP, '/photos/original/3/Lennon.png', 3),
(13, 'Mark.png', 361843, '/photos/thumb/4/Mark.png', CURRENT_TIMESTAMP, '/photos/original/4/Mark.png', 4),
(14, 'Memphis.png', 433684, '/photos/thumb/4/Memphis.png', CURRENT_TIMESTAMP, '/photos/original/4/Memphis.png', 4),
(15, 'Parker.png', 364974, '/photos/thumb/4/Parker.png', CURRENT_TIMESTAMP, '/photos/original/4/Parker.png', 4),
(16, 'Pat.png', 328237, '/photos/thumb/4/Pat.png', CURRENT_TIMESTAMP, '/photos/original/4/Pat.png', 4),
(17, 'Phoenix.png', 350404, '/photos/thumb/5/Phoenix.png', CURRENT_TIMESTAMP, '/photos/original/5/Phoenix.png', 5),
(18, 'Skyler.png', 253654, '/photos/thumb/5/Skyler.png', CURRENT_TIMESTAMP, '/photos/original/5/Skyler.png', 5),
(19, 'Tandy.png', 394117, '/photos/thumb/5/Tandy.png', CURRENT_TIMESTAMP, '/photos/original/5/Tandy.png', 5),
(20, 'Tennyson.png', 278830, '/photos/thumb/5/Tennyson.png', CURRENT_TIMESTAMP, '/photos/original/5/Tennyson.png', 5),
(21, 'Tory.png', 314714, '/photos/thumb/5/Tory.png', CURRENT_TIMESTAMP, '/photos/original/5/Tory.png', 5);