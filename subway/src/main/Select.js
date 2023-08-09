import mainst from './Main.module.css';
import { useState, useEffect } from 'react';
import styled from 'styled-components';
import colors from '../colors';
import LineMap from './LineMap';

const commonButtonStyles = `
    margin: 0.6rem;
    border-radius: 0.6rem;
    color: white;
    border: none;
    padding: 0 1rem;
    width: fit-content;
    height: 2.6rem;
`;
const LineButton = styled.button`
    ${commonButtonStyles}
    background-color: ${props => colors[props.$line]};
    border: ${props => props.$sel ? '0.2rem solid #33333366' : 'none'};
`;
const EndButton = styled.button`
    ${commonButtonStyles}
    background-color: ${props => colors[props.$line]};
    border: ${props => props.$sel ? '0.2rem solid #33333366' : 'none'};
    height: 2.1rem;
    &:hover {
        font-weight: bold;
        background-color: white;
        color: ${props => colors[props.$line]};
        border: 0.2rem solid ${props => colors[props.$line]};
    }
`;

const Select = () => {
    const [time, setTime] = useState(new Date());
    setInterval(() => setTime(new Date()), 10000);
    const getDayType = (weekday) => {
        return weekday === 0 ? '일요일:3' : weekday === 6 ? '토요일:2' : '평일:1';
    }
    const timeformat = (time) => {
        const hour = time.getHours();
        const minute = time.getMinutes();
        let timestr = "";
        if (hour < 10) timestr += '0';
        timestr += hour;
        timestr += ":";
        if (minute < 10) timestr += '0';
        timestr += minute;
        return timestr;
    }

    const [line, setline] = useState(-1);
    const lines = ['1호선', '2호선', '3호선', '4호선'];
    const linebtns = lines.map((item, idx) =>
        <LineButton key={lines[idx]} $line={idx} onClick={() => setline(idx)}
            className={mainst.large} $sel={idx === line ? "true" : undefined}>
            {item}
        </LineButton>
    );
    const [endidx, setend] = useState(-1);
    const end = [['노포:134', '신평:101', '다대포해수욕장:095'], ['장산:201', '광안:209', '전포:218', '호포:239', '양산:243'],
        ['수영:301', '대저:317'], ['안평:414', '미남:401']];
    const endbtns = end.map((lineend, lineidx) => lineend.map((item, idx) =>
        <EndButton key={item} $line={lineidx} onClick={() => setend(idx)}
            className={mainst.normal} $sel={idx === endidx ? "true" : undefined}>
            {item.split(':')[0]}
        </EndButton>)
    );
    const [holiday, toggleholiday] = useState(false);
    const totalmap = <img src='img/종합노선도(Metro Line Map).png' alt='종합노선도'></img>

    useEffect(() => {
        setend(-1);
    }, [line]);

    return (
        <>
            <div><strong className={mainst.left + ' ' + mainst.xlarge}>출발역과 방향을 선택하세요</strong>{linebtns}</div>
            <div className={mainst.up}>
                <span className={mainst.large + ' ' + mainst.left}><strong>{getDayType(time.getDay()).split(':')[0]} {timeformat(time)}</strong>
                <span className={mainst.blank}></span><span className={mainst.normal}>공휴일</span>
                <input type='checkbox' onChange={() => toggleholiday(!holiday)} checked={holiday} /></span>
                {line !== -1 && endbtns[line]}
            </div>
            {line === -1 ? totalmap : endidx !== -1 && <LineMap line={line} day={holiday ? '공휴일:4' : getDayType(time.getDay())} end={end[line][endidx]} />}
        </>
    );
}

export default Select;