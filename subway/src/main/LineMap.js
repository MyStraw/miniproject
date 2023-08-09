import axios from 'axios';
import { useState } from 'react';
import MapDiv from './LineComponents';
import Modal from '../modal/Modal';
import Arrival from '../modal/Arrival';
import Station from '../modal/Station';
import Facility from '../modal/Facility';

const colors = ['#cf5127', '#1b9012', '#987337', '#225db8'];

const LineMap = (props) => {
    const line = props.line;
    const [arrival3, setArrival] = useState([]);
    const [code, setCode] = useState(0);
    const [open, setOpen] = useState(false);
    const select = (code) => {
        setOpen(true);
        setCode(code);
        axios.get(`${process.env.REACT_APP_BASEURL}/station/${props.day.split(':')[1]}/${props.end.split(':')[1]}/${code}`)
        .then(response => {
            let arrivals = response.data;
            arrivals.sort((a, b) => {
                const as = a.split(':');
                const bs = b.split(':');
                let ah = parseInt(as[0]);
                if (ah === 0)
                    ah = 24;
                const am = parseInt(as[1]);
                let bh = parseInt(bs[0]);
                if (bh === 0)
                    bh = 24;
                const bm = parseInt(bs[1]);
                if (ah === bh) {
                    return am - bm;
                }
                return ah - bh;
            });
            arrivals = arrivals.filter((item) => {
                const time = new Date();
                const hournow = time.getHours();
                const minutenow = time.getMinutes();
                const hourmin = item.split(':');
                let arrivehour = parseInt(hourmin[0]);
                if (arrivehour === 0)
                    arrivehour = 24;
                const arrivemin = parseInt(hourmin[1]);
                if (arrivehour === hournow) {
                    return arrivemin >= minutenow;
                } else if (arrivehour >= hournow) {
                    return true;
                }
                return false;
            });
            if (arrivals.length >= 3) {
                setArrival(arrivals.slice(0, 3));
            }
            else {
                setArrival(arrivals);
            }
        }).catch(error => console.log(error));
    }
    const info = <>{arrival3.length !== 0 && <Arrival arrival={arrival3} />}
    {code !== 0 && <Station code={code} color={colors[line]} />}{code !== 0 && <Facility code={code} />}</>;
    return (
        <div>
            <MapDiv line={line} onClick={select} />
            {open && <Modal open={setOpen} line={line} content={info} />}
        </div>
    )
}

export default LineMap;