package cotato.backend.domain.location.entity;

import java.math.BigDecimal;

import cotato.backend.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location extends BaseEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private BigDecimal latitude;

	@Column(nullable = false)
	private BigDecimal longitude;

	@Column(nullable = false, name = "is_pinned")
	private Boolean isPinned = false;

	public void togglePin() {
		isPinned = !isPinned;
	}

	@Builder
	public Location(String name, BigDecimal latitude, BigDecimal longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
