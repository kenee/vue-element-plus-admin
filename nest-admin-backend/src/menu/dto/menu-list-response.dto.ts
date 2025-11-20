import { ApiProperty } from '@nestjs/swagger'

export class PermissionItemDto {
  @ApiProperty({ description: 'Permission ID' })
  id: number

  @ApiProperty({ description: 'Permission label' })
  label: string

  @ApiProperty({ description: 'Permission value' })
  value: string
}

export class MenuMetaDto {
  @ApiProperty({ description: 'Menu title', required: false })
  title?: string

  @ApiProperty({ description: 'Menu icon', required: false })
  icon?: string

  @ApiProperty({ description: 'Always show', required: false })
  alwaysShow?: boolean

  @ApiProperty({ description: 'No cache', required: false })
  noCache?: boolean

  @ApiProperty({ description: 'Permissions', type: [String], required: false })
  permission?: string[];

  // 索引签名不能使用装饰器，使用 Record 类型代替
  [key: string]: any
}

export class MenuItemDto {
  @ApiProperty({ description: 'Menu ID' })
  id: string

  @ApiProperty({ description: 'Menu path' })
  path: string

  @ApiProperty({ description: 'Component path', required: false })
  component?: string

  @ApiProperty({ description: 'Route name' })
  name: string

  @ApiProperty({ description: 'Menu title' })
  title: string

  @ApiProperty({ description: 'Menu type (0: Directory, 1: Menu, 2: Button)' })
  type: number

  @ApiProperty({ description: 'Parent menu ID', required: false })
  parentId?: string

  @ApiProperty({ description: 'Menu status (0: disabled, 1: enabled)' })
  status: number

  @ApiProperty({ description: 'Menu meta', type: MenuMetaDto, required: false })
  meta?: MenuMetaDto

  @ApiProperty({ description: 'Permission list', type: [PermissionItemDto], required: false })
  permissionList?: PermissionItemDto[]

  @ApiProperty({ description: 'Redirect path', required: false })
  redirect?: string

  @ApiProperty({ description: 'Child menus', type: [MenuItemDto], required: false })
  children?: MenuItemDto[]
}

export class MenuListResponseDto {
  @ApiProperty({ description: 'Menu list', type: [MenuItemDto] })
  list: MenuItemDto[]
}
