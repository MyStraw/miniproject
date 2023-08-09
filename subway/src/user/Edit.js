import React, { useState, useEffect } from 'react';
import userst from './User.module.css';
import { useParams } from "react-router-dom";
import { Helmet } from 'react-helmet';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Edit = () => {
    const id = useParams().id;
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const navigate = useNavigate();
    const goHome = () => navigate('/');
    useEffect(() => {
        axios.get(`http://10.125.121.185:8080/board/find/${id}`).then((response) => {
            setTitle(response.data.title);
            setContent(response.data.content);
        }).catch((error) => console.log(error));
    }, [id]);
    const submit = () => {
        axios.put(`http://10.125.121.185:8080/board/update/${id}`, {'title': title, 'content': content}, {headers: {Authorization: localStorage.getItem('accesstoken'), 'Content-Type': 'application/json'}})
        .then((response) => {
            goHome();
        }).catch((error) => {
            alert('수정 실패');
        });
    }
    return (
        <div className={userst.center}>
            <Helmet><title>부산 지하철 | 게시글 수정</title></Helmet>
            <label htmlFor='title'>제목</label>
            <input type='text' id='title' value={title} onChange={e => setTitle(e.target.value)}></input>
            <label htmlFor='content'>내용</label>
            <input type='text' id='content' value={content} onChange={e => setContent(e.target.value)}></input>
            <button onClick={submit}>글쓰기</button>
        </div>
    )
}

export default Edit