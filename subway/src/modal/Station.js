import React from 'react';
import axios from 'axios';
import { useEffect } from 'react';
import { useState } from 'react';
import styled from 'styled-components';
import modalst from './Modal.module.css';
import KakaoMap from './KakaoMap';
import Board from './Board';

const ColorSpan = styled.span`
    font-weight: bold;
    color: ${props => props.color};
`;
const SpanBtn = styled.span`
    &:hover {
        font-weight: bold;
        color: ${props => props.color};
    }
`;

const Station = (props) => {
    const code = props.code;
    const color = props.color;
    const [infotag, setInfo] = useState(<></>);
    const [show, toggle] = useState(false);
    useEffect(() => {
        axios.get(`${process.env.REACT_APP_BASEURL}/station/${code}`).then(response => {
            const info = response.data[0];
            setInfo(<div style={{display: 'flex'}}>
                <div style={{width: '45vmax'}}>
                    <div><ColorSpan color={color}>{info.line_num}</ColorSpan><span className={modalst.blank}></span>
                    <span>{info.stationcode} <ColorSpan color={color}>{info.stationname_plus}</ColorSpan>({info.english_name})</span></div>
                    <div><strong>전화번호</strong>: {info.tel}</div>
                    <div><strong>주소</strong>: {info.address}</div>
                    <SpanBtn color={color} onClick={() => toggle(!show)}>유래</SpanBtn>
                    {show ? <span>: {info.history}</span> : <span> ← Click!</span>}
                    <Board code={code} />
                </div>
                <KakaoMap addr={info.address} />
            </div>);
        }).catch(error => console.log(error));
    }, [code, color, show]);
    return (
        <div className={modalst.vspace}>
            <strong className={modalst.large}>역 정보</strong>
            {infotag}
        </div>
    );
}

export default Station