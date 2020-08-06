package wooteco.subway.maps.line.application;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineRepository;
import wooteco.subway.maps.line.dto.LineRequest;
import wooteco.subway.maps.line.dto.LineResponse;
import wooteco.subway.maps.line.dto.LineStationResponse;
import wooteco.subway.maps.station.application.StationService;
import wooteco.subway.maps.station.domain.Station;
import wooteco.subway.maps.station.dto.StationResponse;

@Service
@Transactional
public class LineService {
    private LineRepository lineRepository;
    private StationService stationService;

    public LineService(LineRepository lineRepository, StationService stationService) {
        this.lineRepository = lineRepository;
        this.stationService = stationService;
    }

    public Line saveLine(LineRequest request) {
        return lineRepository.save(request.toLine());
    }

    public List<Line> findLines() {
        return lineRepository.findAll();
    }

    public Line findLineById(Long id) {
        return lineRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void updateLine(Long id, LineRequest lineUpdateRequest) {
        Line persistLine = lineRepository.findById(id).orElseThrow(RuntimeException::new);
        persistLine.update(lineUpdateRequest.toLine());
    }

    public void deleteLineById(Long id) {
        lineRepository.deleteById(id);
    }

    public List<LineResponse> findLineResponses() {
        List<Line> lines = findLines();

        return lines.stream()
                .map(line -> LineResponse.of(line))
                .collect(Collectors.toList());
    }

    public LineResponse findLineResponsesById(Long id) {
        Line line = lineRepository.findById(id).orElseThrow(RuntimeException::new);
        List<Long> stationIds = line.getStationInOrder().stream()
                .map(it -> it.getStationId())
                .collect(Collectors.toList());

        Map<Long, Station> stations = stationService.findStationsByIds(stationIds);

        List<LineStationResponse> lineStationResponses = extractLineStationResponses(line, stations);

        return LineResponse.of(line, lineStationResponses);
    }

    private List<LineStationResponse> extractLineStationResponses(Line line, Map<Long, Station> stations) {
        return line.getStationInOrder().stream()
                .map(it -> LineStationResponse.of(line.getId(), it, StationResponse.of(stations.get(it.getStationId()))))
                .collect(Collectors.toList());
    }

    public int getMaxExtraFare(Set<Long> ids) {
        List<Line> lines = lineRepository.findAllById(ids); // TODO: 2020/08/06 일급컬렉션
        if (lines.size() == 1) {
            return lines.get(0).getExtraFare().getExtraFare();
        }

        return lines.stream()
            .map(line -> line.getExtraFare().getExtraFare())
            .max(Integer::compare).orElse(-1);
    }
}
