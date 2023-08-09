import styled from 'styled-components';
import colors from '../colors';

const color2 = ['#099fd1', '#e054db', '#3b77be', '#d89125'];
const btnW = ['1.7%', '1.6%', '2.2%', '3%'];
const btnH = ['1.7vmax', '1.6vmax', '1.628vmax', '1.62vmax'];
const btnMar = ['15.9vmax 0 13vmax', '17.7vmax 0 12.5vmax', '13.25vmax 0 8.1vmax', '15.8vmax 0 13vmax'];
const bigW = ['2%', '2.1%', '2.75%', '3.8%'];
const bigH = ['2vmax', '2.1vmax', '2.035vmax', '2.052vmax'];
const bigMar = ['15.8vmax 0 13vmax', '17.5vmax 0 12.5vmax', '13.05vmax 0 7.4vmax', '15.55vmax 0 12.8vmax'];
const rowW = ['98.4%', '98.7%', '96.6%', '95.1%'];
const rowMar = ['0 0.8%', '0 0.7% 0 0.6%', '0 2% 0 1.4%', '0 2.4% 0 2.5%'];
const mapUrl = ['./img/line1.png', './img/line2.png', './img/line3.png', './img/line4.png'];
const mapMar = ['0', '0', '0 13%', '0 23%'];

const StationBtnSt = styled.button`
    width: ${props => btnW[props.$line]};
    height: ${props => btnH[props.$line]};
    border: 0.15vmax solid ${props => colors[props.$line]};
    border-radius: 50%;
    background-color: transparent;
    margin: ${props => btnMar[props.$line]};
    padding: 0;
    &:hover {
        background-color: ${props => colors[props.$line]};
        box-shadow: 0 0 0.2vmax 0.2vmax ${props => color2[props.$line]};
    }
`;

const BigBtnSt = styled(StationBtnSt)`
    width: ${props => bigW[props.$line]};
    height: ${props => bigH[props.$line]};
    margin: ${props => bigMar[props.$line]};
`;

const BtnRowSt = styled.div`
    display: flex;
    justify-content: space-between;
    flex-wrap: nowrap;
    width: ${props => rowW[props.$line]};
    margin: ${props => rowMar[props.$line]};
`;

const MapDivSt = styled.div`
    background-image: url(${props => mapUrl[props.$line]});
    background-size: contain;
    margin: ${props => mapMar[props.$line]};
`;

const StationButton = (props) => {
    return props.big ?
    <BigBtnSt $line={props.line} onClick={props.onClick} />
    : <StationBtnSt $line={props.line} onClick={props.onClick} />;
};

const ButtonRow = (props) => {
    const select = props.onClick;
    const line = props.line;
    let lineBtns = [];
    let buttons = [];
    for (let i = 0; i < 40; i++)
        buttons.push(<StationButton big={((i >= 9 && i <= 11) || i === 15)}
        line={line} key={134 - i} onClick={() => select(134 - i)} />);
    lineBtns.push(buttons);
    buttons = [];
    for (let i = 0; i < 43; i++)
        buttons.push(<StationButton big={(i === 4 || i === 7 || i === 18 || i === 26 || i === 32)}
        line={line} key={201 + i} onClick={() => select(201 + i)} />);
    lineBtns.push(buttons);
    buttons = [];
    for (let i = 0; i < 17; i++)
        buttons.push(<StationButton big={(i % 4 === 0 || i === 5)}
        line={line} key={301 + i} onClick={() => select(301 + i)} />);
    lineBtns.push(buttons);
    buttons = [];
    for (let i = 0; i < 14; i++)
        buttons.push(<StationButton big={(i >= 12)}
        line={line} key={414 - i} onClick={() => select(414 - i)} />);
    lineBtns.push(buttons);
    return <BtnRowSt $line={line}>{lineBtns[line]}</BtnRowSt>;
}

const MapDiv = (props) => {
    return <MapDivSt $line={props.line}>
        <ButtonRow line={props.line} onClick={props.onClick} />
    </MapDivSt>
}

export default MapDiv;