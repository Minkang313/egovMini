<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-12-28
  Time: 오후 9:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <title>Title</title>
    <style>
        tr, th, td, table{
            border: black solid 1px;
        }
        .hidden {
            display: none;
        }
    </style>
</head>
<body>
    <div id="boardListDiv">
        <table>
            <thead>
                <tr>
                    <th>No</th>
                    <th>제목</th>
                    <th>작성자</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${result.list}" var="board">
                    <tr onclick="showBoardDetail(${board.id})">
                        <td>${board.id}</td>
                        <td>${board.title}</td>
                        <td>${board.user_id}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <button onclick="showAddBoard()">추가</button>
    </div>
    <div id="boardDetailDiv" class="hidden">
        <table>
            <tbody id="boardDetailTbody">

            </tbody>
        </table>
        <button onclick="location.href='/test2/board.do'">목록</button>
        <button onclick="showModifyBoard()">수정</button>
        <button onclick="deleteBoard()">삭제</button>
    </div>
    <div id="addBoardDiv" class="hidden">
        <table>
            <tbody id="addboardTbody">
                <tr>
                    <td>제목</td>
                    <td><input type="text" id="addTitle"></td>
                </tr>
                <tr>
                    <td>내용</td>
                    <td><input type="text" id="addContent"></td>
                </tr>
            </tbody>
        </table>
        <button onclick="addboard()">추가</button>
    </div>

    <div id="updateboardDiv" class="hidden">
        <table>
            <tbody id="updateboardTbody">

            </tbody>
        </table>
        <button onclick="updateboard()">수정</button>
    </div>

<script>
    const init = () => {
        $('#boardListDiv').addClass('hidden');
        $('#boardDetailDiv').addClass('hidden');
        $('#addBoardDiv').addClass('hidden');
        $('#updateboardDiv').addClass('hidden');

        $('#boardDetailTbody').html('');
        $('#updateboardTbody').html('');

        $('#addTitle').val('');
        $('#addContent').val('');
    }

    const showBoardDetail = (id) => {
        $.ajax({
            url: "/test2/getBoardDetail.do",
            method: "Post",
            data: {'id': id},
            success: (res) => {
                let html = '';
                html += `<tr>`;
                html += `<td>아이디</td>`;
                html += `<td id="boardDetailId">\${res.id}</td>`;
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
                html += `<td>제목</td>`;
                html += `<td>\${res.title}</td>`;
                html += `</tr>`;
                html += `<tr>`;
                html += `<td>내용</td>`;
                html += `<td>\${res.content}</td>`;
                html += `</tr>`;

                init();
                $('#boardDetailTbody').html(html);
                $('#boardDetailDiv').removeClass('hidden');
            }
        })
    }

    const showAddBoard = () => {
        init();
        $('#addBoardDiv').removeClass('hidden');
    }

    const addboard = () => {
        let title = $('#addTitle').val();
        let content = $('#addContent').val();

        let param = {
            'title': title,
            'content': content
        }

        $.ajax({
            data: param,
            url: "/test2/addboard.do",
            method: "Post",
            success: (res) => {
                alert('추가되었습니다.');
                init();
                location.href="/test2/board.do";
            }
        })
    }

    const showModifyBoard = () => {
        let id = $('#boardDetailId').text();

        $.ajax({
            url: "/test2/getBoardDetail.do",
            method: "Post",
            data: {'id': id},
            success: (res) => {

                let html = '';
                html += `<tr>`;
                html += `<td>No</td>`;
                html += `<td id="boardUpdateId">\${res.id}</td>`;
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
                html += `<td>제목</td>`;
                html += `<td><input type="text" value="\${res.title}" id="boardUpdateTitle"></td>`;
                html += `</tr>`;
                html += `<tr>`;
                html += `<td>내용</td>`;
                html += `<td><input type="text" value="\${res.content}" id="boardUpdateContent"></td>`;
                html += `</tr>`;

                init();
                $('#updateboardTbody').append(html);
                $('#updateboardDiv').removeClass('hidden');
            }
        })
    }

    const updateboard = () => {
        let id = $('#boardUpdateId').text();
        let title = $('#boardUpdateTitle').val();
        let content = $('#boardUpdateContent').val();

        let param = {
            'id': id,
            'title': title,
            'content': content
        }

        $.ajax({
            data: param,
            method: "Post",
            url: "/test2/updateBoard.do",
            success: (res) => {
                alert('수정되었습니다.');
                showBoardDetail(id);
            }
        })
    }

    const deleteBoard = () => {
        if (confirm('삭제하시겠습니까?')) {
            let id = $('#boardDetailId').text();

            $.ajax({
                url: "/test2/deleteBoard.do",
                method: "Post",
                data: {'id': id},
                success: (res) => {
                    if (res > 0) {
                        alert('삭제되었습니다.');
                        init();
                        location.href = "/test2/board.do";
                    }
                }
            })
        }
    }
</script>
</body>
</html>
