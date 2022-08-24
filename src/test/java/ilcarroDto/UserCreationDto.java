package ilcarroDto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class UserCreationDto{

    String first_name;
    String second_name;
    String order_id;
}