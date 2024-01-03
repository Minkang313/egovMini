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

<div style="height: 30px"></div>

<button onclick="logout()">로그아웃</button>

<div style="height: 30px"></div>

<button onclick="checkSession()">세션 확인</button>

<script>
    /**
     * 일반 세션 로그인 시도
     */
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
                let resultCode = data.resultCode;
                let msg = data.msg;

                alert(msg);
            })
            .catch(error => console.log(error));
    }

    /**
     * 세션 확인
     */
    async function checkSession() {
        let url = '/checkSession.do';
        try {
            let response = await fetch(url);
            let data = await response.json();
            alert("로그인 아이디: " + data.loginId);
        } catch (error) {
            console.log(error);
        }
    }

    /**
     * 로그아웃
     */
    const logout = async () => {
        let url = '/logout.do'
        try {
            let response = await fetch(url);
            let data = await response.json();
        } catch (error) {
            console.log(error)
        }
    }
</script>
</body>
</html>
