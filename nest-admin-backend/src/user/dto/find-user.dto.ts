import { ApiPropertyOptional } from '@nestjs/swagger'

export class FindUserDto {
  @ApiPropertyOptional({ description: 'Page number', default: 1 })
  page?: number

  @ApiPropertyOptional({ description: 'Page size', default: 10 })
  pageSize?: number

  @ApiPropertyOptional({ description: 'Department ID' })
  deptId?: string

  @ApiPropertyOptional({ description: 'Username (Account)' })
  username?: string

  @ApiPropertyOptional({ description: 'Nickname' })
  nickname?: string
}
