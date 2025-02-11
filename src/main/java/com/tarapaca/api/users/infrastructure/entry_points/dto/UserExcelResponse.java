package com.tarapaca.api.users.infrastructure.entry_points.dto;

import com.tarapaca.api.users.infrastructure.driven_adapters.jpa_repository.user.UserData;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserExcelResponse {
    @Builder.Default
    Set<UserData> users = new HashSet<>();
    @Builder.Default
    Set<String> errors = new HashSet<>();
}
