package wooteco.subway.maps.line.domain;

import java.util.List;

public class Lines {
	private static final int SINGLE_ELEMENT = 1;
	private List<Line> lines;

	public Lines(List<Line> lines) {
		this.lines = lines;
	}

	public int getMaxExtraFare() {
		if (lines.size() == SINGLE_ELEMENT) {
			return lines.get(0).getExtraFare().getExtraFare();
		}

		return lines.stream()
			.map(line -> line.getExtraFare().getExtraFare())
			.max(Integer::compare).orElseThrow(() -> new IllegalStateException("노선의 추가운임 정보가 없습니다."));
	}
}
