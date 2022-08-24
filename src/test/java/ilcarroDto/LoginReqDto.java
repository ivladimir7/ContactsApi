package ilcarroDto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder


public class LoginReqDto {
    String email;
    String password;
}