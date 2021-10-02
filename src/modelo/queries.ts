import {COVIDData} from './data';
import {currentLocation} from './geolocation';

async function getAllFromCurrentLocation(data: COVIDData[]): Promise<COVIDData[]> {
    let {lat, lon} = await currentLocation();
    // TODO: add logic to determine whether an area is "close"
    return data.filter(d => d.coords.lat === lat && d.coords.lon === lon);
}