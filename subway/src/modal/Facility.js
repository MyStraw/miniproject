import React from 'react'
import axios from 'axios'
import { useEffect, useState } from 'react'
import style from './Facility.module.css'
import modalst from './Modal.module.css'

const Facility = (props) => {
    const code = props.code;
    const [infotag, setInfo] = useState(<></>);
    const isAvailable = (avail) => {
        return avail ? 'O' : 'X';
    }
    useEffect(() => {
        axios.get(`http://10.125.121.185:8080/facilities/${code}`).then(reponse => {
            const info = reponse.data[0];
            let names1 = ["외부 경사로", "시각장애인 유도로", "휠체어 리프트", "도시철도 경찰대", "비상 인터폰"];
            let names2 = ["외부 엘리베이터", "내부 엘리베이터", "에스컬레이터", "수유실", "만남의 광장"];
            let names3 = ["물품 보관함", "자전거 보관소", "환승 주차장", "무인 민원 발급기", "자동 사진기"];
            let facil1 = [info.external_ramp, info.blind_way, info.wheel_lift, info.police, info.emergency_phone];
            let facil2 = [info.elevator_out, info.elevator_inside, info.escalator, info.baby_milk, info.meet_place];
            let facil3 = [info.locker, info.cycle_locker, info.transfer_parking, info.doc_machine, info.photo];
            setInfo(<div>
                <span>승강장: {info.above_under}</span> <span>{info.floor}층</span>{info.connector ? <span>, 연결</span> : <></>}
                <table><tbody>
                    {<tr>{names1.map(item => <td key={item} className={style.vs}>{item}</td>)}</tr>}
                    {<tr>{facil1.map((item, idx) => <td key={'facil' + idx} className={modalst.center}>{isAvailable(item)}</td>)}</tr>}
                    {<tr>{names2.map(item => <td key={item} className={style.vs}>{item}</td>)}</tr>}
                    {<tr>{facil2.map((item, idx) => <td key={'facil' + idx} className={modalst.center}>{isAvailable(item)}</td>)}</tr>}
                    {<tr>{names3.map(item => <td key={item} className={style.vs}>{item}</td>)}</tr>}
                    {<tr>{facil3.map((item, idx) => <td key={'facil' + idx} className={modalst.center}>{isAvailable(item)}</td>)}</tr>}
                </tbody></table>
            </div>);
        }).catch(error => console.log(error));
    }, [code]);
    return (
        <div className={modalst.vspace}>
            <strong className={modalst.large}>편의시설 정보</strong>
            {infotag}
        </div>
    )
}

export default Facility