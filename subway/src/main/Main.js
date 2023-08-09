import React from 'react'
import style from './Main.module.css'
import styled from 'styled-components'
import Select from './Select'
import { Link } from 'react-router-dom'
import { Helmet } from 'react-helmet'
import { useState, useEffect } from 'react'

const Button = styled.button`
    padding: 0.15rem 0.3rem;
    border-radius: 0.5rem;
    border: 0.12vmax solid #00000099;
    background: radial-gradient(white, #ff3366 50%);
    &:hover {
        background: radial-gradient(white, #ff3366 75%);
        border: 0.15vmax solid black;
        font-weight: bold;
    }
`;

const Main = () => {
    const [userid, setUser] = useState(null);
    const logout = () => {
        localStorage.removeItem('userid');
        localStorage.removeItem('accesstoken');
        setUser(null);
    };
    useEffect(() => {
        setUser(localStorage.getItem('userid'));
    }, []);
    const info = <span className={style.info}>{userid}님 <Button onClick={logout}>로그아웃</Button></span>;
    return (
        <main>
            <Helmet><title>부산 지하철 정보</title></Helmet>
            <header><h1 className={style.center}>부산 지하철 정보</h1></header>
            {userid == null ? <Link to='/login' className={style.login}>로그인 / 회원가입</Link> : info}
            <Select></Select>
        </main>
    )
}

export default Main