package wooteco.subway.maps.line.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ExtraFare {
	private int extraFare;

	public ExtraFare() {
	}

	public ExtraFare(Integer extraFare) {
		if (extraFare == null) {
			this.extraFare = 0;
			return;
		}

		if (extraFare < 0) {
			throw new IllegalArgumentException("추가 운임은 0보다 작을 수 없습니다.");
		}
		this.extraFare = extraFare;
	}

	public int getExtraFare() {
		return extraFare;
	}

	public void setExtraFare(int extraFare) {
		this.extraFare = extraFare;
	}
}
