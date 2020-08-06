package wooteco.subway.maps.map.domain;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SubwayPath {
    private static final int NON_CHARGE_THRESHOLD = 10;
    private static final int LITTLE_CHARGE_THRESHOLD = 50;
    private static final int No_CHARGE = 0;
    private List<LineStationEdge> lineStationEdges;

    public SubwayPath(List<LineStationEdge> lineStationEdges) {
        this.lineStationEdges = lineStationEdges;
    }

    public List<LineStationEdge> getLineStationEdges() {
        return lineStationEdges;
    }

    public List<Long> extractStationId() {
        List<Long> stationIds = Lists.newArrayList(lineStationEdges.get(0).getLineStation().getPreStationId());
        stationIds.addAll(lineStationEdges.stream()
                .map(it -> it.getLineStation().getStationId())
                .collect(Collectors.toList()));

        return stationIds;
    }

    public Set<Long> extractLineId() {
        return lineStationEdges.stream()
            .map(LineStationEdge::getLineId)
            .collect(Collectors.toSet());
    }

    public int calculateDuration() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDuration()).sum();
    }

    public int calculateDistance() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDistance()).sum();
    }

    public int calculateExtraFareByDistance() {
        int distance = calculateDistance();

        if (distance <= NON_CHARGE_THRESHOLD) {
            return No_CHARGE;
        }

        if (distance <= LITTLE_CHARGE_THRESHOLD) {
            return (int) ((Math.ceil(((distance - NON_CHARGE_THRESHOLD) - 1) / 5) + 1) * 100);
        }

        return (int) ((Math.ceil(((distance - LITTLE_CHARGE_THRESHOLD) - 1) / 8) + 1) * 100) + 800;
    }
}
