package com.elogix.api.users.infrastructure.entry_points.dto;

import com.elogix.api.generics.infrastructure.dto.ExcelResponse;
import com.elogix.api.users.domain.model.UserModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@SuppressWarnings("unchecked")
public class UserExcelResponse extends ExcelResponse<UserModel> {
}
