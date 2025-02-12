package com.elogix.api.authentication.infrastructure.repository.token;

import java.util.Arrays;
import java.util.Objects;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import com.elogix.api.authentication.domain.model.ETokenType;
import com.elogix.api.generics.infrastructure.repository.GenericEntity.GenericEntityData;
import com.elogix.api.users.infrastructure.driven_adapters.jpa_repository.user.UserData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tokens")
@SQLDelete(sql = "UPDATE tokens SET is_deleted = true WHERE id=?")
@FilterDef(name = "deletedTokenFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedTokenFilter", condition = "is_deleted = :isDeleted")
@ToString
public class TokenData extends GenericEntityData {
    @Column(name = "token", nullable = false)
    private byte[] token;

    @Column(name = "refresh_token", nullable = false)
    private byte[] refreshToken;

    @Enumerated(EnumType.STRING)
    private ETokenType type;

    @Builder.Default
    @Column(name = "is_revoked", columnDefinition = "boolean DEFAULT false")
    private boolean isRevoked = false;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserData user;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TokenData tokenData = (TokenData) o;
        return isRevoked == tokenData.isRevoked &&
                Arrays.equals(token, tokenData.token) &&
                Arrays.equals(refreshToken, tokenData.refreshToken) &&
                type == tokenData.type &&
                Objects.equals(user, tokenData.user);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(type, isRevoked, user);
        result = 31 * result + Arrays.hashCode(token);
        result = 31 * result + Arrays.hashCode(refreshToken);
        return result;
    }

    public TokenData() {
        super();
    }
}
