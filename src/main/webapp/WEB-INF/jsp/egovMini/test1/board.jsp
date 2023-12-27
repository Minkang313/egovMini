<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-12-26
  Time: 오후 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <title>게시판1</title>
    <style>
        tr, td, th, table {
            border: black solid 1px;
        }
        table{
            text-align: center;
            width: 400px;
        }
        #titleTh, .wideTd{
            width: 300px;
        }
        .TitleTd{
            cursor: pointer;
        }
        .hidden {
            display: none;
        }
        .container {
            width: 400px;
        }
        .listBtn, .saveBtn {
            position: relative;
            left: 200px;
        }
        .contentTd {
            height: 400px;
        }
        .addBoardBtn {
            position: relative;
            left: 355px
        }
        .cancelBtn {
            position: relative;
            left: -40px;
        }
        #boardAddDiv input, #boardAddDiv textarea{
            width: 100%;
            height: 100%;
            text-align: center;
        }
        .contentTd, textarea {
            white-space: pre-line;
        }
    </style>
</head>
<body>
    <h1>게시판 연습1</h1>
    <div class="container">
        <div id="boardDiv">
            <button onclick="loadAddBoard()" class="addBoardBtn">작성</button>
            <div style="height: 10px"></div>
            <table>
                <thead>
                <tr>
                    <th>No</th>
                    <th id="titleTh">제목</th>
                    <th>작성자</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${board1Map.board1List}" var="board">
                    <tr onclick="LoadBoardDetail(${board.id})">
                        <td>${board.id}</td>
                        <td class="TitleTd">${board.title}</td>
                        <td>${board.user_id}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <%--        게시판 상세      --%>
        <div id="boardDetailDiv" class="hidden">
            <table id="boardDetailTb">

            </table>
            <div style="height: 10px"></div>
            <button onclick="location.href='/test1/board.do'" class="listBtn">목록</button>
        </div>

        <%--        게시판 작성      --%>
        <div id="boardAddDiv" class="hidden">
            <table>
                <tr>
                    <td>작성자</td>
                    <td class="wideTd"><label for="addBoardUser"><input type="text" id="addBoardUser"></label></td>
                </tr>
                <tr>
                    <td>제목</td>
                    <td><label for="addBoardTitle"><input type="text" id="addBoardTitle"></label></td>
                </tr>
                <tr>
                    <td>내용</td>
                    <td class="contentTd"><label for="addBoardContent"><textarea id="addBoardContent"></textarea></label></td>
                </tr>
            </table>
            <div style="height: 10px"></div>
            <button class="saveBtn" onclick="saveAddBoard()">저장</button>
            <button onclick="location.href='/test1/board.do'" class="cancelBtn">취소</button>
        </div>



    </div>



<script>
    const init = () => {
        $('#boardDiv').addClass('hidden');
        $('#boardDetailDiv').addClass('hidden');
        $('#boardAddDiv').addClass('hidden');

        $('#boardDetailTb').html('');

        $('#addBoardUser').val('');
        $('#addBoardTitle').val('');
        $('#addBoardContent').val('');
    }

    /**
     * 게시판 상세조회
     * @param id
     * @constructor
     */
    const LoadBoardDetail = (id) => {
        $.ajax({
            url: "/test1/boardDetail.do",
            method: "POST",
            data: {'id': id},
            success: (res) => {
                let html = '';
                html += `<tr>`;
                html += `<td>No</td>`;
                html += `<td class="wideTd">\${res.id}</td>`;
                html += `</tr>`;
                html += `<tr>`;
                html += `<td>작성자</td>`;
                html += `<td>\${res.user_id}</td>`;
                html += `</tr>`;
                html += `<tr>`;
                html += `<td>작성일</td>`;
                html += `<td>\${res.rdate}</td>`;
                html += `</tr>`;
                html += `<tr>`;
                html += `<td>조회수</td>`;
                html += `<td>\${res.view_cnt}</td>`;
                html += `</tr>`;
                html += `<tr>`;
                html += `<td>제목</td>`;
                html += `<td>\${res.title}</td>`;
                html += `</tr>`;
                html += `<tr>`;
                html += `<td>내용</td>`;
                html += `<td class=contentTd>\${res.content}</td>`;
                html += `</tr>`;

                init();
                $('#boardDetailTb').append(html);
                $('#boardDetailDiv').removeClass('hidden');
            }
        })
    }

    /**
     * 게시판 작성 화면
     */
    const loadAddBoard = () => {
        init();
        $('#boardAddDiv').removeClass('hidden');
    }

    /**
     * 게시판 작성 저장
     */
    const saveAddBoard = () => {
        let addBoardUser = $('#addBoardUser').val();
        let addBoardTitle = $('#addBoardTitle').val();
        let addBoardContent = $('#addBoardContent').val();

        let param = {
            'user_id': addBoardUser,
            'title': addBoardTitle,
            'content': addBoardContent
        }

        $.ajax({
            data: param,
            url: "/test1/addBoard.do",
            method: "POST",
            success: (res) => {
                if (res == 1) {
                    alert('게시글이 등록되었습니다.');
                    location.href='/test1/board.do'
                } else {
                    alert('게시글 등록에 실패했습니다.');
                }
            }
        })
    }
</script>
</body>
</html>
