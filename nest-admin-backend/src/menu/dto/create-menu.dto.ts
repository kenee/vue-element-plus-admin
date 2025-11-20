import { ApiProperty } from '@nestjs/swagger'

export class CreateMenuDto {
  @ApiProperty({ example: '0', description: 'The parent ID of the menu' })
  parentId?: string

  @ApiProperty({ example: '/dashboard', description: 'The path of the menu' })
  path?: string

  @ApiProperty({ example: 'Layout', description: 'The component of the menu' })
  component?: string

  @ApiProperty({ example: '/dashboard/analysis', description: 'The redirect path of the menu' })
  redirect?: string

  @ApiProperty({ example: 'Dashboard', description: 'The title of the menu' })
  title: string

  @ApiProperty({ example: 'Dashboard', description: 'The name of the menu (for route)' })
  name?: string

  @ApiProperty({ example: 'dashboard', description: 'The icon of the menu' })
  icon?: string

  @ApiProperty({
    example: { title: 'Dashboard', icon: 'dashboard', alwaysShow: true },
    description: 'Menu meta information',
    required: false
  })
  meta?: Record<string, any>

  @ApiProperty({
    example: 1,
    description: 'The type of the menu (0: Directory, 1: Menu, 2: Button)'
  })
  type?: number

  @ApiProperty({ example: 'dashboard:list', description: 'The permission identifier' })
  permission?: string

  @ApiProperty({ example: 1, description: 'The sort order' })
  sort?: number

  @ApiProperty({ example: 1, description: 'The status of the menu (0: disabled, 1: enabled)' })
  status?: number
}
