전자정부프레임워크 3.9.0
Open JDK 1.8.0
postgrsql


-------------------
게시판 제목, 내용에 게시판 id를 추가해 insert
INSERT INTO board1 
(title, "content", view_cnt, rdate, user_id, use_yn)
VALUES 
('제목' || (SELECT max(substring(title, 3, 4)::Integer) + 1 FROM board.board1), '내용' || (SELECT max(substring(title, 3, 4)::Integer) + 1 FROM board.board1), 0, NOW(), 'admin', 'Y')
