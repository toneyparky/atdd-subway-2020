package wooteco.subway.maps.line.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExtraFareTest {

	@DisplayName("음수로 객체를 생성시 예외처리한다.")
	@Test
	void throwException_whenNegativeInput() {
		assertThatThrownBy(() -> new ExtraFare(-1))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("추가 운임은 0보다 작을 수 없습니다.");
	}
}