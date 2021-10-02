import {readFileSync} from 'fs';

export interface Coordinates {
    lat: number,
    lon: number
}

export interface COVIDData {
    country: string,
    coords: Coordinates,
    deaths: number,
    confirmed: number,
    recovered: number
}

function parseData(data: string[]): COVIDData[] {
    let parsedData: COVIDData[] = [];

    for (let line of data) {
        let sline = line.split(',');
        let country = sline[3];
        let lat = Number(sline[5]);
        let lon = Number(sline[6]);
        let confirmed = Number(sline[7]);
        let deaths = Number(sline[8]);
        let recovered = Number(sline[9]);
        parsedData.push({country, coords: {lat, lon}, confirmed, deaths, recovered});

    }

    return parsedData;
}

export function slurpAndParseFile(fname: string): COVIDData[] {
    let data = readFileSync(fname, {encoding: 'utf-8'});
    // Splice 1 to skip header
    let lines = data.split('\n').splice(1);
    return parseData(lines);
}


