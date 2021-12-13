package com.project.jwtspringsec.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredenciaisDTO {

    private String usuario;
    private String senha;
}
