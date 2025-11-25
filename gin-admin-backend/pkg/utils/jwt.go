package utils

import (
	"gin-admin-backend/internal/conf"
	"time"

	"github.com/golang-jwt/jwt/v5"
)

type Claims struct {
	Username string `json:"username"`
	ID       string `json:"id"`
	jwt.RegisteredClaims
}

func GenerateToken(id, username string) (string, error) {
	nowTime := time.Now()
	expireTime := nowTime.Add(time.Duration(conf.GlobalConfig.Jwt.Expire) * time.Second)

	claims := Claims{
		Username: username,
		ID:       id,
		RegisteredClaims: jwt.RegisteredClaims{
			ExpiresAt: jwt.NewNumericDate(expireTime),
			Issuer:    "gin-admin",
			Subject:   id, // Add Subject claim for compatibility
		},
	}

	tokenClaims := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	token, err := tokenClaims.SignedString([]byte(conf.GlobalConfig.Jwt.Secret))
	return token, err
}

func ParseToken(token string) (*Claims, error) {
	tokenClaims, err := jwt.ParseWithClaims(token, &Claims{}, func(token *jwt.Token) (interface{}, error) {
		return []byte(conf.GlobalConfig.Jwt.Secret), nil
	})

	if tokenClaims != nil {
		if claims, ok := tokenClaims.Claims.(*Claims); ok && tokenClaims.Valid {
			// If ID is empty (token from NestJS), use Subject as ID
			if claims.ID == "" {
				claims.ID = claims.Subject
			}
			return claims, nil
		}
	}

	return nil, err
}
