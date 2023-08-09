import React from 'react'
import style from './Arrival.module.css'
import modalst from './Modal.module.css'

const Arrival = (props) => {
    let arrivals = props.arrival;
    arrivals = arrivals.map((item) => {
        const time = new Date();
        const arrive = item.split(':');
        let arrivehour = parseInt(arrive[0]);
        if (arrivehour === 0)
            arrivehour = 24;
        const arrivemin = parseInt(arrive[1]);
        const remain = (arrivehour - time.getHours()) * 60 + arrivemin - time.getMinutes();
        return <span key={item} className={style.large}>{`${item}(${remain}분 후)`}<span className={modalst.blank}></span></span>
    });

    return (
        <div className={modalst.center}>
            <strong className={style.xlarge}>열차 정보</strong>
            <div>{arrivals}</div>
        </div>
    )
}

export default Arrival