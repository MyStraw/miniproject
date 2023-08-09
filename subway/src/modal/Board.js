import React, { useEffect, useState } from 'react'
import axios from 'axios'
import style from './Modal.module.css'
import Accordian from './Accordian'
import { Link } from 'react-router-dom'

const Board = (props) => {
    const code = props.code;
    const [list, setList] = useState([]);
    useEffect(() => {
        axios.get(`${process.env.REACT_APP_BASEURL}/board/list/${code}`).then((response) => {
            setList(response.data);
        }).catch((error) => console.log(error));
    }, [code]);
    return (
        <>
            <div className={style.vspace} style={{fontWeight: 'bold', marginBottom: '1%'}}>
                게시판
                {localStorage.getItem('userid') != null && <><span style={{marginRight: '18rem'}}></span>
                    <Link to={`/write/${code}`} style={{
                        textDecoration: 'none'
                    }}>글쓰기</Link>
                </>}
            </div>
            <div style={{fontWeight: 'bold'}}>
                <span style={{width: '2.5rem', display: 'inline-block'}}></span>
                <span style={{width: '10rem', display: 'inline-block'}}>제목</span>
                <span style={{width: '8rem', display: 'inline-block'}}>작성자</span>
            </div>
            <Accordian list={list} />
        </>
    )
}

export default Board