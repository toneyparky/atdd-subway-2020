package wooteco.subway.maps.map.dto;

import wooteco.subway.maps.station.dto.StationResponse;

import java.util.List;

public class PathResponse {
    private List<StationResponse> stations;
    private int duration;
    private int distance;
    private int fare = 1250;

    public PathResponse() {
    }

    public PathResponse(List<StationResponse> stations, int duration, int distance) {
        this.stations = stations;
        this.duration = duration;
        this.distance = distance;
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDuration() {
        return duration;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }
}
