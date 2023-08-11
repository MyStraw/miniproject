import React from 'react';
import { Helmet } from 'react-helmet';
import axios from 'axios';
import styled from 'styled-components';
import { useState, useEffect } from 'react';
import Accordian from '../modal/Accordian';
import { Link } from 'react-router-dom';

const InfoText = styled.span`
    padding: 0.2rem 0.5rem;
    border-radius: 0.5rem;
    border: 0.12vmax solid #00000099;
    background: radial-gradient(white, ${props => props.$color} 50%);
    font-weight: bold;
    font-size: 0.9rem;
    display: inline-block;
    margin: 3% 0 2%;
`;

const Container = styled.div`
    width: max-content;
    height: 84%;
    position: fixed;
    top: 50%;
    left: 50%;
    padding: 2% 5%;
    transform: translate(-50%, -50%);
    overflow-y: auto;
    scrollbar-width: none;
    -ms-overflow-style: none;
    &::-webkit-scrollbar {
        display: none;
    }
`;

const MyContent = () => {
    const [myCon, setContent] = useState(<></>);
    const [myHeart, setHeart] = useState(<></>);
    useEffect(() => {
        axios.get(`${process.env.REACT_APP_BASEURL}/board/myboards`, {headers: {Authorization: localStorage.getItem('accesstoken')}})
        .then((response) => {
            setContent(<Accordian list={response.data} />);
        }).catch((error) => console.log(error));
        axios.get(`${process.env.REACT_APP_BASEURL}/board/mylikedboards`, {headers: {Authorization: localStorage.getItem('accesstoken')}})
        .then((response) => {
            setHeart(<Accordian list={response.data} />);
        }).catch((error) => console.log(error));
    }, []);

    return (
        <>
            <Helmet><title>부산 지하철 | 마이페이지</title></Helmet>
            <Container>
                <InfoText $color='#ffcc33'>내가 쓴 글</InfoText>
                {myCon}
                <InfoText $color='#cc99ff'>내가 좋아요 누른 글</InfoText>
                {myHeart}
            </Container>
            <Link to='/' style={{position: 'fixed', left: 'calc(50% + 10rem)', top: '6%', borderRadius: '0.4rem', backgroundColor: 'white', padding: '0.1rem 0.4rem'}}>돌아가기</Link>
        </>
    )
}

export default MyContent;