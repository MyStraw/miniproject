import React, { useState } from 'react';
import userst from './User.module.css';
import { useParams } from "react-router-dom";
import { Helmet } from 'react-helmet';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

const Button = styled.button`
    padding: 0.15rem 0.3rem;
    border-radius: 0.5rem;
    border: 0.12vmax solid #00000099;
    background: radial-gradient(white, #33ff66 50%);
    font-weight: bold;
    &:hover {
        background: radial-gradient(white, #33ff66 75%);
        border: 0.15vmax solid black;
    }
`;

const Write = () => {
    const code = useParams().code;
    const [selfile, setFile] = useState(null);
    const fileChange = (event) => {
        setFile(event.target.files[0]);
    };
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const navigate = useNavigate();
    const goHome = () => navigate('/');
    const submit = () => {
        const formData = new FormData();
        formData.append('board', JSON.stringify({'title': title, 'content': content}));
        formData.append('image', selfile);
        axios.post(`${process.env.REACT_APP_BASEURL}/board/create/${code}`, formData, {headers: {Authorization: localStorage.getItem('accesstoken'), 'Content-Type': 'multipart/form-data'}})
        .then((response) => {
            goHome();
        }).catch((error) => {
            alert('글쓰기 실패');
            goHome();
        });
    }
    return (
        <div className={userst.center} style={{marginTop: '-5%', border: '0.12vmax solid #00000099', borderRadius: '0.5rem'}}>
            <Helmet><title>부산 지하철 | 게시글 쓰기</title></Helmet>
            <div>
                <label htmlFor='title' className={userst.board}>제목</label>
                <input type='text' id='title' value={title} onChange={e => setTitle(e.target.value)} className={userst.board}></input>
            </div>
            <div style={{marginBottom: '3%'}}>
                <label htmlFor='content' className={userst.board}>내용</label>
                <textarea cols='50' rows='8' id='content' value={content} onChange={e => setContent(e.target.value)} className={userst.board}></textarea>
            </div>
            <input type="file" accept="image/*" onChange={e => fileChange(e)} style={{marginRight: '8rem'}} />
            <Button onClick={submit} style={{position: 'absolute', right: '3.2rem'}}>글쓰기</Button>
        </div>
    )
}

export default Write;