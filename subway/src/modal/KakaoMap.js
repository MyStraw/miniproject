import React from 'react'
import { useEffect } from 'react'
const { kakao } = window

const KakaoMap = (props) => {
    const addr = props.addr;
    useEffect(() => {
        var mapContainer = document.getElementById('station-map'),
            mapOption = {
                center: new kakao.maps.LatLng(35.2296168581211, 129.089342280899),
                level: 3
            };

        var map = new kakao.maps.Map(mapContainer, mapOption);
        var geocoder = new kakao.maps.services.Geocoder();

        geocoder.addressSearch(addr, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                new kakao.maps.Marker({
                    map: map,
                    position: coords
                });
                map.setCenter(coords);
            }
        });
    }, [addr]);

    return (
        <div id='station-map' style={{
            width: '25vmax',
            height: '25vmax',
            marginLeft: '1vmax',
            border: '0.15vmax solid black',
            borderRadius: '0.5rem'
        }}></div>
    )
}

export default KakaoMap