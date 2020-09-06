package wooteco.subway.maps.map.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.maps.station.dto.StationResponse;
import wooteco.subway.members.member.domain.LoginMember;

public class PathResponseAssembler {
    private static final int DEFAULT_FARE = 1250;

    public static PathResponse assemble(SubwayPath subwayPath, Map<Long, Station> stations, int extraFareByLine, LoginMember member) {
        List<StationResponse> stationResponses = subwayPath.extractStationId().stream()
                .map(it -> StationResponse.of(stations.get(it)))
                .collect(Collectors.toList());

        int distance = subwayPath.calculateDistance();
        int fare = subwayPath.calculateExtraFareByDistance() + extraFareByLine + DEFAULT_FARE;

        if (member.isDummy()) {
            return new PathResponse(stationResponses, subwayPath.calculateDuration(), distance, fare);
        }

        return new PathResponse(stationResponses, subwayPath.calculateDuration(), distance, member.discountFare(fare));
    }
}
