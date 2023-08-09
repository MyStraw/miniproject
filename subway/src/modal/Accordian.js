import React from 'react';
import { useState } from 'react';
import styled from 'styled-components';
import { FiEdit } from 'react-icons/fi';
import { RiDeleteBin6Line } from 'react-icons/ri';
import { Link } from 'react-router-dom';

const Div = styled.div`
    &:hover {
        color: #6600cc;
        font-weight: bold;
    }
`;

const Accordian = (props) => {
    let list = props.list;
    const [index, setIndex] = useState(-1);
    const accordian = list.map((item, idx) => 
        <Div key={'board' + item.id} onClick={() => setIndex(idx)}>
            <span style={{width: '2.5rem', display: 'inline-block'}}>{item.id}</span>
            <span style={{width: '10rem', display: 'inline-block'}}>{item.title}</span>
            <span style={{width: '8rem', display: 'inline-block'}}>{item.author}</span>
            <Link to={`/edit/${item.id}`}><FiEdit style={{width: '2rem', display: 'inline-block'}} /></Link>
            <Link to={`/delete/${item.id}`}><RiDeleteBin6Line style={{width: '2rem', display: 'inline-block'}} /></Link>
            {index === idx && <div style={{marginLeft: '2.5rem', color: 'black'}}>{item.content}
                {item.image != null && <img src={`http://10.125.121.185:8080/display?fileName=${item.image}`} alt='게시글 이미지' style={{width: '25%'}}></img>}
            </div>}
        </Div>
    );
    return (
        <>{accordian}</>
    )
}

export default Accordian;