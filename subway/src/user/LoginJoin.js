import React from 'react';
import { useState } from 'react';
import userst from './User.module.css';
import style from './LoginJoin.module.css';
import styled from 'styled-components';
import { Helmet } from 'react-helmet';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Button = styled.button`
    width: 8rem;
    height: 2.4rem;
    padding: 0.3rem;
    margin: 0.4rem;
    border-radius: 0.6rem;
    font-size: 0.9rem;
    border: 0.12vmax solid #00000099;
    background: radial-gradient(white, ${props => props.$color} 50%);
    &:hover {
        background: radial-gradient(white, ${props => props.$color} 75%);
        border: 0.15vmax solid black;
        font-weight: bold;
    }
`;

const parseJWT = (token) => {
    try {
        // 토큰을 점(.)을 기준으로 나누어 페이로드 부분만 추출
        const payload = token.split('.')[1];
        
        // Base64Url 포맷을 Base64 포맷으로 변환
        const base64 = payload.replace('-', '+').replace('_', '/');
        
        // Base64 포맷의 페이로드를 디코딩
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        return JSON.parse(jsonPayload);
    } catch (e) {
        console.error("토큰 파싱 중 오류 발생:", e);
        return null;
    }
}

const LoginJoin = () => {
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const goHome = () => navigate('/');
    const login = () => {
        const member = { username: userId, password: password };
        axios.post(`${process.env.REACT_APP_BASEURL}/login`, member).then((response) => {
            if (response.data.message === '로그인 성공') {
                const accessToken = response.headers.getAuthorization();
                const parsedToken = parseJWT(accessToken);
                if (parsedToken) {
                    localStorage.setItem('userid', parsedToken.username);
                    localStorage.setItem('roles', JSON.stringify(parsedToken.roles));
                    localStorage.setItem('accesstoken', accessToken);
                }
                goHome();
            } else {
                alert('로그인 실패');
            }
        }).catch(() => {
            alert('로그인 실패');
            localStorage.removeItem('userid');
            localStorage.removeItem('accesstoken');
        });
    }
    const register = () => {
        const member = { username: userId, password: password };
        axios.post(`${process.env.REACT_APP_BASEURL}/register`, member).then((response) => {
            alert(response.data);
        }).catch(() => alert('회원가입 실패'));
    }

    return (
        <div className={userst.center} style={{border: '0.12vmax solid #00000099', borderRadius: '0.5rem', marginTop: '-10%'}}>
            <Helmet><title>부산 지하철 | 로그인</title></Helmet>
            <div className={style.input}>
                <label htmlFor="userId" className={style.lj}>아이디</label>
                <input type="text" id="userId" value={userId} onChange={e => setUserId(e.target.value)} className={style.lj} />
            </div>
            <div className={style.input}>
                <label htmlFor="password" className={style.lj}>비밀번호</label>
                <input type="password" id="password" value={password} onChange={e => setPassword(e.target.value)} className={style.lj} />
            </div>
            <div className={style.flex}>
                <Button onClick={login} className={style.lj} $color='#9966ff'>로그인</Button>
                <Button onClick={register} className={style.lj} $color='#33ff66'>회원가입</Button>
            </div>
        </div>
    )
}

export default LoginJoin;