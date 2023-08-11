import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { FiEdit, FiHeart } from 'react-icons/fi';
import { RiDeleteBin6Line } from 'react-icons/ri';
import { FaHeart } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import axios from 'axios';

const Div = styled.div`
    &:hover {
        color: #6600cc;
        font-weight: bold;
    }
`;

const LikeButton = ({ id, likesCount }) => {
    const [likeStatus, setLikeStatus] = useState(null);
    const [count, setCount] = useState(likesCount);

    useEffect(() => {
        const fetchLikeStatus = async () => {
            try {
                const response = await axios.get(`${process.env.REACT_APP_BASEURL}/board/${id}/checkliked`, { headers: { Authorization: localStorage.getItem('accesstoken') } });
                setLikeStatus(response.data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchLikeStatus();
    }, [id]);

    const heart = (id) => {
        axios.get(`${process.env.REACT_APP_BASEURL}/board/${id}/like`, { headers: { Authorization: localStorage.getItem('accesstoken') } });
        setLikeStatus(!likeStatus);
        setCount(count + (likeStatus ? -1 : 1));
    }

    if (likeStatus === null) {
        return null;
    }

    return (
        <span>
            {likeStatus ? (
                <FaHeart style={{ width: '2rem', display: 'inline-block', color: '#9933ff' }} onClick={() => heart(id)} />
            ) : (
                <FiHeart style={{ width: '2rem', display: 'inline-block', color: '#9933ff' }} onClick={() => heart(id)} />
            )}
            <span>{count}</span>
        </span>
    );
};

const Accordian = (props) => {
    let list = props.list;
    const [index, setIndex] = useState(-1);
    const role = localStorage.getItem('roles');
    const manager = role === "[\"ROLE_MANAGER\"]" || role === "[\"ROLE_ADMIN\"]";
    const admin = manager && role === "[\"ROLE_ADMIN\"]";

    const accordian = list.map((item, idx) => 
        <Div key={'board' + item.id} onClick={() => setIndex(idx)}>
            <span style={{width: '2.5rem', display: 'inline-block'}}>{item.id}</span>
            <span style={{width: '10rem', display: 'inline-block'}}>{item.title}</span>
            <span style={{width: '8rem', display: 'inline-block'}}>{item.author}</span>
            {manager && <Link to={`/edit/${item.id}`}><FiEdit style={{width: '2rem', display: 'inline-block', color: '#3366ff'}} /></Link>}
            {admin && <Link to={`/delete/${item.id}`}><RiDeleteBin6Line style={{width: '2rem', display: 'inline-block', color: '#ff3366'}} /></Link>}
            <LikeButton id={item.id} likesCount={item.likesCount} />
            {index === idx && <div style={{marginLeft: '2.5rem', color: 'black', width: '24rem'}}><div>{item.content}</div>
                {item.image != null && <img src={`${process.env.REACT_APP_BASEURL}/display?fileName=${item.image}`} alt='게시글 이미지' style={{maxWidth: '24rem'}}></img>}
            </div>}
        </Div>
    );
    return (
        <div>{accordian}</div>
    )
}

export default Accordian;