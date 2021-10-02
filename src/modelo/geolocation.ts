import { Coordinates } from "./data";

function getCoordinates(): Promise<GeolocationPosition> {
  return new Promise(function(resolve: PositionCallback, reject) {
    navigator.geolocation.getCurrentPosition(resolve, reject);
  });
}

export async function currentLocation(): Promise<Coordinates | null> {
    if (!('geolocation' in navigator)) {
        return null;
    }
    
    let position  = await getCoordinates();
    let lat = position.coords.latitude;
    let lon = position.coords.longitude;

    return {lat, lon};
}