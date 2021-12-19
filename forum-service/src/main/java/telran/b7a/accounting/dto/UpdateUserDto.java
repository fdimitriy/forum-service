package telran.b7a.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter	
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDto {
	String firstName;
	String lastName;
}
