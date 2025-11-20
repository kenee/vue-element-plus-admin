import { ApiProperty } from '@nestjs/swagger'
import { MenuItemDto } from '../../menu/dto/menu-list-response.dto'

export class RoleItemDto {
  @ApiProperty({ description: 'Role ID' })
  id: string

  @ApiProperty({ description: 'Role name' })
  roleName: string

  @ApiProperty({ description: 'Role value (alias for roleValue)' })
  role: string

  @ApiProperty({ description: 'Role status (0: disabled, 1: enabled)' })
  status: number

  @ApiProperty({ description: 'Role remark', required: false })
  remark?: string

  @ApiProperty({ description: 'Create time' })
  createTime: string

  @ApiProperty({ description: 'Associated menus', type: [MenuItemDto], required: false })
  menu?: MenuItemDto[]
}

export class RoleListResponseDto {
  @ApiProperty({ description: 'Role list', type: [RoleItemDto] })
  list: RoleItemDto[]

  @ApiProperty({ description: 'Total number of roles' })
  total: number
}
