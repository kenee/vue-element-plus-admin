import { ExtractJwt, Strategy } from 'passport-jwt'
import { PassportStrategy } from '@nestjs/passport'
import { Injectable } from '@nestjs/common'
import { ConfigService } from '@nestjs/config'
import { Request } from 'express'

/**
 * 自定义 JWT 提取器，兼容带 Bearer 前缀和不带前缀的 token
 */
const extractJwtFromHeader = (req: Request): string | null => {
  const authHeader = req.headers.authorization

  if (!authHeader) {
    return null
  }

  // 如果包含 Bearer 前缀，提取 token
  if (authHeader.startsWith('Bearer ')) {
    return authHeader.substring(7) // 移除 "Bearer " 前缀
  }

  // 如果不包含 Bearer 前缀，直接返回整个值作为 token
  return authHeader
}

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
  constructor(private configService: ConfigService) {
    super({
      jwtFromRequest: extractJwtFromHeader,
      ignoreExpiration: false,
      secretOrKey: configService.get('JWT_SECRET') || 'secretKey'
    })
  }

  async validate(payload: any) {
    return { userId: payload.sub, username: payload.username }
  }
}
