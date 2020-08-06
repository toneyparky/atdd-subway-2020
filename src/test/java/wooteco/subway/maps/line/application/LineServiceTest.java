package wooteco.subway.maps.line.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wooteco.subway.common.TestObjectUtils;
import wooteco.subway.maps.line.domain.ExtraFare;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineRepository;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.line.dto.LineResponse;
import wooteco.subway.maps.station.application.StationService;
import wooteco.subway.maps.station.domain.Station;

public class LineServiceTest {
    private Map<Long, Station> stations;
    private Line line;

    @BeforeEach
    void setUp() {
        Station station1 = TestObjectUtils.createStation(1L, "강남역");
        Station station2 = TestObjectUtils.createStation(2L, "역삼역");
        stations = Lists.newArrayList(station1, station2).stream()
                .collect(Collectors.toMap(Station::getId, Function.identity()));

        line = TestObjectUtils.createLine(1L, "신분당선", "RED", new ExtraFare(500));
        line.addLineStation(new LineStation(1L, null, 10, 10));
        line.addLineStation(new LineStation(2L, 1L, 10, 10));
    }

    @Test
    void findLineResponsesById() {
        LineRepository lineRepository = mock(LineRepository.class);
        StationService stationService = mock(StationService.class);
        LineService lineService = new LineService(lineRepository, stationService);

        when(lineRepository.findById(anyLong())).thenReturn(Optional.of(line));
        when(stationService.findStationsByIds(anyList())).thenReturn(stations);

        LineResponse result = lineService.findLineResponsesById(line.getId());

        assertThat(result.getStations()).hasSize(2);
    }

    @DisplayName("노선에 따른 최대 추가 운임을 반환한다.")
    @Test
    void getMaxExtraFare() {
        LineRepository lineRepository = mock(LineRepository.class);
        StationService stationService = mock(StationService.class);
        LineService lineService = new LineService(lineRepository, stationService);

        List<Line> lines = Arrays.asList(
            new Line("신분당선", "red lighten-1", LocalTime.now(), LocalTime.now(), 10, new ExtraFare(500)),
            new Line("3호선", "orange darken-1", LocalTime.now(), LocalTime.now(), 10, new ExtraFare(900))
        );

        when(lineRepository.findAllById(anySet())).thenReturn(lines);

        assertThat(lineService.getMaxExtraFare(anySet())).isEqualTo(900);
    }
}
