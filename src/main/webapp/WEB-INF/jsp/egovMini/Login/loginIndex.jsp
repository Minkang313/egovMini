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
<button onclick="clickCookieLogin()">쿠키 로그인</button>

<div style="height: 30px"></div>

<button onclick="sessoionLogout()">세션 로그아웃</button>

<div style="height: 30px"></div>

<button onclick="checkSession()">세션 확인</button>
<button onclick="checkCookie()">쿠키 확인</button>

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
     * 세션 로그아웃
     */
    const sessoionLogout = async () => {
        let url = '/sessionLogout.do'
        try {
            let response = await fetch(url);
            let data = await response.json();
        } catch (error) {
            console.log(error)
        }
    }

    /**
     * 세션 로그인
     */
    const clickCookieLogin = () => {
        let username = document.getElementById('username').value
        let password = document.getElementById('password').value
        let url = '/cookieLogin.do'
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
                let loginId = data.loginId;
                if (resultCode === 'S') {
                    let date = new Date();
                    date.setTime(date.getTime() + (60 * 1000))
                    let expires = "expires=" + date.toUTCString();

                    document.cookie = `loginId=\${loginId};\${expires};path=/`;
                    alert('로그인 성공');
                }
            })
    }

    /**
     * 쿠키 로드
     */
    const checkCookie = () => {
        let cookieArr = document.cookie.split(';')

        for(let i = 0; i < cookieArr.length; i++){
            let cookiePair = cookieArr[i].split('=') // 쿠키 이름과 값으로 분리
            if (cookiePair[0].trim() === 'loginId') {
                alert('로그인 아이디: ' + cookiePair[1]);
            } else {
                alert('로그인 쿠키가 존재하지 않습니다.');
            }
        }
    }
</script>
</body>
</html>
