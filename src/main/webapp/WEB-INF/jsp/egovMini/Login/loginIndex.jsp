<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<input type="text" placeholder="아이디" id="username">
<input type="password" placeholder="비밀번호" id="password">
<div style="height: 5px"></div>
<button onclick="clickSessionLogin()">세션 로그인</button>

<script>
    const clickSessionLogin = () => {
        let username = document.getElementById('username').value
        let password = document.getElementById('password').value
        let url = '/sessionLogin.do'
        param = {
            'username': username,
            'password': password
        }

        fetch(url, {
            method: "Post",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(param)
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
            })
    }
</script>
</body>
</html>
