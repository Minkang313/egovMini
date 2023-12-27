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
        }
        #titleTh{
            width: 300px;
        }
        .TitleTd{
            cursor: pointer;
        }
        .hidden {
            display: none;
        }
    </style>
</head>
<body>
    <h1>게시판 연습1</h1>
    <div id="boardDiv">
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

    <div id="boardDetailDiv" class="hidden">
        <table id="boardDetailTb">

        </table>
    </div>

<script>
    const init = () => {
        $('#boardDiv').addClass('hidden');
        $('#boardDetailDiv').addClass('hidden');
        $('#boardDetailTb').html('');
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
                html += `<td>\${res.id}</td>`;
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
                html += `<td>내용</td>`;
                html += `<td>\${res.content}</td>`;
                html += `</tr>`;

                init();
                $('#boardDetailTb').append(html);
                $('#boardDetailDiv').removeClass('hidden');
            }
        })
    }
</script>
</body>
</html>
