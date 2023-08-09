import React, { useEffect, useState } from 'react'
import axios from 'axios'
import style from './Modal.module.css'
import Accordian from './Accordian'
import { Link } from 'react-router-dom'

const Board = (props) => {
    const code = props.code;
    const [list, setList] = useState([]);
    useEffect(() => {
        axios.get(`http://10.125.121.185:8080/board/list/${code}`).then((response) => {
            setList(response.data);
        }).catch((error) => console.log(error));
    }, [code]);
    return (
        <>
            <div className={style.vspace} style={{fontWeight: 'bold', marginBottom: '2%'}}>
                게시판
                {localStorage.getItem('userid') != null && <><span style={{marginRight: '14rem'}}></span>
                    <Link to={`/write/${code}`} style={{
                        borderRadius: '0.4rem'
                    }}>글쓰기</Link>
                </>}
            </div>
            <Accordian list={list} />
        </>
    )
}

export default Board