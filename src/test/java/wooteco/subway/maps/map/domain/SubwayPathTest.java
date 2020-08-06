package wooteco.subway.maps.map.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import wooteco.subway.common.TestObjectUtils;
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.station.domain.Station;

class SubwayPathTest {
	private Map<Long, Station> stations;

	private SubwayPath subwayPath;

	private List<LineStationEdge> lineStationsNoCharging;
	private List<LineStationEdge> lineStationsLittleCharging;
	private List<LineStationEdge> lineStationsMoreCharging;
	@BeforeEach
	void setUp() {
		stations = new HashMap<>();
		stations.put(1L, TestObjectUtils.createStation(1L, "교대역"));
		stations.put(2L, TestObjectUtils.createStation(2L, "강남역"));
		stations.put(3L, TestObjectUtils.createStation(3L, "양재역"));
		stations.put(4L, TestObjectUtils.createStation(4L, "남부터미널역"));
		stations.put(5L, TestObjectUtils.createStation(5L, "토니역"));
		stations.put(6L, TestObjectUtils.createStation(6L, "브라운역"));

		Line line3 = TestObjectUtils.createLine(3L, "3호선", "ORANGE");
		line3.addLineStation(new LineStation(1L, null, 0, 0));
		LineStation lineStation6 = new LineStation(4L, 1L, 1, 2);
		LineStation lineStation7 = new LineStation(3L, 4L, 2, 2);
		LineStation lineStation8 = new LineStation(5L, 3L, 40, 30);
		LineStation lineStation9 = new LineStation(6L, 5L, 40, 30);
		line3.addLineStation(lineStation6);
		line3.addLineStation(lineStation7);
		line3.addLineStation(lineStation8);
		line3.addLineStation(lineStation9);

		lineStationsNoCharging = Lists.newArrayList(
			new LineStationEdge(lineStation6, line3.getId()),
			new LineStationEdge(lineStation7, line3.getId())
		);

		lineStationsLittleCharging = Lists.newArrayList(
			new LineStationEdge(lineStation6, line3.getId()),
			new LineStationEdge(lineStation7, line3.getId()),
			new LineStationEdge(lineStation8, line3.getId())
		);

		lineStationsMoreCharging = Lists.newArrayList(
			new LineStationEdge(lineStation6, line3.getId()),
			new LineStationEdge(lineStation7, line3.getId()),
			new LineStationEdge(lineStation8, line3.getId()),
			new LineStationEdge(lineStation9, line3.getId())
		);
	}

	@DisplayName("거리에 따른 추가요금을 구한다. - 10km 이하")
	@Test
	void calculateExtraFareByDistance() {
		subwayPath = new SubwayPath(lineStationsNoCharging);
		assertThat(subwayPath.calculateExtraFareByDistance()).isEqualTo(0);
	}

	@DisplayName("거리에 따른 추가요금을 구한다. - 10km 초과 50km 이하")
	@Test
	void calculateExtraFareByDistance_littleCharging() {
		subwayPath = new SubwayPath(lineStationsLittleCharging);
		assertThat(subwayPath.calculateExtraFareByDistance()).isEqualTo(700);
	}

	@DisplayName("거리에 따른 추가요금을 구한다. - 50km 초과")
	@Test
	void calculateExtraFareByDistance_moreCharging() {
		subwayPath = new SubwayPath(lineStationsMoreCharging);
		assertThat(subwayPath.calculateExtraFareByDistance()).isEqualTo(1300);
	}
}